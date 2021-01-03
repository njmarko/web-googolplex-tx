const WebShop = { template: '<web-shop></web-shop>' }

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/', component: WebShop},
	  ]
});

var app = new Vue({
	router,
	el: '#webShop'
});