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
				url: "http://localhost:8080/QQLoginServlet",
				type: "post",
				data: {
					"openid": response.openid
				},
				success: function(data) {
					console.log(data);
					if(data.indexOf("jsp")) {
						window.location = "index.jsp"
					} else {
						$("#mess").text(data);
					}
				},
				error: function() {
					console.log("error")
				}
			})
		}
	});

}

$("#qqLogin").click(function() {
	window.location = "https://graph.qq.com/oauth2.0/authorize?response_type=token&client_id=101505441&redirect_uri=http://127.0.0.1:8080/login.jsp&scope=get_user_info"
});

var form = new Vue({
	el: "#form_1",
	data: {
		username: "",
		password: "",
		errors: ""
	},
	methods: {
		submitForm: function() {
			//let loadingInstance = Loading.service(options);
			//this.$nextTick(() => { // 以服务的方式调用的 Loading 需要异步关闭
			//loadingInstance.close();
			//});
			if(this.username == "" || this.password == "") {
				this.errors = "账号和密码不能为空"
				return
			} else {
				this.errors = ""
			}

			axios({
					url: 'http://localhost:8080/Login',
					method: "post",
					data: {
						username: this.username,
						password: this.password,
					},
					//将JSON对象 转 键=值&键=值
					/*
					{
					  name:"david",
					  age:30
					}
					name=小李&age=30
					// 在发送数据之前 将对象转键值对
					*/
					transformRequest: [function(data) {
						// Do whatever you want to transform the data
						let ret = ''
						for(let it in data) {
							// 如果要发送中文 编码 
							ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
						}
						return ret
					}],
					headers: {
						'Content-Type': 'application/x-www-form-urlencoded'
					}
				})
				.then(response => {
					var data = response.data;
					console.log(data);
					if(data.account != null) {
						if(data.status === '1') {
							data.status = "管理员";
						} else if(data.status === '2') {
							data.status = "新闻发布员"
						} else {
							data.status = "普通用户"
						}
						login(data.account, data.status);
						window.location = "index.html"
					} else {
						this.errors = data;
					}
				})
				.catch(function(error) {
					console.log(error);
				})
		},
		qqLogin: function() {
			window.location = "https://graph.qq.com/oauth2.0/authorize?response_type=token&client_id=101505441&redirect_uri=http://127.0.0.1:8080/login.jsp&scope=get_user_info"
		}
	}
})