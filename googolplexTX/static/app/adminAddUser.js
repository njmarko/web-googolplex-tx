Vue.component("admin-add-user", {
	data: function() {
		return {
			registerData: {gender:'MALE', userRole: "SALESMAN"},
			registerError: "",
			saveInfo: null
		}
	},
	template: ` 
	<div class="container">
				<div id="particleJS-container" style="position:fixed; top:0; left:0;width:100%;z-index:0"></div>
		
		<div v-if="registerError" class="alert alert-danger alert-dismissible">
			<!-- <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a> -->
			<strong>Error</strong> {{registerError}}
		</div>
		<div v-if="saveInfo" class="alert alert-success alert-dismissible">
			<!-- <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a> -->
			<strong>Success</strong> {{saveInfo}}
		</div>
		<br />	
		<div class="row">
			<div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
			<div class="card card-signin my-5">
				<div class="card-body">
				<h5 class="card-title text-center">Add User</h5>
				<form class="form-signin" v-on:submit.prevent="registerUser">

					<div class="form-label-group">
					<input type="name" id="inputUsername" class="form-control" placeholder="Username" v-model="registerData.username" required ref='focusMe'>
					<label for="inputUsername">Username</label>
					</div>

					<div class="form-label-group">
					<input type="password" id="inputPassword" class="form-control" placeholder="Password" v-model="registerData.password1" required>
					<label for="inputPassword">Password</label>
					</div>
					
					<div class="form-label-group">
					<input type="password" id="inputConfirmPassword" class="form-control" placeholder="Confirm Password" v-model="registerData.password2" required>
					<label for="inputConfirmPassword">Confirm Password</label>
					</div>

					<br class="my-4">
					
					<div class="form-label-group">
					<input type="name" id="inputFirstName" class="form-control" placeholder="FirstName" v-model="registerData.firstName" required autofocus>
					<label for="inputFirstName">FirstName</label>
					</div>
	
					<div class="form-label-group">
					<input type="name" id="inputLastName" class="form-control" placeholder="Last Name" v-model="registerData.lastName" required autofocus>
					<label for="inputLastName">Last Name</label>
					</div>

					<div class="form-label-group">
					<input type="date" id="inputBirthDate" class="form-control" placeholder="Birthday" v-model="registerData.birthDate" required autofocus>
					<label for="inputBirthDate">Birthday</label>
					</div>

					<div class="form-label-group">
					<select name="inputGender" id="inputGender" v-model="registerData.gender" required>
						<option value="MALE">Male</option>
						<option value="FEMALE">Female</option>
					</select>
					<label for="inputGender">Gender</label>
					</div>

					<div class="form-label-group">
					<select name="inputUserRole" id="inputUserRole" v-model="registerData.userRole" required>
						<option value="SALESMAN">Salesman</option>
						<option value="CUSTOMER">Customer</option>
					</select>
					<label for="inputUserRole">User Role</label>
					</div>


					<input class="btn btn-lg btn-primary btn-block text-uppercase" type="submit" value="REGISTER USER" />

				</form>
				</div>
			</div>
			</div>
		</div>
		</div>
`
	,
	mounted() {
			this.$nextTick(() => this.$refs.focusMe.focus());		
	},
	methods: {
		registerUser : function(){
			let component = this;

			console.log(this.registerData);	// DEBUG

			var userData = {
				username: this.registerData.username,
				password1: this.registerData.password1,
				password2: this.registerData.password2,
				firstName: this.registerData.firstName, 
				lastName: this.registerData.lastName, 
				gender: this.registerData.gender, 
				userRole: this.registerData.userRole,
				birthDate: new Date(this.registerData.birthDate).getTime()}
			axios
				.post('api/register', userData)
				.then(response => {
					this.saveInfo = "User Registered Successfully";
				})
				.catch(function (error) {
					if (error.response) {
						component.registerError = error.response.data;
						console.log(error.response.data);
					} else if (error.request) {
						component.registerError = error.response.data;
						console.log(error.request);
					} else {
						component.registerError = error.response.data;
						console.log('Error', error.message);
					}
					component.registerError = error.response.data;
					console.log("error.config");
					console.log(error.config);
				});
		}
	},
});