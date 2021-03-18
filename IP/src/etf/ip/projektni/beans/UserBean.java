package etf.ip.projektni.beans;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;


import javax.mail.*;
import javax.mail.internet.*;
import javax.net.ssl.SSLSocketFactory;
import javax.activation.*;

import etf.ip.projektni.dao.UserDAO;
import etf.ip.projektni.dto.Opasnost;
import etf.ip.projektni.dto.User;;

public class UserBean implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private User user = new User();
	private boolean isLoggedIn = false;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public boolean isLoggedIn() {
		return isLoggedIn;
	}
	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
	
	public boolean login (String username, String password) {
		if ((user = UserDAO.selectByUsernameAndPassword(username, password)) != null) {
			setLoggedIn(true);
			return true;
		}
		return false;
	}
	
	public boolean register(User user) {
		this.user = user;
		return UserDAO.insert(user);
	}
	
	public boolean update() throws SQLException{
		return UserDAO.updateUser(user);
	}
	
	public boolean isUsernameAvailable (String username) {
		return UserDAO.usernameAvailable(username);
	}
	
	public boolean isEmailAvailable (String email) {
		return UserDAO.emailAvailable(email);
	}
	
	
	public boolean updateUserProfilePicture(String username, InputStream is, long duzinaSlike ) {
		String slika  = UserDAO.updateUserProfilePicture(username,is,duzinaSlike);
		if ("".equals(slika))
		{
			return false;
		}
		else {
			user.setProfilePicturex(slika);
			return true;
		}
	}
	
	public static void sendEmail(Opasnost opasnost) {
		ArrayList<String> mailovi = UserDAO.getEmails();
		//mail from
	      String from = "miroslavmandicip@gmail.com";
	      String lozinka = "internetprogramiranje";
	      String host = "smtp.gmail.com";

	        // Get system properties
	        Properties properties = new Properties();   

	        // Setup mail server
	        properties.put("mail.smtp.host", host);
	        properties.put("mail.smtp.port", 587);
	        properties.put("mail.smtp.auth", "true");
	        properties.put("mail.smtp.starttls.enable", "true");
	        properties.put("mail.smtp.socketFactory.port", 465);
	        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        
	        

	        // Get the Session object.// and pass username and password
	        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(from, lozinka);
	            }
	        });
	        //session.setDebug(true);
	      
	      for(String mail : mailovi) {
	    	  try {
	 	         // Create a default MimeMessage object.
	 	         MimeMessage message = new MimeMessage(session);	 	
	 	         // Set From: header field of the header.
	 	         message.setFrom(new InternetAddress(from));	 	
	 	         // Set To: header field of the header.
	 	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
	 	         
	 	
	 	         // Set Subject: header field
	 	         message.setSubject("Opasnost!");	
	 	         // Now set the actual message
	 	         message.setText(opasnost.getNaslov() +" /n"
	 	         		+ "Autor: " + opasnost.getUsernameAutora() +"/n"
         				+ "Datum objave: " + opasnost.getDatumObjave() + "/n" 
	 	         		+ "Opis opasnosti" + opasnost.getOpis() + "/n"
	 	         		+ "lokacija: lon = " + opasnost.getLon() + ", lat= " + opasnost.getLat() + "/n"
	 	         		+ opasnost.getKategorijeOpasnostisString());
	 	
	 	         // Send message
	 	         System.out.println("poziv slanja mail");
	 	         Transport.send(message);
	 	         System.out.println("Sent message to "  +mail+ " successfully....");
	 	      } catch (MessagingException mex) {
	 	         mex.printStackTrace();
	 	      }
	 	}
	      }
	      

}
