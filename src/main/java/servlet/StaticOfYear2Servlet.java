package servlet;

import com.alibaba.fastjson.JSONObject;
import dao.DBConnection;
import domain.YearOfNum;

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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: wt
 * @Date: 2019/2/7 18:47
 */
@WebServlet(value = "/StaticOfYear2Servlet")
public class StaticOfYear2Servlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
        ArrayList<YearOfNum> list = new ArrayList<>();
        //为提高检索速度
        Map<String, Integer> map = new HashMap<>();
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
                "FROM user LEFT OUTER JOIN (SELECT * FROM new WHERE time BETWEEN ? AND ? )  a ON user.`account` LIKE a.`author`\r\n" +
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
