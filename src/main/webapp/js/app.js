var vm = new Vue({
	el: "#app",
	data: {
		allNew: [],
		user: {
			name: '',
			status: ''
		}
	},
	mounted() {
		axios
			.get("http://localhost:8080/GetAllNewsServlet")
			.then(response => {
				var data = response.data;
				this.allNew = data;
			})
			.catch(error => console.log(error))
		if(islogin()) {
			this.user = getUser();
		} else {
			this.user.name = ''
		}
		console.log(this.user)
	},
	methods: {
		check: function(obj) {
			console.log(obj.id);
			location.replace("http://localhost:8080/showNew.html?id=" + obj.id);
		},
		logout: function() {
			logout();
			window.location = "index.html";
		}
	}
})