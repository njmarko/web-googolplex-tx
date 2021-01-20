Vue.component("logout", {
	data: function() {
		return {
		}
	},
	template: ` 
	<div class="container">
		<h1>LOGOUT</h1>
	</div>
`
	,

	mounted() {
		alert("logout");
		window.localStorage.removeItem('user');
		this.$router.push("/");

	},
	methods: {
	},
});