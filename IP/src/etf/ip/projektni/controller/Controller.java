package etf.ip.projektni.controller;

import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.mysql.cj.Session;

import etf.ip.projektni.dto.Opasnost;
import etf.ip.projektni.dto.User;
import etf.ip.projektni.dto.Vijest;
import etf.ip.projektni.dto.VremenskaPrognoza;
import etf.ip.projektni.beans.HelperBean;
import etf.ip.projektni.beans.UserBean;
import etf.ip.projektni.dao.UserDAO;
import etf.ip.projektni.dao.HelperDAO;
import etf.ip.projektni.dao.OpasnostDAO;

/**
 */
@WebServlet("/Controller")
@MultipartConfig(maxFileSize = 1024*1024*16)  
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");		
		String address = "WEB-INF/pages/login.jsp";
		System.out.println("action =  " + action);		
		HttpSession session = request.getSession();		
		
		UserBean userBean = (UserBean) session.getAttribute("registrovaniKorisnik");
		
		if ((action == null) || (action.equals("")))
		{
			session.setAttribute("notification", "");
			if (userBean != null && userBean.isLoggedIn())
				address = "WEB-INF/pages/main.jsp";
			else
				address = "WEB-INF/pages/login.jsp";
		}
		///////////////////////////////////
		//			LOGIN
		///////////////////////////////////
		else if ("login".equals(action))
		{	
			session.setAttribute("notification", "");
			
			if (userBean != null && userBean.isLoggedIn())
				address = "WEB-INF/pages/main.jsp";
			else {
				userBean = new UserBean();
				String username = request.getParameter("username");
				session.setAttribute("username", username);
				String password = request.getParameter("password");
				if(userBean.login(username, password)) {
					if(userBean.getUser().isBlocked()) {
						session.setAttribute("notification", "Vas nalog je blokiran");
						userBean.setLoggedIn(false);
					}
					else if (!userBean.getUser().isRegistrationApproved()) {
						userBean.setLoggedIn(false);
						session.setAttribute("notification", "Treba da sacekate odobrenje registracije da bi se mogli prijaviti");
					}
					else {
						address = "WEB-INF/pages/main.jsp";
						session.setAttribute("registrovaniKorisnik", userBean);
						HelperBean hb = new HelperBean();
						try {//u slucaju da servis nema vremensku prognozu za drzavu, ne ispisivati nista
						hb.getVremenskePrognoze(userBean.getUser().getState());}
						catch (Exception e ) {e.printStackTrace();}
						session.setAttribute("helperBean", hb);
						session.setAttribute("logId", UserDAO.insertLogin(username));
					}
				}
				else
					session.setAttribute("notification", "Korisnicko ime i lozinka nisu ispravni.");
			}
			
		}
		///////////////////////////////////
		//			LOG OUT
		///////////////////////////////////
		else if ("logout".equals(action))
		{
			session.invalidate();
			address = "/WEB-INF/pages/login.jsp";
		}
		/////////////////////////////////////////
		//			DRUGI EKRAN ZA REGISTRACIJU
		/////////////////////////////////////////
		else if ("register2".equals(action))
		{
			UserBean userBean2 =  new UserBean();
			session.setAttribute("notification", "");
			
			if(userBean2.isUsernameAvailable(request.getParameter("username")))
			{
				if(userBean2.isEmailAvailable(request.getParameter("email"))) {
					User user = new User(request.getParameter("username"),request.getParameter("password"),request.getParameter("firstname")
							,request.getParameter("lastname"),request.getParameter("email"));
					if(userBean2.register(user))
						{
							session.setAttribute("registrovaniKorisnik", userBean2);
							address = "WEB-INF/pages/izmjenaPodataka.jsp";
						}
					else
					{
						address = "WEB-INF/pages/error.jsp";
					}
				}
				else
					session.setAttribute("notification",  "Email je zauzet.");
			}
			else 
			{
				session.setAttribute("notification",  "Korisnicko ime je zauzeto.");
				address = "WEB-INF/pages/register.jsp";
			}
		}
		//PRVI EKRAN ZA REGISTRACIJU
		else if ("register".equals(action))
		{
			session.setAttribute("notification", "");
			address = "WEB-INF/pages/register.jsp";
		}
		//IZMJENA PODATAKA
		else if ("drugaforma".equals(action) && userBean != null && userBean.isLoggedIn()) {
			address = "WEB-INF/pages/izmjenaPodataka.jsp";
		}
		else if ("testjs".equals(action)) {
			address = "WEB-INF/pages/testjs.jsp";
		}
		else if ("izmjenaPodataka".equals(action)) {
			session.setAttribute("notification", "");
			
			userBean.getUser().setFirstName((String) request.getParameter("firstname"));
			userBean.getUser().setLastName((String) request.getParameter("lastname"));
			userBean.getUser().setState((String) request.getParameter("country"));
			userBean.getUser().setRegion((String) request.getParameter("region"));
			userBean.getUser().setCity((String) request.getParameter("city"));
			userBean.getUser().setFlag((String) request.getParameter("flag"));
			userBean.getUser().setEmailNotification((String) request.getParameter("obavjestenjaEmail"));
			userBean.getUser().setAppNotification((String) request.getParameter("obavjestenjaApp"));
			
	        
            boolean uspjesno = false;
			try {
				uspjesno = userBean.update();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            if (uspjesno) {
            	System.out.println("Uspjesan upis podataka.");
            	session.setAttribute("registrovaniKorisnik", userBean);
            	if(userBean.isLoggedIn()) {
            		address = "WEB-INF/pages/main.jsp";
            	}
            	else
            		address = "WEB-INF/pages/login.jsp";
            }
            else
            {
            	System.out.println("Greska prilikom upisa");
            	address = "WEB-INF/pages/izmjenaPodataka.jsp";
            	session.setAttribute("notification", "Greska prilikom azuriranja podataka!");
            }
	        			
		}
		else if ("promjenaProfilneSlike".equals(action) && userBean != null) {
			address = "WEB-INF/pages/promjenaProfilneSlike.jsp";
		}
		else if ("izmjeniSliku".equals(action) && userBean != null) {
			
			InputStream inputStream = null;
	         
	        Part filePart = request.getPart("file");
	        if (filePart != null) {            
	           
	            inputStream = filePart.getInputStream();
	            if (userBean.updateUserProfilePicture(userBean.getUser().getUsername(),inputStream,filePart.getSize()))
	            	{
	            	address = "WEB-INF/pages/izmjenaPodataka.jsp";
	            		
	            	}
	            else
	            {
	            	address = "WEB-INF/pages/promjenaProfilneSlike.jsp";
	            	System.out.println("SLIKA NIJE UPISANA");
	            }
	            
	        }
			
		}
		else if ("promjenaLozinke".equals(action) && userBean != null ) {
			address = "WEB-INF/pages/promjenaLozinke.jsp";			
		}
		else if ("izmjeniLozinu".equals(action)) {
			
			String lozinka = request.getParameter("password");
			if(userBean.isLoggedIn()) {
				if (UserDAO.chagnePassword(userBean.getUser().getUsername(), lozinka)) {
					address = "WEB-INF/pages/izmjenaPodataka.jsp";
				}
				else {
					address = "WEB-INF/pages/promjenaLozinke.jsp";
					session.setAttribute("notification", "Greska prilikom izmjene lozinke!");
				}
			}
		}
		else if ("noviPostForma".equals(action) && userBean != null && userBean.isLoggedIn()){
			address = "WEB-INF/pages/opasnost.jsp";
		}
		else if("testPage".equals(action)) {
			address = "WEB-INF/pages/test.jsp";			
		}
		else if("test".equals(action)) {
			System.out.println(UserDAO.getAvatarKorisnika("1"));
			address = "WEB-INF/pages/test.jsp";
		}
		else if ("objaviOpasnost".equals(action) && userBean != null && userBean.isLoggedIn()) {
			ArrayList<String> lista = new ArrayList<String>();
			String kategorije = request.getParameter("sveKategorije");
			String[] nizKategorija = kategorije.split("#");
			for (int i =0; i <nizKategorija.length -1; i++) {
				lista.add(nizKategorija[i]);
			}
			String opis = request.getParameter("opis");
			String hitna = request.getParameter("hitno");
			String lat = request.getParameter("lat");
			String lon = request.getParameter("lon");
			boolean isHitna = (hitna != null);
			
			String naslov =  "Opasnost";
			if (isHitna)
				naslov = "Hitna opsasnost";
				
			Opasnost op = OpasnostDAO.insertOpasnost(naslov, userBean.getUser().getUsername(),"O", new Date(),isHitna,
					opis, lat, lon, lista);
			//address = "WEB-INF/pages/opasnost.jsp";
			if (op != null) {
				if (isHitna)
					UserBean.sendEmail(op);
				session.setAttribute("Notification", "Uspjesno objavljena opasnost");
				address = "WEB-INF/pages/main.jsp";
			}
			else {
				address = "WEB-INF/pages/opasnost.jsp";
				session.setAttribute("Notification", "Doslo je do greske prilikom dodavanja opasnosti");
			}
				
			
		}
		else 
		{
			address = "WEB-INF/pages/error.jsp";
		}
		
		
		try {
			RequestDispatcher dispacher = request.getRequestDispatcher(address);
			dispacher.forward(request, response);
		} catch (Exception e) {
			System.out.println("Throw error page!");
			request.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(request, response);
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
	
	

}
