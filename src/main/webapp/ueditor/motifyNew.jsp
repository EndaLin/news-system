<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>新闻发布</title>
		<style type="text/css">
			div {
				width: 100%;
				margin-left: auto;
				margin-right: auto;
			}
			
			.title {
				margin-left: auto;
				margin-right: auto;
				display: block;
				width: 200px;
				letter-spacing: 6px;
			}
			
			a {
				text-decoration: none;
			}
			
			.frameTop {
				width: 1903px;
				height: 200px;
				margin-top: 50px;
			}
			
			.img_1 {
				margin-left: 450px;
				float: left;
			}
			
			.form {
				float: left;
				margin-left: 200px;
				margin-top: 150px;
			}
			
			.img_2 {
				width: 30px;
				height: 30px;
				position: relative;
				top: 148px;
				left: 20px;
			}
			
			.f_ul {
				width: 200px;
				height: 100px;
				position: absolute;
				top: 185px;
				left: 1200px;
			}
			
			.f_ul li {
				list-style: none;
				font-size: 20px;
				float: left;
				display: block;
				margin-left: 20px;
			}
		</style>
	</head>

	<body>
		<div class="frameTop">
			<img src="../img/1.png" class="img_1">
			<form action="" method="post">
				<input type="text" class="form">
			</form>
			<img src="../img/2.jpg" class="img_2" onclick="frmsubmit()">
			<ul class="f_ul">
				<a href="../index.jsp">
					<li>首页</li>
				</a>
			</ul>
		</div>
		<!-- 加载编辑器的容器 -->
		<form action="/NewsSys/MotifyNews" method="post">
			请选择文章类型：
			<select name="type">
				<option value="国际">国际</option>
				<option value="社会">社会</option>
				<option value="体育">体育</option>
				<option value="汽车">汽车</option>
			</select>
			<br /> 请输入文章标题：
			<input type="text" name="title" value="${myTitle }" />
			<br />
			<!-- 加载编辑器的容器 -->
			<script id="container" name="content" type="text/plain">
				${myContent}
			</script>
			<div style="color: red;">${mess} ${mess = ""}</div>
			<input type="hidden" name="id" value="${myID }" />
			<input type="hidden" name="step" value="2" />
			<input type="submit" value="提交" style="margin-left:400px;margin-top:50px;" />
		</form>
		<!-- 配置文件 -->
		<script type="text/javascript" src="ueditor.config.js"></script>
		<!-- 编辑器源码文件 -->
		<script type="text/javascript" src="ueditor.all.js"></script>
		<!-- 实例化编辑器 -->
		<script type="text/javascript">
			var ue = UE.getEditor('container', {
				// 这里可以选择自己需要的工具按钮名称
				toolbars: [
					['fullscreen', 'source', 'undo', 'redo'],
					['bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', 'simpleupload']
				],

				// 默认的编辑区域高度和宽度 
				initialFrameHeight: 500,
				initialFrameWidth: 1100,

				//关闭elementPath

				// 更多其他参数，请参考ueditor.config.js中的配置项  
			});
		</script>
	</body>

</html>