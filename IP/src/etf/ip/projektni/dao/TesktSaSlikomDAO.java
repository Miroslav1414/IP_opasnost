package etf.ip.projektni.dao;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;



import com.mysql.cj.jdbc.exceptions.PacketTooBigException;

import etf.ip.projektni.dto.Komentar;
import etf.ip.projektni.dto.Link;
import etf.ip.projektni.dto.TekstSaSlikom;

public class TesktSaSlikomDAO implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	public static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_INSERT_TEKST = "Insert into TekstSaSlikom (id,Slika,Tekst) values (?,?,?)";
	private static final String SQL_GET_SLIKA = "Select slika from TekstSaSlikom where id = ?";
	private static final String SQL_SELECT_SLIKA_ID = "Select v.id,naslov,tip,usernameAutora,datumObjave,tekst from tekstsaslikom t"
			+ " inner join vijest v on t.id = v.id  where v.id = ? ";
//	private static final String LOKACIJA_SLIKA = "C:\\Users\\miroslav.mandic\\git\\IP\\IP\\podaci\\slike\\";
//	private static final String LOKACIJA_VIDEO = "C:\\Users\\miroslav.mandic\\git\\IP\\IP\\podaci\\video\\";
//	private static final String LOKACIJA_ISPIS = "C:/Users/miroslav.mandic/git/IP/IP/podaci/slike";
	
	

	
	public static TekstSaSlikom insertTekstSaSlikom (String naslov, String username,Date datumObjave,String tip,InputStream slika, String tekst) {
		Connection conn = null;
		TekstSaSlikom tekstSlika = null;
		int idVijesti  = VijestDAO.insertVijest(naslov, username, tip, datumObjave);
		if (idVijesti > 0) {
			try {
				         
				conn = connectionPool.checkOut();
				PreparedStatement ps  = conn.prepareStatement(SQL_INSERT_TEKST);
				ps.setObject(1,idVijesti);
				ps.setBlob(2, slika );
				ps.setString(3, tekst);				
								
				int rezz = ps.executeUpdate();
				if (rezz <1) 
					return null;
				else {
					
					tekstSlika = new TekstSaSlikom(idVijesti,naslov,username,datumObjave,tip,
							UserDAO.getAvatarKorisnika(username),null,getSlikaTekstaSaSlikom(idVijesti),tekst);
				}
				
			}
			catch (PacketTooBigException ex) {
				
				System.out.println("fajl je prevelik za upis " + ex.getMessage());
				return null;
			}
			catch (Exception e) {
				
				e.printStackTrace();
				return tekstSlika;
			}
			finally {
				connectionPool.checkIn(conn);
			}
		}
		return tekstSlika;
	}
	
	public static String getSlikaTekstaSaSlikom (int id) {
		Connection conn = null;
		String base64Image = null;
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = conn.prepareStatement(SQL_GET_SLIKA);
			ps.setObject(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Blob blob = rs.getBlob("Slika");
				InputStream inputStream = blob.getBinaryStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead = -1;
                 
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);                  
                }
                 
                byte[] imageBytes = outputStream.toByteArray();
                base64Image = Base64.getEncoder().encodeToString(imageBytes);
                 
                inputStream.close();
                outputStream.close();
                return base64Image;
			}
			
		}
		catch(Exception e) {
			return null;
		}
		finally {
			connectionPool.checkIn(conn);
		}
		return base64Image;
	}
	
	
	public static TekstSaSlikom selectTekstId(int id){
		TekstSaSlikom tekst = null;
		Connection conn = null;
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = conn.prepareStatement(SQL_SELECT_SLIKA_ID);
			ps.setObject(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				tekst  = new TekstSaSlikom(rs.getInt(1),rs.getString(2),rs.getString(4),rs.getTimestamp(5),rs.getString(3),
						UserDAO.getAvatarKorisnika(rs.getString(4)),null,getSlikaTekstaSaSlikom(rs.getInt(1)),rs.getString(6));
				//tekst.setKomentari(KomentarDAO.getKomentariAll(rs.getInt(1)));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally {
			connectionPool.checkIn(conn);
		}
		return tekst;
	}

}
