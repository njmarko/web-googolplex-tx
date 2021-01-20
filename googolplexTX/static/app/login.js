Vue.component("login-form", {
	data: function() {
		return {
			loginData: {},
			loginError: ""
		}
	},
	template: ` 
	<div class="container">
		<div id="particleJS-container" style="position:fixed; top:0; left:0;width:100%;z-index:0"></div>
		<br/>

		<div v-if="loginError" class="alert alert-danger alert-dismissible">
			<!-- <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a> -->
			<strong>Error</strong> {{loginError}}
		</div>

		<div class="row">
			<div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
			<div class="card card-signin my-5">
				<div class="card-body">
				<h5 class="card-title text-center">Sign In</h5>
				<form class="form-signin" v-on:submit.prevent="loginUser">

					<div class="form-label-group">
					<input type="text" id="inputUsername" class="form-control" placeholder="Username" v-model="loginData.username" required>
					<label for="inputUsername">Username</label>
					</div>
	
					<div class="form-label-group">
					<input type="password" id="inputPassword" class="form-control" placeholder="Password" v-model="loginData.password" required>
					<label for="inputPassword">Password</label>
					</div>
	
					<div class="custom-control custom-checkbox mb-3">
					<input type="checkbox" class="custom-control-input" id="customCheck1">
					<label class="custom-control-label" for="customCheck1">Remember password</label>
					</div>

					<input class="btn btn-lg btn-primary btn-block text-uppercase" type="submit" value="SIGN UP" />

					<hr class="my-4">
					<h5 class="card-title text-center">Don't have an account?</h5>

					<router-link to="/register" class="btn btn-warning btn-block text-uppercase">Register</router-link>

				</form>
				</div>
			</div>
			</div>
		</div>
		</div>
`
	,

	mounted() {
		
	},
	methods: {
		loginUser : function(){

			let component = this;

			let loginData = this.loginData
			let errorText = this.loginError

			console.log(this.loginData);
			var user = {username: loginData.username, password: loginData.password}
			axios
				.post('api/login', user)
				.then(response => {
					console.log(response.data);
					window.localStorage.setItem('user', JSON.stringify(response.data));
					console.log("111");
					axios.defaults.headers.common['Authorization'] = "Bearer " + response.data.jwt;
					console.log("222");

					this.$router.push("/");

				})
				.catch(function (error) {
					if (error.response) {
						component.loginError = error.response.data;
						console.log(error.response.data);
					} else if (error.request) {
						component.loginError = error.response.data;
						console.log(error.request);
					} else {
						//component.loginError = error.response.data;
						//console.log('Error', error.message);
					}
					console.log("error.config");
					console.log(error.config);
				});
		}
	},
});