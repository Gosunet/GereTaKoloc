angular.module('app.services', [])

.factory('BlankFactory', [function(){

}])

// .service('BlankService', [function(){
//
// }]);


.service('LoginService', function($q) {
    return {
        loginUser: function() {
            var deferred = $q.defer();
            var promise = deferred.promise;

            if (data != null) {
                deferred.resolve('Welcome  !');
                // console.log('Vous avez réussi à vous logger'+name);
            } else {
                deferred.reject('Vos identifiants sont erronés');
            }
            promise.success = function(fn) {
                promise.then(fn);
                return promise;
            }
            promise.error = function(fn) {
                promise.then(null, fn);
                return promise;
            }
            return promise;
        }
    }
});



// .service('CalendarService', function ($http, $q){
//   return({
//     getEvents : getEvents,
//   });
//   // ------------- PUBLIC METHOD ----------------------//
//
//   //get all of the events from a Json file
//   function getEvents(){
//     var request = $http({
//       method: "get",
//       url: "colocataires/events.json",
//       params: {
//         action: "get"
//       }
//     });
//
//     return (request.then(handleSuccess, handleError));
//   }
//
//   //------------------- PRIVATE METHOD ----------------//
//   function handleError(response) {
//
//       // The API response from the server should be returned in a
//       // nomralized format. However, if the request was not handled by the
//       // server (or what not handles properly - ex. server error), then we
//       // may have to normalize it on our end, as best we can.
//       if (!angular.isObject(response.data) ||!response.data.message) {
//           return ($q.reject("An unknown error occurred."));
//       }
//
//       // Otherwise, use expected error message.
//       return ($q.reject(response.data.message));
//
//   }
//
//   // I transform the successful response, unwrapping the application data
//   // from the API response payload.
//   function handleSuccess(response) {
//       var res = null;
//       try {
//           res = JSON.parse(response.data);
//       } catch (e) {
//
//       }
//       return (response.data);
//
//   }
// }); // fin de la fonction
