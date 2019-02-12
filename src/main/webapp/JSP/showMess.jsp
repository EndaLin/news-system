<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>管理界面</title>
		<link href="css/normalUser.css" rel="stylesheet" type="text/css">
	</head>

	<body>
		<div class="frameTop">
			<img src="img/1.png" class="img_1">
			<form action="" method="post">
				<input type="text" class="form">
			</form>
			<img src="img/2.jpg" class="img_2" onclick="frmsubmit()">
			<ul class="f_ul">
				<a href="index.jsp">
					<li>首页</li>
				</a>
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
					用户类型：${sessionScope.status } <br /> 用户名： ${sessionScope.user } <br /> 头像
					<div class="head">
						<img src="${sessionScope.path }" height="200px" width="200px" />
					</div>
					注册时间：2016-6-6 6：16:16<br /> 性别：${sessionScope.sex } <br /> 爱好：小明
				</div>
			</div>
		</div>
		<div style="clear: both"></div>
		<hr />
		<div class="copyright">
			Copyright @ 2006-2015 renming.com All Rights Reserved. 人民网 版权所有 <br />京ICP证120085号 京ICP备16004154号 京网文[2012]0620-206号 <br />京公网安备 11011202000608号
		</div>
	</body>

</html>