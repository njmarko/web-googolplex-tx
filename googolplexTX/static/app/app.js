const WebShop = { template: '<web-shop></web-shop>' }
const Users = {template: '<users></users>'}

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/', component: WebShop},
	    { path: '/manifestations', component: WebShop}
	  ]
});

var app = new Vue({
	router,
	el: '#webShop'
});