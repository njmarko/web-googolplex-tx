Vue.component("navbar", {
	data: function () {
		return {
			manifestations: null,
			user: JSON.parse(window.localStorage.getItem('user'))
		}
	},
	template: ` 
	<nav id="navbar" class="navbar navbar-expand-lg navbar-dark bg-dark">
		<div class="container">
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarNavAltMarkup"
				aria-controls="navbarNavAltMarkup" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNavAltMarkup">
				<div class="navbar-nav mr-auto mt-2 mt-lg-0">
					<a class="navbar-brand" href="/">Googolplex-Tx</a>


					
					<router-link  v-if="user && user.userRole != 'SALESMAN'" to="/manifestations" class="nav-item nav-link">Manifestations</router-link>
					
					<li v-if="user && user.userRole == 'ADMIN'" class="nav-item dropdown">
						<a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuUsers" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						  Users
						</a>
						<div class="dropdown-menu dropdown-menu-dark" style="z-index: 150;" aria-labelledby="navbarDropdownMenuUsers">
							<router-link v-if="user && user.userRole == 'ADMIN'" to="/users" class="nav-link">Users</router-link> 
							<router-link v-if="user && user.userRole == 'ADMIN'" to="/add-user" class="nav-link">Add User</router-link>
							<router-link v-if="user && user.userRole == 'ADMIN'" to="/suspicious-users" class="nav-link">Suspicious Users</router-link>
						</div>
					</li>


					<li v-if="user && user.userRole == 'ADMIN'" class="nav-item dropdown">
						<a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuTypes" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						  Types
						</a>
						<div class="dropdown-menu dropdown-menu-dark" style="z-index: 150;" aria-labelledby="navbarDropdownMenuTypes">
							<router-link v-if="user && user.userRole == 'ADMIN'" to="/manifestation-types" class="nav-link">Manif Types</router-link>
							<router-link v-if="user && user.userRole == 'ADMIN'" to="/customer-types" class="nav-link">Cust Types</router-link>
						</div>
					</li>

					<li v-if="user && user.userRole == 'SALESMAN'" class="nav-item dropdown">
						<a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuManif" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						  Manifestations
						</a>
						<div class="dropdown-menu dropdown-menu-dark" style="z-index: 150;" aria-labelledby="navbarDropdownMenuManif">
							<router-link v-if="user && user.userRole == 'SALESMAN'" to="/salesman-add-manif" class="nav-link">Add Manifestation</router-link>
							<router-link to="/manifestations" class="nav-item nav-link">All Manifestations</router-link>
							<router-link to="/my-manifestations" v-if="user && user.userRole == 'SALESMAN'" class="nav-item nav-link">My Manifestations</router-link>
						</div>
					</li>
				
					<li v-if="user && user.userRole == 'SALESMAN'" class="nav-item dropdown">
						<a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuTickets" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						  Tickets
						</a>
						<div class="dropdown-menu dropdown-menu-dark" style="z-index: 150;" aria-labelledby="navbarDropdownMenuTickets">
							<router-link to="/sold-tickets" v-if="user && user.userRole == 'SALESMAN'" class="nav-item nav-link">Sold Tickets</router-link>
							<router-link to="/sold-to-users" v-if="user && user.userRole == 'SALESMAN'" class="nav-item nav-link">My Customers</router-link>
						</div>
					</li>


					<router-link to="/tickets" v-if="user && (user.userRole == 'CUSTOMER' || user.userRole == 'ADMIN')"  class="nav-item nav-link">Tickets</router-link>
				</div>
				<div v-if="!user" class="navbar-nav my-2 my-lg-0">
					<router-link to="/login" class="nav-link">Login</router-link>
					<router-link to="/register" class="nav-link">Register</router-link>
				</div>
				<div v-if="user" class="navbar-nav my-2 my-lg-0">
					<router-link to="/profile" class="nav-link">Profile</router-link>
					<router-link to="/logout" class="nav-link">Logout</router-link>
				</div>
			</div>
		</div>
	</nav>


	


`,
	mounted() {
		// NAVBAR always renders so it is always mounted on page refresh. Because of this, Authorization is added to the header on every refresh.
		let userData = JSON.parse(window.localStorage.getItem('user'));
		if (userData != null && userData.jwt != null) {
			axios.defaults.headers.common['Authorization'] = "Bearer " + JSON.parse(window.localStorage.getItem('user')).jwt; // for all requests
		}

	},
	methods: {},
});