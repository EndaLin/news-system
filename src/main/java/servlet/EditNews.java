package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DBConnection;

/**
 * Servlet implementation class EditNews
 */
@WebServlet("/EditNews")
public class EditNews extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditNews() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int id = 1;
		int ischeck = 0;
		String type = request.getParameter("type");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String author = (String) request.getSession().getAttribute("user");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String time = df.format(new Date());
		String sql = "insert into new values (?,?,?,?,?,?,?)";
		String sql2 = "select max(nid) id from new";
		Connection con = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sql2);
			rs = ps.executeQuery();
			if(rs.next()) {
				id = Integer.valueOf(rs.getInt("id")) + 1;
			} else {
				id = 1;
			}
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, title);
			ps.setString(3, content);
			ps.setString(4, author);
			ps.setString(5, time);
			ps.setString(6, type);
			ps.setInt(7, ischeck);
			ps.executeUpdate();
			request.getSession().setAttribute("mess", "保存成功，审核通过后会给予发布！");
			response.sendRedirect("./ueditor/editNew.jsp");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnection.free(con, ps, null);
		}
	}

}
