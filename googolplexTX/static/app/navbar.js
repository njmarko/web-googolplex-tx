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

					<router-link to="/" class="nav-item nav-link">Home</router-link>
					<router-link to="/manifestations" class="nav-item nav-link">Manifestations</router-link>
					<router-link v-if="user && user.userRole == 'ADMIN'" to="/users" class="nav-link">Users</router-link> 
					<router-link v-if="user && user.userRole == 'ADMIN'" to="/add-user" class="nav-link">Add User</router-link>
					<router-link to="/my-manifestations" v-if="user && user.userRole == 'SALESMAN'" class="nav-item nav-link">My Manifestations</router-link>
					<router-link to="/sold-tickets" v-if="user && user.userRole == 'SALESMAN'" class="nav-item nav-link">Sold Tickets</router-link>
					<router-link to="/sold-to-users" v-if="user && user.userRole == 'SALESMAN'" class="nav-item nav-link">My Customers</router-link>
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
	methods: {},
	mounted() {
		axios
			.get('api/manifestations')
			.then(response => (this.manifestations = response.data))
	},
});