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

import com.alibaba.fastjson.JSONObject;
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
		ArrayList<YearOfNum> list = new ArrayList<YearOfNum>();
		Map<String, Integer> map = new HashMap<String, Integer>(); //为提高检索速度
		for(int i = 1;i <= 12;i++) {
			list.add(new YearOfNum(0, 0, 0));
		}
		list.get(0).setYear(year2);
		list.get(0).setMonth(month2);
		list.get(11).setYear(year1);
		list.get(11).setMonth(month1);
		for(int i = 1;i < list.size() - 1;i++) {
			if(month2 != 12) {
				month2++;
				list.get(i).setYear(year2);
				list.get(i).setMonth(month2);
			} else {
				month2 = 1;
				year2 ++;
				list.get(i).setYear(year2);
				list.get(i).setMonth(month2);
			}
		}
		String sql = "SELECT COUNT(nid) num, YEAR(a.time) YEAR, MONTH(a.time) MONTH\n" +
				"                FROM (SELECT * FROM comment WHERE TIME BETWEEN ? AND ? ) a\n" +
				"                GROUP BY YEAR(a.time), MONTH(a.time)\n" +
				"                ORDER BY num DESC";
		Connection con = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, time2);
			ps.setString(2, time1);
			rs = ps.executeQuery();
			while(rs.next()) {
				map.put(String.valueOf(rs.getInt("year")) + String.valueOf(rs.getInt("month")), rs.getInt("num"));
			}
			for(int i = 1;i < list.size();i++) {
				String mess = String.valueOf(list.get(i).getYear()) + String.valueOf(list.get(i).getMonth());
				if(map.get(mess) != null) {
					num = map.get(mess);
				} else {
					num = 0;
				}
				list.get(i).setNum(num);
			}
			System.out.println(JSONObject.toJSONString(list));
			response.getWriter().println(JSONObject.toJSONString(list));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
