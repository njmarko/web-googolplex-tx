Vue.component("navbar", {
	data: function() {
		return {
			manifestations: null
		}
	},
	template: ` 
	<nav id="navbar" class="navbar navbar-expand-lg navbar-dark bg-dark">
		<div class="container">
			<a class="navbar-brand" href="/">Googolplex-Tx</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarNavAltMarkup"
				aria-controls="navbarNavAltMarkup" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNavAltMarkup">
				<div class="navbar-nav">
					<a class="nav-item nav-link active" href="/">Home <span
						className="sr-only">(current)</span></a> <a class="nav-item nav-link"
						href="#/manifestations">Manifestations</a> <a
						class="nav-item nav-link" href="#/login">login</a> <a
						class="nav-item nav-link" href="#/register">Register</a>
				</div>
			</div>
		</div>
	</nav>
`
	,
	methods: {
	},
	mounted() {
		axios
			.get('api/manifestations')
			.then(response => (this.manifestations = response.data))
	},
});