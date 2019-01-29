package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dao.DBConnection;

public class MotifyPasswordService {
	public static boolean change(String account, String form_password) {
		Connection con = DBConnection.getConnection();
		PreparedStatement ps = null;
		String sql = "update user set password = ? where account like ?";
		byte[] resultBytes;
		try {
			resultBytes = MD5.eccrypt(form_password);   //加密
			String msg = new String(resultBytes);
			String password = Base64.getBase64(msg);  //修改编码
			ps = con.prepareStatement(sql);
			ps.setString(1, password);
			ps.setString(2, account);
			ps.executeUpdate();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnection.free(con, ps, null);
		}
		return false;
	}
}
