Vue.component("web-shop", {
	data: function() {
		return {
			manifestations: null
		}
	},
	template: ` 
<div  class="row" >
		<div id="particleJS-container" style="position:fixed; top:0; left:0;width:100%;z-index:0"></div>
	<div id="top" class="container" >
		<br>
		

		
		<h1 class="text-center">Manifestations</h1>
		<div  v-for="p in manifestations">
			<table class="table table-hover table-bordered table-striped text-center">
				<tbody >
					<tr>
						<td>Id</td>
						<td>{{p.id }}</td>
					</tr>
					<tr>
						<td>Name</td>
						<td>{{p.name }}</td>
					</tr>
					<tr>
						<td>Available Seats</td>
						<td>{{p.availableSeats }}</td>
					</tr>
					<tr>
						<td>Date of Occurence</td>
						<td>{{p.dateOfOccurence.date.month + "/" + p.dateOfOccurence.date.day + "/" + p.dateOfOccurence.date.year + "." }}</td>
					</tr>
					<tr>
						<td>Regular Price</td>
						<td>{{p.regularPrice}}</td>
					</tr>
					<tr>
						<td>Status</td>
						<td>{{p.status}}</td>
					</tr>
					<tr>
						<td>Manifestation Type</td>
						<td>{{p.manifestationType.name}}</td>
					</tr>
					<tr>
						<td>Salesman</td>
						<td>{{p.salesman.firstName + " " + p.salesman.lastName}}</td>
					</tr>
					<tr>
						<td>Location</td>
						<td>{{p.location.address.city}}</td>
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
			.get('api/manifestations')
			.then(response => (this.manifestations = response.data))
	},
});