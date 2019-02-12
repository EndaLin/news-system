<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人民网</title>
<link rel="stylesheet" href="css/index.css" />
</head>
<body>
<body>
	<div class="frameTop">
		<img src="img/1.png" class="img_1">
		<form action="" method="post">
			<input type="text" class="form">
		</form>
		<img src="img/2.jpg" class="img_2" onclick="frmsubmit()">
		<ul class="f_ul">
			<a href="index.jsp"><li>首页</li></a>
			<c:if test="${user == null }">
				<a href="login.jsp"><li>登陆</li></a>
				<a href="register.jsp"><li>注册</li></a>
			</c:if>
			<c:if test="${user != null }">
				<a href="showMess.jsp"><li>${user}</li></a>
				<a href="./Logout"><li>注銷</li></a>
			</c:if>
		</ul>
	</div>
	<div class="frameTwo">
		<div class="new_1">
			<div class="top_1">国际</div>
			<div class="top_2">
				<a href="#">更多</a>
			</div>
			<ul class="f_new_ul">
				<c:forEach var="p" items="${nlist1 }">
					<a href="./showNews?id=${p.id }"><li>${p.title }</li></a>
				</c:forEach>
			</ul>
			<ul class="f_new_u2">
				<c:forEach var="p" items="${nlist1 }">
					<li>${p.time }</li>
				</c:forEach>
			</ul>
		</div>
		<div class="new_1">
			<div class="top_1">社会</div>
			<div class="top_2">
				<a href="#">更多</a>
			</div>
			<ul class="f_new_ul">
				<c:forEach var="p" items="${nlist2 }">
					<a href="./showNews?id=${p.id }"><li>${p.title }</li></a>
				</c:forEach>
			</ul>
			<ul class="f_new_u2">
				<c:forEach var="p" items="${nlist2 }">
					<li>${p.time }</li>
				</c:forEach>
			</ul>
		</div>
		<div class="new_1">
			<div class="top_1">体育</div>
			<div class="top_2">
				<a href="#">更多</a>
			</div>
			<ul class="f_new_ul">
				<c:forEach var="p" items="${nlist3 }">
					<a href="./showNews?id=${p.id }"><li>${p.title }</li></a>
				</c:forEach>
			</ul>
			<ul class="f_new_u2">
				<c:forEach var="p" items="${nlist3 }">
					<li>${p.time }</li>
				</c:forEach>
			</ul>
		</div>
		<div class="new_1">
			<div class="top_1">汽车</div>
			<div class="top_2">
				<a href="#">更多</a>
			</div>
			<ul class="f_new_ul">
				<c:forEach var="p" items="${nlist4 }">
					<a href="./showNews?id=${p.id }"><li>${p.title }</li></a>
				</c:forEach>
			</ul>
			<ul class="f_new_u2">
				<c:forEach var="p" items="${nlist4 }">
					<li>${p.time }</li>
				</c:forEach>
			</ul>
		</div>
	</div>
	<div class="frameThree">
		<hr />
		Copyright @ 2006-2015 renming.com All Rights Reserved. 人民网 版权所有 <br />京ICP证120085号
		京ICP备16004154号 京网文[2012]0620-206号 <br />京公网安备 11011202000608号
	</div>
</body>
<script type="text/javascript" src="js/index.js"></script>
</body>
</html>