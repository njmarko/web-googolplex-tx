Vue.component("manifestation-types", {
	data: function () {

		return {
			error: {},
			success: {},
			manifTypes: {},
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

		
		<h1 class="text-center">Manifestation Types <span class="badge badge-danger">{{userData.userRole}}</span></h1>

		<router-link :to="{name: 'addManifType'}" class="btn-info btn mb-3">Add</router-link>

		<div>
            <table class="table table-hover table-bordered table-striped text-center">

                <tbody >
                    <tr>
                        <th>Name</th>
                        <th></th>
                        <th></th>
                    </tr>
					<tr v-for="m in manifTypes">
                        <td>{{m.name }} <span v-if="m.deleted == true" class="badge badge-danger">DELETED</span></td>
                        <td><button 
                                v-bind:disabled="m.deleted" 
                                v-on:click="deleteManifType(m)" 
                                class="btn btn-danger text-uppercase">DELETE {{m.name}}</button>
                        </td>
						<td>
							<router-link v-show="!m.deleted"  :to="{ name: 'editManifType', params: {manifestationTypeId: m.name } }" class="btn btn-warning">Edit</router-link>
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


		this.getManifestationTypes();


	},
	methods: {
		getManifestationTypes : function(event){


			axios
			.get('api/manifestation-type')
			.then(response => {
                this.manifTypes = response.data;
			});
		
		},

		deleteManifType : function(obj) {
			axios
				.delete('api/manifestation-type/' + obj.name)
				.then(response => {
                    obj.deleted = true;
                    this.$forceUpdate();    // Because obj.deleted is undefined

			});
        },
        



	},



});