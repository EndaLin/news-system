<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>统计</title>
<link href="css/QueryResult.css" rel="stylesheet" type="text/css">
</head>
<body>
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
		    请选择需要统计数据的时间区间
		    <br />
		    <form action="./StaticData" method="post">
		         <select name="time">
		             <option value="1">近一周</option>
		             <option value="2">近一月</option>
		             <option value="3">近一年</option>
		         </select>
		         <input type="submit" value="查询"/>
		    </form>
			<div class="info">
				用户类型&emsp;&emsp;用户名&emsp;&emsp;新闻数目<br />
				<c:forEach var="p" items="${slist1}" varStatus="status">
                ${p.status }&emsp;&emsp;${p.author }&emsp;&emsp;${p.num}
                <br />
				</c:forEach>
				<br /> 
				<a href="#">尾页</a>&emsp;&emsp; <a href="#">上一页</a>&emsp;&emsp; <a href="#">下一页</a>&emsp;&emsp; <a href="#">尾页</a>
			</div>
			<div class="info">
				用户类型&emsp;&emsp;用户名&emsp;&emsp;评论数目<br />
				<c:forEach var="p" items="${slist2}" varStatus="status">
                ${p.status }&emsp;&emsp;${p.author }&emsp;&emsp;${p.num}
                <br />
				</c:forEach>
				<br /> <a href="#">下一页</a>&emsp;&emsp; <a href="#">尾页</a>
			</div>
		</div>
	</div>
	<div style="clear: both"></div>
	<hr />
</html>