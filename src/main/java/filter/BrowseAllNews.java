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
 * Servlet Filter implementation class BrowseAllNews
 */
@WebFilter(dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE,
        DispatcherType.ERROR}, urlPatterns = {"/showAllNews.jsp"})
public class BrowseAllNews implements Filter {

    /**
     * Default constructor.
     */
    public BrowseAllNews() {
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
        int id;
        String title = null;
        String author = null;
        String time = null;
        String ischeck = null;
        String sql = "select * from new";
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<News> list = new ArrayList<News>();
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("nid");
                title = rs.getString("title");
                author = rs.getString("author");
                time = rs.getString("time");
                switch (rs.getInt("ischeck")) {
                    case 0:
                        ischeck = "待审核";
                        break;
                    case 1:
                        ischeck = "正常";
                        break;
                    default:
                        break;
                }
                list.add(new News(id, title, author, time, ischeck));
            }
            ((HttpServletRequest) request).getSession().setAttribute("nlist", list);
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
