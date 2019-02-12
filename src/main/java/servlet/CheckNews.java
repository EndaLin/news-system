package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DBConnection;

/**
 * Servlet implementation class CheckNews
 */
@WebServlet("/CheckNews")
public class CheckNews extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CheckNews() {
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
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String check = request.getParameter("check");
		String id = request.getParameter("id");
		Connection con = DBConnection.getConnection();
		PreparedStatement ps = null;
		try {
			if (check.equals("0")) {
				String sql = "delete from new where nid = ?";
				ps = con.prepareStatement(sql);
				ps.setInt(1, Integer.valueOf(id));
				ps.executeUpdate();
			} else {
				String sql = "update new set ischeck = 1 where nid = ?";
				ps = con.prepareStatement(sql);
				ps.setInt(1, Integer.valueOf(id));
				ps.executeUpdate();
			}
			//RequestDispatcher rd = request.getRequestDispatcher("/showAllNews.jsp");
			//rd.forward(request, response);
			response.getWriter().println("1");
		} catch (Exception e) {
			response.getWriter().println("0");
			e.printStackTrace();
		}   finally {
			DBConnection.free(con, ps, null);
		}
	}

}
