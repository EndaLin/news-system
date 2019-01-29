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
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DBConnection;
import domain.YearOfNum;

/**
 * Servlet implementation class StaticOfYear
 */
@WebServlet("/StaticOfYear")
public class StaticOfYear extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StaticOfYear() {
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
		HttpSession session = request.getSession();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String time1 = df.format(new Date());
		Calendar calendar = Calendar.getInstance();
		int num = 0;
		calendar.setTime(new Date());
		int year1 = calendar.get(Calendar.YEAR);
		int month1 = calendar.get(Calendar.MONTH) + 1;
		calendar.add(Calendar.MONTH, -11);
		String time2 = df.format(calendar.getTime());
		int year2 = calendar.get(Calendar.YEAR);
		int month2 = calendar.get(Calendar.MONTH) + 1;
		ArrayList<YearOfNum> list = new ArrayList<YearOfNum>(12);
		Map<String, Integer> map = new HashMap<String, Integer>(); //为提高检索速度
		list.set(0, new YearOfNum(year1, month1, 0));
		list.set(0, new YearOfNum(year2, month2, 0));
		for(int i = 1;i < list.size() - 1;i++) {
			if(month1 != 12) {
				month1++;
				list.set(0, new YearOfNum(year1, month1, 0));
			} else {
				month1 = 1;
				year1 ++;
				list.set(0, new YearOfNum(year1, month1, 0));
			}
		}
		String sql = "SELECT COUNT(nid) num, YEAR(a.time) year, MONTH(a.time) month\r\n" +
				"FROM user LEFT OUTER JOIN (SELECT * FROM COMMENT WHERE time BETWEEN ? AND ? )  a ON user.`account` LIKE a.`author`\r\n" +
				"GROUP BY YEAR(a.time), MONTH(a.time)\r\n" +
				"ORDER BY num DESC";
		Connection con = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, time1);
			ps.setString(2, time2);
			rs = ps.executeQuery();
			while(rs.next()) {
				map.put(String.valueOf(rs.getInt("year")) + String.valueOf(rs.getInt("month")), rs.getInt("num"));
			}
			for(int i = 1;i < list.size() - 1;i++) {
				String mess = String.valueOf(list.get(i).getYear()) + String.valueOf(list.get(i).getMonth());
				if(map.get(mess) != null) {
					num = map.get(mess);
				} else {
					num = 0;
				}
				list.get(i).setNum(num);
			}
			session.setAttribute("slist3", list);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
