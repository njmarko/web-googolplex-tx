Vue.component("register-form", {
	data: function() {
		return {
			registerData: {}
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
					<input type="name" id="inputUsername" class="form-control" placeholder="Username" v-model="registerData.username" required autofocus>
					<label for="inputUsername">Username</label>
					</div>

					<div class="form-label-group">
					<input type="name" id="inputFirstName" class="form-control" placeholder="FirstName" v-model="registerData.firstName" required autofocus>
					<label for="inputFirstName">FirstName</label>
					</div>
	
					<div class="form-label-group">
					<input type="name" id="inputLastName" class="form-control" placeholder="Last Name" v-model="registerData.lastName" required autofocus>
					<label for="inputLastName">Last Name</label>
					</div>

					<div class="form-label-group">
					<input type="password" id="inputPassword" class="form-control" placeholder="Password" v-model="registerData.password1" required>
					<label for="inputPassword">Password</label>
					</div>
					
					<div class="form-label-group">
					<input type="password" id="inputConfirmPassword" class="form-control" placeholder="Confirm Password" v-model="registerData.password2" required>
					<label for="inputConfirmPassword">Confirm Password</label>
					</div>
	
					<div class="custom-control custom-checkbox mb-3">
					<input type="checkbox" class="custom-control-input" id="customCheck1">
					<label class="custom-control-label" for="customCheck1">Remember password</label>
					</div>

					<button class="btn btn-lg btn-primary btn-block text-uppercase" v-on:click="registerUser(registerData)" >Sign in</button>

					<hr class="my-4">
					<h5 class="card-title text-center">Already have an account?</h5>
					<a class="btn btn-warning btn-block text-uppercase" type="submit" href="#/login">Log in</a>

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
		registerUser : function(registerData){
			alert(registerData);
			console.log(registerData);
			var userData = {username: registerData.username, password1: registerData.password1, password2: registerData.password2, firstName: registerData.firstName, lastName: registerData.lastName, gender: 'MALE', birthDate: 12345678}
			axios
				.post('api/register', userData)
				.then(response => (alert(response.data)))
		}
	},
});