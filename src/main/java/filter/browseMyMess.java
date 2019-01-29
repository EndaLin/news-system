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
		String sql = "select * from user where account like ?";
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        HttpServletRequest hsr = (HttpServletRequest) request;
        HttpSession session = hsr.getSession();
        String status = null; //���
        String sex = null;
        String path = null;  //ͷ��������ַ
          try {
			ps = con.prepareStatement(sql);
			ps.setString(1, (String) hsr.getSession().getAttribute("user"));
			rs = ps.executeQuery();
			while(rs.next()) {
				switch(rs.getString("status")) {
					case "1" : status = "����Ա";break;
					case "2" : status = "���ŷ���Ա";break;
					case "3" : status = "��ͨ�û�";break;
				}
				sex = rs.getString("status").equals("0") ? "��" : "Ů";
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
