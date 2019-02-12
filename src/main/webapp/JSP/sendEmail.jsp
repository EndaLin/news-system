<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改密码</title>
<link rel="stylesheet" href="css/register.css" />
</head>
	<body>
		<div class="frameTop">
			<img src="img/1.png" class="img_1" />
			<form action="" method="post">
				<input type="text" class="form">
			</form>
			<img src="img/2.jpg" class="img_2" onclick="frmsubmit()" />
			<ul class="f_ul">
				<a href="index.jsp">
					<li>首页</li>
				</a>
			</ul>
		</div>
		<div class="form_2">
		    <h3>修改密码</h3>
			<form action="./Login" method="post">
				<div>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;账号 ：
					<input type="text" id="account" name="form-account" onblur=""/>
				</div>
				<div>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;输入邮箱 ：
					<input id="email" type="text" onblur=""/>
				</div>
					<input type="button" value="send" id="change"/>
			</form>
		</div>
		<div class="frameThree">
			<hr />
			   Copyright @ 2006-2015 renming.com All Rights Reserved. 人民网 版权所有
			   <br />京ICP证120085号 京ICP备16004154号 京网文[2012]0620-206号
			   <br />京公网安备 11011202000608号
		</div>
	</body>
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
	<script type="text/javascript">
		$("#change").click(function(){
			$.ajax({
				type:"post",
				url:"./CheckEmailServlet",
				traditional : false,
				data:{
					"account" : $("#account").attr("value"),
					"email" : $("#email").attr("value")
				},
				async:true,
				success:function(data){
					console.log(data)
					$("#mess").text(data)
				},
        		error:function(XMLHttpRequest, textStatus, errorThrown){
        		     console.log(XMLHttpRequest)
        		     console.log(textStatus)
        		     console.log(errorThrown)
        	    }
			});
		})
	</script>
</html>