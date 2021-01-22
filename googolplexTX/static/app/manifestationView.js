Vue.component("manifestation-view", {
	data: function () {
		return {
			manifestation: null,
			tickets: null,
			comments: null,
			userData: {}
		}
	},
	template: ` 
<div  class="d-flex" >
		<div id="particleJS-container" style="position:fixed; top:0; left:0;width:100%;z-index:0"></div>
	<div id="top" class="container" >
		<br>
		

		
		<div  v-if="manifestation">
			<h1 class="text-center">{{manifestation.name}}</h1>

			<table class="table table-hover table-bordered table-striped text-center">
				<tbody >
					<tr>
						<td>Id</td>
						<td>{{manifestation.id }}</td>
					</tr>
					<tr>
						<td>Name</td>
						<td>{{manifestation.name }}</td>
					</tr>
					<tr>
						<td>Available Seats</td>
						<td>{{manifestation.availableSeats }}</td>
					</tr>
					<tr>
						<td>Date of Occurence</td>
						<td>{{manifestation.dateOfOccurence}}</td>
					</tr>
					<tr>
						<td>Regular Price</td>
						<td>{{manifestation.regularPrice}}</td>
					</tr>
					<tr>
						<td>Status</td>
						<td>{{manifestation.status}}</td>
					</tr>
					<tr>
						<td>Manifestation Type</td>
						<td>{{manifestation.manifestationType}}</td>
					</tr>
					<tr>
						<td>Salesman</td>
						<td>{{manifestation.salesman}}</td>
					</tr>
					<tr>
						<td>Location</td>
						<td>{{manifestation.location.city}}</td>
					</tr>
					<tr v-if="userData && (userData.userRole == 'ADMIN' || (userData.userRole == 'SALESMAN' && manifestation.salesman == userData.username))">
						<td colspan="2">
							<router-link :to="{ path: '/manifestations/' + manifestation.id + '/edit'}" class="btn btn-warning btn-block text-uppercase">Edit Manifestation</router-link>
						</td>
					</tr>



				</tbody>
			</table>
			<br>
			<hr>

			<div  v-if="tickets">
				<h2>Tickets</h2>
				<br>
				<a class="btn btn-primary" data-toggle="collapse" href="#collapseTickets" role="button" aria-expanded="false" aria-controls="collapseTickets">
					Show tickets list
				</a>

				<div class="collapse" id="collapseTickets">
				
					<div v-for="(t,i) in tickets">
						<h5>{{i + 1}}</h5>

						<table class="table table-hover table-bordered table-striped text-center">
							<tbody >
								<tr>
									<td>Id</td>
									<td>{{t.id }}</td>
								</tr>
								<tr>
									<td>Date of Occurence</td>
									<td>{{dateFromInt(t.dateOfManifestation)}}</td>
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
					<div class="h2" v-if="tickets.length === 0">No tickets</div>

				</div>
				<br>
			</div>

			<div  v-if="comments">
				<h2>Comments</h2>
				<a class="btn btn-primary" data-toggle="collapse" href="#collapseComments" role="button" aria-expanded="false" aria-controls="collapseComments">
					Show comments list
				</a>

				<div class="collapse" id="collapseComments">
				
					<div v-for="(c,i) in comments">
						<h5>{{i + 1}}</h5>

						<table class="table table-hover table-bordered table-striped text-center">
							<tbody >
								<tr>
									<td>Id</td>
									<td>{{c.id }}</td>
								</tr>
								<tr>
									<td>Rating</td>
									<td>{{c.rating}}</td>
								</tr>
								<tr>
									<td>Price</td>
									<td>{{c.text}}</td>
								</tr>
								<tr>
									<td>Status</td>
									<td>{{c.approved}}</td>
								</tr>
							</tbody>
						</table>		
						<hr/>	
					</div>
					<div class="h2" v-if="comments.length === 0">No Comments</div>

				</div>
				<br>

			</div>

		</div>
	</div>	
</div>		  
`
	,
	mounted() {
		let localUserData = JSON.parse(window.localStorage.getItem('user'));
		axios
			.get('api/users/' + localUserData.username)
			.then(response => {
				this.userData = response.data;
				console.log(response.data.birthDate);
				console.log((new Date(response.data.birthDate)).toISOString().substring(0, 10));
				this.userData.birthDate = new Date(response.data.birthDate).toISOString().substring(0, 10);
				this.userData.gender = response.data.gender;
			});
		axios
			.get('api/manifestations/' + this.$route.params.id)
			.then(response => {
				this.manifestation = response.data;
			});

		axios
			.get('api/manifestations/' + this.$route.params.id + '/tickets')
			.then(response => {

				this.tickets = response.data;
			});

		axios
			.get('api/manifestations/' + this.$route.params.id + '/comments')
			.then(response => {

				this.comments = response.data;
			});

	},
	methods: {

		// TODO: Make this global (store or smth)
		dateFromInt: function (value) {
			return new Date(value).toISOString().substring(0, 10);

		}

	},

});