Vue.component("web-shop", {
	data: function() {
		return {
			manifestations: null
		}
	},
	template: ` 
<div>
	Manifestacije:
	<div v-for="p in manifestations">
		<table border = "1">
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
		</table>
		<br>
	</div>
			
</div>		  
`
	,
	methods: {
	},
	mounted() {
		axios
			.get('/manifestations')
			.then(response => (this.manifestations = response.data))
	},
});