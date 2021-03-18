package etf.ip.projektni.dto;

import java.io.Serializable;
import java.util.Date;

public class Komentar implements Serializable, Comparable<Komentar>{
	
	
	private static final long serialVersionUID = 1L;
	
	
	private int id;
	private String tekst;
	private String usernameAutora;
	private Date datumObjave;
    private String slika;
    private int idVijesti;
	private String avatarKorisnika;
	
	
	
	public String getAvatarKorisnika() {
		return avatarKorisnika;
	}
	public void setAvatarKorisnika(String avatarKorisnika) {
		this.avatarKorisnika = avatarKorisnika;
	}
    
    
    
	public Komentar() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Komentar(int id, String tekst, String usernameAutora, Date datumObjave, String slika, int idVijesti,String avatarKorisnika) {
		super();
		this.id = id;
		this.tekst = tekst;
		this.usernameAutora = usernameAutora;
		this.datumObjave = datumObjave;
		this.slika = slika;
		this.idVijesti = idVijesti;
		this.avatarKorisnika = avatarKorisnika;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTekst() {
		return tekst;
	}
	public void setTekst(String tekst) {
		this.tekst = tekst;
	}
	public String getUsernameAutora() {
		return usernameAutora;
	}
	public void setUsernameAutora(String usernameAutora) {
		this.usernameAutora = usernameAutora;
	}
	public Date getDatumObjave() {
		return datumObjave;
	}
	public void setDatumObjave(Date datumObjave) {
		this.datumObjave = datumObjave;
	}
	public String getSlika() {
		return slika;
	}
	public void setSlika(String slika) {
		this.slika = slika;
	}
	public int getIdVijesti() {
		return idVijesti;
	}
	public void setIdVijesti(int idVijesti) {
		this.idVijesti = idVijesti;
	}
	
	@Override
    public int compareTo(Komentar comparestu) {
        Date compareage=((Komentar)comparestu).getDatumObjave();
        /* For Ascending order*/
        
        //return compareage.compareTo(this.getDatumObjave());			//desc
        return this.getDatumObjave().compareTo(compareage);		//asc

        /* For Descending order do like this */
        //return compareage-this.studentage;
    }
	
	
	
	

}
