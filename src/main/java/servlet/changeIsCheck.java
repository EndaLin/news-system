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

import dao.DBConnection;

/**
 * Servlet implementation class changeIsCheck
 */
@WebServlet("/changeIsCheck")
public class changeIsCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public changeIsCheck() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String account = request.getParameter("account");
	    int ischeck = Integer.valueOf(request.getParameter("check"));
	    Connection con = DBConnection.getConnection();
	    PreparedStatement ps = null;
	    String sql = "update user set ischeck = ? where account like ?";
	    try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, ischeck);
			ps.setString(2, account);
			ps.executeUpdate();
			response.getWriter().println("1");
			//RequestDispatcher rd = request.getRequestDispatcher("/showAllUsers.jsp");
			//rd.forward(request, response);
		} catch (SQLException e) {
			response.getWriter().println("-1");
			e.printStackTrace();
		} finally {
			DBConnection.free(con, ps, null);
		}
	    
	}

}
