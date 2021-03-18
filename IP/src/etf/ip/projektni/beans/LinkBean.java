package etf.ip.projektni.beans;

import java.io.Serializable;
import java.util.Date;

import etf.ip.projektni.dao.LinkDAO;
import etf.ip.projektni.dto.Link;



public class LinkBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static Link insertLink (Link link, String tip) {
		return LinkDAO.insertLink(link.getNaslov(), link.getUsernameAutora(),tip, new Date(), link.getLink());
		
	}

}
