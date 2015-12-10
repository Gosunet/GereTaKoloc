angular.module('app.directives', [])

.directive('blankDirective', [function(){

}]);
// .directive('ngHtml', function () {
//     return function (scope, element, attrs) {
//         scope.$watch(attrs.ngHtml, function (value) {
//             element[0].innerHTML = value;
//         });
//     }
// });
//
// .directive("calendar", function () {
//     return {
//         restrict: "EA",
//         scope: {
//             content: '=calendarContent',
//             assignedMonth: '=calendarMonth',
//             assignedyear: '=calendarYear',
//             events: '@'
//         },
//         replace: true,
//         link: calendarLinkFunction,
//         templateUrl: 'templates/calendar/calendar-template.html'
//     }
// });
