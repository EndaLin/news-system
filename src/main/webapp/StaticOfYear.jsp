<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="domain.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>统计</title>
<link href="css/QueryResult.css" rel="stylesheet" type="text/css">
</head>
<body>
	<!-- 分页操作-->
	<%
		ArrayList<YearOfNum> list = (ArrayList<YearOfNum>) request.getSession().getAttribute("slist3");
		int page_current = 1; //当前页数
		int page_begin = 0; //起始点,注意:下标从0开始
		int page_end = 9; //终点,每页十条信息
		int total_count = 0;
		if (list != null)
			total_count = list.size(); //信息的总量
		int page_total = total_count / 10 + (total_count % 10 != 0 ? 1 : 0);
		if (request.getParameter("begin") != null) {
			page_current = Integer.parseInt(request.getParameter("begin")); //获取当前页数
		}
		page_begin = (page_current - 1) * 10;
		page_end = page_begin + 9 > total_count ? total_count : page_begin + 9;
		request.getSession().setAttribute("page_current", page_current); //保存到session中
		request.getSession().setAttribute("page_total", page_total);
	%>
	<div class="frameTop">
		<img src="img/1.png" class="img_1">
		<form action="" method="post">
			<input type="text" class="form">
		</form>
		<img src="img/2.jpg" class="img_2" onclick="frmsubmit()">
		<ul class="f_ul">
			<a href="index.jsp"><li>首页</li></a>
		</ul>
	</div>
	<div class="middle">
		<div class="left">
			<div class="show_info">
				<a href="showMess.jsp">显示个人信息</a>
			</div>
			<div class="alter_info">
				<a href="motifyMess.jsp">修改个人信息</a>
			</div>
			<c:if test="${sessionScope.status1 == '1' }">
				<div>
					<a href="showAllUsers.jsp">审查用户</a>
				</div>
				<div>
					<a href="showAllNews.jsp">审查新闻</a>
				</div>
				<div>
					<a href="static.jsp">统计</a>
				</div>
				<div>
					<a href="StaticOfYear.jsp">全年数据统计(评论)</a>
				</div>
				<div>
					<a href="StaticOfYear2.jsp">全年数据统计(新闻)</a>
				</div>
			</c:if>
			<c:if test="${sessionScope.status1 == '2' }">
				<div>
					<a href="showMyNews.jsp">新闻管理</a>
				</div>
				<div>
					<a href="./ueditor/editNew.jsp">新闻发表</a>
				</div>
			</c:if>
		</div>
		<div class="right">
			<div class="info">
				年份&emsp;&emsp;月份&emsp;&emsp;评论数目<br />
				<c:forEach var="p" items="${slist3}" varStatus="status"
					begin="<%=page_begin %>" end="<%=page_end %>">
                ${p.year }&emsp;&emsp;${p.month }&emsp;&emsp;${p.num}
                <br />
				</c:forEach>
				<br />
				<ul>
					<c:if test="${sessionScope.page_current != 1 }">
						<li><a
							href="./StaticOfYear.jsp?begin=${sessionScope.page_current - 1 }">Prev</a></li>
					</c:if>
					<c:if
						test="${sessionScope.page_current != sessionScope.page_total }">
						<li><a
							href="./StaticOfYear.jsp?begin=${sessionScope.page_current + 1 }">Next</a></li>
					</c:if>
				</ul>
				当前页数 : ${sessionScope.page_current } / ${sessionScope.page_total }
			</div>
		</div>
	</div>
	<div style="clear: both"></div>
	<hr />
</html>