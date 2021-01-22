Vue.component("user-profile", {
	data: function () {
		return {
			userData: {},
			saveInfo: null
		}
	},
	template: ` 
<div  class="d-flex" >
		<div id="particleJS-container" style="position:fixed; top:0; left:0;width:100%;z-index:0"></div>
	<div id="top" class="container" >
		<br>
		
		<div v-if="saveInfo" class="alert alert-success alert-dismissible">
			<!-- <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a> -->
			<strong>Success</strong> {{saveInfo}}
		</div>

		
		<h1 class="text-center">User Profile <span class="badge badge-danger">{{userData.userRole}}</span></h1>

		<form class="form-signin" v-on:submit.prevent="updateUser">

			<div class="form-label-group">
			<input type="name" id="inputUsername" class="form-control" placeholder="Username" v-model="userData.username" disabled required autofocus>
			<label for="inputUsername">Username</label>
			</div>

<!-- 
			<div class="row">
				<div class="col-md-6">
					<div class="form-label-group">
						<input type="password" id="inputPassword" class="form-control" placeholder="Password" v-model="userData.password" required>
						<label for="inputPassword">Password</label>
						</div>
				</div>
				<div class="col-md-6">
					<div class="form-label-group">
						<input type="password" id="inputPassword" class="form-control" placeholder="Password" v-model="userData.password" required>
						<label for="inputPassword">Password</label>
						</div>
				</div>
			</div> -->

			


			<br class="my-4">
			
			<div class="row">
				<div class="col-md-6">

					<div class="form-label-group">
						<input type="name" id="inputFirstName" class="form-control" placeholder="FirstName" v-model="userData.firstName" required autofocus>
						<label for="inputFirstName">FirstName</label>
					</div>
				</div>	

				<div class="col-md-6">
						<div class="form-label-group">
						<input type="name" id="inputLastName" class="form-control" placeholder="Last Name" v-model="userData.lastName" required autofocus>
						<label for="inputLastName">Last Name</label>
						</div>
				</div>	
			</div>
			
			<div class="row">

				<div class="col-md-6">
					<div class="form-label-group">
						<input type="date" id="inputBirthDate" class="form-control" placeholder="Birthday" v-model="userData.birthDate" required autofocus>
						<label for="inputBirthDate">Birthday</label>
					</div>

				</div>
				<div class="col-md-6">
		
					<div class="form-label-group">
						<select name="inputGender" id="inputGender" v-model="userData.gender" required>
							<option value="MALE">Male</option>
							<option value="FEMALE">Female</option>
						</select>
						<label for="inputGender">Gender</label>
						</div>
				</div>
			</div>


			<input class="btn btn-lg btn-primary btn-block text-uppercase" type="submit" value="Save changes" />
			<hr class="my-4">

			<div class="row">
				<div class="col-md-6">
					<h5 class="card-title text-center">Password:</h5>
				</div>
				<div class="col-md-6">
					<router-link to="/change-password" class="btn btn-warning btn-block text-uppercase">Change password</router-link>

				</div>
			</div>


		</form>

		<div v-if="userData.userRole == 'CUSTOMER'">

			<table class="table table-hover table-bordered table-striped text-center">
				<tbody>
					<tr>
						<td>Username</td>
						<td>{{userData.username }}</td>
					</tr>
					<tr>
						<td>First Name</td>
						<td>{{userData.firstName }}</td>
					</tr>
					<tr>
						<td>Last Name</td>
						<td>{{userData.lastName }}</td>
					</tr>
					<tr>
						<td>Birthday</td>
						<td>{{userData.birthDate}}</td>
					</tr>
					<tr>
						<td>Gender</td>
						<td>{{userData.gender}}</td>
					</tr>
					<tr>
						<td>Role</td>
						<td>{{userData.userRole}}</td>
					</tr>
				</tbody>
			</table>
			<br>
		</div>
	</div>	
</div>		  
`
	,
	mounted() {
		let localUserData = JSON.parse(window.localStorage.getItem('user'));
		console.log(localUserData);

		this.$nextTick(() => {
			axios
				.get('api/users/' + localUserData.username)
				.then(response => {
					this.userData = response.data;
					console.log(response.data.birthDate);
					console.log((new Date(response.data.birthDate)).toISOString().substring(0, 10));
					this.userData.birthDate = new Date(response.data.birthDate).toISOString().substring(0, 10);
					this.userData.gender = response.data.gender;
				});
		});


	},
	methods: {

		updateUser: function () {
			let localUserData = JSON.parse(window.localStorage.getItem('user'));
			var userData = {
				username: this.userData.username,
				firstName: this.userData.firstName,
				lastName: this.userData.lastName,
				gender: this.userData.gender,
				birthDate: new Date(this.userData.birthDate).getTime()
			}
			axios
				.patch('api/users/' + localUserData.username, userData)
				.then(response => {
					this.saveInfo = "Changes successfully saved";
				})
				.catch(function (error) {
					if (error.response) {
						console.log(error.response.data);
					} else if (error.request) {
						console.log(error.request);
					} else {
						console.log('Error', error.message);
					}
					console.log("error.config");
					console.log(error.config);
				});
		}

	},



});