<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>修改个人信息</title>
		<link href="css/motification.css" rel="stylesheet" type="text/css">
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
				<form action="/web/motifyMess" method="post" class="info" enctype="multipart/form-data">
					修改个人信息：<br /> 名称：<input type="text" value="${user}" readonly="readonly" name="account" /> <br /> 头像： <input type="file" name="image" /> <br /> 性别：
					<select name="sex">
						<option value="1">男</option>
						<option value="0">女</option>
					</select> <br /> <input type="submit" value="修改">
				</form>
				<br /> 绑定第三方账号：
				<img src="http://qzonestyle.gtimg.cn/qzone/vas/opensns/res/img/Connect_logo_1.png" id="bindQQ" />
				<div style="color: red;" id="mess">${mess}${mess = ""}</div>
			</div>
		</div>
		<div style="clear: both"></div>
		<hr />
		<div class="copyright">
			Copyright @ 2006-2015 renming.com All Rights Reserved. 人民网 版权所有 <br />京ICP证120085号 京ICP备16004154号 京网文[2012]0620-206号 <br />京公网安备 11011202000608号
		</div>
	</body>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
	<script type="text/javascript">
		function callback(response) {
			console.log(response)
		}
		if(window.location.hash.length != 0) {
			//获取access token
			var accessToken = accessToken = window.location.hash.substring(1).split("&")[0].split("=")[1];
			//使用Access Token来获取用户的OpenID
			$.ajax({
				async: true,
				url: "https://graph.qq.com/oauth2.0/me",
				type: "GET",
				dataType: "jsonp", // 返回的数据类型，设置为JSONP方式
				jsonp: "callback", //指定一个查询参数名称来覆盖默认的 jsonp 回调参数名 callback
				jsonpCallback: 'callback', //设置回调函数名
				data: {
					"access_token": accessToken
				},
				success: function(response, status, xhr) {
					console.log('状态为：' + status + ',状态是：' + xhr.statusText);
					console.log(response.openid);
					$.ajax({
						url: "./BindQQServlet",
						type: "post",
						data: {
							"openid": response.openid
						},
						success: function(data) {
							console.log(data);
							$("#mess").text(data);
						},
						error: function() {
							console.log("error")
						}
					})
				}
			});

		}

		$("#bindQQ").click(function() {
			window.location = "https://graph.qq.com/oauth2.0/authorize?response_type=token&client_id=101505441&redirect_uri=http://localhost:8080/modifyMess.html&scope=get_user_info"
		});
	</script>

</html>