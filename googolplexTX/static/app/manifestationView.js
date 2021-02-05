Vue.component("manifestation-view", {
	data: function () {
		return {
			manifestation: null,
			tickets: null,
			comments: null,
			locationString: "",
			userData: {},
			manifFinished: null,
			userHasReservedTicket: null,
			canComment: null,
			customerComment: {},
			editTicket: false,
			showRejectedComments:false,
			customerType: null,
			prices: {
				REGULAR: 1,
				FAN_PIT: 2,
				VIP: 4,
			},

			reservation: {
				ticketType: "REGULAR",
				quantity: 1,
			},

			total: 0,
		}
	},
	computed: {
		getSum: function () {
			total = this.manifestation.regularPrice * this.prices[this.reservation.ticketType] * this.reservation.quantity;
		  	return Math.round(total * 10) / 10;
		},
		getTotalDiscont: function () {
			sum = this.getSum;
			if (this.customerType == null){
				return sum;
			}

			sum = sum - sum * this.customerType.discount / 100; 
			return Math.round(sum * 10) / 10;
		}

	},


	template: ` 
<div  class="d-flex">
	<!-- Modal -->
	<div  v-if="manifestation && manifestation.status == 'ACTIVE' && userData && (userData.userRole == 'CUSTOMER') && this.customerType"
		 class="modal fade" id="paymentConfirmationModal" tabindex="-1" role="dialog" aria-labelledby="paymentConfirmationModalTitle" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLongTitle">Payment informations</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
				<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<table>
				<tr>
					<th colspan="2"><h3>Custoemr info</h3></th>
				</tr>
				<tr>
					<td>Customer: </td>
					<td>{{userData.username}}</td>
				</tr>
				<tr>
					<td>Full name: </td>
					<td>{{userData.firstName}} {{userData.lastName}}</td>
				</tr>
				<tr>
					<td>Points: </td>
					<td>{{userData.points}}</td>
				</tr>
				<tr>
					<td>Status: </td>
					<td>{{customerType.name}}</td>
				</tr>
				<tr>
					<td>Discount: </td>
					<td>{{customerType.discount}}%</td>
				</tr>


				<tr>
					<th colspan="2"><h3>Manifestation info</h3></th>
				</tr>
				<tr>
					<td>Name: </td>
					<td>{{manifestation.name}}</td>
				</tr>
				<tr>
					<td>Manifestation Type: </td>
					<td>{{manifestation.manifestationType}}</td>
				</tr>
				<tr>
					<td>Ticket Type: </td>
					<td>{{reservation.ticketType}}</td>
				</tr>
				<tr>
					<td>Starting at: </td>
					<td>{{formatDate(manifestation.dateOfOccurence)}}</td>
				</tr>
				<tr>
					<td>Location: </td>
					<td>{{manifestation.location.city}}, {{manifestation.location.street}} {{manifestation.location.number}}, {{manifestation.location.zipCode}}</td>
				</tr>


				<tr>
					<th colspan="2"><h3>Payment info</h3></th>
				</tr>
				<tr>
					<td>Unit Price: </td>
					<td>{{manifestation.regularPrice}}</td>
				</tr>
				<tr>
					<td>Quantity: </td>
					<td>{{reservation.quantity}}</td>
				</tr>
				<tr>
					<td>Sum: </td>
					<td>{{ getSum }}  - {{customerType.discount}}%</td>
				</tr>
				<tr>
					<td>Points earned: </td>
					<td>{{ calculatePoint( getSum ) }} ( {{userData.points + calculatePoint( getSum )}}  )</td>
				</tr>
				<tr colspan="2">
					<th><h2 class="font-weight-bold">TOTAL: {{ getTotalDiscont }}</h2></th>
				</tr>
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
				<button type="button" class="btn btn-primary" data-dismiss="modal" 
				v-on:click="reserveTicket">Confirm Reservation</button>
			</div>
			</div>
		</div>
	</div>

	<div id="particleJS-container" style="position:fixed; top:0; left:0;width:100%;z-index:0"></div>
	<div id="top" class="container" >
		<br>
		

		
		<div  v-if="manifestation">
			<h1 class="text-center">{{manifestation.name}}</h1>

			<table class="table table-hover table-bordered table-striped text-center">
				<tbody >
					<tr>
						<td>Id</td>
						<td>{{manifestation.id }}</td>
					</tr>
					<tr>
						<td>Name</td>
						<td>{{manifestation.name }}</td>
					</tr>
					<tr>
						<td>Total Seats</td>
						<td>{{manifestation.totalSeats }}</td>
					</tr>
					<tr>
						<td>Available tickets</td>
						<td>{{manifestation.availableSeats }}</td>
					</tr>
					<tr>
						<td>Date of Occurence</td>
						<td>{{formatDate(manifestation.dateOfOccurence)}}</td>
					</tr>
					<tr>
						<td>Regular Price</td>
						<td>{{manifestation.regularPrice}}</td>
					</tr>
					<tr>
						<td>Status</td>
						<td>{{manifestation.status}}</td>
					</tr>
					<tr>
						<td>Manifestation Type</td>
						<td>{{manifestation.manifestationType}}</td>
					</tr>
					<tr>
						<td>Salesman</td>
						<td>{{manifestation.salesman}}</td>
					</tr>
					<tr v-if="manifestation.averageRating">
						<td>Average Rating</td>
						<td>{{manifestation.averageRating}}</td>
					</tr>
					<tr>
						<td>Location</td>
						<td>{{manifestation.location.street }} {{ manifestation.location.number}}</td>
					</tr>
					<tr>
						<td></td>
						<td>{{manifestation.location.city }} {{manifestation.location.zipCode}}</td>
					</tr>
					<tr>
						<td></td>
						<td>{{manifestation.location.longitude }} {{manifestation.location.latitude}}</td>
					</tr>
					<tr>
						<td colspan="2">
							<map-component :inpLongitude="manifestation.location.longitude" :inpLatitude="manifestation.location.latitude" :readonly=true :locString="locationString" ></map-component>
						</td>
					</tr>
					<tr v-if="userData && (userData.userRole == 'ADMIN' || (userData.userRole == 'SALESMAN' && manifestation.salesman == userData.username))">
						<td colspan="1" >
							<router-link :to="{ path: '/manifestations/' + manifestation.id + '/edit'}" class="btn btn-warning btn-block text-uppercase">Edit Manifestation</router-link>
						</td>
						<td>
							<button v-if="userData && userData.userRole == 'ADMIN'" v-on:click="deleteManif(manifestation)" class="btn btn-danger text-uppercase btn-block mt-auto">DELETE</button>
						</td>
					</tr>

				</tbody>
			</table>
			<form v-on:submit.prevent="" v-if="manifestation.status == 'ACTIVE' && userData && (userData.userRole == 'CUSTOMER') && this.customerType">


			




				<div class="buy-section row">
					<div class="col-lg-2 col-sm-6">
						<div class="form-label-group">
							<select name="inputTicketType" id="inputTicketType" v-model="reservation.ticketType" required>
								<option value="REGULAR">Regular</option>
								<option value="FAN_PIT">Fan Pit</option>
								<option value="VIP">Vip</option>
							</select>
							<label for="inputTicketType">Ticket Type</label>
						</div>
					</div>
					<div class="col-lg-2 col-sm-6">
						<div class="form-label-group">
							<input type="number" min="1" v-bind:max="manifestation.availableSeats" v-bind:disabled="manifestation.availableSeats <= 0" id="inputQuantity" class="form-control" placeholder="Quantity" required ref='focusMe' v-model="reservation.quantity">
							<label for="inputQuantity">Quantity</label>
						</div>
					</div>
					<div class="col-lg-2 col-sm-6">
						<span>Type: {{ customerType.name }}</span>
						<p>Discount: {{ customerType.discount }}%</p>
					</div>
					<div class="col-lg-2 col-sm-6">
						<span>Total: {{ getSum }} ( -{{customerType.discount}}% )</span>
						<p>Points: {{ calculatePoint( getSum ) }}</p>
					</div>
					<div class="col-lg-2">
						<span><h2><strong>={{ getTotalDiscont }}</strong></h2></span>
					</div>
					<div class="col-lg-2">
						<button type="button" class="btn btn-lg btn-primary btn-block text-uppercase" data-toggle="modal" data-target="#paymentConfirmationModal"
						v-bind:disabled="manifestation.availableSeats <= 0">
						Reserve
						</button>
					</div>
				</div>
			</form>


			<br>
			<hr>

			<div class="col md-12" v-if="userData && manifestation && this.canComment">
				<form v-on:submit.prevent="submitComment">
					<div class="col-md-12">
					<textarea id="userComment" name="text" rows="5"  placeholder="Insert comment here" v-model="customerComment.text">
					</textarea>	
					</div>
						<div class="row">
					  <div class="rate col-lg-6">
					    <input type="radio" id="star5" name="rate" value="5" v-model="customerComment.rating" />
					    <label for="star5" title="text">5 stars</label>
					    <input type="radio" id="star4" name="rate" value="4" v-model="customerComment.rating" />
					    <label for="star4" title="text">4 stars</label>
					    <input type="radio" id="star3" name="rate" value="3" v-model="customerComment.rating" />
					    <label for="star3" title="text">3 stars</label>
					    <input type="radio" id="star2" name="rate" value="2" v-model="customerComment.rating" />
					    <label for="star2" title="text">2 stars</label>
					    <input type="radio" id="star1" name="rate" value="1" v-model="customerComment.rating" />
					    <label for="star1" title="text">1 star</label>
					  </div>

					  <div class="col-lg-6">
						<input type="submit" value="Post Review" class="btn btn-primary">
					  </div>
					  </div>
				</form>
			</div>

			<div v-if="userData && manifestation && this.canComment">
				<h1> Prosla provera za komentar</h1>

			</div>

			<div  v-if="tickets && (userData && (userData.userRole == 'ADMIN' || (userData.userRole == 'SALESMAN' && manifestation.salesman == userData.username)))">
				<h2>Tickets</h2>
				<br>
				<a class="btn btn-primary" data-toggle="collapse" href="#collapseTickets" role="button" aria-expanded="false" aria-controls="collapseTickets">
					Show tickets list
				</a>

				<div class="collapse" id="collapseTickets">
				
					<div v-for="(t,i) in tickets">
						<h5>{{i + 1}}</h5>

						<table class="table table-hover table-bordered table-striped text-center">
							<tbody >
								<tr>
									<td>Id</td>
									<td>{{t.id }}</td>
								</tr>
								<tr>
									<td>Date of Occurence</td>
									<td>{{dateFromInt(t.dateOfManifestation)}}</td>
								</tr>
								<tr>
									<td>Price</td>
									<td>{{t.price}}</td>
								</tr>
								<tr>
									<td>Status</td>
									<td>{{t.ticketStatus}}</td>
								</tr>
								<tr>
									<td>Manifestation</td>
									<td>{{t.manifestationName}}</td>
								</tr>
								<tr>
									<td>Customer</td>
									<td>{{t.cutomerFullName}}</td>
								</tr>
								<tr>
									<td>Ticket Type</td>
									<td>{{t.ticketType}}</td>
								</tr>
							</tbody>
						</table>		
						<hr/>	
					</div>
					<div class="h2" v-if="tickets.length === 0">No tickets</div>

				</div>
				<br>
			</div>



			<div  v-if="comments">



				<h2>Comments</h2>
				<a class="btn btn-primary" data-toggle="collapse" href="#collapseComments" role="button" aria-expanded="false" aria-controls="collapseComments">
					Show comments list
				</a>

				<div class="collapse" id="collapseComments">
					<hr/>
					<button 
						v-show="manifestation.salesman == userData.username || userData.userRole == 'ADMIN'" 
						v-on:click="showRejectedComments = !showRejectedComments" v-bind:class="[showRejectedComments? 'btn-info' : 'btn-success', 'btn']">
							{{showRejectedComments == false? 'SHOW':'HIDE' }} REJECTED COMMENTS
					</button>
					
					<div v-for="(c,i) in comments">
						<div v-show="c.approved != 'REJECTED' || showRejectedComments" v-bind:style="{ background: c.deleted ? '#aaa' : ''}" >
							<h5>{{i + 1}}</h5>

							<table class="table table-hover table-bordered table-striped text-center">
								<tbody >
									<tr>
										<td>Id</td>
										<td>{{c.id }}</td>
									</tr>
									<tr>
										<td>Rating</td>
										<td>{{c.rating}}</td>
									</tr>
									<tr>
										<td>Text</td>
										<td>{{c.text}}</td>
									</tr>
									<tr>
										<td>Status</td>
										<td>{{c.approved}}</td>
									</tr>
									<tr>
										<td>User</td>
										<td>{{c.customer}}</td>
									</tr>
									<tr>
										<td colspan="2">
											<button v-bind:disabled="c.deleted"  v-show="(manifestation.salesman == userData.username || userData.userRole == 'ADMIN') && c.approved == 'PENDING'" 
											v-on:click="changeCommentStatus(c,'ACCEPTED')" v-bind:class="['btn-success', 'btn']">
												ACCEPT
											</button>

											<button v-bind:disabled="c.deleted" v-show="(manifestation.salesman == userData.username || userData.userRole == 'ADMIN') && c.approved == 'PENDING'" 
											v-on:click="changeCommentStatus(c, 'REJECTED')" class="btn btn-danger text-uppercase">
												REJECT
											</button>

											<button v-bind:disabled="c.deleted" v-show="userData.userRole == 'ADMIN'" 
											v-on:click="deleteComment(c)" class="btn btn-danger text-uppercase">
												DELETE
											</button>
										</td>
									</tr>
								</tbody>
							</table>		
							<hr/>	
						</div>
					</div>
					<div class="h2" v-if="comments.length === 0">No Comments</div>

				</div>
				<br>

			</div>

		</div>
	</div>	
</div>		  
`,

	mounted() {
		this.localUserData = JSON.parse(window.localStorage.getItem('user'));
		//null check in case the local storage was deleted
		let self = this;
		if (this.localUserData == null) {
			this.localUserData = { username: "" };
		} else {
			axios
				.get('api/users/' + this.localUserData.username)
				.then(response => {
					this.userData = response.data;
					console.log(response.data.birthDate);
					console.log((new Date(response.data.birthDate)).toISOString().substring(0, 10));
					this.userData.birthDate = new Date(response.data.birthDate).toISOString().substring(0, 10);
					this.userData.gender = response.data.gender;
					if (response.data.customerType != null) {
						this.loadCustomerTypes(response.data.customerType);
					}
				})
				.catch(error => {
					console.log("User not logged in");
				});

		}

		axios
			.get('api/manifestations/' + this.$route.params.id)
			.then(response => {
				this.manifestation = response.data;
				this.locationString = response.data.location.city + ", " + response.data.location.street + ", " + response.data.location.number + ", " + response.data.location.zipCode;
				this.manifestation.dateOfOccurence = new Date(response.data.dateOfOccurence);

				this.isManiffinished(self.manifestation);

				this.getManifTickets();

				this.$nextTick(() => {
					self.sendMapCallback();
				});
			});


	},
	methods: {

							
		deleteManif: function(manif){
			var confirmed = confirm("Are your sure that you want to remove the manifestation: " + manif.name);
			
			if (confirmed == false){
				console.log("aborted");
				return;
			}

			axios
				.delete('api/manifestations/' + manif.id)
				.then(response => {
					// manif.deleted = true;
					// this.$forceUpdate();
					this.$router.push("/");
					//TODO: Consider if you want to update model
					//this.users = this.users.filter(item => item !== obj);
				
			})
			.catch(response=>{
				console.log("Delete failed: " + response.data);
			});
		},

							
		deleteComment: function(comment){
			var confirmed = confirm("Are your sure that you want to remove comment from: " + comment.customer);
			
			if (confirmed == false){
				console.log("aborted");
				return;
			}
			let path = "api/comments/"+ comment.id
			axios
				.delete(path, comment)
				.then(response=>{
					// comment.deleted = true;
					// this.$forceUpdate();
					this.getManifComments();
				})
				.catch(response=>{
					console.log("Delete failed: " + response.data);
				});
		},
		changeCommentStatus: function(comment, newStatus){
			let path = "api/comments/"+ comment.id
			comment.approved = newStatus;
			axios
				.patch(path, comment)
				.then(response=>{
					 comment = response.data
				})
				.catch(response=>{
					comment.approved = "PENDING";
				});
		},

		submitComment: function(event){
			event.preventDefault();
			if (this.editTicket == true) {
				let path = 'api/tickets';
				axios
					.get(path, { params: sp })
					.then(response => {
						this.loadData(response);
					})
			} else {
				let path = 'api/manifestations/' + this.manifestation.id + "/comments";
				if (this.customerComment != null) {
					this.customerComment.manifestation = this.manifestation.id;
					this.customerComment.customer = this.localUserData.username;
				}
				axios
					.post(path, this.customerComment)
					.then(response => {
						this.getManifComments();
					})
					.catch(error =>{
						console.log("Error occured when adding comment" + error.response.data);
					});
			}
		},
		getManifTickets: function () {
			axios
				.get('api/manifestations/' + this.$route.params.id + '/tickets')
				.then(response => {
					this.tickets = response.data;

					this.checkIfUserHasManifTicket(this.tickets);


					this.getManifComments();
				});
		},
		checkIfUserHasManifTicket: function (tickets) {
			if (this.localUserData != null) {
				let username = this.localUserData.username;
				for (const t of tickets) {
					if (t.ticketStatus == "RESERVED" && t.customer == username) {
						this.userHasReservedTicket = true;
						break;
					}
				}
			}
		},


		getManifComments: function () {

			axios
				.get('api/manifestations/' + this.$route.params.id + '/comments')
				.then(response => {
					this.comments = response.data;
					this.checkIfUserCommented(this.comments);
					if (this.manifFinished && this.userHasReservedTicket) {
						this.canComment = true;
					}
					
				});
		},
		checkIfUserCommented: function(comments){
					let username = this.localUserData.username;
					for (const c of comments) {
						if (c.customer == username) {
							this.customerComment = c;
							this.editTicket = true;
							break;
						}	
					}
					return this.customerComment;
		},
		// TODO: Make this global (store or smth)
		dateFromInt: function (value) {
			return new Date(value).toISOString().substring(0, 10);

		},

		formatDate: function (value) {
			return moment(this.manifestation.dateOfOccurence).format('DD/MM/YYYY hh:mm');
		},

		sendMapCallback() {
			var current_location = {}
			current_location.longitude = this.manifestation.location.longitude;
			current_location.latitude = this.manifestation.location.latitude;
			this.$root.$emit('mapCallback', current_location);
		},

		isManiffinished: function (manif) {
			let isActive = manif.status == "ACTIVE";
			// check if the manifestation is finished
			let manifDate = moment(manif.dateOfOccurence).format('YYYY-MM-DD');
			let curDate = moment(Date.now()).format('YYYY-MM-DD');
			// When this format is used i can compare two date strings to determine if one date is after another
			let isInThePast = curDate > manifDate;
			this.manifFinished = isActive && isInThePast;
			return this.manifFinished;
		},

		calculatePoint : function(price){
			total = price / 1000 * 133;
			return Math.round(total * 10) / 10;
		},

		reserveTicket : function() {
			let reserveData =Object.assign({}, this.reservation); 
			reserveData.customer = this.userData.username;
			reserveData.manifestation = this.manifestation.id;
			
			axios
				.post('api/manifestations/' + this.manifestation.id + '/reserve'  , reserveData)
				.then(response => {
					this.manifestation.availableSeats -= reserveData.quantity;
					console.log(response)
					this.updateQuantity();
					//this.loadData(response);
			});
		},

		loadCustomerTypes : function(id) {
			axios
			.get('api/customer-type/' + id)
			.then(response => {
				console.log(response);
				this.customerType = response.data;
			});
		},


		updateQuantity : function() {
			if (this.reservation.quantity > this.manifestation.availableSeats){
				this.reservation.quantity = this.manifestation.availableSeats;
			}
		},

	},

});