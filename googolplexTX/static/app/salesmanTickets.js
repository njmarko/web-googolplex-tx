Vue.component("salesman-tickets", {
	data: function () {
		return {
			error: {},
			tickets: {},
			userData: {},
			customerType: {},
			searchParams: {},
			localUserData: {},
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
									<input type="number" step="any" placeholder="MIN PRICE" id="findMinPrice" class="form-control" v-model="searchParams.minPrice"	>
									<label for="findMinPrice">Min Price</label>
							</div>	
							<div class="form-label-group">
									<input type="number" step="any" placeholder="MAX PRICE" id="findMaxPrice" class="form-control" v-model="searchParams.maxPrice"	>
									<label for="findMaxPrice">Max Price</label> 
							</div>
							<div class="form-label-group">
									<input type="date"  placeholder="FROM" id="findBeginDate" class="form-control" v-model="searchParams.endDate"	>
									<label for="findBeginDate">Begin Date</label> 
							</div>

							<div class="form-label-group">
									<input type="date"  placeholder="TO" id="findEndDate" class="form-control" v-model="searchParams.endDate"	>
									<label for="findEndDate">End Date</label> 
							</div>

							<div class="form-label-group">
							<select name="inputSortCriteria" id="inputSortCriteria" v-model="searchParams.sortCriteria" >
								<option :value="undefined"></option>
								<option value="MANIF_NAME">Manifestation Name</option>
								<option value="TICKET_PRICE">Ticket Price</option>
								<option value="MANIF_DATE">Manifestation Date</option>
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
							<select name="input" id="inputTicketType"  v-model="searchParams.ticketType">
								<option :value="undefined"></option>
								<option value="VIP">Vip</option>
								<option value="REGULAR">Regular</option>
								<option value="FAN_PIT">Fan Pit</option>
							</select>
							<label for="inputTicketType">Ticket Type</label>
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

		this.localUserData = JSON.parse(window.localStorage.getItem('user'));
		//null check in case the local storage was deleted
		if (this.localUserData == null) {
			this.localUserData = { username: "" };
		}

		this.$nextTick(() => {
			this.searchParams = this.$route.query;
			this.$refs.focusMe.focus();
			axios
				.get('api/users/' + this.localUserData.username + '/manif-tickets')
				.then(response => {
					this.tickets = response.data;
					for (let index = 0; index < this.tickets.length; index++) {
						this.tickets[index].dateOfManifestation = new Date(response.data[index].dateOfManifestation).toISOString().substring(0, 10);
					}

				})
				.catch(function (error) {
					if (error.response) {
						console.log(error.response.data);
						
				}});

		});

	},
	methods: {
		loadData: function (response) {
			this.tickets = response.data;
			for (let index = 0; index < this.tickets.length; index++) {
				this.tickets[index].dateOfManifestation = new Date(response.data[index].dateOfManifestation).toISOString().substring(0, 10);
			}
		},
		searchTickets: function (event) {
			event.preventDefault();
			this.$router.push({ query: {} });
			let sp = this.searchParams;
			this.$router.push({ query: sp });
			axios
				.get('api/users/' + this.localUserData.username + '/manif-tickets', { params: sp })
				.then(response => {
					this.loadData(response);
				})
		},
		clearParameters: function (event) {
			event.preventDefault();
			this.searchParams = {};
			let sp = this.searchParams;
			this.$router.push({ query: sp });
			axios
				.get('api/users/' + this.localUserData.username + '/manif-tickets', { params: sp })
				.then(response => {
					this.loadData(response);
				})
		}

	},



});