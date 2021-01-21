Vue.component("footer-comp", {
	data: function() {
		return {
			manifestations: null
		}
	},
	template: ` 
	<div class="footer-content">
		<div class="footer-gap"></div>
		<footer class="page-footer font-small bg-dark text-light sticky-footer">
			<div class="footer-copyright text-center py-3">
				<span>© 2021 Copyright: </span"> <a href="#" class="text-info">Don't click here</a>
			</div>
		</footer>
	</div>
`
	,
	methods: {},
	mounted() {},
});