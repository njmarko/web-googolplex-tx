Vue.component("salesman-manifestations", {
	data: function () {
		return {
			error: {},
			manifestations: null,
			userData: {},
			searchParams: {},
			manifTypes: {},
			saveInfo: null,
			numberOfColumns: 3,
		}
	},
	computed: {
		rowCount: function () {
			if (this.manifestations)
				return Math.floor(((this.manifestations.length - 1) / this.numberOfColumns)) + 1
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

		<form v-on:submit.prevent="searchManifestations" class="collapse" id="searchCollapse">
			<div class="row">
				<div class="col-lg-3 col-md-4 col-sm-6">
					<div class="form-label-group">
						<input placeholder="Manifestation" id="findManifestation" class="form-control" ref="focusMe"
							v-model="searchParams.name">
						<label for="findManifestation">Manifestation Name</label>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6">
					<div class="form-label-group">
						<input type="number" step="any" placeholder="MIN PRICE" id="findMinPrice"
							class="form-control" v-model="searchParams.minPrice">
						<label for="findMinPrice">Min Price</label>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6">	
					<div class="form-label-group">
						<input type="number" step="any" placeholder="MAX PRICE" id="findMaxPrice"
							class="form-control" v-model="searchParams.maxPrice">
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
						<input placeholder="Location" id="findLocation" class="form-control" ref="focusMe"
							v-model="searchParams.location">
						<label for="findLocation">City Location</label>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6">

					<div class="form-label-group">
						<select name="inputSortCriteria" id="inputSortCriteria" v-model="searchParams.sortCriteria">
							<option :value="undefined"></option>
							<option value="MANIF_NAME">Manifestation Name</option>
							<option value="MANIF_DATE">Manifestation Date</option>
							<option value="TICKET_PRICE">Ticket Price</option>
							<option value="LOCATION">Location</option>
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
						<select name="inputManifType" id="inputManifType" v-model="searchParams.manifestationType">
							<option :value="undefined"></option>
							<option v-for='(value, key) in manifTypes' :value='value.name'> {{value.name}}</option>
						</select>
						<label for="inputManifType">Manifestation Type</label>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6" v-if="userData && userData.userRole == 'ADMIN'">

					<div class="form-label-group">
						<select name="inputStatus" id="inputStatus" v-model="searchParams.status">
							<option :value="undefined"></option>
							<option value="ACTIVE">Active</option>
							<option value="INACTIVE">Inactive</option>
						</select>
						<label for="inputStatus">Status</label>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6">

					<div class="form-label-group checkbox">
						<input type="checkbox" name="inputHasAvailableTickets" class="form-control"
							v-model="searchParams.hasAvailableTickets" id="inputHasAvailableTickets">
						<label for="inputHasAvailableTickets">Has available tickets</label>
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
	

		<h1 class="text-center">My Manifestations</h1>

		<div class="row row-spacing" v-for="(row, rowI) in rowCount" :key="manifestations.id">
			<div class="col-md-4" v-for="(manifestation, index) in manifestations.slice(rowI * numberOfColumns, rowI * numberOfColumns + numberOfColumns)" :key="manifestations.id">
				<div class="card manif-card">
					<router-link class="card-image" :to="{ path: '/manifestations/' + manifestation.id}">

						<div class="image-dim">
							<div class="vertical-centered">
								<span class="badge badge-primary">{{manifestation.manifestationType}}</span>
								<h6>Available seats: {{manifestation.availableSeats}}</h6>
								<h6 v-if="manifestation.averageRating">Rating: {{manifestation.averageRating}}</h6>
								<br>			
								<h6>Date: {{formatDateTime(manifestation.dateOfOccurence)}}</h6>
								<h6>Price: {{manifestation.regularPrice}}</h6>
								<button v-if="userData && userData.userRole == 'ADMIN' && manifestation.status=='INACTIVE'" v-on:click="activateManif($event, manifestation)" class="btn btn-success text-uppercase btn-block mt-auto">ACTIVATE</button>
							</div>
						</div>
						<!-- <img class="card-img-top" :src="'https://picsum.photos/300/200/' + '/'.repeat(row * numberOfColumns + index) " alt=""> -->
						<img class="card-img-top" v-bind:src="'/uploads/' + manifestation.poster" alt="">
						
					</router-link>
					<div class="card-body d-flex flex-column">
						<router-link :to="{ path: '/manifestations/' + manifestation.id}"><h5>{{manifestation.name}}</h5></router-link>
						<span v-if="manifestation.status == 'INACTIVE'" class="badge badge-warning">INACTIVE</span>
						<p><i class="glyphicon glyphicon-globe"></i> {{manifestation.location.city}}</p>
						<button v-if="userData && userData.userRole == 'ADMIN'" v-on:click="deleteManif(manifestation)" class="btn btn-danger text-uppercase btn-block mt-auto">DELETE</button>
					</div>
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
		if (this.localUserData == null) {
			this.$router.push("/");
		}else{
			axios
				.get('api/users/' + this.localUserData.username)
				.then(response => {
					this.userData = response.data;
					this.userData.birthDate = new Date(response.data.birthDate).toISOString().substring(0, 10);
					this.userData.gender = response.data.gender;
				})
				.catch(error => {
					console.log("User not logged in");
				})
		}

		// load params from the address line
		this.searchParams = this.$route.query;
		if (this.$route.query.beginDate ) {
			this.searchParams.beginDate = new Date(new Number(this.$route.query.beginDate)).toISOString().substring(0, 10);
		}
		if (this.$route.query.endDate ) {
			this.searchParams.endDate = new Date(new Number(this.$route.query.endDate)).toISOString().substring(0, 10);
		}
		this.$router.push({ query: {} });
		let sp = Object.assign({}, this.searchParams);
		if (sp.beginDate ) {
			sp.beginDate = new Date(sp.beginDate).getTime()
		}
		if (sp.endDate ) {
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
			.get('api/users/' + this.localUserData.username + '/manifestations', { params: sp })
			.then(response => {
				this.manifestations = response.data;
				for (let index = 0; index < this.manifestations.length; index++) {
					this.manifestations[index].dateOfOccurence = new Date(response.data[index].dateOfOccurence).toISOString().substring(0, 10);
				}

			});

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
			if (sp.beginDate ) {
				sp.beginDate = new Date(sp.beginDate).getTime()
			}
			if (sp.endDate ) {
				sp.endDate = new Date(sp.endDate).getTime()
			}

			this.$router.push({ query: sp });

			let path = 'api/users/' + this.localUserData.username + '/manifestations';
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
			if (sp.beginDate ) {
				sp.beginDate = new Date(sp.beginDate).getTime()
			}
			if (sp.endDate ) {
				sp.endDate = new Date(sp.endDate).getTime()
			}
			this.$router.push({ query: sp });

			let path = 'api/users/' + this.localUserData.username + '/manifestations';
			axios
				.get(path, { params: sp })
				.then(response => {
					this.loadData(response);
				})
		},

		formatDateTime: function (value) {
			return moment(value).format('DD/MM/YYYY hh:mm');
		},

	},



});