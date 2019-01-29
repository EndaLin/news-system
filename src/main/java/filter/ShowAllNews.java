package filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import dao.DBConnection;
import domain.News;

/**
 * Servlet Filter implementation class ShowAllNews
 */
@WebFilter(dispatcherTypes = { DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE,
		DispatcherType.ERROR }, urlPatterns = { "/index.jsp" })
public class ShowAllNews implements Filter {

	/**
	 * Default constructor.
	 */
	public ShowAllNews() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		// pass the request along the filter chain
		ArrayList<News> list1 = new ArrayList<>();
		ArrayList<News> list2 = new ArrayList<>();
		ArrayList<News> list3 = new ArrayList<>();
		ArrayList<News> list4 = new ArrayList<>();
		int id;
		String title = null;
		String author = null;
		String time = null;
		String ischeck = "1";
		String type = null;
		String sql = "select * from new where ischeck = 1";
		Connection con = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				id = rs.getInt("nid");
				title = rs.getString("title");
				author = rs.getString("author");
				time = rs.getString("time");
				type = rs.getString("type");
				if (type.equals("国际")) {
					list1.add(new News(id, title, author, time, ischeck));
				} else if (type.equals("社会")) {
					list2.add(new News(id, title, author, time, ischeck));
				} else if (type.equals("体育")) {
					list3.add(new News(id, title, author, time, ischeck));
				} else {
					list4.add(new News(id, title, author, time, ischeck));
				}
			}
			((HttpServletRequest) request).getSession().setAttribute("nlist1", list1);
			((HttpServletRequest) request).getSession().setAttribute("nlist2", list2);
			((HttpServletRequest) request).getSession().setAttribute("nlist3", list3);
			((HttpServletRequest) request).getSession().setAttribute("nlist4", list4);
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
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
