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
                $rootScope.userName=$scope.data.username;
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

.controller('ColocListCtrl', function($scope, $rootScope, $http, $timeout) {
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
            //Tache
            $scope.showAjoutTache = false;
            $scope.showCard = function(){
                $scope.showAjoutTache = true;
            };

            $scope.hideCard = function(){
                $scope.showAjoutTache = false;
            };

            var tache_lundi = [];
            var tache_mardi = [];
            var tache_mercredi = [];
            var tache_jeudi = [];
            var tache_vendredi = [];
            var tache_samedi = [];
            var tache_dimanche = [];

            var monthNames = [
                "Janvier", "Février", "Mars",
                "Avril", "Mai", "Juin", "Juillet",
                "Aout", "Septembre", "Octobre",
                "Novembre", "Decembre"
            ];


            angular.forEach($scope.coloc.users, function(value,key){
                // console.log(value);
                angular.forEach(value.taches, function(value2,key2){
                    var date = new Date(value2.date);
                    var day=date.getDay();
                    var jour = date.getDate();
                    var monthIndex = date.getMonth();
                    var year = date.getFullYear();

                    var hour = new Date(value2.hour);
                    var heure= hour.getHours();
                    var minute=hour.getMinutes();

                    if (heure<10){
                        heure = "0"+heure;
                    }
                    if (minute<10){
                        minute="0"+ minute;
                    }
                    var full_hour = heure+":"+minute;

                    $scope.hour = full_hour;

                    var date_complet = jour+"-"+monthNames[monthIndex]+"-"+year;

                    $scope.date_comp=date_complet;

                    // if(date[10]=="M"){
                    if(day==1){
                        tache_lundi.push(value2);
                    }
                    // else if(date[10]=="T"){
                    else if(day==2){
                        tache_mardi.push(value2);
                    }
                    // else if(date[10]=="W"){
                    else if(day==3){

                        tache_mercredi.push(value2);
                    }
                    // else if(date[10]=="B"){
                    else if(day==4){
                        tache_jeudi.push(value2);
                    }
                    // else if(date[10]=="F"){
                    else if(day==5){
                        tache_vendredi.push(value2);
                    }
                    // else if(date[10]=="S"){
                    else if(day==6){
                        tache_samedi.push(value2);
                    }
                    // else if(date[10]=="S"){
                    else if(day==0){
                        tache_dimanche.push(value2);
                    }
                    $scope.lun_task=tache_lundi;
                    $scope.mar_task=tache_mardi;
                    $scope.mer_task=tache_mercredi;
                    $scope.jeu_task=tache_jeudi;
                    $scope.ven_task=tache_vendredi;
                    $scope.sam_task=tache_samedi;
                    $scope.dim_task=tache_dimanche;
                })
            });

    var poll = function(){
        $timeout(function(){
            $http.get('api/v1/colocs/'+$rootScope.coloc.name).success(function(data){
                $scope.coloc = data;
            });
            poll();
        }, 5000);
    };
    poll();

    //Fonction

    $scope.ajouter_tache = function(){
        var new_tache={"content":$scope.nom_evt,"date":$scope.date,"heure":$scope.hour,"urgency":$scope.urgency};
        $scope.nom_evt="";
        $scope.date="";
        $scope.hour="00:00";



        $http.post('api/v1/colocs/'+$scope.coloc.name+'/users/'+$rootScope.userName+'/taches',new_tache);
            $scope.coloc.users[0].taches.push(new_tache); // à ajouter à l'user correspondant plus tard. Ne se met pas à jour ????

            //Pour mettre à jour les jours du scope, à mettre dans une fonction.
            var tache_lundi = [];
            var tache_mardi = [];
            var tache_mercredi = [];
            var tache_jeudi = [];
            var tache_vendredi = [];
            var tache_samedi = [];
            var tache_dimanche = [];

            var monthNames = [
                "Janvier", "Février", "Mars",
                "Avril", "Mai", "Juin", "Juillet",
                "Aout", "Septembre", "Octobre",
                "Novembre", "Decembre"
            ];


            angular.forEach($scope.coloc.users, function(value,key){
                // console.log(value);
                angular.forEach(value.taches, function(value2,key2){
                    var date = new Date(value2.date);
                    var day=date.getDay();
                    var jour = date.getDate();
                    var monthIndex = date.getMonth();
                    var year = date.getFullYear();

                    var hour = new Date(value2.hour);
                    var heure= hour.getHours();
                    var minute=hour.getMinutes();

                    if (heure<10){
                        heure = "0"+heure;
                    }
                    if (minute<10){
                        minute="0"+ minute;
                    }
                    var full_hour = heure+":"+minute;

                    $scope.hour = full_hour;

                    var date_complet = jour+"-"+monthNames[monthIndex]+"-"+year;

                    $scope.date_comp=date_complet;

                    // if(date[10]=="M"){
                    if(day==1){
                        tache_lundi.push(value2);
                    }
                    // else if(date[10]=="T"){
                    else if(day==2){
                        tache_mardi.push(value2);
                    }
                    // else if(date[10]=="W"){
                    else if(day==3){

                        tache_mercredi.push(value2);
                    }
                    // else if(date[10]=="B"){
                    else if(day==4){
                        tache_jeudi.push(value2);
                    }
                    // else if(date[10]=="F"){
                    else if(day==5){
                        tache_vendredi.push(value2);
                    }
                    // else if(date[10]=="S"){
                    else if(day==6){
                        tache_samedi.push(value2);
                    }
                    // else if(date[10]=="S"){
                    else if(day==0){
                        tache_dimanche.push(value2);
                    }
                    $scope.lun_task=tache_lundi;
                    $scope.mar_task=tache_mardi;
                    $scope.mer_task=tache_mercredi;
                    $scope.jeu_task=tache_jeudi;
                    $scope.ven_task=tache_vendredi;
                    $scope.sam_task=tache_samedi;
                    $scope.dim_task=tache_dimanche;
                })
            });

    };

    $scope.ajouterRegle = function (){

        var numberInt =0;
        var number = 0;
        angular.forEach($scope.coloc.regles, function(value, key){
            numberInt = parseInt(value.number,10)+1;
            number = numberInt.toString();

        });
        var new_regle={"content":$scope.data.nouvelle_regle,"number":number};
        $scope.data.nouvelle_regle="";
        $http.post('api/v1/colocs/'+$scope.coloc.name+'/regles',new_regle);
        $scope.coloc.regles.push(new_regle);
    };

    $scope.ajouterNote = function (){
        var date = new Date();
        var new_note={"content":$scope.data.nouvelle_note, "date":date};
        $scope.data.nouvelle_note="";
        $http.post('api/v1/colocs/'+$scope.coloc.name+'/notes',new_note);
        $scope.coloc.notes.push(new_note);
    };


    $scope.ajouter_charge = function() {

                        var new_charge={"nameCharge":$scope.data.nom_charge,"montant":$scope.data.montant_charge};
                        $scope.data.nom_charge="";
                        $scope.data.montant_charge=0;

                        $http.post('api/v1/colocs/'+$scope.coloc.name+'/charges',new_charge);
                        //$http.get('api/v1/colocs/'+$scope.coloc.name).success(function(data){
                            //$rootScope.coloc=data;
                        $scope.coloc.charges.push(new_charge);
                        var charge_totale = 0;
                        var charge_pers = 0;

                        angular.forEach($scope.coloc.charges, function (value, key) {
                            charge_totale += parseFloat(value.montant, 10);
                        });
                        $scope.montant_total = charge_totale;

            //});
    };

    $scope.deleteCharge = function (charge) {
        var index = $scope.coloc.charges.indexOf(charge);
        $http.delete('api/v1/colocs/'+$scope.coloc.name +'/charges/'+index).success(function(data){
            $scope.coloc=data;
            var charge_totale = 0;
            var charge_pers = 0;

            angular.forEach($scope.coloc.charges, function (value, key) {
                charge_totale += parseFloat(value.montant, 10);
            });
            $scope.montant_total = charge_totale;
        });
    };

    $scope.deleteRegle = function(regle){
        var index =$scope.coloc.regles.indexOf(regle);
        $http.delete('api/v1/colocs/'+$scope.coloc.name + '/regles/'+index).success(function(data){
            $scope.coloc=data;
        });

    };

    $scope.deleteNote = function(note){
        var index = $scope.coloc.notes.indexOf(note);
        $http.delete('api/v1/colocs/' + $scope.coloc.name + '/notes/' + index).success(function(data){
            $scope.coloc=data;
        });
    };

    $scope.deleteTache = function(task){

        $http.delete('api/v1/colocs/'+ $scope.coloc.name + '/users/' + $rootScope.userName +'/taches/' + task.content).success(function(data){
            $scope.coloc=data;
            var tache_lundi = [];
            var tache_mardi = [];
            var tache_mercredi = [];
            var tache_jeudi = [];
            var tache_vendredi = [];
            var tache_samedi = [];
            var tache_dimanche = [];

            var monthNames = [
                "Janvier", "Février", "Mars",
                "Avril", "Mai", "Juin", "Juillet",
                "Aout", "Septembre", "Octobre",
                "Novembre", "Decembre"
            ];


            angular.forEach($scope.coloc.users, function(value,key){
                // console.log(value);
                angular.forEach(value.taches, function(value2,key2){
                    var date = new Date(value2.date);
                    var day=date.getDay();
                    var jour = date.getDate();
                    var monthIndex = date.getMonth();
                    var year = date.getFullYear();

                    var hour = new Date(value2.hour);
                    var heure= hour.getHours();
                    var minute=hour.getMinutes();

                    if (heure<10){
                        heure = "0"+heure;
                    }
                    if (minute<10){
                        minute="0"+ minute;
                    }
                    var full_hour = heure+":"+minute;

                    $scope.hour = full_hour;

                    var date_complet = jour+"-"+monthNames[monthIndex]+"-"+year;

                    $scope.date_comp=date_complet;

                    // if(date[10]=="M"){
                    if(day==1){
                        tache_lundi.push(value2);
                    }
                    // else if(date[10]=="T"){
                    else if(day==2){
                        tache_mardi.push(value2);
                    }
                    // else if(date[10]=="W"){
                    else if(day==3){

                        tache_mercredi.push(value2);
                    }
                    // else if(date[10]=="B"){
                    else if(day==4){
                        tache_jeudi.push(value2);
                    }
                    // else if(date[10]=="F"){
                    else if(day==5){
                        tache_vendredi.push(value2);
                    }
                    // else if(date[10]=="S"){
                    else if(day==6){
                        tache_samedi.push(value2);
                    }
                    // else if(date[10]=="S"){
                    else if(day==0){
                        tache_dimanche.push(value2);
                    }
                    $scope.lun_task=tache_lundi;
                    $scope.mar_task=tache_mardi;
                    $scope.mer_task=tache_mercredi;
                    $scope.jeu_task=tache_jeudi;
                    $scope.ven_task=tache_vendredi;
                    $scope.sam_task=tache_samedi;
                    $scope.dim_task=tache_dimanche;
                })
            });
        })

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
