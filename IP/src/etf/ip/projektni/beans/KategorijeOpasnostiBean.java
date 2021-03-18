package etf.ip.projektni.beans;

import java.io.Serializable;
import java.util.ArrayList;

import etf.ip.projektni.dao.KategorijeOpasnostiDAO;
import etf.ip.projektni.dto.KategorijeOpasnosti;

public class KategorijeOpasnostiBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<KategorijeOpasnosti> nizKategorijeOpasnosti;
	
	
	
	public KategorijeOpasnostiBean() {
		super();
		nizKategorijeOpasnosti = KategorijeOpasnostiDAO.getKategorijeOpasnosti();
	}


	public ArrayList<KategorijeOpasnosti> getNizKategorijeOpasnosti() {
		return nizKategorijeOpasnosti;
	}



	public void setNizKategorijeOpasnosti(ArrayList<KategorijeOpasnosti> nizKategorijeOpasnosti) {
		this.nizKategorijeOpasnosti = nizKategorijeOpasnosti;
	}

}
