package servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import service.IsExcel2003ServiceImpl;
import service.ReadExcelServiceImpl;
import service.SaveFromExcelServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/SaveMessageFromExcelServlet")
/**
 *
 * @author wt
 * @date 2016/10/31
 */
public class SaveMessageFromExcelServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean isExcel2003 = false;
        String type = null;
        // 检测是否为多媒体上传
        if (!ServletFileUpload.isMultipartContent(request)) {
            // 如果不是则停止
            PrintWriter writer = response.getWriter();
            writer.println("Error: 表单必须包含 enctype=multipart/form-data");
            writer.flush();
            return;
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 中文处理
        upload.setHeaderEncoding("utf-8");

        try {
            // 解析请求的内容提取文件数据
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);
            for (FileItem item : formItems) {
                if (item.isFormField()) {
                    // 获取请求来源
                    type = item.getString();
                }
                if (!item.isFormField()) {
                    System.out.println("文件上传！");
                    String fileName = item.getName();
                    if (fileName.isEmpty()) {
                        continue;
                    }
                    System.out.println("上传的文件名：" + fileName);

                    IsExcel2003ServiceImpl check = new IsExcel2003ServiceImpl();

                    //检查Excel版本
                    isExcel2003 = check.isExcel2003(fileName);

                    // 获取文件名后缀, 返回 "."在文件名最后出现的索引, 就是文件后缀名
                    String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);

                    if (!"xlsx".equals(prefix) && !"xls".equals(prefix)) {
                        request.getSession().setAttribute("mess", "错误：上传的文件格式错误！请上传Excel表格");
                        request.getRequestDispatcher(type).forward(request, response);
                        return;
                    }

                    // 获取文件输入流
                    FileInputStream inputStream = (FileInputStream)item.getInputStream();

                    // 调用读取Excel的方法
                    ReadExcelServiceImpl poi = new ReadExcelServiceImpl();

                    List<List<String>> list = poi.read(inputStream, isExcel2003);

                    SaveFromExcelServiceImpl saveTool = new SaveFromExcelServiceImpl();

                    saveTool.save(list);

                    // 关闭流
                    inputStream.close();
                    item.delete();

                }
            }
            System.out.println("success");
            response.getWriter().println("success");

        } catch (Exception e) {
            request.getSession().setAttribute("message", "error" + e.getMessage());
        } finally {
            request.getRequestDispatcher(type).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
