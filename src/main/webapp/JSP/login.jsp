<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
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
		    <h3>登陆</h3>
			<form action="./Login" method="post">
					Username： <input type="text" name="username"/><br /><br />
					Password: <input type="password" id="password" name="password"/><br /><br />
					<input type="submit" value="login"/>
					<a href="sendEmail.jsp">忘记密码</a>
					<div style="color: red;" id="mess">${mess} ${mess = ""}</div>
			</form>
			<img src="http://qzonestyle.gtimg.cn/qzone/vas/opensns/res/img/Connect_logo_3.png" id="qqLogin"/>
		</div>
		<div class="frameThree">
			<hr />
			   Copyright @ 2006-2015 renming.com All Rights Reserved. 人民网 版权所有
			   <br />京ICP证120085号 京ICP备16004154号 京网文[2012]0620-206号
			   <br />京公网安备 11011202000608号
		</div>
	</body>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
<script type="text/javascript">
       function callback(response){
	      console.log(response)
       }
	   if (window.location.hash.length != 0) {
       //获取access token
       var accessToken = accessToken = window.location.hash.substring(1).split("&")[0].split("=")[1];
       //使用Access Token来获取用户的OpenID
       $.ajax({
            async : true,
            url : "https://graph.qq.com/oauth2.0/me",
            type : "GET",
            dataType : "jsonp", // 返回的数据类型，设置为JSONP方式
            jsonp : "callback", //指定一个查询参数名称来覆盖默认的 jsonp 回调参数名 callback
            jsonpCallback: 'callback', //设置回调函数名
            data : {
               "access_token":accessToken
            }, 
            success: function(response, status, xhr){
               console.log('状态为：' + status + ',状态是：' + xhr.statusText);
               console.log(response.openid);
               $.ajax({
               	url:"./QQLoginServlet",
               	type:"post",
               	data:{
               		"openid":response.openid
               	},
               	success:function(data){
               		console.log(data);
               		if(data.indexOf("jsp")){
               			window.location = "index.jsp"
               		}
               		else {
               		    $("#mess").text(data);
               		}
               	},
               	error:function(){
               		console.log("error")
               	}
               })
            }
          });
                 
    }
	      
	   
	  $("#qqLogin").click(function(){
		      window.location = "https://graph.qq.com/oauth2.0/authorize?response_type=token&client_id=101505441&redirect_uri=http://127.0.0.1:8080/login.jsp&scope=get_user_info"
	  });
	            
</script>
</html>