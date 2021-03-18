package etf.ip.projektni.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import etf.ip.projektni.dto.OpasnostKategorija;

public class OpasnostKategorijaDAO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_INSERT_KATEGORIJAOPASNOST = "Insert into OpasnostKategorija (idOpasnosti,idkategorije) values (?,?)";
	
	public static  ArrayList<OpasnostKategorija> insertKategorijaOpasnost(int idOpasnosti, int idKategorije) {
		ArrayList<OpasnostKategorija> rezultat = new ArrayList<OpasnostKategorija>();
		Connection conn = null;
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = conn.prepareStatement(SQL_INSERT_KATEGORIJAOPASNOST);
			ps.setObject(1, idOpasnosti);
			ps.setObject(2, idKategorije);
			int rezz = ps.executeUpdate();
			if (rezz <1) 
				return null;
			else 
				rezultat.add(new OpasnostKategorija(idKategorije,idOpasnosti));
				
							
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
	
	public static  boolean insertKategorijaOpasnost(int idOpasnosti, ArrayList<String> kategorije) {
		boolean rezultat = true;
		Connection conn = null;
		try {
			conn = connectionPool.checkOut();
			for (String s : kategorije) {
				PreparedStatement ps = conn.prepareStatement(SQL_INSERT_KATEGORIJAOPASNOST);
				ps.setObject(1, idOpasnosti);
				ps.setObject(2, Integer.parseInt(s));
				int rezz = ps.executeUpdate();
				if (rezz <1) 
					return false;
			}	
							
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally {
			connectionPool.checkIn(conn);
		}
		
		return rezultat;
	}

}
	
	
