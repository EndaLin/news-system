package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import dao.DBConnection;
import domain.Users;
import service.Base64;
import service.MD5;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String form_password = request.getParameter("password");

		System.out.println("user login:" + username);
		String ischeck = null;
		String sql = "select * from user where account like ?";
		Connection con = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		HttpSession session = request.getSession();
		String message = null;
		Users user = new Users();
		try {
			byte[] resultBytes;
			//加密
			resultBytes = MD5.eccrypt(form_password);
			String msg = new String(resultBytes);
			//修改编码
			String password = Base64.getBase64(msg);
			ps = con.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()) {
				if (password.equals(rs.getString("password"))) {
					if (rs.getString("ischeck").equals("1")) {
						//session.setAttribute("user", username);
						//session.setAttribute("status1", rs.getString("status"));
//						RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
//						rd.forward(request, response);
						user.setAccount(username);
						user.setStatus(rs.getString("status"));
						response.getWriter().println(JSONObject.toJSONString(user));
						return;
					} else if (rs.getString("ischeck").equals("-1")){
						message = "该账号已被停用！";
						session.setAttribute("mess", "该账号已被停用！");
					} else {
						message = "该账号正在审核中！";
						session.setAttribute("mess", "该账号正在审核中！");
					}
				} else {
					message = "密码错误！";
					session.setAttribute("mess", "密码错误！");
				}

			} else {
				message = "账号不存在";
				session.setAttribute("mess", "账号不存在");
			}
			response.getWriter().println(message);
			/*RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
			rd.forward(request, response);
			return;*/

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnection.free(con, ps, null);
		}
	}

}
