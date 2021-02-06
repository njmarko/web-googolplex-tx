Vue.component("suspicious-users", {
	data: function () {
		var date1 = new Date();
		var date2 = new Date();
		date1.setDate(date2.getDate() - 30);
		date2.setDate(date2.getDate() + 1);

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
	<div id="top" class="container" >
		<br>
		
		<div v-if="success && success.length == 0" class="alert alert-success alert-dismissible">
			<!-- <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a> -->
			<strong>Success</strong> {{success}}
		</div>

		
		<h1 class="text-center">Suspicious Users</h1>

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
					<input type="number" min='0' v-on:keyup="evalQuantityFrequency($event)" step='1'  placeholder="Cancelation frequency" id="frequencyDateInput" class="form-control" ref="focusMe" v-model="searchParams.frequency"	>
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
		
		<div>
			<table class="table table-hover table-bordered table-striped text-center">
				<tbody >
					<tr>
						<td>Username</td>
						<td>First Name</td>
						<td>Last Name</td>
						<td>Points</td>
						<td>Birthday</td>
						<td>gender</td>
						<td></td>
						<td></td>
					</tr>

					<tr v-bind:style="{ background: u.deleted ? '#aaa' : ''}" v-for="u in users">
						<td>{{u.username }}
							<span v-if="u.blocked" class="badge badge-danger">Blocked</span>
							<span v-if="u.deleted" class="badge badge-danger">Deleted</span>
						</td>
						<td>{{u.firstName }}</td>
						<td>{{u.lastName }}</td>
						<td>{{u.points}}</td>
						<td>{{formatDate(u.birthDate)}}</td>
						<td>{{u.gender}}</td>
						<td>
							<button v-bind:disabled="u.userRole == 'ADMIN' || u.deleted" v-on:click="blockUser(u, u.blocked)" v-bind:class="[u.blocked ? 'btn-info' : 'btn-danger', 'btn']">{{u.blocked ? 'UNBLOCK' : 'BLOCK' }} {{u.username}}</button>
						</td>
						<td>
							<button v-bind:disabled="u.userRole == 'ADMIN' || u.deleted" v-on:click="deleteUser(u, u.blocked)" class="btn btn-danger text-uppercase">DELETE {{u.username}}</button>
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
	beforeMount() {
		this.userData = JSON.parse(window.localStorage.getItem('user'));
	},
	mounted() {
		let localUserData = JSON.parse(window.localStorage.getItem('user'));
		if (localUserData == null || (localUserData.userRole != "ADMIN")) {
			this.$router.push("/");
		}



		var self = this;

		window.onpopstate = function (event) {
			self.updateInputs();
		};


		this.getSuspiciousUsers();


	},
	methods: {
		getSuspiciousUsers: function (event) {
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

		updateInputs: function () {
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

		clearEmptyObjects: function (o) {
			for (var k in o) {
				if (!o[k] || o[k] == "") {
					delete o[k];
				}

			}
		},

		blockUser: function (obj, block) {
			console.log(block);

			action = "";
			if (block == true) {
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
		},
		deleteUser : function(obj) {
			var confirmed = confirm("Are your sure that you want to remove user: " + obj.username);
			
			if (confirmed == false){
				console.log("aborted");
				return;
			}

			axios
				.delete('api/users/' + obj.username)
				.then(response => {
					obj.deleted = true;
					this.$forceUpdate();

					//TODO: Consider if you want to update model
					//this.users = this.users.filter(item => item !== obj);
			});
		},
		formatDate: function (value) {
			return moment(value).format('DD/MM/YYYY');
		},
		evalQuantityFrequency: function(event){
			if (this.searchParams.frequency && this.searchParams.frequency < 0) {
				this.searchParams.frequency = '';
			}
            this.searchParams.frequency = Number(this.searchParams.frequency);
		},

	},



});