Vue.component("salesman-add-manif", {
	data: function () {
		return {
			manifData: { location: {} },
			registerError: "",
			saveInfo: [],
			manifTypes: {}
		}
	},
	template: ` 
	<div class="container">
				<div id="particleJS-container" style="position:fixed; top:0; left:0;width:100%;z-index:0"></div>
		
		<div v-if="registerError" class="alert alert-danger alert-dismissible">
			<!-- <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a> -->
			<strong>Error</strong> {{registerError}}
		</div>
		<div v-for="info in saveInfo">
			<div  class="alert alert-success alert-dismissible">
				<a href="#" class="close" aria-label="close" v-on:click="removeSaveInfo(info)">&times;</a>
				<strong>Success</strong> {{info}}
			</div>
		</div>
		<br />	
		<div class="row">
			<div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
				<div class="card card-signin my-5">
					<div class="card-body">
					<h5 class="card-title text-center">Add Manifestation</h5>
					<form class="form-signin" v-on:submit.prevent="addManifestation">

						<div class="form-label-group">
						<input type="text" id="inputName" class="form-control" placeholder="Name" v-model="manifData.name" required ref='focusMe'>
						<label for="inputName">Name</label>
						</div>

						<div class="form-label-group">
						<input type="number" min='0' v-on:keyup="evalQuantityRangeSeats($event)" step='1'  id="inputAvailableSeats" class="form-control" placeholder="Available Seats" v-model="manifData.availableSeats" required>
						<label for="inputAvailableSeats">Available Seats</label>
						</div>

						<div class="form-label-group">
						<input type="date" id="inputDateOfOccurence" class="form-control" placeholder="Date of Occurence" v-model="manifData.dateOfOccurence" required autofocus>
						<label for="inputDateOfOccurence">Date of Occurence</label>
						</div>

						<div class="form-label-group">
						<input type="time" id="inputTimeOfOccurence" class="form-control" placeholder="Time of Occurence" v-model="manifData.timeOfOccurence" required autofocus>
						<label for="inputTimeOfOccurence">Time of Occurence</label>
						</div>

						<div class="form-label-group">
						<input type="number" step="any" min='0' v-on:keyup="evalQuantityRangePrice($event)"  id="inputRegularPrice" class="form-control" placeholder="Regular Price" v-model="manifData.regularPrice" required>
						<label for="inputRegularPrice">Regular Price</label>
						</div>

						<div class="form-label-group">
						<select name="inputManifType" id="inputManifType" v-model="manifData.manifestationType" required>
							<option v-for='(value, key) in manifTypes' :value='value.name'  > {{value.name}}</option>
						</select>
						<label for="inputManifType">Manifestation Type</label>
						</div>

						<div class="form-label-group">
						<input type="file" id="inputPoster" class="form-control" accept="image/*">
						<label for="inputPoster">Poster</label>
						</div>

						<br class="my-4">
						
						
						
						<div class="form-label-group">
						<input type="number" step="any" id="inputLongitude" class="form-control" placeholder="Longitude" v-model="manifData.location.longitude" required>
						<label for="inputLongitude">Longitude</label>
						</div>

						<div class="form-label-group">
						<input type="number" step="any" id="inputLatitude" class="form-control" placeholder="Latitude" v-model="manifData.location.latitude" required>
						<label for="inputLatitude">Latitude</label>
						</div>

						<div class="form-label-group">
						<input type="text" id="inputCity" class="form-control" placeholder="City" v-model="manifData.location.city" required >
						<label for="inputCity">City</label>
						</div>
						
						<div class="form-label-group">
						<input type="text" id="inputStreet" class="form-control" placeholder="Street" v-model="manifData.location.street" required >
						<label for="inputStreet">Street</label>
						</div>

						<div class="form-label-group">
							<input type="text" id="inputNumber" class="form-control" placeholder="Number" v-model="manifData.location.number" required >
							<label for="inputNumber">Number</label>
						</div>

						<div class="form-label-group">
						<input type="number" id="inputZipCode" min='0' v-on:keyup="evalQuantityRangeZipCode($event)"  class="form-control" placeholder="Zip Code" v-model="manifData.location.zipCode" required>
						<label for="inputZipCode">Zip Code</label>
						</div>

						<input class="btn btn-lg btn-primary btn-block text-uppercase" type="submit" value="ADD MANIFESTATION" />

					</form>
					</div>

				</div>
			</div>
			<div class="col-md-5 col-lg-7 mx-auto">
				<div class="card my-5 sticky-map">
					<map-component></map-component>

				</div>
			</div>
		</div>
	</div>
`
	,
	mounted() {

		this.$nextTick(() => {
			this.$refs.focusMe.focus();
		});

		axios
			.get("api/manifestation-type")
			.then(response => {
				this.manifTypes = response.data;
				this.manifData.manifestationType = Object.values(this.manifTypes)[0].name;
			});	
		this.$root.$on('mapLocation', (text) => {
			this.updateLocation(text);
		});

		this.$root.$on('mapAddress', (text) => {
			this.updateAddress(text);
		});



	},
	methods: {
		updateLocation(location) {
			this.manifData.location.longitude = location.longitude;
			this.manifData.location.latitude = location.latitude;
			this.$forceUpdate();
		},

		updateAddress(address) {
			var isEmpty = address.address == undefined
			if (!isEmpty){

				this.manifData.location.number = address.address.house_number;
				this.manifData.location.city = address.address.city;
				this.manifData.location.street = address.address.road;
				this.manifData.location.zipCode = address.address.postcode;
				this.$forceUpdate();
			}

		},

		removeSaveInfo(info) {
			this.saveInfo.pop();
		},

		addManifestation: function () {
			this.registerError = "";
			let component = this;

			// MANUAL DEEP COPY of all the attributes that can be changed in this edit form by using spread operator ... 
			// this does not do deep copy of the two lists that are tied to the manifestation as those list are not changed here
			var requestData = { ...this.manifData };
			requestData.location = { ...this.manifData.location };
			// Adding date and time and then i am parsing it into JS Date
			let dateTime = this.manifData.dateOfOccurence + " " + this.manifData.timeOfOccurence;
			requestData.dateOfOccurence = new Date(dateTime).getTime() ;

			axios
				.post('api/manifestations', requestData)
				.then(response => {
					this.saveInfo.push("Manifestation added successfully");
					console.log(response.data);
					this.uploadPoster(response.data.id);
					this.$router.push('/manifestations/' + response.data.id);
				})
				.catch(function (error) {
					if (error.response) {
						component.registerError = error.response.data;
						console.log(error.response.data);
					} else if (error.request) {
						component.registerError = error.response.data;
						console.log(error.request);
					}

					component.registerError = error.response.data;
					console.log("error.config");
					console.log(error.config);
				});


		},

		uploadPoster : function(id){
			// Image upload
			var formData = new FormData();
			var imagefile = document.querySelector('#inputPoster');
			if (imagefile.files.length > 0){
				formData.append("filename", imagefile.files[0]);
				formData.append("id", id);
				axios.post('api/upload', formData, {
					headers: {
					'Content-Type': 'multipart/form-data'
					}
				}).then(response =>{})
			}
		},
		evalQuantityRangePrice: function(event){
			if (this.manifData.regularPrice && this.manifData.regularPrice < 0) {
				this.manifData.regularPrice = '';
			}
            // To prevent numbers that start with 0 like 0003. decimal numbers are allowed like 0.3
            this.manifData.regularPrice = Number(this.manifData.regularPrice);
		},
		evalQuantityRangeSeats: function(event){
			if (this.manifData.availableSeats && this.manifData.availableSeats < 0) {
				this.manifData.availableSeats = '';
			}
            this.manifData.availableSeats = Number(this.manifData.availableSeats);
		},
		evalQuantityRangeZipCode: function(event){
			if (this.manifData.location.zipCode && this.manifData.location.zipCode < 0) {
				this.manifData.location.zipCode = '';
			}
            this.manifData.location.zipCode = Number(this.manifData.location.zipCode);
		},
	},
});