package etf.ip.projektni.dto;

import java.io.Serializable;

public class HelperClass implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int pocetakListe;
	private int krajListe;
	
	public int getPocetakListe() {
		return pocetakListe;
	}
	public void setPocetakListe(int pocetakListe) {
		this.pocetakListe = pocetakListe;
	}
	public int getKrajListe() {
		return krajListe;
	}
	public void setKrajListe(int krajListe) {
		this.krajListe = krajListe;
	}
	public HelperClass() {
		super();
		// TODO Auto-generated constructor stub
	}
	public HelperClass(int pocetakListe, int krajListe) {
		super();
		this.pocetakListe = pocetakListe;
		this.krajListe = krajListe;
	}
	
	

}
