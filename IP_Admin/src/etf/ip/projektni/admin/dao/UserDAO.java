package etf.ip.projektni.admin.dao;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Random;

import etf.ip.projektni.admin.dto.User;



public class UserDAO implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	public static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();

	public static final String SQL_COUNT_USERS = "select count(*) as BrojRegistrovanih from user where Blocked = 0 and   RegistrationApproved = 1";
	public static final String SQL_WAITING_APPROVEAL ="select * from user where Blocked = 0 and   RegistrationApproved =0";
	public static final String SQL_ALL ="select * from user";
	public static final String SQL_APPROVE_REG = "update user set RegistrationApproved = 1 where username = ?";
	public static final String SQL_BLOK = "update user set blocked = 1 where username = ?";
	public static final String SQL_CHANGE_PASSWORD = "update user set password = ? where username = ?";
	public static final String SQL_DAILY_LOG = "select count(*) as 'count' from (\r\n" + 
			"select username, count(*)  from userlog \r\n" + 
			"where date(login) = curdate()\r\n" + 
			"and hour(login) <= ? \r\n" + 
			"and (hour(logout) >= ? or logout is null)\r\n" + 
			"group by username) as a";
	public static final String SQL_TRENUTNO_PRIJAVLJENI = "select count(*) from userlog where logout is null";
	
	public static int getTrenutnoPrijavljeni() {
		int rez = 0;
		Connection conn = null;
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = conn.prepareStatement(SQL_TRENUTNO_PRIJAVLJENI);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				rez = rs.getInt(1);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return 0;			
		}
		finally {
			connectionPool.checkIn(conn);
		}
		return rez;
	}
	
	public static int getRegisteredUsersCount() {
		int rez = 0;
		Connection conn = null;
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = conn.prepareStatement(SQL_COUNT_USERS);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				rez = rs.getInt(1);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return 0;			
		}
		finally {
			connectionPool.checkIn(conn);
		}
		return rez;
	}
	
	//citanje slike iz baze i pretvaranje u base 64 string
	public static String getImage (String username) {
			Connection conn = null;
			try {
				conn = connectionPool.checkOut();
				PreparedStatement ps = conn.prepareStatement("Select ProfilePicture from user where username = ?", Statement.NO_GENERATED_KEYS);
				ps.setObject(1, username);
				ResultSet rs = ps.executeQuery();
				if(rs.next()) {
					Blob blob = rs.getBlob("profilePicture");

					InputStream inputStream = blob.getBinaryStream();
	                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	                byte[] buffer = new byte[4096];
	                int bytesRead = -1;
	                 
	                while ((bytesRead = inputStream.read(buffer)) != -1) {
	                    outputStream.write(buffer, 0, bytesRead);                  
	                }
	                 
	                byte[] imageBytes = outputStream.toByteArray();
	                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
	                 
	                inputStream.close();
	                outputStream.close();
	                return base64Image;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			finally {
				connectionPool.checkIn(conn);
			}
			return null;
		}
	
	public static ArrayList<User> getRegisteredUsers() {
		ArrayList<User> rez = new ArrayList<User>();
		Connection conn = null;
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = conn.prepareStatement(SQL_WAITING_APPROVEAL);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String slika = getImage ( rs.getString("username"));
				
				User user = new User(rs.getString("username"), rs.getString("password"), rs.getString("FirstName"), rs.getString("LastName"),
						rs.getString("email"), slika, rs.getString("state"), rs.getString("region"), rs.getString("city"), rs.getString("flag"), rs.getString("emailNotification"),
						rs.getString("appNotification"), rs.getInt("loginCounter") + 1,rs.getBoolean("Blocked"),rs.getBoolean("RegistrationApproved"));
				rez.add(user);
				
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
	
	public static ArrayList<User> getAllUsers() {
		ArrayList<User> rez = new ArrayList<User>();
		Connection conn = null;
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = conn.prepareStatement(SQL_ALL);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				//String slika = getImage ( rs.getString("username"));
				
				User user = new User(rs.getString("username"), rs.getString("password"), rs.getString("FirstName"), rs.getString("LastName"),
						rs.getString("email"), null, rs.getString("state"), rs.getString("region"), rs.getString("city"), rs.getString("flag"), rs.getString("emailNotification"),
						rs.getString("appNotification"), rs.getInt("loginCounter") + 1,rs.getBoolean("Blocked"),rs.getBoolean("RegistrationApproved"));
				rez.add(user);
				
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
	
	//Odobravanje registracije
	public static void approveReg (String username) {
		Connection conn = null;
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps  = conn.prepareStatement(SQL_APPROVE_REG );
			ps.setObject(1, username);
			ps.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			connectionPool.checkIn(conn);
		}
	}
	
	public static void blockKorisnika(String username) {
		Connection conn = null;
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps  = conn.prepareStatement(SQL_BLOK);
			ps.setObject(1, username);
			ps.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			connectionPool.checkIn(conn);
		}
	}
	
	
	public static void resetujLozinku(String username,String password) {
		Connection conn = null;		
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = conn.prepareStatement(SQL_CHANGE_PASSWORD);
			ps.setObject(1, password);
			ps.setObject(2, username);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		finally {
			connectionPool.checkIn(conn);
		}
	}
	
	public static ArrayList<Number> logovaniKorisniciUDanu(){
		ArrayList<Number> rez = new ArrayList<Number>();
		Connection conn = null;		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = conn.prepareStatement(SQL_DAILY_LOG);
			ResultSet rs = null;
			Date datum = new Date();
			@SuppressWarnings("deprecation")
			int sat = datum.getHours();
			for (int i=0; i<=sat; i++) {
				ps.setObject(1, i);
				ps.setObject(2, i);
				rs = ps.executeQuery();
				while (rs.next()) {
					rez.add(rs.getInt(1));
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally {
			connectionPool.checkIn(conn);
		}
		return rez;
	}
	
	
}
