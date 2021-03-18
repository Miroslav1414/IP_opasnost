package etf.ip.projektni.dao;

import java.sql.*;

public class DAOUtil {

	public static PreparedStatement preparedStatement(Connection conn, String sql, 
			boolean returnGeneratedKeys,Object... values) throws SQLException {

		PreparedStatement ps = conn.prepareStatement(sql,returnGeneratedKeys ? 1:2);
		
		for(int i = 0; i < values.length; i ++)
			ps.setObject(1+ i, values[i]);
		
		return ps;
		
	}
}
