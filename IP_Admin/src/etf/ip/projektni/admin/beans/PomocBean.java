package etf.ip.projektni.admin.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import etf.ip.projektni.admin.dao.PomocDAO;
import etf.ip.projektni.admin.dao.UserDAO;
import etf.ip.projektni.admin.dto.Pomoc;
import etf.ip.projektni.admin.dto.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@ManagedBean (name="pomocBean")
@SessionScoped
public class PomocBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String naziv;
	private Date vrijemeOd;
	private Date vrijemeDo;
	private String lokacija;
	private String opis;
	private String slika;
	private String kategorija;
	private String message;
	private ArrayList<Pomoc> nizPomoci;
	
	public PomocBean(){
		nizPomoci = PomocDAO.getAll();
	}
	
	
	
	public String obrisiPomoc() {
		Map<String,String> reqMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		if(reqMap.containsKey("id")) {
			int id = Integer.parseInt(reqMap.get("id"));
			for(int i = 0; i<nizPomoci.size(); i++) {
				if (nizPomoci.get(i).getId() == id) {
					nizPomoci.remove(i);
					PomocDAO.deletePomoc(id);
				}
			}
		}
		
		return null;
	}
	
	public String administracija() {
		nizPomoci = PomocDAO.getAll();
		return "pomoc.xhtml?faces-redirect=true";		
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ArrayList<Pomoc> getNizPomoci() {
		return nizPomoci;
	}

	public void setNizPomoci(ArrayList<Pomoc> nizPomoci) {
		this.nizPomoci = nizPomoci;
	}
	
	

}
