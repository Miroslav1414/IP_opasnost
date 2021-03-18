package etf.ip.projektni.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


public class Vijest implements Serializable ,  Comparable<Vijest>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String naslov;
	private String usernameAutora;
	private Date datumObjave;
	private String tip;
	private ArrayList<Komentar> komentari;
	private String avatarKorisnika;
	
	
	
	public String getAvatarKorisnika() {
		return avatarKorisnika;
	}
	public void setAvatarKorisnika(String avatarKorisnika) {
		this.avatarKorisnika = avatarKorisnika;
	}
	
	
	
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNaslov() {
		return naslov;
	}
	public void setNaslov(String naslov) {
		this.naslov = naslov;
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
	public Vijest() {
		super();
		this.komentari = new ArrayList<Komentar>();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public Vijest(int id, String naslov, String usernameAutora, Date datumObjave, String tip,
			String avatarKorisnika, ArrayList<Komentar> komentari) {
		super();
		this.id = id;
		this.naslov = naslov;
		this.usernameAutora = usernameAutora;
		this.datumObjave = datumObjave;
		this.tip = tip;
		this.avatarKorisnika = avatarKorisnika;
		this.komentari = komentari;
	}
	@Override
    public int compareTo(Vijest comparestu) {
        Date compareage=((Vijest)comparestu).getDatumObjave();
        /* For Ascending order*/
        
        //return compareage.compareTo(this.getDatumObjave());			//desc
        return this.getDatumObjave().compareTo(compareage);		//asc

        /* For Descending order do like this */
        //return compareage-this.studentage;
    }
	public ArrayList<Komentar> getKomentari() {
		return komentari;
	}
	public void setKomentari(ArrayList<Komentar> komentari) {
		this.komentari = komentari;
	}
	

}
