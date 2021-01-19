Vue.component("footer-comp", {
	data: function() {
		return {
			manifestations: null
		}
	},
	template: ` 
	<footer class="page-footer font-small bg-dark text-light">
		<div class="footer-copyright text-center py-3">
			<span>© 2021 Copyright: </span"> <a href="#" class="text-info">Don't click here</a>
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