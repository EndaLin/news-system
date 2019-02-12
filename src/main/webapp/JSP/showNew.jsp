<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>新闻展示</title>
		<link rel="stylesheet" href="css/new_1.css" />
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
		<div class="frameTwo">
			<h3>${this_title }</h3>
			<span class="title">作者：${this_author }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${this_time }</span>
			<hr style="margin-top: 30px;">
			<div>${this_content}</div>
			<hr style="margin-top: 30px;">
		</div>
		<form action="./AddComment" method="post" style="margin-top:1000px;margin-left:600px;">
			<h3>用户评论</h3>
			<textarea clos="1500" rows="10" id="txt" name="content"></textarea>
			<input type="hidden" name="nid" value="${this_id }" />
			<c:if test="${user != null }">
				<input type="submit" value="评论" class="changePosition" />
			</c:if>
			<c:if test="${user == null }">
				<div style="color: red;">登陆后再评论</div>
			</c:if>
		</form>
		<div style="margin-top:100px;margin-left:600px;">
			<c:forEach var="p" items="${clist }">
				${p.author }:
				<form action="./ChangeComment" method="post">
					<input type="text" value="${p.content }" name="content" /> --发表于：${p.time }
					<c:if test="${p.ischange == 0 && p.author == user && status1 != '1'}">
						<input type="hidden" name="id" value="${p.cid }" />
						<input type="hidden" name="author" value="${p.author }" />
						<input type="hidden" name="time" value="${p.time }" />
						<input type="hidden" name="step" value="1" />
						<input type="submit" value="修改">
					</c:if>
					<c:if test="${status1 == '1'}">
						<input type="hidden" name="id" value="${p.cid }" />
						<input type="hidden" name="step" value="2" />
						<input type="submit" value="删除">
					</c:if>
				</form>
			</c:forEach>
		</div>
	</body>

</html>