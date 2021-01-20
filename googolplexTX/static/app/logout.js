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
		window.localStorage.removeItem('user');
		axios.defaults.headers.common['Authorization'] = "";
		this.$router.push("/");

	},
	methods: {
	},
});