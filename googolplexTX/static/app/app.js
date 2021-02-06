const WebShop = { template: '<div><navbar></navbar><web-shop></web-shop><footer-comp></footer-comp><particles></particles></div>' }
const Login = { template: '<div><navbar></navbar><login-form></login-form><footer-comp></footer-comp><particles></particles></div>' }
const Register = { template: '<div><navbar></navbar><register-form></register-form><footer-comp></footer-comp><particles></particles></div>' }
const Logout = { template: '<div><navbar></navbar><logout></logout><footer-comp></footer-comp><particles></particles></div>' }
const UserProfile = { template: '<div><navbar></navbar><user-profile></user-profile><footer-comp></footer-comp><particles></particles></div>' }
const ChangePassword = { template: '<div><navbar></navbar><change-password></change-password><footer-comp></footer-comp><particles></particles></div>' }
const DisplayUsers = { template: '<div><navbar></navbar><display-users></display-users><footer-comp></footer-comp><particles></particles></div>' }
const AdminAddUser = { template: '<div><navbar></navbar><admin-add-user></admin-add-user><footer-comp></footer-comp><particles></particles></div>' }
const SalesmanManifestations = { template: '<div><navbar></navbar><salesman-manifestations></salesman-manifestations><footer-comp></footer-comp><particles></particles></div>' }
const SalesmanTickets = { template: '<div><navbar></navbar><salesman-tickets></salesman-tickets><footer-comp></footer-comp><particles></particles></div>' }
const SalesmanUsers = { template: '<div><navbar></navbar><salesman-users></salesman-users><footer-comp></footer-comp><particles></particles></div>' }
const SalesmanAddManif = { template: '<div><navbar></navbar><salesman-add-manif></salesman-add-manif><footer-comp></footer-comp><particles></particles></div>' }
const ManifestationView = { template: '<div><navbar></navbar><manifestation-view></manifestation-view><footer-comp></footer-comp><particles></particles></div>' }
const EditManif = { template: '<div><navbar></navbar><edit-manif></edit-manif><footer-comp></footer-comp><particles></particles></div>' }
const CustomerTickets = { template: '<div><navbar></navbar><tickets></tickets><footer-comp></footer-comp><particles></particles></div>' }
const SuspiciousUsers = { template: '<div><navbar></navbar><suspicious-users></suspicious-users><footer-comp></footer-comp><particles></particles></div>' }
const ManifTypes = { template: '<div><navbar></navbar><manifestation-types></manifestation-types><footer-comp></footer-comp><particles></particles></div>' }
const AddManifTypes = { template: '<div><navbar></navbar><add-manifestation-type></add-manifestation-type><footer-comp></footer-comp><particles></particles></div>' }
const CustTypes = { template: '<div><navbar></navbar><customer-types></customer-types><footer-comp></footer-comp><particles></particles></div>' }
const AddCustType = { template: '<div><navbar></navbar><add-customer-type></add-customer-type><footer-comp></footer-comp><particles></particles></div>' }

const router = new VueRouter({
  mode: 'hash',
  linkExactActiveClass: 'active',
  routes: [
    { path: '/', component: WebShop },
    { path: '/login', component: Login },
    { path: '/register', component: Register },
    { path: '/logout', component: Logout },
    { path: '/manifestations', component: WebShop },
    { path: '/manifestations/:id', component: ManifestationView },
    { path: '/manifestations/:id/edit', component: EditManif },

    { path: '/profile', component: UserProfile },
    { path: '/change-password', component: ChangePassword },
    { path: '/users', component: DisplayUsers },
    { path: '/add-user', component: AdminAddUser },
    { path: '/my-manifestations', component: SalesmanManifestations },
    { path: '/sold-tickets', component: SalesmanTickets },
    { path: '/sold-to-users', component: SalesmanUsers },
    { path: '/salesman-add-manif', component: SalesmanAddManif },
    { path: '/tickets', component: CustomerTickets },
    { path: '/suspicious-users', component: SuspiciousUsers },
    { path: '/manifestation-types', component: ManifTypes },
    { path: '/manifestation-types/add', name: 'addManifType', component: AddManifTypes },
    { path: '/manifestation-types/edit/:manifestationTypeId', name: 'editManifType', component: AddManifTypes },
    { path: '/customer-types', component: CustTypes },
    { path: '/customer-types/add', name: 'addCustType', component: AddCustType },
    { path: '/customer-types/edit/:customerTypeId', name: 'editCustType', component: AddCustType },

  ]
});


var app = new Vue({
  router,
  el: '#webShop',
  beforeMount(){
    let userData = JSON.parse(window.localStorage.getItem('user'));
    if (userData != null && userData.jwt != null) {
      axios.defaults.headers.common['Authorization'] = "Bearer " + JSON.parse(window.localStorage.getItem('user')).jwt; // for all requests
    }

    // error handling

    axios.interceptors.response.use(function (response) {

      return response;
    }, function (error) {
      if (error.response.status === 401) {


        router.push('/logout');
      }
      return Promise.reject(error);
    });

    Date.prototype.toDateInputValue = (function() {
        var local = new Date(this);
        local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
        return local.toJSON().slice(0,10);
    });
  },
  mounted() {

    this.$nextTick(() => {
      this.initParticleJS()	
    })

  },
  methods: {
    
  }

});