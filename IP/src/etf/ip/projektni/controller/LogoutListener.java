package etf.ip.projektni.controller;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import etf.ip.projektni.dao.UserDAO;
@WebListener
public class LogoutListener  implements HttpSessionListener{
	
	public void sessionDestroyed(HttpSessionEvent event) {
		
		int logId = Integer.parseInt( event.getSession().getAttribute("logId").toString());
		UserDAO.insertLogout(logId);
		System.out.println("Session Destroyed: " + event.getSession().getId());
	} 

}
