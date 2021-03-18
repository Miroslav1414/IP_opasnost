package etf.ip.projektni.admin.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import etf.ip.projektni.admin.dao.UserDAO;
import etf.ip.projektni.admin.dto.User;


@ManagedBean(name = "userBean")
@SessionScoped
public class UserBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	private ArrayList<User> regKorisnici;
	private ArrayList<User> sviKorisnici;
	
	
	public ArrayList<User> getSviKorisnici() {
		return sviKorisnici;
	}
	public void setSviKorisnici(ArrayList<User> sviKorisnici) {
		this.sviKorisnici = sviKorisnici;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public ArrayList<User> getRegKorisnici() {
		return regKorisnici;
	}
	public void setRegKorisnici(ArrayList<User> regKorisnici) {
		this.regKorisnici = regKorisnici;
	}
	
	public String registracija() {
		regKorisnici = UserDAO.getRegisteredUsers();
		return "korisniciRegistracija.xhtml?faces-redirect=true";
	}
	
	public String administracija() {
		sviKorisnici = UserDAO.getAllUsers();
		return "korisniciAdministracija.xhtml?faces-redirect=true";
	}
	
	public String blokirajKorisnika() {
		Map<String,String> reqMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		if(reqMap.containsKey("username")) {
			String username = reqMap.get("username");
			for(int i = 0; i<sviKorisnici.size(); i++) {
				User user = sviKorisnici.get(i);
				if (user.getUsername().equals(username)) {
					sviKorisnici.get(i).setBlocked(true);
					UserDAO.blockKorisnika(username);
				}
			}
		}
		
		return null;
	}
	
	public String odobriRegistraciju() {
		Map<String, String> reqMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		if (reqMap.containsKey("username")) {
			String username = reqMap.get("username");
			for (User user : regKorisnici) {
				if (user.getUsername().equals(username)) {
					regKorisnici.remove(user);
					UserDAO.approveReg(username);
					break;
				}
			}
		}
		return null;
	}
	
	public String resetujLozinku() {
		Map<String, String> reqMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String password = "";
		Random r = new Random();
		int low = 'a';
		int high = 'z';
		StringBuilder niz = new StringBuilder(10);
		for(int i =0; i<10; i++) {
			int result = r.nextInt(high-low) + low;
			niz.append((char) result);
		}
		password = niz.toString();		
		if(reqMap.containsKey("username")) {
			String username = reqMap.get("username");
			for(int i = 0; i<sviKorisnici.size(); i++) {
				User user = sviKorisnici.get(i);
				if (user.getUsername().equals(username)) {
					sviKorisnici.get(i).setPassword(password);
					UserDAO.resetujLozinku(username, password);
				}
			}
		}
		return null;
	}
	

}
