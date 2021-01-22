Vue.component("salesman-manifestations", {
	data: function () {
		return {
			error: {},
			manifestations: {},
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

		
		<div v-for="m in manifestations">
			<table class="table table-hover table-bordered table-striped text-center">
				<tbody >
					<tr>
						<td>Id</td>
						<td>{{m.id }}</td>
					</tr>
					<tr>
						<td>Name</td>
						<td>{{m.name }}</td>
					</tr>
					<tr>
						<td>Available Seats</td>
						<td>{{m.availableSeats }}</td>
					</tr>
					<tr>
						<td>Date of Occurence</td>
						<td>{{m.dateOfOccurence.date.month + "/" + m.dateOfOccurence.date.day + "/" + m.dateOfOccurence.date.year + "." }}</td>
					</tr>
					<tr>
						<td>Regular Price</td>
						<td>{{m.regularPrice}}</td>
					</tr>
					<tr>
						<td>Status</td>
						<td>{{m.status}}</td>
					</tr>
					<tr>
						<td>Manifestation Type</td>
						<td>{{m.manifestationType}}</td>
					</tr>
					<tr>
						<td>Salesman username TODO</td>
						<td>{{m.salesman + " " + m.salesman.lastName}}</td>
					</tr>
					<tr>
						<td>Location</td>
						<td>{{m.location.city}}</td>
					</tr>
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
		if (localUserData == null) {
			this.$router.push("/");
		}

		this.$nextTick(() => {
			axios
				.get('api/users/' + localUserData.username + '/manifestations')
				.then(response => {
					this.manifestations = response.data;
					console.log(this.manifestations);
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