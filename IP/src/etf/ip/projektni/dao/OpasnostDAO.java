package etf.ip.projektni.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import etf.ip.projektni.dto.Opasnost;

import etf.ip.projektni.dto.Link;

public class OpasnostDAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_INSERT_OPASNOSTI = "Insert into opasnost (id,hitna,opis,lon,lat) values (?,?,?,?,?)";
	private static final String SQL_SELECT_OPASNOST_BY_ID = "select v.id,naslov,tip,datumObjave,hitna,opis,lon,"
			+ "lat from vijest v inner join opasnost o on v.id = o.id where v.id = ?";
	public static  Opasnost insertOpasnost(String naslov, String username,String tip, Date datumObjave,Boolean hitna,
			String opis, String lat, String lon, ArrayList<String> kategorije) {
		Opasnost rezultat = null;
		Connection conn = null;
		try {
			int idVijesti  = VijestDAO.insertVijest(naslov, username, tip, datumObjave);
			if (idVijesti > 0) {				
				conn = connectionPool.checkOut();
				PreparedStatement ps = conn.prepareStatement(SQL_INSERT_OPASNOSTI);
				ps.setObject(1, idVijesti);
				ps.setObject(2, hitna);
				ps.setObject(3, opis);
				if (lat.equals("")){
					ps.setObject(4, null);
					ps.setObject(5, null);
				}
				else {
					ps.setObject(4, Double.parseDouble(lon));
					ps.setObject(5, Double.parseDouble(lat));
				}
				
				int rezz = ps.executeUpdate();
				if (rezz <1) 
					return null;
				else
				{
					
					if (lat.equals("")){
						rezultat = new Opasnost(idVijesti,naslov,username,datumObjave,tip,hitna,opis,0,0);
					}
					else {
						rezultat = new Opasnost(idVijesti,naslov,username,datumObjave,tip,hitna,opis,Double.parseDouble(lon),Double.parseDouble(lat));
					}
					if (OpasnostKategorijaDAO.insertKategorijaOpasnost(idVijesti, kategorije)) {
						rezultat.setKategorijeOpasnostis(KategorijeOpasnostiDAO.getKategorijeOpasnostiString(kategorije));
					}
						
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
		
		return rezultat;
	}
	
	public static Opasnost selectOpasnostId(int id){
		Opasnost opasnost = null;
		Connection conn = null;
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = conn.prepareStatement(SQL_SELECT_OPASNOST_BY_ID);
			ps.setObject(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {				
				opasnost  = new Opasnost(rs.getInt("id"),rs.getString("naslov"),null,rs.getTimestamp("datumObjave"),
						rs.getString("tip"),rs.getBoolean("hitna"),rs.getString("opis"),rs.getDouble("lon"),rs.getDouble("lat"));
				opasnost.setKategorijeOpasnostis(KategorijeOpasnostiDAO.getKategorijeOpasnostiStringById(id));
				
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally {
			connectionPool.checkIn(conn);
		}
		return opasnost;
	}
	
	public static ArrayList<Opasnost> getOpasnosti (){
		ArrayList<Opasnost> rez = new ArrayList<Opasnost>();
		Connection conn = null;
		try {
			conn = connectionPool.checkOut();
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return rez;
		}
		finally {
			connectionPool.checkIn(conn);
		}
		
		return rez;
	}

}
