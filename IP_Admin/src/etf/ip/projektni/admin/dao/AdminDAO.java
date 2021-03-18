package etf.ip.projektni.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import etf.ip.projektni.admin.dto.Admin;

public class AdminDAO  {

	
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	
	private static final String SQL_SELECT_ADMIN = "Select ime,prezime,username,password from admin where username = ? and password = ?";
	
	public static Admin login(String username, String password) {
		Admin admin = null;
		Connection conn = null;
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ADMIN);
			ps.setObject(1, username);
			ps.setObject(2, password);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				admin = new Admin(rs.getString("username"),rs.getString("password"),rs.getString("ime"),rs.getString("prezime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return admin;
		}
		finally {
			connectionPool.checkIn(conn);
		}
		return admin;		
	}

}
