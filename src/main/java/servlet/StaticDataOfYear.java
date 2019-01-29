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

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dao.DBConnection;
import domain.Static;
import domain.YearOfNum;

/**
 * Servlet Filter implementation class StaticDataOfYear
 */
@WebFilter(dispatcherTypes = {
		DispatcherType.REQUEST,
		DispatcherType.FORWARD,
		DispatcherType.INCLUDE,
		DispatcherType.ERROR
}
		, urlPatterns = { "/StaticOfYear.jsp" })
public class StaticDataOfYear implements Filter {

	/**
	 * Default constructor.
	 */
	public StaticDataOfYear() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		// pass the request along the filter chain
		HttpServletRequest hsr = (HttpServletRequest)request;
		HttpSession session = hsr.getSession();
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
		String sql = "SELECT COUNT(nid) num, YEAR(a.time) year, MONTH(a.time) month\r\n" +
				"FROM user LEFT OUTER JOIN (SELECT * FROM comment WHERE time BETWEEN ? AND ? )  a ON user.`account` LIKE a.`author`\r\n" +
				"GROUP BY YEAR(a.time), MONTH(a.time)\r\n" +
				"ORDER BY num DESC";
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
			session.setAttribute("slist3", list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnection.free(con, ps, rs);
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
