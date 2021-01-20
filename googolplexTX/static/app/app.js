const WebShop = { template: '<div><navbar></navbar><web-shop></web-shop><footer-comp></footer-comp></div>' }
const Login = { template: '<div><navbar></navbar><login-form></login-form><footer-comp></footer-comp></div>' }
const Register = { template: '<div><navbar></navbar><register-form></register-form><footer-comp></footer-comp></div>' }
const Logout = { template: '<div><navbar></navbar><logout></logout><footer-comp></footer-comp></div>' }
const UserProfile = { template: '<div><navbar></navbar><user-profile></user-profile><footer-comp></footer-comp></div>' }
const ChangePassword = { template: '<div><navbar></navbar><change-password></change-password><footer-comp></footer-comp></div>' }
const DisplayUsers = { template: '<div><navbar></navbar><display-users></display-users><footer-comp></footer-comp></div>' }

const router = new VueRouter({
    mode: 'hash',
    linkExactActiveClass: 'active',
	  routes: [
	    { path: '/', component: WebShop},
	    { path: '/login', component: Login},
	    { path: '/register', component: Register},
	    { path: '/logout', component: Logout},
	    { path: '/manifestations', component: WebShop},
	    { path: '/profile', component: UserProfile},
	    { path: '/changePassword', component: ChangePassword},
	    { path: '/users', component: DisplayUsers}
	  ]
});


var app = new Vue({
  router,
	el: '#webShop',
	  mounted () {

      let userData = JSON.parse(window.localStorage.getItem('user'));
      if (userData != null && userData.jwt != null){
        axios.defaults.headers.common['Authorization'] = "Bearer " + JSON.parse(window.localStorage.getItem('user')).jwt; // for all requests
      }

      // error handling

      axios.interceptors.response.use(function (response) {

        return response;
      }, function (error) {
        if (error.response.status === 401){

          
          router.push('/logout');
        }
        return Promise.reject(error);
      });

  	/*this.$nextTick(() => {
    	this.initParticleJS()	
    })*/
    
  },
  methods: {
  	initParticleJS () {
      particlesJS('particleJS-container', {
          "particles": {
            "number": {
              "value": 100,
              "density": {
                "enable": true,
                "value_area": 800
              }
            },
            "color": {
              "value": "#ffffff"
            },
            "shape": {
              "type": "circle",
              "stroke": {
                "width": 0,
                "color": "#000000"
              },
              "polygon": {
                "nb_sides": 5
              }
            },
            "opacity": {
              "value": 0.5,
              "random": false,
              "anim": {
                "enable": false,
                "speed": 1,
                "opacity_min": 0.1,
                "sync": false
              }
            },
            "size": {
              "value": 3,
              "random": true,
              "anim": {
                "enable": false,
                "speed": 40,
                "size_min": 0.1,
                "sync": false
              }
            },
            "line_linked": {
              "enable": true,
              "distance": 150,
              "color": "#ffffff",
              "opacity": 0.4,
              "width": 1
            },
            "move": {
              "enable": true,
              "speed": 6,
              "direction": "none",
              "random": false,
              "straight": false,
              "out_mode": "out",
              "bounce": false,
              "attract": {
                "enable": false,
                "rotateX": 600,
                "rotateY": 1200
              }
            }
              },
              "interactivity": {
            "detect_on": "canvas",
            "events": {
              "onhover": {
                "enable": true,
                "mode": "grab"
              },
              "onclick": {
                "enable": true,
                "mode": "push"
              },
              "resize": true
            },
            "modes": {
              "grab": {
                "distance": 140,
                "line_linked": {
                  "opacity": 1
                }
              },
              "bubble": {
                "distance": 400,
                "size": 40,
                "duration": 2,
                "opacity": 8,
                "speed": 3
              },
              "repulse": {
                "distance": 200,
                "duration": 0.4
              },
              "push": {
                "particles_nb": 4
              },
              "remove": {
                "particles_nb": 2
              }
            }
          },
          "retina_detect": true
        });
    }
  }

});