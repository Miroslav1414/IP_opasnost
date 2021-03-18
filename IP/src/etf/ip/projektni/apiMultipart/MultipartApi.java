package etf.ip.projektni.apiMultipart;

import java.io.InputStream;
import java.util.Date;

import javax.servlet.annotation.MultipartConfig;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import etf.ip.projektni.dao.KomentarDAO;
import etf.ip.projektni.dao.TesktSaSlikomDAO;
import etf.ip.projektni.dto.Komentar;
//import org.glassfish.jersey.server.internal.inject.AbstractValueFactoryProvider;
//import org.glassfish.jersey.server.internal.inject.AbstractValueParamProvider;
import etf.ip.projektni.dto.TekstSaSlikom;

@Path("/Multipart")
@MultipartConfig(maxFileSize = 1024*1024*16)
public class MultipartApi extends ResourceConfig {
	public MultipartApi() {
		register(MultiPartFeature.class);
	}
	
	@POST
	@Path("/AddTekstSaSlikom")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addTekstSaSlikom
	(
			
			@FormDataParam("slikUpis") InputStream uploadedInputStream,
		    @FormDataParam("naslov") String naslov,
		    @FormDataParam("tekst") String tekst,
		    @FormDataParam("usernameAutora") String username
	)
	{
		
		TekstSaSlikom tekstSlika  = TesktSaSlikomDAO.insertTekstSaSlikom(naslov,username,new Date(),"T",uploadedInputStream,tekst);
		
		if (tekstSlika != null){
			System.out.println("uspjesan upis slike");
			return Response.status(200).entity(tekstSlika).build();
		} else {
			return Response.status(500).entity("Greska pri dodavanju").build();
		}
	}
		
	@POST
	@Path("/AddVideo")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addVideo
	(
			
			@FormDataParam("slikUpis") InputStream uploadedInputStream,
		    @FormDataParam("naslov") String naslov,
		    @FormDataParam("usernameAutora") String username
	)
	{
		
		TekstSaSlikom tekstSlika  = TesktSaSlikomDAO.insertTekstSaSlikom(naslov,username,new Date(),"V",uploadedInputStream,null);
		
		if (tekstSlika != null){
			System.out.println("uspjesan upis videa");
			return Response.status(200).entity(tekstSlika).build();
		} else {
			return Response.status(500).entity("Greska pri dodavanju").build();
		}
	}
	
	
	
	@POST
	@Path("/AddKomentar")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addKomentar
	(
			
			@FormDataParam("slika") InputStream slika,
		    @FormDataParam("tekst") String tekst,
		    @FormDataParam("usernameAutora") String usernameAutora,
		    @FormDataParam("idVijesti") int idVijesti
	)
	{
		
		Komentar komentar  = KomentarDAO.insertKomentar(tekst, idVijesti, usernameAutora, new Date(), slika);
		
		if (komentar != null){
			System.out.println("uspjesan upis komentara");
			return Response.status(200).entity(komentar).build();
		} else {
			return Response.status(500).entity("Greska pri dodavanju").build();
		}
	}

}
