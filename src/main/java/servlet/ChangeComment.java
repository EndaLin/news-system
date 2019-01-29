package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DBConnection;
import domain.Comment;

/**
 * Servlet implementation class ChangeComment
 */
@WebServlet("/ChangeComment")
public class ChangeComment extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChangeComment() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		int id = Integer.valueOf(request.getParameter("id"));
		String step = request.getParameter("step");
		HttpSession session = request.getSession();
		ArrayList<Comment> list = (ArrayList<Comment>)session.getAttribute("clist");
		if (step.equals("1")) {
			String content = request.getParameter("content");
			String sql = "update comment set content = ? where cid = ?";
			String author = request.getParameter("author");
			String time = request.getParameter("time");
			Connection con = DBConnection.getConnection();
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(sql);
				ps.setString(1, content);
				ps.setInt(2, id);
				ps.executeUpdate();
				Comment c = new Comment(id, content, author, time, 0);
				for(int i = 0;i < list.size();i++) {
					if(list.get(i).getCid() == id) {
						list.set(i, c);
						break;
					}
				}
				response.sendRedirect("/NewsSys/showNew.jsp");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				DBConnection.free(con, ps, rs);
			}
		} else {
			String sql = "delete from comment where cid = ?";
			Connection con = DBConnection.getConnection();
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(sql);
				ps.setInt(1, id);
				ps.executeUpdate();
				for (Iterator<Comment> iter = list.iterator(); iter.hasNext();)
				{
					Comment c = iter.next();
					if(c.getCid() == id)
						iter.remove();   //注意这个地方
				}
				response.sendRedirect("/NewsSys/showNew.jsp");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				DBConnection.free(con, ps, rs);
			}
		}
	}

}
