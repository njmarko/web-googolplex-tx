Vue.component("web-shop", {
	data: function () {
		return {
			manifestations: null,
			searchParams: {},
			manifTypes: {},
			userData: {}
		}
	},
	template: ` 
<div  class="d-flex" >
		<div id="particleJS-container" style="position:fixed; top:0; left:0;width:100%;z-index:0"></div>
	<div id="top" class="container" >
		<br>
		
		<div class="row">
					<div class="col-md-12">
						<form v-on:submit.prevent="searchManifestations">
							<div class="form-inline">
								<div class="form-label-group">
										<input placeholder="Manifestation" id="findManifestation" class="form-control" ref="focusMe" v-model="searchParams.name"	>
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
										<input type="date"  placeholder="FROM" id="findBeginDate" class="form-control" v-model="searchParams.beginDate"	>
										<label for="findBeginDate">Begin Date</label> 
								</div>

								<div class="form-label-group">
										<input type="date"  placeholder="TO" id="findEndDate" class="form-control" v-model="searchParams.endDate"	>
										<label for="findEndDate">End Date</label> 
								</div>

								<div class="form-label-group">
										<input placeholder="Location" id="findLocation" class="form-control" ref="focusMe" v-model="searchParams.location"	>
										<label for="findLocation">City Location</label>
								</div>
								
								<div class="form-label-group">
								<select name="inputSortCriteria" id="inputSortCriteria" v-model="searchParams.sortCriteria" >
									<option :value="undefined"></option>
									<option value="MANIF_NAME">Manifestation Name</option>
									<option value="MANIF_DATE">Manifestation Date</option>
									<option value="TICKET_PRICE">Ticket Price</option>
									<option value="LOCATION">Location</option>
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
       			                 <select name="inputManifType" id="inputManifType" v-model="searchParams.manifestationType" >
									<option :value="undefined"></option>
       			                     <option v-for='(value, key) in manifTypes' :value='value.name' > {{value.name}}</option>
       			                 </select>
       			                 <label for="inputManifType">Manifestation Type</label>
									</div>				

								<div class="form-label-group" v-if="userData && userData.userRole == 'ADMIN'">
									<select name="inputStatus" id="inputStatus"  v-model="searchParams.status"	>
										<option :value="undefined"></option>
										<option value="ACTIVE">Active</option>
										<option value="INACTIVE">Inactive</option>
									</select>
									<label for="inputStatus">Status</label>
								</div>

								<div class="form-label-group">
									<input type="checkbox" name="inputHasAvailableTickets" class="form-control" v-model="searchParams.hasAvailableTickets" id="inputHasAvailableTickets" >
									<label for="inputHasAvailableTickets">Has available tickets</label>
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


		
		<h1 class="text-center">Manifestations</h1>
		<div  v-for="p in manifestations">
			<table class="table table-hover table-bordered table-striped text-center">
				<tbody >
					<tr>
						<td>Id</td>
						<td>{{p.id }}</td>
					</tr>
					<tr>
						<td>Name</td>
						<td>{{p.name }}</td>
					</tr>
					<tr>
						<td>Available Seats</td>
						<td>{{p.availableSeats }}</td>
					</tr>
					<tr>
						<td>Date of Occurence</td>
						<td>{{p.dateOfOccurence}}</td>
					</tr>
					<tr>
						<td>Regular Price</td>
						<td>{{p.regularPrice}}</td>
					</tr>
					<tr>
						<td>Status</td>
						<td>{{p.status}}</td>
					</tr>
					<tr>
						<td>Manifestation Type</td>
						<td>{{p.manifestationType}}</td>
					</tr>
					<tr>
						<td>Salesman</td>
						<td>{{p.salesman}}</td>
					</tr>
					<tr>
						<td>Location</td>
						<td>{{p.location.city}}</td>
					</tr>
					<tr>
						<td colspan="2">
							<img v-bind:src="'/uploads/' + p.poster" class="poster-img" />
						</td>
					</tr>
					<tr>
					<td colspan="2">
						<router-link :to="{ path: '/manifestations/' + p.id}" class="btn btn-warning btn-block text-uppercase">View</router-link>
					</td>
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
		this.localUserData = JSON.parse(window.localStorage.getItem('user'));
		//null check in case the local storage was deleted
		if (this.localUserData == null) {
			this.localUserData = { username: "" };
		} else {
			axios
				.get('api/users/' + this.localUserData.username)
				.then(response => {
					this.userData = response.data;
					this.userData.birthDate = new Date(response.data.birthDate).toISOString().substring(0, 10);
					this.userData.gender = response.data.gender;
				})
				.catch(error => {
					console.log("User not logged in");
				});
			}
		

		// load params from the address line
		this.searchParams = this.$route.query;
		if (this.$route.query.beginDate != null) {
			this.searchParams.beginDate = new Date(new Number(this.$route.query.beginDate)).toISOString().substring(0, 10);
		}
		if (this.$route.query.endDate != null) {
			this.searchParams.endDate = new Date(new Number(this.$route.query.endDate)).toISOString().substring(0, 10);
		}
		this.$router.push({ query: {} });
		let sp = Object.assign({}, this.searchParams);
		if (sp.beginDate != null) {
			sp.beginDate = new Date(sp.beginDate).getTime()
		}
		if (sp.endDate != null) {
			sp.endDate = new Date(sp.endDate).getTime()
		}

		this.$router.push({ query: sp });

		this.$refs.focusMe.focus();

		axios
			.get("api/manifestation-type")
			.then(response => {
				this.manifTypes = response.data;
			});



		axios
			.get('api/manifestations', { params: sp })
			.then(response => {
				this.manifestations = response.data;
				for (let index = 0; index < this.manifestations.length; index++) {
					this.manifestations[index].dateOfOccurence = new Date(response.data[index].dateOfOccurence).toISOString().substring(0, 10);
				}

			})
	},
	methods: {
		loadData: function (response) {
			this.manifestations = response.data;
			for (let index = 0; index < this.manifestations.length; index++) {
				this.manifestations[index].dateOfOccurence = new Date(response.data[index].dateOfOccurence).toISOString().substring(0, 10);
			}
		},
		searchManifestations: function (event) {
			event.preventDefault();

			this.$router.push({ query: {} });
			let sp = Object.assign({}, this.searchParams);
			if (sp.beginDate != null) {
				sp.beginDate = new Date(sp.beginDate).getTime()
			}
			if (sp.endDate != null) {
				sp.endDate = new Date(sp.endDate).getTime()
			}

			this.$router.push({ query: sp });

			let path = 'api/manifestations';
			axios
				.get(path, { params: sp })
				.then(response => {
					this.loadData(response);
				})
		},
		clearParameters: function (event) {
			event.preventDefault();

			this.searchParams = {};
			let sp = Object.assign({}, this.searchParams);
			if (sp.beginDate != null) {
				sp.beginDate = new Date(sp.beginDate).getTime()
			}
			if (sp.endDate != null) {
				sp.endDate = new Date(sp.endDate).getTime()
			}
			this.$router.push({ query: sp });

			let path = 'api/manifestations';
			axios
				.get(path, { params: sp })
				.then(response => {
					this.loadData(response);
				})
		},
	},

});