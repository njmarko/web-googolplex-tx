Vue.component("change-password", {
	data: function () {
		return {
			userData: {},
			newPasswordData: {},
			formError: ""
		}
	},
	template: ` 
<div  class="d-flex" >
		<div id="particleJS-container" style="position:fixed; top:0; left:0;width:100%;z-index:0"></div>
	<div id="top" class="container" >
		<br>
		

		
		<h1 class="text-center">User Change Password</h1>

		<div v-if="formError" class="alert alert-danger alert-dismissible">
			<!-- <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a> -->
			<strong>Error</strong> {{formError}}
		</div>

		<form class="form-signin" v-on:submit.prevent="changePassword">


			<div class="row justify-content-center">
				<div class="col-md-6 col-md-offset-3">
					<div class="form-label-group">
						<input type="password" id="inputPassword" class="form-control" placeholder="Old Password" v-model="newPasswordData.oldPassword" required>
						<label for="inputPassword">Old Password</label>
						</div>
				</div>
			</div>

			<div class="row justify-content-md-center">
				<div class="col-md-6 col-md-offset-3">
					<div class="form-label-group">
						<input type="password" id="inputPassword" class="form-control" placeholder="New Password" v-model="newPasswordData.password1" required>
						<label for="inputPassword">New Password</label>
					</div>
				</div>
			</div>

			<div class="row justify-content-md-center">
				<div class="col-md-6">

					<div class="form-label-group">
						<input type="password" id="inputConfirmPassword" class="form-control" placeholder="Confirm Password" v-model="newPasswordData.password2" required>
						<label for="inputConfirmPassword">Confirm Password</label>
					</div>
				</div>
			</div>

			

			<div class="row justify-content-md-center">
				<div class="col-md-6">
					<input class="btn btn-lg btn-primary btn-block text-uppercase" type="submit" value="Change password" />
				</div>
			</div>

		</form>

	</div>	
</div>		  
`
	,
	methods: {
		changePassword: function () {
			let localUserData = JSON.parse(window.localStorage.getItem('user'));
			let component = this;

			var userData = {
				oldPassword: this.newPasswordData.oldPassword,
				newPassword: this.newPasswordData.password1
			}
			axios
				.patch('api/users/' + localUserData.username + '/change-password', userData)
				.then(response => {
					this.$router.push('/profile');
				})
				.catch(function (error) {
					if (error.response) {
						component.formError = error.response.data;
						console.log(error.response.data);
					} else if (error.request) {
						component.formError = error.response.data;
						console.log(error.request);
					} else {
						component.formError = error.response.data;
						console.log('Error', error.message);
					}

					console.log("error.config");
					console.log(error.config);
				});
		}
	},
	mounted() {
		/*axios
			.get('api/users/admin')
			.then(response => {
				
				this.userData = response.data;
				console.log((new Date(response.data.birthDate)).toISOString().substring(0, 10));
				this.userData.birthDate = new Date(response.data.birthDate).toISOString().substring(0, 10);
				this.userData.gender = response.data.gender;
			});
		*/
	},

});