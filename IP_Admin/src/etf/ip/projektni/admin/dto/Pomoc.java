package etf.ip.projektni.admin.dto;

import java.io.Serializable;
import java.util.Date;

public class Pomoc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String naziv;
	private Date vrijemeOd;
	private Date vrijemeDo;
	private String lokacija;
	private String opis;
	private String slika;
	private String kategorija;
	
	
	public Pomoc() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Pomoc(int id ,String naziv, Date vrijemeOd, Date vrijemeDo, String lokacija, String opis, String slika,
			String kategorija) {
		super();
		this.setId(id);
		this.naziv = naziv;
		this.vrijemeOd = vrijemeOd;
		this.vrijemeDo = vrijemeDo;
		this.lokacija = lokacija;
		this.opis = opis;
		this.slika = slika;
		this.kategorija = kategorija;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public Date getVrijemeOd() {
		return vrijemeOd;
	}
	public void setVrijemeOd(Date vrijemeOd) {
		this.vrijemeOd = vrijemeOd;
	}
	public Date getVrijemeDo() {
		return vrijemeDo;
	}
	public void setVrijemeDo(Date vrijemeDo) {
		this.vrijemeDo = vrijemeDo;
	}
	public String getLokacija() {
		return lokacija;
	}
	public void setLokacija(String lokacija) {
		this.lokacija = lokacija;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public String getSlika() {
		return slika;
	}
	public void setSlika(String slika) {
		this.slika = slika;
	}
	public String getKategorija() {
		return kategorija;
	}
	public void setKategorija(String kategorija) {
		this.kategorija = kategorija;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
