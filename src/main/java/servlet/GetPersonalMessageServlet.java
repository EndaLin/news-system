package servlet;

import com.alibaba.fastjson.JSONObject;
import dao.DBConnection;
import domain.Users;

import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @Author: wt
 * @Date: 2019/2/3 15:51
 */
@WebServlet(value = "/GetPersonalMessageServlet")
public class GetPersonalMessageServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String account = request.getParameter("name");
        String sql = "select * from user where account like ?";
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Users user = new Users();
        String path;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, account);
            rs = ps.executeQuery();
            while(rs.next()) {
                user.setStatus(rs.getString("status"));
                rs.getString("sex");
                path =  rs.getString("path");
                user.setPath(path);
            }
            response.getWriter().println(JSONObject.toJSONString(user));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            DBConnection.free(con, ps, rs);
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
