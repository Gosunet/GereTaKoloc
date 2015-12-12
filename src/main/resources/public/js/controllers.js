angular.module('app.controllers', [])

// Controller pour le login de l'application
.controller('LoginCtrl', function($scope, LoginService, $ionicPopup, $state, $http,$rootScope) {
    $scope.data = {};


    $scope.logIn = function () {

        //console.log("LOGIN user : " + $scope.data.username + "-PW : " + $scope.data.password);
        $http.get('api/v1/login/'+$scope.data.username+'/'+$scope.data.password).success(function (data) {

            if (data!="null") {
                $rootScope.coloc = data;
                $state.go('home')
            }
            else {
                var alertPopup = $ionicPopup.alert({
                    title: 'Echec authentifiacation!',
                    template: 'Veuillez vérifier vos identifiants!'
                });
            }
        });

    }
})
// Controller pour l'inscription
.controller('signupCtrl', function($scope,$state,$http) {
    $scope.data = {};

    $scope.signUp = function () {
        $http.get('api/v1/colocs/'+$scope.colocName).success(function(data){
            if (data!="null"){ //creé un user

                var new_user = {"login":$scope.userLogin,"name":$scope.userFirstName,"surname":$scope.userLastName,"mdp":$scope.userMdp,"mail":$scope.userMail};
                $http.post('api/v1/colocs/'+$scope.colocName+'/users',new_user).success(function()
                {
                $state.go('login');
                });
            }
            else { // creer une coloc et un user
                var new_user = {"login":$scope.userLogin,"name":$scope.userFirstName,"surname":$scope.userLastName,"mdp":$scope.userMdp,"mail":$scope.userMail};
                var new_coloc = {"name":$scope.colocName,"address":$scope.colocAdresse};
                $http.post('api/v1/colocs',new_coloc).success(function(){
                    $http.post('api/v1/colocs/'+$scope.colocName+'/users',new_user).success(function()
                    {
                        $state.go('login');
                    });
                });
            }
        });
    }

})

// Controller du Menu slide
.controller('menuCtrl', function($scope,$ionicSideMenuDelegate) {
  $scope.toggleLeft = function() {
      $ionicSideMenuDelegate.toggleLeft();
    };
})

.controller('ColocListCtrl', function($scope, $rootScope, $http) {
        // console.log(data)
        $scope.coloc = $rootScope.coloc;

            var charge_totale = 0;
            var charge_pers = 0;

            angular.forEach($scope.coloc.charges, function (value, key) {
                charge_totale += parseFloat(value.montant, 10);
            });
            $scope.montant_total = charge_totale;

            // compter le nombre de user d'une colocation
            var nb_users = 0;
            angular.forEach($scope.coloc.users, function (value, key) {
                nb_users++;
            });

    $scope.ajouterRegle = function (){

        var numberInt =0;
        var number = 0;
        angular.forEach($scope.coloc.regles, function(value, key){
            numberInt = parseInt(value.number,10)+1;
            number = numberInt.toString();

        });
        var new_regle={"content":$scope.data.nouvelle_regle,"number":number};
        $http.post('api/v1/colocs/'+$scope.coloc.name+'/regles',new_regle);
        $scope.coloc.regles.push(new_regle);
    };

    $scope.ajouterNote = function (){
        var date = new Date();
        var new_note={"content":$scope.data.nouvelle_note, "date":date};
        $http.post('api/v1/colocs/'+$scope.coloc.name+'/notes',new_note);
        $scope.coloc.notes.push(new_note);
    };


    $scope.ajouter_charge = function() {

                        var new_charge={"nameCharge":$scope.data.nom_charge,"montant":$scope.data.montant_charge};
                        $http.post('api/v1/colocs/'+$scope.coloc.name+'/charges',new_charge);
                        //$http.get('api/v1/colocs/'+$scope.coloc.name).success(function(data){
                            //$rootScope.coloc=data;
                        $scope.coloc.charges.push(new_charge);

            //});
    };
        $scope.nb_users = nb_users;

  $scope.orderProp = 'name';

});
// .controller("CalendarEventController", function ($scope, EventService) {
//
//     EventService.getEvents().then(function (events) {
//         $scope.events = events;
//     });
// });
//
// .controller('CalendarPopupController', function ($scope, $ionicPopup, $timeout) {
//
//       // Triggered on a button click, or some other target
//       $scope.showPopup = function ($event, day) {
//           $scope.data = {}
//
//           if (day.events.length > 0) {
//               //show popup only if events on day cell
//
//               var $element = $event.currentTarget;
//
//               // An elaborate, custom popup
//               var myPopup = $ionicPopup.show({
//                   templateUrl: 'templates/calendar/calendar-event-popup-template.html',
//                   title: '' + day.date,
//                   subTitle: '',
//                   scope: $scope,
//                   buttons: [{
//                       text: '<b>Close</b>',
//                       type: 'button-positive',
//                       onTap: function (e) {
//                           return 'cancel button'
//                       }
//                   }, ]
//               });
//               myPopup.then(function (res) {
//
//               });
//           }
//
//       };
//
//       $scope.sendEvent = function () {
//           var startDate = new Date(2014, 10, 15, 18, 30, 0, 0, 0); // beware: month 0 = january, 11 = december
//           var endDate = new Date(2014, 10, 15, 19, 30, 0, 0, 0);
//           var title = "My nice event";
//           var location = "Home";
//           var notes = "Some notes about this event.";
//           var success = function (message) {
//               alert("Success: " + JSON.stringify(message));
//           };
//           var error = function (message) {
//               alert("Error: " + message);
//           };
//
//           window.plugins.calendar.createEventInteractively(title,location,notes,startDate,endDate,success,error);
//       }
//
//   });
