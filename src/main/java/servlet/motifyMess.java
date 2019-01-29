package servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import dao.DBConnection;

/**
 * Servlet implementation class motifyMess
 */
@WebServlet("/motifyMess")
public class motifyMess extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public motifyMess() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String sex = null; // 获取修改后的性别
		String fileSaveName = null; // 获取文件名称
		String id = null; // 获取用户ID

		// 检测是否为多媒体上传
		if (!ServletFileUpload.isMultipartContent(request)) {
			// 如果不是则停止
			PrintWriter writer = response.getWriter();
			writer.println("Error: 表单必须包含 enctype=multipart/form-data");
			writer.flush();
			return;
		}

		// 获取路径来存储文件
		String path = request.getSession().getServletContext().getRealPath("") + "face\\";
		System.out.println("图片存储路径：" + path);

		// 根据路径名创建一个 File实例
		File file = new File(path);
		if (!file.exists()) {
			file.mkdir(); // 如果不存在则创建此路径的目录
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
					// 处理普通表单域
					String field = item.getFieldName(); // 表单域名
					if (field.equals("sex")) {
						sex = item.getString(); // 获取表单数据
					}
				} else {
					System.out.println("文件上传！");
					String fileName = item.getName();
					if (fileName.isEmpty()) {
						continue;
					}
					System.out.println("上传的文件名：" + fileName);

					// 获取文件名后缀, 返回 "."在文件名最后出现的索引, 就是文件后缀名
					String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
					// 存储的文件名根据获取的id来唯一确定, 这里测试使用 "test"
					// id可以绑定到session或request变量等等，自己根据需要来扩展
					id = (String) request.getSession().getAttribute("user");
					fileSaveName = id + "." + prefix; // id.后缀

					// 获取文件输入流
					InputStream inputStream = item.getInputStream();
					// 创建文件输出流，用于向指定文件名的文件写入数据
					FileOutputStream fileOutputStream = new FileOutputStream(path + "/" + fileSaveName);
					int index = 0;

					// 从输入流读取数据的下一个字节，到末尾时返回 -1
					while ((index = inputStream.read()) != -1) {
						fileOutputStream.write(index); // 将指定字节写入此文件输出流
					}

					// 关闭流
					inputStream.close();
					fileOutputStream.close();
					item.delete();
				}

			}
			request.getSession().setAttribute("mess", "修改成功");
			if (fileSaveName != null) {
				String sql = "update user set sex = ? , path = ? where account like ?";
				Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, sex);
				ps.setString(2, fileSaveName);
				ps.setString(3, id);
				ps.executeUpdate();
				DBConnection.free(con, ps, null);
			} else {
				String sql = "update user set sex = ? where account like ?";
				Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, sex);
				ps.setString(2, id);
				ps.executeUpdate();
				DBConnection.free(con, ps, null);
			}
			RequestDispatcher rd = request.getRequestDispatcher("/motifyMess.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
