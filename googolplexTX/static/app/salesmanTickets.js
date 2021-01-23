Vue.component("salesman-tickets", {
	data: function () {
		return {
			error: {},
			tickets: {},
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

		
		<div v-for="t in tickets">
			<table class="table table-hover table-bordered table-striped text-center">
				<tbody >
					<tr>
						<td>Id</td>
						<td>{{t.id }}</td>
					</tr>
					<tr>
						<td>Date of Occurence</td>
						<td>{{t.dateOfManifestation}}</td>
					</tr>
					<tr>
						<td>Price</td>
						<td>{{t.price}}</td>
					</tr>
					<tr>
						<td>Status</td>
						<td>{{t.ticketStatus}}</td>
					</tr>
					<tr>
						<td>Manifestation</td>
						<td>{{t.manifestation}}</td>
					</tr>
					<tr>
						<td>Ticket Type</td>
						<td>{{t.ticketType}}</td>
					</tr>
				</tbody>
			</table>		
			<hr/>	
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
		axios
			.get('api/users/' + localUserData.username + '/manif-tickets')
			.then(response => {
				this.tickets = response.data;
				for (let index = 0; index < this.tickets.length; index++) {
					this.tickets[index].dateOfManifestation = new Date(response.data[index].dateOfManifestation).toISOString().substring(0, 10);
				}
				console.log(this.tickets);
			});


	},
	methods: {

		/*updateUser : function(){
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
		}*/

	},



});