package etf.ip.projektni.dao;

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

import etf.ip.projektni.dto.User;

public class UserDAO implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	public static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	public static final String  SQL_USERNAME_EXISTS = "select username from user where username = ?";
	public static final String 	SQL_EMAIL_EXISTS = "select email from user where email = ?";
	public static final String 	SQL_INSERT_USER = "insert into user (username,firstname,lastname,password,email) values (?,?,?,?,?)";
	public static final String  SQL_LOGIN = "SELECT * FROM user WHERE username=? AND password=?";
	public static final String  SQL_UPDATE_USER = "UPDATE User set Firstname =?,lastname=?,State=?,Region=?,City=?,EmailNotification=?,AppNotification=?, "
			+ " Flag =? where username=?";
	public static final String  SQL_UPDATE_COUNTER = "update user set LoginCounter = LoginCounter + 1 where username = ?";
	public static final String 	SQL_CHANGE_PASSWORD = "update user set password = ? where username = ?";
	public static final String  SQL_USER_EMAIL_URGENT = "select email from user where EmailNotification = 'on'";
	public static final String 	SQL_LOGIN_LOG = "insert into userLog (username,login,logout) values (?,current_timestamp(),null)";
	public static final String SQL_LOGOUT_LOG = "update userlog set logout = current_timestamp() where id = ?";
	public static final String SQL_GET_APP_NOTIFICATION = "select appNotification from user where username = ?";
	
	public static boolean getAppNotification(String username ) {
		boolean rez = false;
		Connection conn = null;
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = conn.prepareStatement(SQL_GET_APP_NOTIFICATION);
			ps.setObject(1, username);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				rez = (rs.getString(1) != null);
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
	
	public static int insertLogin(String username) {
		int rez = 0;
		Connection conn = null;
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = conn.prepareStatement(SQL_LOGIN_LOG,Statement.RETURN_GENERATED_KEYS);
			ps.setObject(1, username);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			while (rs.next())
				rez = rs.getInt(1);
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
	
	public static void insertLogout(int id) {
		Connection conn = null;
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = conn.prepareStatement(SQL_LOGOUT_LOG);
			ps.setObject(1, id);
			ps.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			connectionPool.checkIn(conn);
		}
	}
	
	
	public static ArrayList<String> getEmails(){
		ArrayList<String> rez = new ArrayList<String>();
		Connection conn = null;
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = conn.prepareStatement(SQL_USER_EMAIL_URGENT);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				rez.add(rs.getString(1));				
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
	
	//registracija korisnika
	public static boolean insert (User user) {
		Connection conn = null;
		boolean rez = false;
		Object values[] = {user.getUsername(),user.getFirstName(),user.getLastName(),user.getPassword(),user.getEmail()};
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = DAOUtil.preparedStatement(conn, SQL_INSERT_USER , false, values);
			ps.executeUpdate();
			if (ps.getUpdateCount() > 0)
				rez = true;			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			connectionPool.checkIn(conn);
		}
		return rez;
	}
	
	//provjera da li postoji korisnicko ime u bazi
	public static boolean usernameAvailable(String username) {
		boolean rez = true;
		Connection conn = null;
		ResultSet rs = null;
		Object values[] = {username};
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = DAOUtil.preparedStatement(conn, SQL_USERNAME_EXISTS, false, values);
			rs = ps.executeQuery();
			if (rs.next())
				rez = false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			connectionPool.checkIn(conn);
		}
		return rez;
	}
	
	//provjera da li postoji email u bazi
	public static boolean emailAvailable(String email) {
		boolean rez = true;
		Connection conn = null;
		ResultSet rs = null;
		Object values[] = {email};
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = DAOUtil.preparedStatement(conn, SQL_EMAIL_EXISTS, false, values);
			rs = ps.executeQuery();
			if (rs.next())
				rez = false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			connectionPool.checkIn(conn);
		}
		return rez;
	}
	
	//koristi se za login
	public static User selectByUsernameAndPassword(String username, String password){
		User user = null;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {username, password};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.preparedStatement(connection,	SQL_LOGIN, false, values);
			rs = pstmt.executeQuery();
			if (rs.next()){
				//uvecati login brojac
				PreparedStatement ps2 = connection.prepareStatement(SQL_UPDATE_COUNTER);
				ps2.setObject(1, username);
				ps2.executeUpdate();
				ps2.close();
				//ucitati sliku i pretvoriti blob u string base 64
				String slika = getImage ( username);
				
				user = new User(rs.getString("username"), rs.getString("password"), rs.getString("FirstName"), rs.getString("LastName"),
						rs.getString("email"), slika, rs.getString("state"), rs.getString("region"), rs.getString("city"), rs.getString("flag"), rs.getString("emailNotification"),
						rs.getString("appNotification"), rs.getInt("loginCounter") + 1,rs.getBoolean("Blocked"),rs.getBoolean("RegistrationApproved"));
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return user;
	}
	
	//izmjena podataka
	public static boolean updateUser (User user) {
		Connection conn = null;
		boolean rez = false;
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps  = conn.prepareStatement(SQL_UPDATE_USER,Statement.NO_GENERATED_KEYS );
			ps.setObject(1, user.getFirstName());
			ps.setObject(2, user.getLastName());
			ps.setObject(3, user.getState());
			ps.setObject(4, user.getRegion());
			ps.setObject(5, user.getCity());
			ps.setObject(6, user.getEmailNotification());
			ps.setObject(7, user.getAppNotification());
			ps.setObject(8, user.getFlag());
			ps.setObject(9, user.getUsername());
			int count = ps.executeUpdate();
			
			if (count <0) 
				System.out.println("greska prilikom azuriranja");
			else 
				rez = true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			connectionPool.checkIn(conn);
		}
		return rez;
	}
	
	
	//izmejna profilne slike
	public static String updateUserProfilePicture (String username, InputStream is,long duzinaSlike) {
		Connection conn = null;
		String rez = "";
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps  = conn.prepareStatement("Update user set profilePicture=? where username = ?",Statement.NO_GENERATED_KEYS );
			ps.setBinaryStream(1, is, duzinaSlike);
			ps.setString(2, username);
			int count = ps.executeUpdate();
			
			
			if (count <0)
			{
				System.out.println("greska prilikom updejta");
			}
				
			else {				
                rez = getImage(username);                
			}
				
			
		}
		catch (Exception e) {
			e.printStackTrace();
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
	
	public static boolean chagnePassword(String username, String password) {
		boolean rez = false;
		Connection conn = null;
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = conn.prepareStatement(SQL_CHANGE_PASSWORD);
			ps.setObject(1, password);
			ps.setObject(2, username);
			int count = ps.executeUpdate();
			if (count <0)
			{
				System.out.println("greska prilikom azuriranja lozinke");
			}
				
			else {				
                rez = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			connectionPool.checkIn(conn);
		}
		
		
		return rez;
	}
	
	public static String getFlag(String username) {
		Connection conn = null;
		String flag = "";
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = conn.prepareStatement("Select flag from user where username = ?", Statement.NO_GENERATED_KEYS);
			ps.setObject(1, username);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				flag = rs.getString("flag");

			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		finally {
			connectionPool.checkIn(conn);
		}
		return flag;
	}
	
	public static String getAvatarKorisnika(String username) {
		
		String avatar = getImage(username);
		if (avatar == null || avatar.length()< 3) {
			return getFlag(username);
		}
		
		return "data:image/jpg;base64," + avatar;
	}
}
