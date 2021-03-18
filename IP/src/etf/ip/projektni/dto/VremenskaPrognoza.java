package etf.ip.projektni.dto;

import java.io.Serializable;

public class VremenskaPrognoza implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private String ikonica;
	private String imeGrada;
	private String temperatura; //-273,15�C
	private String opis;
	private String vlaznost; //%
	private String pritisak; //hpa
	private String brzinaVjetra; //m/s
	private String pravac; //�
	
	
	
	public VremenskaPrognoza(String ikonica, String imeGrada, String temperatura, String opis, String vlaznost,
			String pritisak, String brzinaVjetra, String pravac) {
		super();
		this.ikonica = "pictures/slike_vremenska/" +  ikonica + "@2x.png";
		this.imeGrada = imeGrada;
		this.temperatura = Math.round(Double.parseDouble(temperatura) - 273.15) + "&deg C";
		this.opis = opis;
		this.vlaznost = vlaznost + "%";
		this.pritisak = pritisak + "hpa";
		this.brzinaVjetra = brzinaVjetra + "m/s";
		this.pravac = pravac + "�";
	}
	public String getIkonica() {
		return ikonica;
	}
	public void setIkonica(String ikonica) {
		this.ikonica = "pictures/slike_vremenska/" +  ikonica + "@2x.png";
	}
	public String getImeGrada() {
		return imeGrada;
	}
	public void setImeGrada(String imeGrada) {
		this.imeGrada = imeGrada;
	}
	public String getTemperatura() {
		return temperatura;
	}
	public void setTemperatura(String temperatura) {
		System.out.println("temp   " + temperatura);
		this.temperatura = Math.round(Double.parseDouble(temperatura) - 273.15) + "&deg C";
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public String getVlaznost() {
		return vlaznost;
	}
	public void setVlaznost(String vlaznost) {
		this.vlaznost = vlaznost + "%";
	}
	public String getPritisak() {
		return pritisak;
	}
	public void setPritisak(String pritisak) {
		this.pritisak = pritisak + "hpa";
	}
	public String getBrzinaVjetra() {
		return brzinaVjetra;
	}
	public void setBrzinaVjetra(String brzinaVjetra) {
		this.brzinaVjetra = brzinaVjetra + "m/s";
	}
	public String getPravac() {
		return pravac;
	}
	public void setPravac(String pravac) {
		this.pravac = pravac + "\u00B0";
	}
	@Override
	public String toString() {
		return "VremenskaPrognoza [ikonica=" + ikonica + ", imeGrada=" + imeGrada + ", temperatura=" + temperatura
				+ ", opis=" + opis + ", vlaznost=" + vlaznost + ", pritisak=" + pritisak + ", brzinaVjetra="
				+ brzinaVjetra + ", pravac=" + pravac + "]";
	}
	
	
}
