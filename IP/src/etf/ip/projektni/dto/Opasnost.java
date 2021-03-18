package etf.ip.projektni.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Opasnost extends Vijest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean hitna;
	private String opis;
	private double lon;
	private double lat;
	
	private ArrayList<String> kategorijeOpasnostis;
	
	
	public Opasnost() {
		super();
		kategorijeOpasnostis = new ArrayList<String>();
		// TODO Auto-generated constructor stub
	}
	
	public Opasnost(int id, String naslov, String usernameAutora, Date datumObjave, String tip,
			boolean hitna, String opis, double lon, double lat) {
		super(id, naslov, usernameAutora, datumObjave, tip, null, null);
		this.hitna = hitna;
		this.opis = opis;
		this.lon = lon;
		this.lat = lat;
		kategorijeOpasnostis = new ArrayList<String>();
	}
	public boolean isHitna() {
		return hitna;
	}
	public void setHitna(boolean hitna) {
		this.hitna = hitna;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}

	public ArrayList<String> getKategorijeOpasnostis() {
		return kategorijeOpasnostis;
	}

	public void setKategorijeOpasnostis(ArrayList<String> kategorijeOpasnostis) {
		this.kategorijeOpasnostis = kategorijeOpasnostis;
	}
	
	public  String getKategorijeOpasnostisString() {
		String rez = "";
		for (String kat : this.kategorijeOpasnostis) {
			rez += kat + " ";
		}
		return rez;
	}
	

}
