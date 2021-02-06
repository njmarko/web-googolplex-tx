Vue.component("salesman-tickets", {
	data: function () {
		return {
			error: {},
			tickets: {},
			userData: {},
			customerType: {},
			searchParams: {},
			localUserData: {},
			saveInfo: null,
			typeClasses: {
				REGULAR: "regular",
				VIP: "vip",
				FAN_PIT: "fan-pit",
			},
		}
	},
	template: ` 
<div  class="d-flex" >
	<div id="top" class="container" >
		<br>
		
		<div v-if="saveInfo" class="alert alert-success alert-dismissible">
			<!-- <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a> -->
			<strong>Success</strong> {{saveInfo}}
		</div>


		<div class="row mb-3">
			<div class="col-md-12">
				<a class="btn btn-primary float-right" data-toggle="collapse" href="#searchCollapse" role="button" aria-expanded="false" aria-controls="searchCollapse">
					Filter
				</a>
			</div>
		</div>
		
		<form v-on:submit.prevent="searchTickets" class="collapse" id="searchCollapse">
			<div class="row">
				<div class="col-lg-3 col-md-4 col-sm-6">
					<div class="form-label-group">
						<input placeholder="Manifestation" id="findManifestation" class="form-control" ref="focusMe"
							v-model="searchParams.manifestationName">
						<label for="findManifestation">Manifestation Name</label>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6">
					<div class="form-label-group">
						<input type="number" step="any" placeholder="MIN PRICE" id="findMinPrice" class="form-control"
							v-model="searchParams.minPrice">
						<label for="findMinPrice">Min Price</label>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6">
					<div class="form-label-group">
						<input type="number" step="any" placeholder="MAX PRICE" id="findMaxPrice" class="form-control"
							v-model="searchParams.maxPrice">
						<label for="findMaxPrice">Max Price</label>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6">
					<div class="form-label-group">
						<input type="date" placeholder="FROM" id="findBeginDate" class="form-control"
							v-model="searchParams.beginDate">
						<label for="findBeginDate">Begin Date</label>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6">

					<div class="form-label-group">
						<input type="date" placeholder="TO" id="findEndDate" class="form-control"
							v-model="searchParams.endDate">
						<label for="findEndDate">End Date</label>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6">

					<div class="form-label-group">
						<select name="inputSortCriteria" id="inputSortCriteria" v-model="searchParams.sortCriteria">
							<option :value="undefined"></option>
							<option value="MANIF_NAME">Manifestation Name</option>
							<option value="TICKET_PRICE">Ticket Price</option>
							<option value="MANIF_DATE">Manifestation Date</option>
						</select>
						<label for="inputSortCriteria">Sort Criteria</label>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6">

					<div class="form-label-group">
						<select name="inputAscending" id="inputAscending" v-model="searchParams.ascending">
							<option :value="undefined"></option>
							<option value="true">Ascending</option>
							<option value="false">Descending</option>
						</select>
						<label for="inputAscending">Direction</label>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6">

					<div class="form-label-group">
						<select name="inputTicketType" id="inputTicketType" v-model="searchParams.ticketType">
							<option :value="undefined"></option>
							<option value="VIP">Vip</option>
							<option value="REGULAR">Regular</option>
							<option value="FAN_PIT">Fan Pit</option>
						</select>
						<label for="inputTicketType">Ticket Type</label>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6 ml-auto">
					<div class="row">
						<div class="col-sm-6">
							<div class="form-label-group">
								<button class="btn btn-block btn-primary" type="submit">Search</button>
							</div>
						</div>
						<div class="col-sm-6">

							<div class="form-label-group">
								<button class="btn btn-warning btn-block" v-on:click="clearParameters">Clear</button>
							</div>
						</div>
					</div>
				</div>

			</div>
		</form>
		<div v-for="t in tickets">
			<div class="row ticket" v-bind:class="getTicketClass(t.ticketType)">
				<h1 v-if="t.ticketStatus == 'CANCELED'" class="text-overlay">CANCELED</h1>

				<div class="col-sm-4 margin-v-auto nopadding">
					<router-link class="card-image" :to="{ path: '/manifestations/' + t.manifestation}">
						<!-- <img class="ticket-cover" :src="'https://picsum.photos/300/200/'" alt=""> -->
						<img class="ticket-cover"  v-bind:src="'/uploads/' + t.poster" alt="">
					</router-link>
				</div>
				<div class="col-sm-8 content d-flex flex-column">
					<div>
						<router-link class="card-image" :to="{ path: '/manifestations/' + t.manifestation}">
							<h4>{{t.manifestationName}}</h4>
						</router-link>
						<span v-bind:class="[t.ticketStatus == 'RESERVED' ? 'badge-success' : 'badge-danger']" class="badge">{{t.ticketStatus}}</span>
						<span class="badge badge-warning">{{t.ticketType}}</span>
						<span v-if="isOnGoing(t.dateOfManifestation)" class="badge badge-danger">On-going</span>
						<span v-if="isFinished(t.dateOfManifestation)" class="badge badge-success">Finished</span>


						<button  v-if="isStarted(t.dateOfManifestation) && userData.username == t.customer" v-bind:disabled="t.ticketStatus == 'CANCELED' || !isCancelable(t.dateOfManifestation)" class="btn-danger btn" v-on:click="cancelTicket(t)">Cancel</button>
						<div class="row">
							<div class="col-6">
								<table>
									<tr>
										<td>Price:</td>
										<td><strong>{{t.price}}</strong></td>
									</tr>
									<tr>
										<td>Starting at:</td>
										<td><strong>{{ formatDateTime(t.dateOfManifestation) }}</strong></td>
									</tr>
									<!-- <tr>
										<td>Starting at:</td>
										<td><strong>{{ formatDateTime(t.dateOfManifestation) }}</strong></td>
									</tr> -->
								</table>								
							</div>
							<div class="col-6">
								<table>
									<tr>
										<td>Id:</td>
										<td><strong>{{t.id}}</strong></td>
									</tr>
									<!-- <tr>
										<td>Seller:</td>
										<td><router-link class="font-weight-bold" :to="{ path: '/profile/' + t.seller}">{{t.seller }}</router-link></td>
									</tr> -->
									<tr>
										<td>Customer:</td>
										<td><router-link class="font-weight-bold" :to="{ path: '/profile/' + t.customer}">{{ t.cutomerFullName }} ({{ t.customer }})</router-link></td>
									</tr>
								</table>
							</div>

						</div>
						
					</div>
						<button v-if="userData && userData.userRole == 'ADMIN'" v-on:click="deleteTicket(t)" class="btn btn-danger text-uppercase btn-block mt-auto">DELETE</button>
				</div>
			</div>
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
			if (this.$route.query.beginDate ) {
				this.searchParams.beginDate = new Date(new Number(this.$route.query.beginDate)).toISOString().substring(0,10);
			}
			if (this.$route.query.endDate ) {
				this.searchParams.endDate = new Date(new Number(this.$route.query.endDate)).toISOString().substring(0,10);
			}
			this.$router.push({ query: {} });
			let sp =Object.assign({}, this.searchParams); 
			if (sp.beginDate) {
				sp.beginDate =  new Date(sp.beginDate).getTime()
			}
			if (sp.endDate) {
				sp.endDate =  new Date(sp.endDate).getTime()
			}

			this.$router.push({ query: sp });
			
			this.$refs.focusMe.focus();
			axios
				.get('api/users/' + this.localUserData.username + '/manif-tickets',{ params: sp} )
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
		deleteTicket: function(ticket){
			var confirmed = confirm("Are your sure that you want to remove ticket: " + ticket.id);
			
			if (confirmed == false){
				console.log("aborted");
				return;
			}

			axios
				.delete('api/tickets/' + ticket.id)
				.then(response => {
					// manif.deleted = true;
					this.searchTickets();
					// this.$forceUpdate();
					//TODO: Consider if you want to update model
					//this.users = this.users.filter(item => item !== obj);
				
			})
			.catch(response=>{
				console.log("Delete failed: " + response.data);
			});
		},
		loadData: function (response) {
			this.tickets = response.data;
			for (let index = 0; index < this.tickets.length; index++) {
				this.tickets[index].dateOfManifestation = new Date(response.data[index].dateOfManifestation).toISOString().substring(0, 10);
			}
		},
		searchTickets: function (event) {
			event.preventDefault();
			this.$router.push({ query: {} });
			let sp =Object.assign({}, this.searchParams); 
			if (sp.beginDate ) {
				sp.beginDate =  new Date(sp.beginDate).getTime()
			}
			if (sp.endDate ) {
				sp.endDate =  new Date(sp.endDate).getTime()
			}

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
			let sp =Object.assign({}, this.searchParams); 
			if (sp.beginDate ) {
				sp.beginDate =  new Date(sp.beginDate).getTime()
			}
			if (sp.endDate ) {
				sp.endDate =  new Date(sp.endDate).getTime()
			}
			this.$router.push({ query: sp });
			axios
				.get('api/users/' + this.localUserData.username + '/manif-tickets', { params: sp })
				.then(response => {
					this.loadData(response);
				})
		},
		formatDateTime: function (value) {
			return moment(value).format('DD/MM/YYYY hh:mm');
		},

		getTicketClass: function (type) {
			return this.typeClasses[type];
		},
		isOnGoing : function(time){
			startedManif = new Date(time);
			dayAfterManifestation = new Date(time);
			dayAfterManifestation.setDate(dayAfterManifestation.getDate()+1);

			today = new Date();
			return today < dayAfterManifestation && today > startedManif;
		},
		isFinished : function(time){
			dayAfterManifestation = new Date(time);
			dayAfterManifestation.setDate(dayAfterManifestation.getDate()+1);

			today = new Date();
			return today > dayAfterManifestation;
		},
		isCancelable : function(time) {
			weekAgoManif = new Date(time);
			weekAgoManif.setDate(weekAgoManif.getDate()-7);

			today = new Date();
			return today < weekAgoManif;
		},

		isStarted : function(time) {
			manifDate = new Date(time);
			today = new Date();
			console.log(manifDate);
			return today < manifDate;
		},

	},



});