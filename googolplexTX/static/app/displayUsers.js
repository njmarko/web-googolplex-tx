Vue.component("display-users", {
	data: function () {
		return {
			users: null,
			searchParams:{},
			customerType:{}
		}
	},
	template: ` 
<div  class="d-flex" >
		<div id="particleJS-container" style="position:fixed; top:0; left:0;width:100%;z-index:0"></div>
	<div id="top" class="container" >
		<br>
		
		<div class="row">
			<div class="col-md-12">
				<form>
					<div class="form-inline">
						<div class="form-label-group">
								<input placeholder="Username" id="findUsername" class="form-control" ref="focusMe"	>
								<label for="findUsername">Username</label>
						</div>
						<div class="form-label-group">
								<input placeholder="First Name" id="findFirstName" class="form-control"	>
								<label for="findFirstName">First Name</label>
						</div>
						<div class="form-label-group">
								<input placeholder="Last Name" id="findLastName" class="form-control" >
								<label for="findLastName">Last Name</label> 
						</div>
	
						<div class="form-label-group">
						<select name="inputSortCriteria" id="inputSortCriteria"  >
							<option value="FIRST_NAME">First Name</option>
							<option value="LAST_NAME">Last Name</option>
							<option value="USERNAME">Username</option>
							<option value="POINTS">Points</option>
						</select>
						<label for="inputSortCriteria">Sort Criteria</label>
						</div>
						
						<div class="form-label-group">
							<button>Ascending/Descending</button>						
						</div>

						<div class="form-label-group">
						<select name="inputUserRole" id="inputUserRole"  >
							<option value="CUSTOMER">Customer</option>
							<option value="SALESMAN">Salesman</option>
							<option value="ADMIN">Admin</option>
						</select>
						<label for="inputUserRole">User Role</label>
						</div>

						<div class="form-label-group">
						<select name="inputUserType" id="inputUserType"  >
                        	<option v-for='(value, key) in customerType' :value='value.name' > {{value.name}}</option>
						</select>
						<label for="inputUserType">User Type</label>
						</div>
	
						<div class="form-label-group">
						</div>

						<div class="form-label-group">
								<button class="btn btn-primary">Search</button>
						</div>
						<div class="form-label-group">
								<button class="btn btn-warning pull-right">Clear</button>
						</div>
					</div>
				</form>
			</div>
		</div>

		
		<h1 class="text-center">Registered users</h1>
		<div  v-for="p in users">
			<table class="table table-hover table-bordered table-striped text-center">
				<tbody >
					<tr>
						<td>Username</td>
						<td>{{p.username }}</td>
					</tr>
					<tr>
						<td>First name</td>
						<td>{{p.firstName }}</td>
					</tr>
					<tr>
						<td>Last name</td>
						<td>{{p.lastName }}</td>
					</tr>
					<tr>
						<td>Gender</td>
						<td>{{p.gender}}</td>
					</tr>
					<tr>
						<td>Birthdate</td>
						<td>{{p.birthDate}}</td>
					</tr>
					<tr>
						<td>User role</td>
						<td>{{p.userRole}}</td>
					</tr>
					<tr>
						<td>Blocked</td>
						<td>{{p.blocked}}</td>
					</tr>
					<tr v-if="p.userRole == 'CUSTOMER'">
						<td>Points</td>
						<td>{{p.points}}</td>
					</tr>
					<tr v-if="p.userRole == 'CUSTOMER'">
						<td>Customer type</td>
						<td>{{p.customerType}}</td>
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
		this.$nextTick(() => {
			axios
				.get('api/users')
				.then(response => {
					this.users = response.data;
					this.users.forEach(element => {
						element.birthDate = new Date(element.birthDate).toISOString().substring(0, 10);
					});
				})
			this.$refs.focusMe.focus();
			axios
				.get("api/customer-type")
				.then(response => {
					this.customerType = response.data;
					console.log(this.customerType);
				});		
		
		});


	},
	methods: {
	},
});