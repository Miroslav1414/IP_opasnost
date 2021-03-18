package etf.ip.projektni.beans;

import java.io.Serializable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import etf.ip.projektni.dao.HelperDAO;
import etf.ip.projektni.dao.LinkDAO;
import etf.ip.projektni.dao.OpasnostDAO;
import etf.ip.projektni.dao.TesktSaSlikomDAO;
import etf.ip.projektni.dao.UserDAO;
import etf.ip.projektni.dao.VijestDAO;
import etf.ip.projektni.dto.VremenskaPrognoza;
import etf.ip.projektni.helper.RSSParser;
import etf.ip.projektni.dto.RSSFeed;
import etf.ip.projektni.dto.Vijest;

public class HelperBean implements Serializable{


	private static final long serialVersionUID = 1L;
	
	private static final String API_KEY = "21cf1af7ad1b151146fcb4bf67fe54d5";
	
	private VremenskaPrognoza[] nizVremenskigPrognoza = null;	
	public static int BROJ_VIJESTI = 1;
	

	  public VremenskaPrognoza[] getNizVremenskigPrognoza() {
		return nizVremenskigPrognoza;
	}

	public void setNizVremenskigPrognoza(VremenskaPrognoza[] nizVremenskigPrognoza) {
		this.nizVremenskigPrognoza = nizVremenskigPrognoza;
	}

	private  String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }

	  public  JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
	    InputStream is = new URL(url).openStream();
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      JSONObject json = new JSONObject(jsonText);
	      return json;
	      
	    } finally {
	      is.close();
	    }
	  }
	  
	  public void getVremenskePrognoze(String countryCode) {
		  	String [] gradovi = HelperDAO.getRandomGradove(countryCode);
			VremenskaPrognoza [] niz = new VremenskaPrognoza[3];
			int i =0;
			try {
				for(String grad : gradovi) {
					JSONObject json = readJsonFromUrl("http://api.openweathermap.org/data/2.5/weather?q=" + grad + "&appid=" + API_KEY);
					String ikonica = ((JSONArray)json.get("weather")).getJSONObject(0).getString("icon");
					 String imeGrada = json.getString("name");
					 String temperatura = ((JSONObject)json.get("main")).get("temp").toString();
					 String opis = ((JSONArray)json.get("weather")).getJSONObject(0).getString("description");
					 String vlaznost = ((JSONObject)json.get("main")).get("humidity").toString();
					 String pritisak = ((JSONObject)json.get("main")).get("pressure").toString();
					 String brzinaVjetra = ((JSONObject)json.get("wind")).get("speed").toString();
					 String pravac = ((JSONObject)json.get("wind")).get("deg").toString();
					 
					 niz[i++] = new VremenskaPrognoza(ikonica, imeGrada, temperatura, opis, vlaznost, pritisak, brzinaVjetra, pravac);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				nizVremenskigPrognoza = null;
				return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				nizVremenskigPrognoza = null;
				return;
			}
			nizVremenskigPrognoza = niz;
		}

	  public ArrayList<Vijest> getVijesti(){
		  
		  ArrayList<Vijest> niz = new ArrayList<Vijest>();
		  RSSParser parser = new RSSParser("https://europa.eu/newsroom/calendar.xml_en?field_nr_events_by_topic_tid=151");
	      niz = parser.readRSS();
	      niz.addAll(VijestDAO.selectALLVijesti());
	      Collections.sort(niz);
		  return niz;
	  }
	  
	  public  ArrayList<Vijest> getVijestiInterval(int pocetak, int kraj){
			ArrayList<Vijest> rez = new ArrayList<Vijest>(); // rezultat koji se vraca 
			
			ArrayList<Vijest> kandidati = getVijesti(); //id - ovi svih vijesti i RSS
			
			ArrayList<Vijest> privremeni = new ArrayList<Vijest>(); // ideovi i rss koji trebaju da se ucitaju 
			for (int i = pocetak-1; i<kandidati.size(); i++) {
				privremeni.add(kandidati.get(i));
			}
			
			for (int i = 0; i<privremeni.size(); i++) {
				if (privremeni.get(i).getTip().equals("R"))
					rez.add(privremeni.get(i));
				else if (privremeni.get(i).getTip().equals("O")) {
					rez.add(OpasnostDAO.selectOpasnostId(privremeni.get(i).getId()));
				}
				else if (privremeni.get(i).getTip().equals("L") || privremeni.get(i).getTip().equals("Y")) {
					rez.add(LinkDAO.selectLinkId(privremeni.get(i).getId()));
				}
				else if (privremeni.get(i).getTip().equals("T") || privremeni.get(i).getTip().equals("V")) {
					rez.add(TesktSaSlikomDAO.selectTekstId(privremeni.get(i).getId()));
				}
			}
			return rez;
		}
	  
	  public static ArrayList<Vijest> getNotifikacije(){
		  ArrayList<Vijest> niz = new ArrayList<Vijest>();
		  niz = VijestDAO.selectAlLNotifications();
		  Collections.sort(niz);
		  return niz;
	  }
	  
	  public static ArrayList<Vijest> getNotifikacijeInterval(int pocetak, String username){
		  if (!UserDAO.getAppNotification(username)) {
			  return new ArrayList<Vijest>();
		  }
		  ArrayList<Vijest> rez = new ArrayList<Vijest>(); // rezultat koji se vraca 			
			ArrayList<Vijest> kandidati = getNotifikacije(); //id - ovi svih notifikacija		
			ArrayList<Vijest> privremeni = new ArrayList<Vijest>(); //samo oni koji vec nisu ucitani se ucitavaju
			for (int i = pocetak-1; i<kandidati.size(); i++) {
				privremeni.add(kandidati.get(i));
			}
			
			for (int i = 0; i<privremeni.size(); i++) {
				rez.add(OpasnostDAO.selectOpasnostId(privremeni.get(i).getId()));
			} 
			return rez;
	  }

}
