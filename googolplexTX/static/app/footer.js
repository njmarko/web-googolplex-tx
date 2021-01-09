Vue.component("footer-cmp", {
	data: function() {
		return {
			manifestations: null
		}
	},
	template: ` 
	<footer className="page-footer font-small blue">
		<div className="footer-copyright text-center py-3">
			<span>© 2021 Copyright: </span> <a href="/aboutus">Our team</a>
		</div>
	</footer>
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