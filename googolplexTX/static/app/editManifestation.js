Vue.component("edit-manif", {
    data: function () {
        return {
            manifData: { location: {} },
            registerError: "",
            saveInfo: [],
            manifTypes: {},
            userData: {}
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
				<h5 class="card-title text-center">Edit Manifestation</h5>
				<form class="form-signin" v-on:submit.prevent="editManifestation">

                    <div v-if="userData && userData.userRole == 'ADMIN'" class="form-label-group" >
                    <select name="inputStatus" id="inputStatus" v-model="manifData.status" required>
                        <option value='ACTIVE' >ACTIVE</option>
                        <option value='INACTIVE' >INACTIVE</option>
					</select>
					<label for="inputManifType">Manifestation Type</label>

                    </div>
					<div class="form-label-group">
					<input type="text" id="inputName" class="form-control" placeholder="Name" v-model="manifData.name" required ref='focusMe'>
					<label for="inputName">Name</label>
					</div>

					<div class="form-label-group">
					<input type="number" step='1' id="inputAvailableSeats" class="form-control" placeholder="Available Seats" v-model="manifData.availableSeats" required>
					<label for="inputAvailableSeats">Available Seats</label>
					</div>

					<div class="form-label-group">
					<input type="date" id="inputDateOfOccurence" class="form-control" placeholder="Date of Occurence" v-model="manifData.dateOfOccurence" required autofocus>
                    <label for="inputDateOfOccurence">Date of Occurence</label>
                    </div>
                    
					<div class="form-label-group">
					<input type="number" step="any" id="inputRegularPrice" class="form-control" placeholder="Regular Price" v-model="manifData.regularPrice" required>
					<label for="inputRegularPrice">Regular Price</label>
                    </div>

					<div class="form-label-group">
                    <select name="inputManifType" id="inputManifType" v-model="manifData.manifestationType" required>
                        <option v-for='(value, key) in manifTypes' :value='value.name' > {{value.name}}</option>
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
					<input type="text" id="inputNumber" class="form-control" placeholder="Number" v-model="manifData.location.number" required >
					<label for="inputNumber">Number</label>
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
					<input type="number" id="inputZipCode" class="form-control" placeholder="Zip Code" v-model="manifData.location.zipCode" required>
					<label for="inputZipCode">Zip Code</label>
                    </div>

					<input class="btn btn-lg btn-primary btn-block text-uppercase" type="submit" value="SAVE MANIFESTATION" />

				</form>
				</div>
			</div>
			</div>
		</div>
		</div>
`
    ,
    mounted() {
        let localUserData = JSON.parse(window.localStorage.getItem('user'));

        this.$nextTick(() => {
            this.$refs.focusMe.focus();
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
                .get("api/manifestation-type")
                .then(response => {
                    console.log(response.data);
                    this.manifTypes = response.data;
                    console.log()
                });
            axios
                .get('api/manifestations/' + this.$route.params.id)
                .then(response => {
                    this.manifData = response.data;
                    this.manifData.dateOfOccurence = new Date(response.data.dateOfOccurence).toISOString().substring(0, 10);
                });
        });

    },
    methods: {
        removeSaveInfo(info) {
            this.saveInfo.pop();
        },
        editManifestation: function () {
            console.log(this.saveInfo)
            this.registerError = "";
            let component = this;

            console.log(this.manifData);	// DEBUG

            // MANUAL DEEP COPY of all the attributes that can be changed in this edit form by using spread operator ... 
            // this does not do deep copy of the two lists that are tied to the manifestation as those list are not changed here
            var requestData = { ...this.manifData };
            requestData.location = { ...this.manifData.location };
            requestData.dateOfOccurence = new Date(this.manifData.dateOfOccurence).getTime();

            axios
                .patch('api/manifestations/' + this.manifData.id, requestData)
                .then(response => {
                    this.saveInfo.push("Manifestation information updated successfully");
                    this.uploadPoster(response.data.id);

                })
                .catch(function (error) {
                    if (error.response) {
                        component.registerError = error.response.data;
                        console.log(error.response.data);
                    } else if (error.request) {
                        component.registerError = error.response.data;
                        console.log(error.request);
                    } else {
                        component.registerError = error.response.data;
                        console.log('Error', error.message);
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
        }
    },
});

