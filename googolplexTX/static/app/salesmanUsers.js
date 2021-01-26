Vue.component("salesman-users", {
	data: function () {
		return {
			error: {},
			users: {},
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

		
		<div v-for="u in users">
			<table class="table table-hover table-bordered table-striped text-center">
				<tbody >
					<tr>
						<td>Username</td>
						<td>{{u.username }}</td>
					</tr>
					<tr>
						<td>First Name</td>
						<td>{{u.firstName }}</td>
					</tr>
					<tr>
						<td>Last Name</td>
						<td>{{u.lastName }}</td>
					</tr>
					<tr>
						<td>Points</td>
						<td>{{u.points}}</td>
					</tr>
					<tr>
						<td>Birthday</td>
						<td>{{u.birthDate.month + "/" + u.birthDate.day + "/" + u.birthDate.year + "." }}</td>
					</tr>
					<tr>
						<td>gender</td>
						<td>{{u.gender}}</td>
					</tr>
					<hr/>
				</tbody>
			</table>		
		
		</div>

		<br>
	</div>	
</div>		  
`
	,
	mounted() {
		let localUserData = JSON.parse(window.localStorage.getItem('user'));
		if (localUserData == null || (localUserData.userRole != "SALESMAN" )) {
			this.$router.push("/");
		}
		console.log(localUserData);

		axios
			.get('api/users/' + localUserData.username + '/sold-to-users')
			.then(response => {
				this.users = response.data;
				console.log(this.users);
			});


	},
	methods: {
		/*
				updateUser : function(){
					let localUserData = JSON.parse(window.localStorage.getItem('user'));
					var userData = {
						username: this.userData.username, 
						firstName: this.userData.firstName, 
						lastName: this.userData.lastName, 
						gender: this.userData.gender, 
						birthDate: new Date(this.userData.birthDate).getTime()}
					axios
						.patch('api/users/' + localUserData.username  , userData)
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
				*/
	},



});