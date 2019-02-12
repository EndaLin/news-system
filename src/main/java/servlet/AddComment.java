package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DBConnection;
import domain.Comment;

/**
 * Servlet implementation class AddComment
 */
@WebServlet("/AddComment")
public class AddComment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComment() {
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
		String content = request.getParameter("content"); 
		//HttpSession session = request.getSession();
		//String author = (String) session.getAttribute("user");
		String author = request.getParameter("user");
		int nid = Integer.valueOf(request.getParameter("nid"));
		int id = 1;
		//设置时间格式
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String time = df.format(new Date());
	    String sql = "select max(cid) id from comment";
	    String sql2 = "insert into comment values (?,?,?,?,?,?)";
	    Connection con = DBConnection.getConnection();
	    PreparedStatement ps = null;
	    ResultSet rs = null;
       // ArrayList<Comment> list = (ArrayList<Comment>)session.getAttribute("clist");
	    try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				id = rs.getInt(id) + 1;
			}
			ps = con.prepareStatement(sql2);
			ps.setInt(1, id);
			ps.setString(2, content);
			ps.setString(3, author);
			ps.setString(4, time);
			ps.setInt(5, nid);
			ps.setInt(6, 0);
			ps.executeUpdate();
			//list.add(new Comment(id, content, author, time, 0));
			//session.setAttribute("clist", list);
			response.sendRedirect("showNew.html?id=" + nid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnection.free(con, ps, rs);
		}
	}

}
