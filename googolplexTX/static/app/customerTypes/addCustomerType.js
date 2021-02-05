Vue.component("add-customer-type", {

	data: function () {
		return {
			error: "",
			customerTypeData: {},
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

				<form class="form-signin" v-on:submit.prevent="addCustomerType">

					<div v-if="oldName" class="form-label-group">
						<input type="name" id="inputOldName" class="form-control" placeholder="Old Name" v-model="oldName" disabled>
						<label for="inputOldName">Old Name</label>
					</div>

					<div class="form-label-group">
						<input type="name" id="inputName" class="form-control" placeholder="Name" v-model="customerTypeData.name" required ref='focusMe'>
						<label for="inputUsername">Name</label>
					</div>

					<div class="form-label-group">
						<input type="number" min="0"  max="100" v-on:keyup="evalQuantityDiscount($event)"  id="inputDiscount" class="form-control" placeholder="Discount" v-model="customerTypeData.discount" required>
						<label for="inputDiscount">Discount</label>
					</div>
					
					<div class="form-label-group">
						<input type="number" min="0" v-on:keyup="evalQuantityRequiredPoints($event)" id="inputDiscount" class="form-control" placeholder="Required Points" v-model="customerTypeData.requiredPoints" required>
						<label for="inputFirstName">Required Points</label>
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
		this.oldName = this.$route.params.customerTypeId; 
	},
	mounted() {
		this.$nextTick(() => this.$refs.focusMe.focus());

		if (this.oldName != undefined){
			this.getCustomerType();
		}
	},
	methods: {
		removeSaveInfo(info) {
			this.saveInfo.pop();
		},
		addCustomerType: function () {
			if (this.oldName == undefined || this.oldName == null){
				this.postCustomerType();
			} else {
				this.putCustomerType();
			}
		},

		postCustomerType: function() {
			this.registerError = "";
			let component = this;

			console.log(this.customerTypeData);	// DEBUG

			axios
				.post('api/customer-type', this.customerTypeData)
				.then(response => {
					this.saveInfo.push("Customer Type Added Successfully");
					this.error = "";
				})
				.catch(function (error) {
					component.error = error.response.data;
				});
		},
		putCustomerType: function() {
			this.registerError = "";
			let component = this;

			axios
				.put('api/customer-type/' + this.oldName, this.customerTypeData)
				.then(response => {
					this.saveInfo.push("Customer Type Added Successfully");
					this.oldName = response.data.name;
					this.error = "";
				})
				.catch(function (error) {
					component.error = error.response.data;
				});
		},

		getCustomerType: function() {
			let component = this;

			axios
			.get('api/customer-type/' + this.oldName)
			.then(response => {
				this.customerTypeData = response.data;
			})
			.catch(function (error) {
				console.log(error);
				component.error = error.data;
			});
		},
		evalQuantityDiscount: function(event){
			if (this.customerTypeData.discount && this.customerTypeData.discount < 0) {
				this.customerTypeData.discount = '';
			}
			if (this.customerTypeData.discount && this.customerTypeData.discount > 100) {
				this.customerTypeData.discount = 100;
			}
            // To prevent numbers that start with 0 like 0003. decimal numbers are allowed like 0.3
            this.manifData.customerTypeData.discount = Number(this.customerTypeData.discount);
		},
		evalQuantityRequiredPoints: function(event){
			if (this.customerTypeData.requiredPoints && this.customerTypeData.requiredPoints < 0) {
				this.manifData.availableSeats = '';
			}
            this.customerTypeData.requiredPoints = Number(this.customerTypeData.requiredPoints);
		},

	},
});