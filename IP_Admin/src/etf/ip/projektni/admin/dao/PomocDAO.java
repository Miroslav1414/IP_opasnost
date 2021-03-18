package etf.ip.projektni.admin.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import etf.ip.projektni.admin.dto.Pomoc;


public class PomocDAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	
	public static final String SQL_DELETE_POMOC ="delete from pomoc where id = ?";
	public static final String SQL_GET_ALL = "select * from pomoc";
	
	public static boolean deletePomoc(int id) {
		boolean rez = false;
		Connection conn = null;
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = conn.prepareStatement(SQL_DELETE_POMOC);
			ps.setObject(1, id);
			ps.executeUpdate();
			rez = true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally {
			connectionPool.checkIn(conn);
		}
				
		return rez;		
	}
	
	public static ArrayList<Pomoc> getAll(){
		ArrayList<Pomoc> rez = new ArrayList<Pomoc>();
		Connection conn = null;
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = conn.prepareStatement(SQL_GET_ALL);				
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				rez.add( new Pomoc(rs.getInt("id"),rs.getString("Naziv"),rs.getDate("vrijemeOd"),rs.getDate("vrijemeDo"),
						rs.getString("lokacija"),rs.getString("opis"),rs.getString("slika"),rs.getString("Kategorija")));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally {
			connectionPool.checkIn(conn);
		}
		return rez ;
	}

}
