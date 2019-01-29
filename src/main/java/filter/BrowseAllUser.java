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
import javax.servlet.http.HttpSession;

import dao.DBConnection;
import domain.Users;

/**
 * Servlet Filter implementation class BrowseAllUser
 */
@WebFilter(dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE,
        DispatcherType.ERROR}, urlPatterns = {"/showAllUsers.jsp"})
public class BrowseAllUser implements Filter {

    /**
     * Default constructor.
     */
    public BrowseAllUser() {
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
        // 地位
        String status = null;
        // 帐户名
        String account = null;
        // 性别
        String sex = null;
        // 账号的可用性
        String ischeck = null;
        String sql = "select * from user";
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Users> users = new ArrayList<Users>();
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
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
                    default:
                        break;
                }
                sex = rs.getString("status").equals("0") ? "男" : "女";
                account = rs.getString("account");
                switch (rs.getString("ischeck")) {
                    case "-1":
                        ischeck = "禁用";
                        break;
                    case "1":
                        ischeck = "正常";
                        break;
                    case "0":
                        ischeck = "审核中";
                        break;
                        default:break;
                }
                users.add(new Users(status, account, sex, ischeck));
            }
            ((HttpServletRequest) request).getSession().setAttribute("list", users);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        chain.doFilter(request, response);
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        // TODO Auto-generated method stub
    }

}
