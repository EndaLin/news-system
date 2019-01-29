package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dao.DBConnection;

public class BindQQService {
	public static void bind(String account, String openid) {
         Connection con = DBConnection.getConnection();
         PreparedStatement ps = null;
         String sql = "update user set openid = ? where account like ?";
         try {
			ps = con.prepareStatement(sql);
			ps.setString(1, openid);
			ps.setString(2, account);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnection.free(con, ps, null);
		}
	}
}
