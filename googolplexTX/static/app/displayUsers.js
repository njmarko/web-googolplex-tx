Vue.component("display-users", {
	data: function () {
		return {
			users: null
		}
	},
	template: ` 
<div  class="d-flex" >
		<div id="particleJS-container" style="position:fixed; top:0; left:0;width:100%;z-index:0"></div>
	<div id="top" class="container" >
		<br>
		

		
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
	methods: {
	},
	mounted() {
		axios
			.get('api/users')
			.then(response => {
				this.users = response.data;
				this.users.forEach(element => {
					element.birthDate = new Date(element.birthDate).toISOString().substring(0, 10);
				});
			})
	},
});