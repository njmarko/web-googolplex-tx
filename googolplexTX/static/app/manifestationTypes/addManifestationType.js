Vue.component("add-manifestation-type", {

	data: function () {
		return {
			error: "",
			manifestationTypeData: {},
			saveInfo: [],
		}
	},
	template: ` 
	<div class="container">
		<div id="particleJS-container" style="position:fixed; top:0; left:0;width:100%;z-index:0"></div>
		
		<div v-if="error" class="alert alert-danger alert-dismissible">
			<!-- <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a> -->
			<strong>Error</strong> {{error}}
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
				<h5 v-if="oldName" class="card-title text-center">Edit Customer Type</h5>
				<h5 v-if="!oldName" class="card-title text-center">Add Customer Type</h5>

				<form class="form-signin" v-on:submit.prevent="addManifestationType">

					<div v-if="oldName" class="form-label-group">
						<input type="name" id="inputOldName" class="form-control" placeholder="Old Name" v-model="oldName" disabled>
						<label for="inputOldName">Old Name</label>
					</div>

					<div class="form-label-group">
						<input type="name" id="inputName" class="form-control" placeholder="Name" v-model="manifestationTypeData.name" required ref='focusMe'>
						<label for="inputUsername">Name</label>
					</div>

	
					<input class="btn btn-lg btn-primary btn-block text-uppercase" type="submit" :value="oldName ? 'Edit Customer Type' : 'Add Customer Type'" />

				</form>
				</div>
			</div>
			</div>
		</div>
		</div>
`
	,
	created() {
		this.oldName = this.$route.params.manifestationTypeId; 
	},
	mounted() {
		this.$nextTick(() => this.$refs.focusMe.focus());

		if (this.oldName != undefined){
			this.getManifestationType();
		}
	},
	methods: {
		removeSaveInfo(info) {
			this.saveInfo.pop();
		},
		addManifestationType: function () {
			if (this.oldName == undefined || this.oldName == null){
				this.postManifestationType();
			} else {
				this.putManifestationType();
			}
		},

		postManifestationType: function() {
			this.registerError = "";
			let component = this;

			console.log(this.manifestationTypeData);	// DEBUG

			axios
				.post('api/manifestation-type', this.manifestationTypeData)
				.then(response => {
					this.saveInfo.push("Customer Type Added Successfully");
					this.error = "";
				})
				.catch(function (error) {
					component.error = error.response.data;
				});
		},
		putManifestationType: function() {
			this.registerError = "";
			let component = this;

			axios
				.put('api/manifestation-type/' + this.oldName, this.manifestationTypeData)
				.then(response => {
					this.saveInfo.push("Customer Type Added Successfully");
					this.oldName = response.data.name;
					this.error = "";
				})
				.catch(function (error) {
					component.error = error.response.data;
				});
		},

		getManifestationType: function() {
			let component = this;

			axios
			.get('api/manifestation-type/' + this.oldName)
			.then(response => {
				this.manifestationTypeData = response.data;
			})
			.catch(function (error) {
				console.log(error);
				component.error = error.data;
			});
		}

	},
});