package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.Users;
import service.LoginByOpenidService;

/**
 * Servlet implementation class QQLoginServlet
 */
public class QQLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QQLoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String openid = request.getParameter("openid");
		Users user = LoginByOpenidService.find(openid);
		HttpSession session = request.getSession();
		if(user != null) {
			if (user.getIscheck().equals("1")) {
				session.setAttribute("user", user.getAccount());
				session.setAttribute("status1", user.getStatus());
				response.getWriter().println("/index.jsp");
				return;
			} else if (user.getIscheck().equals("-1")){
				response.getWriter().println("该账号已被停用！");
			} else {
				response.getWriter().println("该账号正在审核中！");
			}
		} else {
			response.getWriter().println("账号不存在！请先绑定QQ");
		}
	}

}
