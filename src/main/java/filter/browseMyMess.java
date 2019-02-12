package filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

/**
 * Servlet Filter implementation class browseMyMess
 */
@WebFilter(dispatcherTypes = {
				DispatcherType.REQUEST, 
				DispatcherType.FORWARD, 
				DispatcherType.INCLUDE, 
				DispatcherType.ERROR
		}
					, urlPatterns = { "/showMess.jsp" })
public class browseMyMess implements Filter {

    /**
     * Default constructor. 
     */
    public browseMyMess() {
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
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		// pass the request along the filter chain
		String sql = "select * from user where account like ?";
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        HttpServletRequest hsr = (HttpServletRequest) request;
        HttpSession session = hsr.getSession();
		//身份
        String status = null;
        String sex = null;
		//头像的虚拟地址
        String path = null;
          try {
			ps = con.prepareStatement(sql);
			ps.setString(1, (String) hsr.getSession().getAttribute("user"));
			rs = ps.executeQuery();
			while(rs.next()) {
				switch(rs.getString("status")) {
					case "1" : status = "管理员";break;
					case "2" : status = "新闻发布员";break;
					case "3" : status = "普通用户";break;
					default:break;
				}
				sex = rs.getString("sex").equals("0") ? "男" : "女";
				path =  rs.getString("path") == null ? "default.png" : rs.getString("path");
			}
			//System.out.println("face\\" + path);
			session.setAttribute("status", status);
			session.setAttribute("sex", sex);
			session.setAttribute("path", "face\\" + path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnection.free(con, ps, rs);
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
