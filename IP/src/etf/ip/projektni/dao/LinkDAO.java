package etf.ip.projektni.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import etf.ip.projektni.dto.Komentar;
import etf.ip.projektni.dto.Link;
import etf.ip.projektni.dto.Vijest;


public class LinkDAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_INSERT_LINKA = "Insert into link (id,link) values (?,?)";
	private static final String SQL_SELECT_LINK_ID = "Select v.id,naslov,tip,usernameAutora,datumObjave,link from link as l inner join vijest as v on l.id = v.id where v.id = ?";
			

	
	public static Link insertLink (String naslov, String username, String tip,Date datumObjave,String link) {
		Connection conn = null;
		
		Link noviLink = null;
		int idVijesti  = VijestDAO.insertVijest(naslov, username, tip, datumObjave);
		if (idVijesti > 0) {
			try {
				conn = connectionPool.checkOut();
				PreparedStatement ps = conn.prepareStatement(SQL_INSERT_LINKA);
				ps.setObject(1, idVijesti);
				ps.setObject(2, link);
				int rezz = ps.executeUpdate();
				if (rezz <1) 
					return null;
				else
				{
					noviLink = new Link(idVijesti,naslov,username,datumObjave,tip,link,null,null);
					noviLink.setAvatarKorisnika(UserDAO.getAvatarKorisnika(username));
				}
			}
			catch (Exception e) {
				
				e.printStackTrace();
				return noviLink;
			}
			finally {
				connectionPool.checkIn(conn);
			}
		}
		return noviLink;
	}
	
	public static Link selectLinkId(int id){
		Link link = null;
		Connection conn = null;
		try {
			conn = connectionPool.checkOut();
			PreparedStatement ps = conn.prepareStatement(SQL_SELECT_LINK_ID);
			ps.setObject(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {				
				link  = new Link(rs.getInt(1),rs.getString(2),rs.getString(4),rs.getTimestamp(5),rs.getString(3),rs.getString(6),UserDAO.getAvatarKorisnika(rs.getString(4)),null);
				//link.setKomentari(KomentarDAO.getKomentariAll(rs.getInt(1)));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally {
			connectionPool.checkIn(conn);
		}
		return link;
	}
	

}
