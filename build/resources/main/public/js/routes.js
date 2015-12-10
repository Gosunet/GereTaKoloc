angular.module('app.routes', [])

.config(function($stateProvider, $urlRouterProvider) {

  // Ionic uses AngularUI Router which uses the concept of states
  // Learn more here: https://github.com/angular-ui/ui-router
  // Set up the various states which the app can be in.
  // Each state's controller can be found in controllers.js
  $stateProvider



    .state('login', {
      url: '/login',
      templateUrl: 'templates/login.html',
      controller: 'LoginCtrl',
      service : 'LoginService'
    })


    .state('signup', {
      url: '/signup',
      templateUrl: 'templates/signup.html',
      controller: 'signupCtrl'
    })

    .state('home', {
      url: '/home',
      templateUrl: 'templates/home.html',
      controller: 'menuCtrl'
    })

    .state('menu', {
      url: '/menu',
      abstract:true,
      templateUrl: 'templates/menu.html',
    })

    .state('charges', {
      url: '/menu/charges',
      templateUrl: 'templates/charges.html',
      controller: 'ColocListCtrl'
    })

    .state('colocataires', {
      url: '/colocataires',
      abstract:true,
      controller: 'ColocListCtrl'
    })

    .state('taches', {
      url: '/menu/taches',
      templateUrl: 'templates/taches.html',
      controller: 'TachesCtrl'
    })

      .state('regles', {
        url: '/menu/regles',
        templateUrl: 'templates/regles.html',
        controller: 'ColocListCtrl'
      })

      .state('notes', {
        url: '/menu/notes',
        templateUrl: 'templates/notes.html',
        controller: 'ColocListCtrl'
      })


    ;

  // if none of the above states are matched, use this as the fallback
  $urlRouterProvider.otherwise('/login');

});
