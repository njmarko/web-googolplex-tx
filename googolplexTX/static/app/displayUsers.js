Vue.component("display-users", {
	data: function () {
		return {
			users: null,
			searchParams: {},
			customerType: {}
		}
	},
	template: ` 
<div  class="d-flex" >
		<div id="particleJS-container" style="position:fixed; top:0; left:0;width:100%;z-index:0"></div>
	<div id="top" class="container" >
		<br>
		
		<div class="row">
			<div class="col-md-12">
				<form v-on:submit.prevent="searchUsers">
					<div class="form-inline">
						<div class="form-label-group">
								<input placeholder="Username" id="findUsername" class="form-control" ref="focusMe" v-model="searchParams.username"	>
								<label for="findUsername">Username</label>
						</div>
						<div class="form-label-group">
								<input placeholder="First Name" id="findFirstName" class="form-control" v-model="searchParams.firstName"	>
								<label for="findFirstName">First Name</label>
						</div>
						<div class="form-label-group">
								<input placeholder="Last Name" id="findLastName" class="form-control" v-model="searchParams.lastName"	>
								<label for="findLastName">Last Name</label> 
						</div>
	
						<div class="form-label-group">
						<select name="inputSortCriteria" id="inputSortCriteria" v-model="searchParams.sortCriteria" >
							<option :value="undefined"></option>
							<option value="FIRST_NAME">First Name</option>
							<option value="LAST_NAME">Last Name</option>
							<option value="USERNAME">Username</option>
							<option value="POINTS">Points</option>
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
						<select name="inputUserRole" id="inputUserRole"  v-model="searchParams.userRole"	>
							<option :value="undefined"></option>
							<option value="CUSTOMER">Customer</option>
							<option value="SALESMAN">Salesman</option>
							<option value="ADMIN">Admin</option>
						</select>
						<label for="inputUserRole">User Role</label>
						</div>

						<div class="form-label-group" v-if="searchParams.userRole == 'CUSTOMER'">
						<select name="inputUserType" id="inputUserType"  v-model="searchParams.customerType">
							<option :value="undefined"></option>
                        	<option v-for='(value, key) in customerType' :value='value.name' > {{value.name}}</option>
						</select>
						<label for="inputUserType">User Type</label>
						</div>
	
						<div class="form-label-group">
						</div>

						<button class="btn btn-primary" type="submit">Search</button>
						<button class="btn btn-warning pull-right" v-on:click="clearParameters">Clear</button>
					</div>
				</form>
			</div>
		</div>

		


		<!-- Modal -->
		<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLongTitle">Modal title</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
				<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				...
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary">Save changes</button>
			</div>
			</div>
		</div>
		</div>


		<h1 class="text-center">Registered users</h1>
		<div>
			<table class="table table-hover table-bordered table-striped text-center overflow-table">
				<tbody >
				<tr>
					<th>Username</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Gender</th>
					<th>Birthdate</th>
					<th>UserRole</th>
					<th>CustomerType</th>
					<th>Points</th>
					<th>Blocked</th>
					<th></th>
					<th></th>
				</tr>
				<tr v-bind:style="{ background: p.deleted ? '#aaa' : ''}" v-for="p in users">
					<td>{{ p.username }} <span v-if="p.deleted == true" class="badge badge-danger">DELETED</span></td>
					<td>{{ p.firstName }}</td>
					<td>{{ p.lastName }}</td>
					<td>{{ p.gender }}</td>
					<td>{{ formatDate(p.birthDate) }}</td>
					<td>{{ p.userRole }}</td>
					<td>{{ p.customerType }}</td>
					<td>{{ p.points }}</td>
					<td>{{ p.blocked }}</td>

					<td>
						<button v-bind:disabled="p.userRole == 'ADMIN' || p.deleted" v-on:click="blockUser(p, p.blocked)" v-bind:class="[p.blocked ? 'btn-info' : 'btn-danger', 'btn', 'btn-block']">{{p.blocked ? 'UNBLOCK' : 'BLOCK' }} {{p.username}}</button>
					</td>
					<td>
						<button v-bind:disabled="p.userRole == 'ADMIN' || p.deleted" v-on:click="deleteUser(p)" class="btn btn-danger text-uppercase btn-block">DELETE {{p.username}}</button>
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
		let localUserData = JSON.parse(window.localStorage.getItem('user'));
		if (localUserData == null || (localUserData.userRole != "ADMIN" )) {
			this.$router.push("/");
		}


		this.$nextTick(() => {
			this.searchParams = this.$route.query;
			axios
				.get('api/users', { params: this.searchParams })
				.then(response => {
					this.users = response.data;
				})
			this.$refs.focusMe.focus();
			axios
				.get("api/customer-type")
				.then(response => {
					this.customerType = response.data;
				});

		});


	},
	methods: {
		searchUsers: function (event) {
			event.preventDefault();
			this.$router.push({ query: {} });
			let sp = this.searchParams;
			if(sp.userRole != 'CUSTOMER') {
				// Don't allow for customer type to be sent if there Customer is not selected
				delete sp.customerType;	
			}
			this.$router.push({ query: sp });
			axios
				.get('api/users', { params: sp })
				.then(response => {
					this.users = response.data;
				})
		},
		clearParameters: function (event) {
			event.preventDefault();
			this.searchParams = {};
			let sp = this.searchParams;
			this.$router.push({ query: sp });
			axios
				.get('api/users', { params: sp })
				.then(response => {
					this.users = response.data;
				})
		},
		blockUser : function(obj, block) {
			console.log(block);

			action = "";
			if (block == true){
				action = "/unblock";
			} else {
				action = "/block";
			}
			console.log(action);
			axios
				.patch('api/users/' + obj.username + action)
				.then(response => {
					Object.assign(obj, response.data)
			});
		},

		deleteUser : function(obj) {
			var confirmed = confirm("Are your sure that you want to remove user: " + obj.username);
			
			if (confirmed == false){
				console.log("aborted");
				return;
			}

			axios
				.delete('api/users/' + obj.username)
				.then(response => {
					obj.deleted = true;
					this.$forceUpdate();

					//TODO: Consider if you want to update model
					//this.users = this.users.filter(item => item !== obj);
			});
		},
		formatDate: function (value) {
			return moment(value).format('DD/MM/YYYY');
		},


	},
});