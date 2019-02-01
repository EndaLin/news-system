package web;

import com.alibaba.fastjson.JSONObject;
import domain.News;
import service.GetAllNewsFromDatabaseService;

import java.util.List;

/**
 * @Author: wt
 * @Date: 2019/2/1 17:52
 */
public class GetAllNewsFromDatabaseServiceTest {
    public static void main(String[] args) {
        GetAllNewsFromDatabaseService getAllNewsFromDatabaseService = new GetAllNewsFromDatabaseService();
        List<List<News>> list = getAllNewsFromDatabaseService.get();
        String result = JSONObject.toJSONString(list);
        System.out.println(result);

    }
}
