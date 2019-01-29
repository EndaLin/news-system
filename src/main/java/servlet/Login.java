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

import dao.DBConnection;
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
		String username = request.getParameter("form-username");
		String form_password = request.getParameter("form-password");
		String ischeck = null;
		String sql = "select * from user where account like ?";
		Connection con = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		HttpSession session = request.getSession();
		try {
			byte[] resultBytes;
			resultBytes = MD5.eccrypt(form_password);   //加密
			String msg = new String(resultBytes);
			String password = Base64.getBase64(msg);  //修改编码
			ps = con.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()) {
				if (password.equals(rs.getString("password"))) {
					if (rs.getString("ischeck").equals("1")) {
						session.setAttribute("user", username);
						session.setAttribute("status1", rs.getString("status"));
						RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
						rd.forward(request, response);
						return;
					} else if (rs.getString("ischeck").equals("-1")){
						session.setAttribute("mess", "该账号已被停用！");
						RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
						rd.forward(request, response);
						return;
					} else {
						session.setAttribute("mess", "该账号正在审核中！");
						RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
						rd.forward(request, response);
						return;
					}
				} else {
					session.setAttribute("mess", "密码错误！");
					RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
					rd.forward(request, response);
					return;
				}

			} else {
				session.setAttribute("mess", "账号不存在");
				RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
				rd.forward(request, response);
				return;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnection.free(con, ps, null);
		}
	}

}
