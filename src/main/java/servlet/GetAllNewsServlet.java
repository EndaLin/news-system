package servlet;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import domain.News;
import service.GetAllNewsFromDatabaseService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: wt
 * @Date: 2019/2/1 17:42
 */
@WebServlet("/GetAllNewsServlet")
public class GetAllNewsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GetAllNewsFromDatabaseService getAllNewsFromDatabaseService = new GetAllNewsFromDatabaseService();
        List<List<News>> list = getAllNewsFromDatabaseService.get();
        String result = JSONObject.toJSONString(list);
        System.out.println(result);
        response.getWriter().println(result);
    }
}
