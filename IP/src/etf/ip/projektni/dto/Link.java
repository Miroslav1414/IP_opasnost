package etf.ip.projektni.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Link extends Vijest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String link;
	
	

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Link(int id, String naslov, String usernameAutora, Date datumObjave, String tip, String link, String avatarKorisnika, ArrayList<Komentar> komentari) {
		super(id, naslov, usernameAutora, datumObjave,tip,avatarKorisnika, komentari);
		this.link = link;
	}

	public Link() {
		super();		
	}
	
	public String toString() {
		return "username  :" + getUsernameAutora() + "|naslvo : " + getNaslov() + "|datum :" + getDatumObjave() + "|link :" + getLink();
	}

}
