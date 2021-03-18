package etf.ip.projektni.admin.dto;

import java.io.Serializable;

public class KategorijeOpasnosti implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String kategorija;
	private boolean blokirana;
	
	
	public KategorijeOpasnosti(int id, String kategorija, boolean blokirana) {
		super();
		this.id = id;
		this.kategorija = kategorija;
		this.blokirana = blokirana;
	}


	public KategorijeOpasnosti() {
		super();
		// TODO Auto-generated constructor stub
	}


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


	public boolean isBlokirana() {
		return blokirana;
	}


	public void setBlokirana(boolean blokirana) {
		this.blokirana = blokirana;
	}
	
	

}
