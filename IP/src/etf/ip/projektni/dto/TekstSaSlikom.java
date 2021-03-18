package etf.ip.projektni.dto;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class TekstSaSlikom extends Vijest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String slika;
	private String tekst;
	
	
	
	public TekstSaSlikom(int id, String naslov, String usernameAutora, Date datumObjave, String tip,
			String avatarKorisnika ,ArrayList<Komentar> komentari,String slika, String tekst) {
		super(id,naslov,usernameAutora, datumObjave,tip, avatarKorisnika,komentari);
		
		this.slika = slika;
		this.tekst = tekst;
	}
	
	public TekstSaSlikom() {
		super();
	}
	public String getSlika() {
		return slika;
	}
	public void setSlika(String slika) {
		this.slika = slika;
	}
	public String getTekst() {
		return tekst;
	}
	public void setTekst(String tekst) {
		this.tekst = tekst;
	}
	
	

}
