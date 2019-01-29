package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DBConnection;

/**
 * Servlet implementation class MotifyNews
 */
@WebServlet("/MotifyNews")
public class MotifyNews extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MotifyNews() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String step = request.getParameter("step");
		String id = request.getParameter("id");
		String title = null;
		String content = null;
		String sql = null;
		Connection con = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		HttpSession session = request.getSession();
		try {
			if(step.equals("1")) {
				sql = "select * from new where nid = ?";
				ps = con.prepareStatement(sql);
				ps.setInt(1, Integer.valueOf(id));
				rs = ps.executeQuery();
				while(rs.next()) {
					title = rs.getString("title");
					content = rs.getString("content");
				}
				session.setAttribute("myID", id);
				session.setAttribute("myTitle", title);
				session.setAttribute("myContent", content);
				response.sendRedirect("./ueditor/motifyNew.jsp");
			} else {
				sql = "update new set title=?, content=?, time=?, ischeck=? where nid =?";
				title = request.getParameter("title");
				content = request.getParameter("content");
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
				String time = df.format(new Date());
				ps = con.prepareStatement(sql);
				ps.setString(1, title);
				ps.setString(2, content);
				ps.setString(3, time);
				ps.setString(4, "0");
				ps.setInt(5, Integer.valueOf(id));
				ps.executeUpdate();
				session.setAttribute("mess", "修改成功！待审核后便可发布");
				response.sendRedirect("./ueditor/motifyNew.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.free(con, ps, rs);
		}
	}

}
