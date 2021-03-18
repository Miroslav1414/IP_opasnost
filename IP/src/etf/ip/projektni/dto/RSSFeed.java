package etf.ip.projektni.dto;

import java.io.Serializable;
import java.util.Date;

public class RSSFeed extends Vijest implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private String link;
	private String opis;
	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public RSSFeed() {
		super();
		this.setTip("R");
		// TODO Auto-generated constructor stub
	}
	public RSSFeed(String naslov, Date datumKreiranja, String link, String opis) {
		super();
		this.link = link;
		this.opis = opis;
		this.setNaslov(naslov);
		this.setDatumObjave(datumKreiranja);
		this.setTip("R");
	}
	
	

}
