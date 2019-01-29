package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DBConnection;

public class CheckEmailService {
	public static boolean check(String account, String email) {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select account from user where account like ? and email like ?";
        try {
			ps = con.prepareStatement(sql);
			ps.setString(1, account);
			ps.setString(2, email);
			rs = ps.executeQuery();
			if(rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnection.free(con, ps, rs);
		}
		return false;
	}
}
