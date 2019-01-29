package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DBConnection;
import domain.Static;

/**
 * Servlet implementation class StaticData
 */
@WebServlet("/StaticData")
public class StaticData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StaticData() {
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
		String status = null;
		String author = null;
		int num = 0;
		String step = request.getParameter("time");
		//设置日期格式
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String time1 = df.format(new Date());
		String time2 = null;
		Calendar calendar = Calendar.getInstance();
		Connection con = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Static> list1 = new ArrayList<Static>();
		ArrayList<Static> list2 = new ArrayList<Static>();
		String sql = "SELECT COUNT(nid) num, user.`account`\r\n" + 
				"FROM user LEFT OUTER JOIN (SELECT * FROM new WHERE time BETWEEN ? AND ? )  a ON user.`account` LIKE a.`author`\r\n" + 
				"WHERE user.`status` = 2\r\n" + 
				"GROUP BY user.`account`\r\n" + 
				"ORDER BY num DESC";
		String sql2 = "SELECT COUNT(nid) num, user.`account`, user.status\r\n" + 
				"FROM user LEFT OUTER JOIN (SELECT * FROM comment WHERE time BETWEEN ? AND ? )  a ON user.`account` LIKE a.`author`\r\n" + 
				"GROUP BY user.`account`\r\n" + 
				"ORDER BY num DESC";
		calendar.setTime(new Date());
		if(step.equals("1")) {
			calendar.add(Calendar.DATE, -7);
		} else if(step.equals("2")) {
			calendar.add(Calendar.MONTH, -1);
		} else {
			calendar.add(Calendar.YEAR, -1);
		}
		time2 = df.format(calendar.getTime());
		try {
			//获取新闻的统计量
			ps = con.prepareStatement(sql);
			ps.setString(1, time2);
			ps.setString(2, time1);
			rs = ps.executeQuery();
			while(rs.next()) {
				num = rs.getInt("num");
				author = rs.getString("account");
				list1.add(new Static("新闻发布员", author, num));
			}
			//获取评论的统计量
			ps = con.prepareStatement(sql2);
			ps.setString(1, time2);
			ps.setString(2, time1);
			rs = ps.executeQuery();
			while(rs.next()) {
				switch (rs.getString("status")) {
				case "1":
					status = "管理员";
					break;
				case "2":
					status = "新闻发布员";
					break;
				case "3":
					status = "普通用户";
					break;
				}
				num = rs.getInt("num");
				author = rs.getString("account");
				list2.add(new Static(status, author, num));
			}
			HttpSession session = request.getSession();
			session.setAttribute("slist1", list1);
			session.setAttribute("slist2", list2);
			request.getRequestDispatcher("/static.jsp").forward(request, response);		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnection.free(con, ps, rs);
		}
	}

}
