package etf.ip.projektni.api;


import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import etf.ip.projektni.beans.HelperBean;
import etf.ip.projektni.beans.LinkBean;
import etf.ip.projektni.dao.KomentarDAO;
import etf.ip.projektni.dto.Link;
import etf.ip.projektni.dto.Vijest;



@Path("/Vijest")
public class PostApi  {


	@POST
	@Path("/AddLink")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addLink(Link link) {
		Link  noviLink = LinkBean.insertLink(link,"L");
		if (noviLink != null){
			System.out.println("uspjesan upis linka");
			return Response.status(200).entity(noviLink).build();
		} else {
			return Response.status(500).entity("Greska pri dodavanju").build();
		}
	}
	
	@POST
	@Path("/AddYTVideo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addYTVideo(Link link) {
		Link  noviLink = LinkBean.insertLink(link,"Y");
		if (noviLink != null){
			System.out.println("uspjesan upis YT videa");
			return Response.status(200).entity(noviLink).build();
		} else {
			return Response.status(500).entity("Greska pri dodavanju").build();
		}
	}
	
	@GET
	@Path("/getVijesti/{pocetak}/{kraj}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Vijest> getVijesti(
			@PathParam("pocetak") int pocetak,
			@PathParam("kraj") int kraj
			){
		ArrayList<Vijest> rez = new HelperBean().getVijestiInterval(pocetak,kraj);		
		return rez;
	}
	
	
	@GET
	@Path("/getKomentari")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Vijest> getKomentari(){
		ArrayList<Vijest> rez = KomentarDAO.getKomentariAllUpdate();
		return rez;
	}
	
	@GET
	@Path("/getNotifikacije/{pocetak}/{korisnickoIme}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Vijest> getNotifikacije(
			@PathParam("pocetak") int pocetak,
			@PathParam("korisnickoIme") String username){
		return HelperBean.getNotifikacijeInterval(pocetak,username);
	}
	

}
