package etf.ip.projektni.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import etf.ip.projektni.dto.KategorijeOpasnosti;

public class KategorijeOpasnostiDAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	
	public static final String SQL_GET_KATEGORIJE = "SELECT * from KategorijeOpasnosti";
	public static final String SQL_GET_KATEGORIJE_STRING = "SELECT kategorija from KategorijeOpasnosti where id = ?";
	public static final String SQL_GET_KATEGORIJE_STRING_BY_ID = "select kategorija from KategorijeOpasnosti ko "
			+ "inner join opasnostkategorija ok on ko.id = ok.idKategorije where ok.idOpasnosti = ?;";
	
	public static ArrayList<KategorijeOpasnosti> getKategorijeOpasnosti() {
		ArrayList<KategorijeOpasnosti> rez = new ArrayList<KategorijeOpasnosti>();
		Connection conn = null;
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = conn.prepareStatement(SQL_GET_KATEGORIJE);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				rez.add(new KategorijeOpasnosti(rs.getInt("id"),rs.getString("Kategorija")));
			}
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return rez;
		}
		finally {
			connectionPool.checkIn(conn);
		}
		return rez;
				
	}
	
	public static ArrayList<String> getKategorijeOpasnostiString(ArrayList<String> idKategorija) {
		ArrayList<String> rez = new ArrayList<String>();
		Connection conn = null;
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = conn.prepareStatement(SQL_GET_KATEGORIJE_STRING);
			ResultSet rs = null;
			for (String s : idKategorija) {
				ps.setObject(1, Integer.parseInt(s));
				rs = ps.executeQuery();
				while (rs.next()) {
					rez.add(rs.getString("Kategorija"));
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return rez;
		}
		finally {
			connectionPool.checkIn(conn);
		}
		return rez;
				
	}
	
	public static ArrayList<String> getKategorijeOpasnostiStringById(int id) {
		ArrayList<String> rez = new ArrayList<String>();
		Connection conn = null;
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = conn.prepareStatement(SQL_GET_KATEGORIJE_STRING_BY_ID);
			ps.setObject(1, id);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				rez.add(rs.getString("Kategorija"));
			}
			
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return rez;
		}
		finally {
			connectionPool.checkIn(conn);
		}
		return rez;
				
	}

}
