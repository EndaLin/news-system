var vm = new Vue({
	el: ".frameTwo",
	data: {
		allNew: []
	},
	mounted() {
		axios
			.get("http://localhost:8080/GetAllNewsServlet")
			.then(response => {
				var data = response.data;
				this.allNew = data;
			})
			.catch(error => console.log(error))
	},
	methods: {
		check: function(obj) {
			console.log(obj.id);
			location.replace("http://localhost:8080/showNews?id=" + obj.id);
		}
	}
})
