package etf.ip.projektni.dto;

import java.io.Serializable;

public class OpasnostKategorija implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idOpasnosti ;
	private int idKategorije;
	public OpasnostKategorija(int idOpasnosti, int idKategorije) {
		super();
		this.idOpasnosti = idOpasnosti;
		this.idKategorije = idKategorije;
	}
	public int getIdOpasnosti() {
		return idOpasnosti;
	}
	public void setIdOpasnosti(int idOpasnosti) {
		this.idOpasnosti = idOpasnosti;
	}
	public int getIdKategorije() {
		return idKategorije;
	}
	public void setIdKategorije(int idKategorije) {
		this.idKategorije = idKategorije;
	}
	public OpasnostKategorija() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
