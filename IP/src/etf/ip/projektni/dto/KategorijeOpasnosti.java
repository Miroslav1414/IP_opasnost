package etf.ip.projektni.dto;

import java.io.Serializable;

public class KategorijeOpasnosti implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String kategorija;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getKategorija() {
		return kategorija;
	}
	public void setKategorija(String kategorija) {
		this.kategorija = kategorija;
	}
	public KategorijeOpasnosti(int id, String kategorija) {
		super();
		this.id = id;
		this.kategorija = kategorija;
	}
	public KategorijeOpasnosti() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
