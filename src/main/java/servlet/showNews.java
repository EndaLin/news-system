package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import dao.DBConnection;
import domain.Comment;
import domain.Content;

/**
 * Servlet implementation class showNews
 */
@WebServlet("/showNews")
public class showNews extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public showNews() {
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
		int id = Integer.valueOf(request.getParameter("id"));
		int cid = 1;
		int ischange = 0;
		String title = null;
		String author = null;
		String time = null;
		String content = null;
		String sql = "select * from new where nid = ?";
		String sql2 = "select * from comment where nid = ?";
		String sql3 = "update comment set ischange = 1 where cid = ?";
		Connection con = DBConnection.getConnection();
		PreparedStatement ps = null, ps2 = null;
		ResultSet rs = null;
		List<Comment> list = new ArrayList<>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date that_time = null;
		Date now_Time = null;
		long timeSpan = 0;
		try {
			//获取文章内容
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while(rs.next()) {
				title = rs.getString("title");
				author = rs.getString("author");
				time = rs.getString("time");
				content = rs.getString("content");
			}
//			((HttpServletRequest) request).getSession().setAttribute("this_id", id);
//			((HttpServletRequest) request).getSession().setAttribute("this_title", title);
//			((HttpServletRequest) request).getSession().setAttribute("this_author", author);
//			((HttpServletRequest) request).getSession().setAttribute("this_time", time);
//			((HttpServletRequest) request).getSession().setAttribute("this_content", content);
			Content content1 = new Content();
			content1.setId(String.valueOf(id));
			content1.setTitle(title);
			content1.setTime(time);
			content1.setAuthor(author);
			content1.setContent(content);
			//获取文章下面的评论
			ps = con.prepareStatement(sql2);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				cid = rs.getInt("cid");
				content = rs.getString("content");
				author = rs.getString("author");
				ischange = rs.getInt("ischange");
				time = rs.getString("time");
				if(ischange == 0) {
					that_time = df.parse(time);
					now_Time = df.parse((df.format(new Date())));
					timeSpan = now_Time.getTime() - that_time.getTime();
					if(timeSpan > 300000) {
						ischange = 1;
						ps2 = con.prepareStatement(sql3);
						ps2.setInt(1, cid);
						ps2.executeUpdate();
					}
				}
				list.add(new Comment(cid, content, author, time, ischange));
			}
			//((HttpServletRequest) request).getSession().setAttribute("clist", list);
			//RequestDispatcher rd = request.getRequestDispatcher("/showNew.jsp");
			//rd.forward(request, response);
			content1.setListComment(list);
			System.out.println(JSONObject.toJSONString(content1));
			response.getWriter().println(JSONObject.toJSONString(content1));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  finally {
			DBConnection.free(con, ps, rs);
			DBConnection.free(null, ps2, null);
		}
	}

}
