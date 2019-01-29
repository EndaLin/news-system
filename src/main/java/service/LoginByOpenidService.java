package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DBConnection;
import domain.Users;

public class LoginByOpenidService {
	public static Users find(String openid) {
		Connection con = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from user where openid like ?";
		Users user = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, openid);
			rs = ps.executeQuery();
			if(rs.next()) {
				user = new Users(rs.getString("status"), rs.getString("account"), rs.getString("sex"), rs.getString("ischeck"));
			}
			return user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnection.free(con, ps, rs);
		}
		return user;

	}
}
