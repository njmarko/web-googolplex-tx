Vue.component("register-form", {
	data: function() {
		return {
			manifestations: null
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
					<input type="name" id="inputUsername" class="form-control" placeholder="Username" required autofocus>
					<label for="inputUsername">Username</label>
					</div>

					<div class="form-label-group">
					<input type="name" id="inputFirstName" class="form-control" placeholder="FirstName" required autofocus>
					<label for="inputFirstName">FirstName</label>
					</div>
	
					<div class="form-label-group">
					<input type="name" id="inputLastName" class="form-control" placeholder="Last Name" required autofocus>
					<label for="inputLastName">Last Name</label>
					</div>

					<div class="form-label-group">
					<input type="password" id="inputPassword" class="form-control" placeholder="Password" required>
					<label for="inputPassword">Password</label>
					</div>
					
					<div class="form-label-group">
					<input type="password" id="inputConfirmPassword" class="form-control" placeholder="Confirm Password" required>
					<label for="inputConfirmPassword">Confirm Password</label>
					</div>
	
					<div class="custom-control custom-checkbox mb-3">
					<input type="checkbox" class="custom-control-input" id="customCheck1">
					<label class="custom-control-label" for="customCheck1">Remember password</label>
					</div>

					<button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">Sign in</button>

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
	methods: {
	},
	mounted() {
		axios
			.get('api/manifestations')
			.then(response => (this.manifestations = response.data))
	},
});