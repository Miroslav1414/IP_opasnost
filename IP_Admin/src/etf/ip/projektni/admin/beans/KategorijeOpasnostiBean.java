package etf.ip.projektni.admin.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import etf.ip.projektni.admin.dao.KategorijeOpasnostiDAO;
import etf.ip.projektni.admin.dto.KategorijeOpasnosti;

@ManagedBean (name="kategorijeOpasnostiBean")
@SessionScoped
public class KategorijeOpasnostiBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<KategorijeOpasnosti> kategorije;
	private String novaKategorija;
	
	public String dodajKategoriju () {
		if (novaKategorija != null && !novaKategorija.equals("")){
			KategorijeOpasnosti kat = KategorijeOpasnostiDAO.dodajKategoriju(novaKategorija);
			kategorije.add(kat);
		}
		else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage("addKat:dodavanjeKategorije", new FacesMessage("Morate unijeti naziv kategorije!"));
			return null;
		}
			
		return null;
	}
	
	public  String administracija() {
		kategorije = KategorijeOpasnostiDAO.getAll();
		return "kategorije.xhtml?faces-redirect=true";
	}

	public ArrayList<KategorijeOpasnosti> getKategorije() {
		return kategorije;
	}

	public void setKategorije(ArrayList<KategorijeOpasnosti> kategorije) {
		this.kategorije = kategorije;
	}
	
	public String blokiraj() {
		Map<String,String> reqMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		if (reqMap.containsKey("id")) {
			int id = Integer.parseInt(reqMap.get("id"));
			for(KategorijeOpasnosti  a : kategorije) {
				if (id == a.getId()) {
					KategorijeOpasnostiDAO.blokirajKategoriju(id, (!a.isBlokirana()));
					a.setBlokirana(!a.isBlokirana());
				}
			}			
		}
		return null;
	}

	public String getNovaKategorija() {
		return novaKategorija;
	}

	public void setNovaKategorija(String novaKategorija) {
		this.novaKategorija = novaKategorija;
	}
	

}
