package servlet;

import com.alibaba.fastjson.JSONObject;
import dao.DBConnection;
import domain.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wt
 * @Date: 2019/2/4 13:43
 */
@WebServlet(value = "/GetAllUsersServlet")
public class GetAllUsersServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        List<Users> listUsers = new ArrayList<>();
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
                    default:
                        break;
                }
                listUsers.add(new Users(status, account, sex, ischeck));
            }
            System.out.println(JSONObject.toJSONString(listUsers));
            response.getWriter().println(JSONObject.toJSONString(listUsers));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBConnection.free(con, ps, rs);
        }
    }
}
