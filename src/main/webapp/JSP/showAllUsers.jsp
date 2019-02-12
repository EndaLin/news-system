<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>查看/审查所有用户</title>
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
					ID&emsp;用户类型&emsp;&emsp;用户名&emsp;&emsp;性别&emsp;&emsp;&emsp;&emsp;账号可用性
					<c:forEach var="p" items="${list}" varStatus="status">
						<br /> ${status.count}&emsp;&emsp;${p.status }&emsp;&emsp;&emsp;${p.account }&emsp;&emsp;${p.sex }&emsp;&emsp;&emsp;&emsp;${p.ischeck }&emsp;&emsp;
						<c:if test="${p.ischeck == '审核中' }">
							<a href="./changeIsCheck?account=${p.account}&check=1">通过审核</a>
						</c:if>
						<c:if test="${p.ischeck == '禁用' }">
							<a href="./changeIsCheck?account=${p.account}&check=1">解禁</a>
						</c:if>
						<c:if test="${p.ischeck == '正常' }">
							<a href="./changeIsCheck?account=${p.account}&check=-1">禁用</a>
						</c:if>
					</c:forEach>
					<br />
					<a href="#">下一页</a>&emsp;&emsp;
					<a href="#">尾页</a>
				</div>
				<br />
				<form action="" id="uploadForm" enctype="multipart/form-data">
					<input type="file" name="file" /><br />
					<button id="submit_file">批量添加用户</button>
				</form>
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
		$("#submit_file").click(function() {
			$.ajax({
				type: "post",
				url: "./SaveMessageFromExcelServlet",
				async: true,
				dataType:"text",
				data: new FormData($('#uploadForm')[0]),
				processData: false,
				contentType: false,
				success: function() {
					alert("上传成功");
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					console.log(XMLHttpRequest.status)
					console.log(textStatus)
					console.log(errorThrown)
				}
			});
		});
	</script>

</html>