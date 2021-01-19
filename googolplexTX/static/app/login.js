Vue.component("login-form", {
	data: function() {
		return {
			loginUser: {}
		}
	},
	template: ` 
	<div class="container">
				<div id="particleJS-container" style="position:fixed; top:0; left:0;width:100%;z-index:0"></div>
		<div class="row">
			<div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
			<div class="card card-signin my-5">
				<div class="card-body">
				<h5 class="card-title text-center">Sign In</h5>
				<form class="form-signin">

					<div class="form-label-group">
					<input type="text" id="inputUsername" class="form-control" placeholder="Username" v-model="loginUser.username" required autofocus>
					<label for="inputUsername">Username</label>
					</div>
	
					<div class="form-label-group">
					<input type="password" id="inputPassword" class="form-control" placeholder="Password" v-model="loginUser.password" required>
					<label for="inputPassword">Password</label>
					</div>
	
					<div class="custom-control custom-checkbox mb-3">
					<input type="checkbox" class="custom-control-input" id="customCheck1">
					<label class="custom-control-label" for="customCheck1">Remember password</label>
					</div>

					<button class="btn btn-lg btn-primary btn-block text-uppercase" v-on:click="userLogin(loginUser)">Sign in</button>

					<hr class="my-4">
					<h5 class="card-title text-center">Don't have an account?</h5>
					<a class="btn btn-warning btn-block text-uppercase" href="#/register">Register</a>

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
		userLogin : function(loginUser){
			alert(loginUser);
			console.log(loginUser);
			var user = {username: loginUser.username, password: loginUser.password}
			axios
				.post('api/login', user)
				.then(response => (alert(response.data)))
		}
	},
});