Vue.component("customer-types", {
	data: function () {

		return {
			error: {},
			success: {},
			custTypes: {},
			userData: null,

		}
	},
	template: ` 
<div  class="d-flex" >
		<div id="particleJS-container" style="position:fixed; top:0; left:0;width:100%;z-index:0"></div>
	<div id="top" class="container" >
		<br>
		
		<div v-if="success && success.length == 0" class="alert alert-success alert-dismissible">
			<!-- <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a> -->
			<strong>Success</strong> {{success}}
		</div>

		
		<h1 class="text-center">Customer Types <span class="badge badge-danger">{{userData.userRole}}</span></h1>

		<router-link :to="{name: 'addCustType'}" class="btn-info btn mb-3">Add</router-link>

		<div>
            <table class="table table-hover table-bordered table-striped text-center">

                <tbody >
                    <tr>
						<th>Name</th>
						<th>Discount</th>
						<th>Required Points</th>
						<th></th>
						<th></th>
                    </tr>
					<tr v-for="c in custTypes">
						<td>{{c.name }} <span v-if="c.deleted == true" class="badge badge-danger">DELETED</span></td>
						<td>{{c.discount }}</td>
						<td>{{c.requiredPoints }}</td>

						<td><button 
                                v-bind:disabled="c.deleted" 
                                v-on:click="deleteCustType(c)" 
                                class="btn btn-danger text-uppercase">DELETE {{c.name}}</button>
						</td>
						<td>
							<router-link v-show="!c.deleted" :to="{ name: 'editCustType', params: {customerTypeId: c.name } }" class="btn btn-warning">Edit</router-link>
						</td>
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
	beforeMount(){
		this.userData = JSON.parse(window.localStorage.getItem('user'));
	},
	mounted() {
		let localUserData = JSON.parse(window.localStorage.getItem('user'));
		if (localUserData == null || (localUserData.userRole != "ADMIN" )) {
			this.$router.push("/");
		}



		var self = this;

		window.onpopstate = function(event) {
			self.updateInputs();
		};


		this.getCustomerTypes();


	},
	methods: {
		getCustomerTypes : function(event){


			axios
			.get('api/customer-type')
			.then(response => {
				this.custTypes = response.data.sort(this.compare);
			});
		
		},

		deleteCustType : function(obj) {
			axios
				.delete('api/customer-type/' + obj.name)
				.then(response => {
                    obj.deleted = true;
                    this.$forceUpdate();    // Because obj.deleted is undefined

			});
		},

        compare : function( a, b ) {
            if ( a.requiredPoints < b.requiredPoints ){
              return -1;
            }
            if ( a.requiredPoints > b.requiredPoints ){
              return 1;
            }
            return 0;
          }
	},



});