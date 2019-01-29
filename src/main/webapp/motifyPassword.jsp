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
		    <h3>密码修改</h3>
			<form action="" method="post" id="fr">
				<div>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;密码 ：
					<input type="password" id="password" name="form-password" onblur=""/>
				</div>
				<div>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;重新输入密码 ：
					<input id="password2" type="password" onblur=""/>
				</div>
				<input type="button" value="确定" onclick="change()"/>
				<div style="color: red;" id="mess"></div>
			</form>
		</div>
		<div class="frameThree">
			<hr />
			   Copyright @ 2006-2015 renming.com All Rights Reserved. 人民网 版权所有
			   <br />京ICP证120085号 京ICP备16004154号 京网文[2012]0620-206号
			   <br />京公网安备 11011202000608号
		</div>
	</body>
	<script type="text/javascript" src="js/register.js"></script>
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
	<script type="text/javascript">
         function change() {
         	var account = "<%=request.getParameter("account")%>"
         	var password = $("#password").attr("value")
         	$.ajax({
         		type:"post",
				url:"./MotifyPasswordServlet",
				traditional : false,
				data:{
					"account" : account,
					"password" : password
				},
				async:true,
				success:function(data){
					//console.log(data)
					$("#mess").text(data)
				},
        		error:function(XMLHttpRequest, textStatus, errorThrown){
        		     console.log(XMLHttpRequest)
        		     console.log(textStatus)
        		     console.log(errorThrown)
        	    }
         	})
         }
	</script>
</html>