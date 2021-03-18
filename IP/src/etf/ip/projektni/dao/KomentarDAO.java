package etf.ip.projektni.dao;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;

import etf.ip.projektni.dto.Komentar;
import etf.ip.projektni.dto.Vijest;


public class KomentarDAO implements Serializable {


	private static final long serialVersionUID = 1L;
	
	public static ConnectionPool connectionPool  = ConnectionPool.getConnectionPool();
	
	private static final String SQL_INSERT_KOMENTAR = "insert into komentar (tekst,idVijesti,usernameAutora,datumObjave,slika) values (?,?,?,?,?)";
	private static final String SQL_GET_SLIKA = "Select slika from komentar where id = ?";
	private static final String SQL_GET_KOMENTARI_ALL = "select id,tekst,idVijesti,usernameAutora,datumObjave,slika from komentar where idVijesti = ?";
	
	public static ArrayList<Komentar> getKomentariAll (int idVijesti){
		ArrayList<Komentar>  rez = new ArrayList<Komentar>();
		Connection conn = null;
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = conn.prepareStatement(SQL_GET_KOMENTARI_ALL);
			ps.setObject(1, idVijesti);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				rez.add(new Komentar(rs.getInt(1),rs.getString("tekst"),rs.getString("usernameAutora"),rs.getTimestamp("datumObjave")
						,getSlikaKomentaraFromInputStream(rs.getBlob("slika").getBinaryStream()),idVijesti,UserDAO.getAvatarKorisnika(rs.getString("usernameAutora"))));

			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally {
			connectionPool.checkIn(conn);
		}		
		Collections.sort(rez);
		return rez;
	}
	
	public static ArrayList<Vijest> getKomentariAllUpdate (){
		ArrayList<Vijest> vijestiUpdate = VijestDAO.selectALLVijestiID_UpdateKomentara();
		for( int i = 0; i < vijestiUpdate.size();i++) {
			vijestiUpdate.get(i).setKomentari(getKomentariAll(vijestiUpdate.get(i).getId()));
		}
		return vijestiUpdate;
	}

	public static Komentar insertKomentar(String tekst, int idVijesti, String usernameAutora,Date datumObjave,InputStream slika) {
		Komentar rez = null;
		Connection conn = null;
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = conn.prepareStatement(SQL_INSERT_KOMENTAR,Statement.RETURN_GENERATED_KEYS);
			ps.setObject(1, tekst);
			ps.setObject(2, idVijesti);
			ps.setObject(3, usernameAutora);
			ps.setObject(4, datumObjave);
			ps.setBlob(5, slika);
			
			int rezz = ps.executeUpdate();
			if (rezz >0) {				
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					rez = new Komentar(rs.getInt(1),tekst,usernameAutora,datumObjave,getSlikaKomentara(rs.getInt(1)),idVijesti,UserDAO.getAvatarKorisnika(usernameAutora));
				}
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally {
			connectionPool.checkIn(conn);
		}		
		
		return rez;
		
	}
	
	public static String getSlikaKomentara (int id) {
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
	
	public static String getSlikaKomentaraFromInputStream(InputStream inputStream) {
		String base64Image = null;
		try {
			
				
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
		catch(Exception e) {
			return null;
		}
	}
}
