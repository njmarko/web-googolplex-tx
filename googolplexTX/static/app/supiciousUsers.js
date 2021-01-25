Vue.component("suspicious-users", {
	data: function () {
		var date1 = new Date();
		var date2 = new Date();
		date1.setDate( date2.getDate() - 30 );

		return {
			error: {},
			success: {},
			users: {},
			userData: null,
			searchParams: {
				startDate: date1.toDateInputValue(),
				endDate: date2.toDateInputValue(),
				frequency: 1,
				
			},
			formatedSearchParams: {}
		}
	},
	template: ` 
<div  class="d-flex" >
		<div id="particleJS-container" style="position:fixed; top:0; left:0;width:100%;z-index:0"></div>
	<div id="top" class="container" >
		<br>
		
		<div v-if="success && success.length == 0" class="alert alert-success alert-dismissible">
			<!-- <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a> -->
			<strong>Success</strong> {{success}}
		</div>

		
		<h1 class="text-center">Suspicious Users<span class="badge badge-danger">{{userData.userRole}}</span></h1>

		<form v-on:change="getSuspiciousUsers" v-on:submit.prevent="getSuspiciousUsers">
			<div class="form-inline">
				<div class="form-label-group">
					<input type="date" placeholder="Start Date" id="startDateInput" class="form-control" ref="focusMe" v-model="searchParams.startDate"	>
					<label for="findUsername">Start Date</label>
				</div>
				<div class="form-label-group">
					<input type="date" placeholder="End Date" id="endDateInput" class="form-control" ref="focusMe" v-model="searchParams.endDate"	>
					<label for="findUsername">End Date</label>
				</div>
				<div class="form-label-group">
					<input type="number" placeholder="Cancelation frequency" id="frequencyDateInput" class="form-control" ref="focusMe" v-model="searchParams.frequency"	>
					<label for="findUsername">Cancelation Frequency</label>
				</div>
				<div class="form-label-group">
					<button class="btn btn-primary" type="submit">Search</button>
				</div>
				<div class="form-label-group">
					<button class="btn btn-warning" type="reset">Reset</button>
				</div>
			</div>
		</form>
		
		<div v-for="u in users">
			<table class="table table-hover table-bordered table-striped text-center">
				<tbody >
					<tr>
						<td>Username <span v-if="u.blocked" class="badge badge-danger">Blocked</span></td>
						<td>{{u.username }}</td>
					</tr>
					<tr>
						<td>First Name</td>
						<td>{{u.firstName }}</td>
					</tr>
					<tr>
						<td>Last Name</td>
						<td>{{u.lastName }}</td>
					</tr>
					<tr>
						<td>Points</td>
						<td>{{u.points}}</td>
					</tr>
					<tr>
						<td>Birthday</td>
						<td>{{u.birthDate}}</td>
					</tr>
					<tr>
						<td>gender</td>
						<td>{{u.gender}}</td>
					</tr>
					<tr v-if="p.userRole != 'ADMIN'">
						<td colspan="2">
							<button v-on:click="blockUser(u, u.blocked)" class="btn btn-danger btn-block text-uppercase">{{u.blocked ? 'UNBLOCK' : 'BLOCK' }} {{u.username}}</button>
						</td>
					</tr>
				</tbody>
			</table>
			<hr/>
		
		
		</div>

		<br>
	</div>	
</div>		  
`
	,
	beforeMount(){
		this.userData = JSON.parse(window.localStorage.getItem('user'));
	},
	mounted() {
		let localUserData = JSON.parse(window.localStorage.getItem('user'));
		if (localUserData == null || (localUserData.userRole != "ADMIN" )) {
			this.$router.push("/");
		}



		var self = this;

		window.onpopstate = function(event) {
			self.updateInputs();
		};


		this.getSuspiciousUsers();


	},
	methods: {
		getSuspiciousUsers : function(event){
			console.log(event);

			var formatedSearchParams = Object.assign({}, this.searchParams);
			formatedSearchParams.startDate = new Date(this.searchParams.startDate).getTime();
			formatedSearchParams.endDate = new Date(this.searchParams.endDate).getTime();

			this.clearEmptyObjects(formatedSearchParams);

			if (event != undefined)
				router.push({ query: formatedSearchParams });

			axios
			.get('api/users/suspicious', { params: formatedSearchParams })
			.then(response => {
				this.users = response.data;
				console.log(this.users);
			});
		
		},

		updateInputs : function() {
			if (this.$route.query.startDate != null) {
				this.searchParams.startDate = new Date(new Number(this.$route.query.startDate)).toISOString().substring(0, 10);
			}
	
			if (this.$route.query.endDate != null) {
				this.searchParams.endDate = new Date(new Number(this.$route.query.endDate)).toISOString().substring(0, 10);
			}
			if (this.$route.query.frequency != null) {
				this.searchParams.frequency = new Number(this.$route.query.frequency);
			}

			this.getSuspiciousUsers();
		},

		clearEmptyObjects : function(o){
			for (var k in o) {
				if (!o[k] || o[k] == "") {
					delete o[k];
				}

			}
		},

		blockUser : function(obj, block) {
			console.log(block);

			action = "";
			if (block == true){
				action = "/unblock";
			} else {
				action = "/block";
			}
			console.log(action);
			axios
				.patch('api/users/' + obj.username + action)
				.then(response => {
					Object.assign(obj, response.data)
			});
		}


	},



});