package service;

import dao.DBConnection;
import domain.News;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wt
 * @Date: 2019/2/1 17:44
 */
public class GetAllNewsFromDatabaseService {
    public List<List<News>> get() {
        List<News> list1 = new ArrayList<>();
        List<News> list2 = new ArrayList<>();
        List<News> list3 = new ArrayList<>();
        List<News> list4 = new ArrayList<>();
        List<List<News>> allNews = new ArrayList<>();
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
            allNews.add(list1);
            allNews.add(list2);
            allNews.add(list3);
            allNews.add(list4);
            return allNews;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            DBConnection.free(con, ps, rs);
        }
        return null;
    }
}
