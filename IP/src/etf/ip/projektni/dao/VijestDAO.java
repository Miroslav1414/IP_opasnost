package etf.ip.projektni.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import etf.ip.projektni.dto.Vijest;

public class VijestDAO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	public static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_INSERT_VIJESTI = "Insert into Vijest (naslov,usernameAutora,tip,datumObjave) values (?,?,?,?)";
	private static final String SQL_GET_ALL_VIJESTI = "Select Id,datumObjave,Tip from Vijest";
	private static final String SQL_GET_ID_VIJESTI = "select id from Vijest";
	private static final String SQL_GET_ID_VIJESTI_UPDATEKOMENTARA = "select distinct idVijesti from  komentar;";
	private static final String SQL_GET_ALL_NOTIFICATION = "select v.id ,datumObjave from Vijest v inner join opasnost o "
			+ " on v.id = o.id where tip ='O' and hitna = 1";
	
	//vraca ID vijesti ako je upisana uspjesno a ako nije onda vraca -1
	public static int insertVijest (String naslov, String username,String tip,Date datumObjave) {
		Connection conn = null;
		int rez = 0;
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = conn.prepareStatement(SQL_INSERT_VIJESTI,Statement.RETURN_GENERATED_KEYS);
			ps.setObject(1, naslov);
			ps.setObject(2, username);
			ps.setObject(3, tip);
			ps.setObject(4, datumObjave);
			int rezz = ps.executeUpdate();
			if (rezz <1) 
				rez = -1;
			ResultSet rs = ps.getGeneratedKeys();
			
			if (rs.next()) {
				rez = rs.getInt(1);
			}
		}
		catch (Exception e) {
			rez = -1;
			e.printStackTrace();
		}
		finally {
			connectionPool.checkIn(conn);
		}
		return rez;
	}
	
	public static ArrayList<Vijest> selectAlLNotifications(){
		Connection conn = null;
		ArrayList<Vijest> rez = new ArrayList<Vijest>();
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = conn.prepareStatement(SQL_GET_ALL_NOTIFICATION);			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				rez.add(new Vijest(rs.getInt(1),null,null,rs.getTimestamp(2),"O",null,null));
			}
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
	
	
	public static ArrayList<Vijest> selectALLVijesti () {
		Connection conn = null;
		ArrayList<Vijest> rez = new ArrayList<Vijest>();
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = conn.prepareStatement(SQL_GET_ALL_VIJESTI);			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				rez.add(new Vijest(rs.getInt(1),null,null,rs.getTimestamp(2),rs.getString(3),null,null));
			}
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

	
	public static ArrayList<Vijest> selectALLVijestiID () {
		Connection conn = null;
		ArrayList<Vijest> rez = new ArrayList<Vijest>();
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = conn.prepareStatement(SQL_GET_ID_VIJESTI);			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Vijest temp = new Vijest();
				temp.setId(rs.getInt(1));
				rez.add(temp);
			}
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
	
	public static ArrayList<Vijest> selectALLVijestiID_UpdateKomentara () {
		Connection conn = null;
		ArrayList<Vijest> rez = new ArrayList<Vijest>();
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = conn.prepareStatement(SQL_GET_ID_VIJESTI_UPDATEKOMENTARA);			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Vijest temp = new Vijest();
				temp.setId(rs.getInt(1));
				rez.add(temp);
			}
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
