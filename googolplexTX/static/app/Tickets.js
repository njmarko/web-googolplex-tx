Vue.component("tickets", {
	data: function () {
		return {
			error: {},
			tickets: {},
			userData: {},
			searchParams: {},
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

		<div class="row">
				<div class="col-md-12">
					<form v-on:submit.prevent="searchTickets">
						<div class="form-inline">
							<div class="form-label-group">
									<input placeholder="Manifestation" id="findManifestation" class="form-control" ref="focusMe" v-model="searchParams.manifestationName"	>
									<label for="findManifestation">Manifestation Name</label>
							</div>
							<div class="form-label-group">
									<input type="number" step="any" placeholder="OD" id="findMinPrice" class="form-control" v-model="searchParams.minPrice"	>
									<label for="findMinPrice">OD</label>
							</div>
							<div class="form-label-group">
									<input placeholder="Last Name" id="findLastName" class="form-control" v-model="searchParams.lastName"	>
									<label for="findLastName">Last Name</label> 
							</div>

							<div class="form-label-group">
							<select name="inputSortCriteria" id="inputSortCriteria" v-model="searchParams.sortCriteria" >
								<option :value="undefined"></option>
								<option value="FIRST_NAME">First Name</option>
								<option value="LAST_NAME">Last Name</option>
								<option value="USERNAME">Username</option>
								<option value="POINTS">Points</option>
							</select>
							<label for="inputSortCriteria">Sort Criteria</label>
							</div>

							<div class="form-label-group">
							<select name="inputAscending" id="inputAscending"  v-model="searchParams.ascending"	>
								<option :value="undefined"></option>
								<option value="true">Ascending</option>
								<option value="false">Descending</option>
							</select>
							<label for="inputAscending">Direction</label>
							</div>

							<div class="form-label-group">
							<select name="inputUserRole" id="inputUserRole"  v-model="searchParams.userRole"	>
								<option :value="undefined"></option>
								<option value="CUSTOMER">Customer</option>
								<option value="SALESMAN">Salesman</option>
								<option value="ADMIN">Admin</option>
							</select>
							<label for="inputUserRole">User Role</label>
							</div>

							<div class="form-label-group">
							</div>

							<div class="form-label-group">
									<button class="btn btn-primary" type="submit">Search</button>
							</div>
							<div class="form-label-group">
									<button class="btn btn-warning pull-right" v-on:click="clearParameters">Clear</button>
							</div>
						</div>
					</form>
				</div>
			</div>


		
		<h1 class="text-center">Tickets</h1>

		
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
						<td>{{t.manifestationName}}</td>
					</tr>

					<tr>
						<td>Customer</td>
						<td>{{t.cutomerFullName}}</td>
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
		//null check in case the local storage was deleted
		if (localUserData == null) {
			localUserData = { username: "" };
		}
		if (localUserData != null && localUserData.userRole == 'ADMIN') {
			let path = 'api/tickets';
			axios
				.get(path)
				.then(response => {
					this.tickets = response.data;
					for (let index = 0; index < this.tickets.length; index++) {
						this.tickets[index].dateOfManifestation = new Date(response.data[index].dateOfManifestation).toISOString().substring(0, 10);
					}
					console.log(this.tickets);
				});
		}
		else {
			let path = 'api/users/' + JSON.parse(window.localStorage.getItem('user')).username + '/tickets';
			axios
				.get(path)
				.then(response => {
					this.tickets = response.data;
					for (let index = 0; index < this.tickets.length; index++) {
						this.tickets[index].dateOfManifestation = new Date(response.data[index].dateOfManifestation).toISOString().substring(0, 10);
					}
					console.log(this.tickets);
				});
		}
		this.$nextTick(() => {
			this.searchParams = this.$route.query;
			axios
				.get('api/users', { params: this.searchParams })
				.then(response => {
					this.users = response.data;
					this.users.forEach(element => {
						element.birthDate = new Date(element.birthDate).toISOString().substring(0, 10);
					});
				})
			this.$refs.focusMe.focus();

		});


	},
	methods: {

		searchTickets: function (event) {
			event.preventDefault();
			this.$router.push({ query: {} });
			let sp = this.searchParams;
			this.$router.push({ query: sp });
			axios
				.get('api/users', { params: sp })
				.then(response => {
					this.users = response.data;
					this.users.forEach(element => {
						element.birthDate = new Date(element.birthDate).toISOString().substring(0, 10);
					});
				})
		},
		clearParameters: function (event) {
			event.preventDefault();
			this.searchParams = {};
			let sp = this.searchParams;
			this.$router.push({ query: sp });
			axios
				.get('api/users', { params: sp })
				.then(response => {
					this.users = response.data;
					this.users.forEach(element => {
						element.birthDate = new Date(element.birthDate).toISOString().substring(0, 10);
					});
				})
		}


	},



});
