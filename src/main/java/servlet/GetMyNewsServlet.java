package servlet;

import com.alibaba.fastjson.JSONObject;
import dao.DBConnection;
import domain.News;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @Author: wt
 * @Date: 2019/2/11 17:54
 */
@WebServlet(value = "/GetMyNewsServlet")
public class GetMyNewsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id;
        String title = null;
        String author = request.getParameter("user");
        String time = null;
        String ischeck = null;
        String sql = "select * from new where author like ?";
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<News> list = new ArrayList<>();
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, author);
            rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("nid");
                title = rs.getString("title");
                time = rs.getString("time");
                switch (rs.getInt("ischeck")) {
                    case 0:
                        ischeck = "未审核";
                        break;
                    case 1:
                        ischeck = "正常";
                        break;
                    default:
                        break;
                }
                list.add(new News(id, title, author, time, ischeck));
            }
            System.out.println(JSONObject.toJSONString(list));
            response.getWriter().println(JSONObject.toJSONString(list));
        } catch (SQLException e) {
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
