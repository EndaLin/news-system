package service;

import dao.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

/**
 * @Author: wt
 * @Date: 2018/11/26 20:38
 */
public class SaveFromExcelServiceImpl {
    private Connection con;
    private PreparedStatement ps;
    private String sql;

    public SaveFromExcelServiceImpl() {
        sql = "insert into user values (?,?,?,?,?,?,?,?)";
        con = DBConnection.getConnection();
    }

    public void save(List<List<String>> list) {
        String account = null;
        String password = "123";
        int status;
        String email = null;
        int ischeck = 1;

        try {
            ps = con.prepareStatement(sql);
            byte[] resultBytes;
            //加密
            resultBytes = MD5.eccrypt(password);
            String msg = new String(resultBytes);
            // 把list中的数据读出,并封装到stu里面,(list里面存放的是从Excel中读取出来的数据)
            for (List<String> cellList : list) {

                if (cellList.size() != 3) {
                    System.out.println("data error");
                }

                account = cellList.get(0);
                status = (int) (Double.parseDouble(cellList.get(1)));
                email = cellList.get(2);
                ps.setString(1, account);
                //更改编码格式后录入
                ps.setString(2, Base64.getBase64(msg));
                ps.setInt(3, status);
                ps.setInt(4, ischeck);
                ps.setString(5, "default.png");
                ps.setInt(6, 0);
                ps.setString(7, email);
                ps.setString(8, null);
                System.out.println(account + " " + email);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
