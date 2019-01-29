package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import dao.DBConnection;
import domain.Message;
import service.Base64;
import service.MD5;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Register() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String account = request.getParameter("form-account");
		String password = request.getParameter("form-password");
		String status = request.getParameter("status");
		String code = (request.getParameter("code")).toUpperCase();
		String email = request.getParameter("email");
		String realCode = (String) request.getSession().getAttribute("validateCode");
		Message mess = new Message();
		if(!code.equals(realCode)) {
			mess.setSuccess(false);
			mess.setMess("验证码错误！");
			response.getWriter().println(JSONObject.toJSONString(mess));
			return;
		}
		int ischeck = 0;
		String sql = "insert into user values (?,?,?,?,?,?,?,?)";
		if(status.equals("3")) {
			ischeck = 1;
			mess.setMess("注册成功！");
		} else {
			ischeck = 0;
			mess.setMess("注册申请已提交，请静待审核");
		}
		mess.setSuccess(true);
		Connection con = DBConnection.getConnection();
		PreparedStatement ps = null;
		try {
			byte[] resultBytes;
			//加密
			resultBytes = MD5.eccrypt(password);
			String msg = new String(resultBytes);
			ps = con.prepareStatement(sql);
			ps.setString(1, account);
			//更改编码格式后录入
			ps.setString(2, Base64.getBase64(msg));
			ps.setInt(3, Integer.valueOf(status));
			ps.setInt(4, ischeck);
			ps.setString(5, "default.png");
			ps.setInt(6, 0);
			ps.setString(7, email);
			ps.setString(8, null);
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mess.setMess("该账号已经存在！");
		}  finally {
			DBConnection.free(con, ps, null);
			response.getWriter().println(JSONObject.toJSONString(mess));
		}


	}

}
