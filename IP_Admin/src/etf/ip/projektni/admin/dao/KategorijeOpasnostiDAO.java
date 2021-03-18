package etf.ip.projektni.admin.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.mysql.cj.xdevapi.Statement;

import etf.ip.projektni.admin.dto.KategorijeOpasnosti;

public class KategorijeOpasnostiDAO implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	public static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	
	public static final String SQL_GET_KATEGORIJE = "Select * from kategorijeOpasnosti";
	public static final String SQL_BLOKIRAJ_KATEGORIJE = "update kategorijeOpasnosti set blokirana = ? where id = ?";
	public static final String SQL_INSERT_KATEGORIJE = "Insert into kategorijeOpasnosti (kategorija) values (?)";
	
	public static ArrayList<KategorijeOpasnosti> getAll() {
		ArrayList<KategorijeOpasnosti> rez = new ArrayList<KategorijeOpasnosti>();
		Connection conn = null;
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = conn.prepareStatement(SQL_GET_KATEGORIJE);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				rez.add(new KategorijeOpasnosti(rs.getInt("Id"),rs.getString("kategorija"),rs.getBoolean("blokirana")));
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
	
	public static KategorijeOpasnosti dodajKategoriju(String kategorija) {
		KategorijeOpasnosti kat = null;
		Connection conn = null;
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = conn.prepareStatement(SQL_INSERT_KATEGORIJE,java.sql.Statement.RETURN_GENERATED_KEYS);
			ps.setObject(1, kategorija);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			while (rs.next()) {
				kat = new KategorijeOpasnosti(rs.getInt(1),kategorija,false);
			}
			}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally {
			connectionPool.checkIn(conn);
		}
		return kat;
		
	}
	
public static void blokirajKategoriju(int id, boolean blokiraj) {
		
		Connection conn = null;
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = conn.prepareStatement(SQL_BLOKIRAJ_KATEGORIJE);
			if (blokiraj)
				ps.setObject(1, 1);
			else
				ps.setObject(1, 0);
			ps.setObject(2, id);
			ps.executeUpdate();
			}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			connectionPool.checkIn(conn);
		}
		
	}
	
	
}
