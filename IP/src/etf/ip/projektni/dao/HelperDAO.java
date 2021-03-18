package etf.ip.projektni.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import etf.ip.projektni.dto.Vijest;



public class HelperDAO implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	public static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	
	public static final String SQL_GET_RANDOM_GRADOVE = "SELECT CONVERT(city USING utf8)  FROM gradovi where iso2 = ? ORDER BY RAND() limit 3";
	
	
	public static String[] getRandomGradove(String countryCode) {
		String[] rez = new String[3];
		Connection conn = null;
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = conn.prepareStatement(SQL_GET_RANDOM_GRADOVE);
			ps.setObject(1, countryCode);
			ResultSet rs = ps.executeQuery();
			int i = 0;
			while (rs.next() || i<3) {
				rez[i++] = rs.getString(1);
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
