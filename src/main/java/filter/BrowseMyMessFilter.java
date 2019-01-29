package filter;

import dao.DBConnection;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebFilter(dispatcherTypes = { DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE,
        DispatcherType.ERROR }, urlPatterns = { "/showMess.jsp" })
/**
 * @author wt
 * @date
 */
public class BrowseMyMessFilter implements Filter {
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("enter");
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
                sex = rs.getString("status").equals("0") ? "男" : "女";
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

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
