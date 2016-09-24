var app = angular.module('app', ['ngRoute']);

app.config(function($routeProvider, $httpProvider, $locationProvider) {

    $routeProvider.when('/', {
      templateUrl : '/js/views/home.html',
      controller : 'home',
      controllerAs: 'controller'
    })
    .otherwise('/');

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

    // use the HTML5 History API
    $locationProvider.html5Mode(true);

  })


 app.controller('home', function($http) {
    var self = this;
    $http.get('/resource/').then(function(response) {
      self.greeting = response.data.test;
    })
  })
app.directive('navbar', function(){
  return {
    restrict: 'E',
    templateUrl: 'js/directives/nav.html',
    link: function(scope){

    }
  }
});
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbImFwcC5qcyIsImNvbmZpZy5qcyIsImNvbnRyb2xsZXJzL2hvbWUuanMiLCJkaXJlY3RpdmVzL25hdi5qcyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtBQUNBO0FDREE7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQ2hCQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUNMQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0EiLCJmaWxlIjoibWFpbi5qcyIsInNvdXJjZXNDb250ZW50IjpbInZhciBhcHAgPSBhbmd1bGFyLm1vZHVsZSgnYXBwJywgWyduZ1JvdXRlJ10pO1xuIiwiYXBwLmNvbmZpZyhmdW5jdGlvbigkcm91dGVQcm92aWRlciwgJGh0dHBQcm92aWRlciwgJGxvY2F0aW9uUHJvdmlkZXIpIHtcblxuICAgICRyb3V0ZVByb3ZpZGVyLndoZW4oJy8nLCB7XG4gICAgICB0ZW1wbGF0ZVVybCA6ICcvanMvdmlld3MvaG9tZS5odG1sJyxcbiAgICAgIGNvbnRyb2xsZXIgOiAnaG9tZScsXG4gICAgICBjb250cm9sbGVyQXM6ICdjb250cm9sbGVyJ1xuICAgIH0pXG4gICAgLm90aGVyd2lzZSgnLycpO1xuXG4gICAgJGh0dHBQcm92aWRlci5kZWZhdWx0cy5oZWFkZXJzLmNvbW1vbltcIlgtUmVxdWVzdGVkLVdpdGhcIl0gPSAnWE1MSHR0cFJlcXVlc3QnO1xuXG4gICAgLy8gdXNlIHRoZSBIVE1MNSBIaXN0b3J5IEFQSVxuICAgICRsb2NhdGlvblByb3ZpZGVyLmh0bWw1TW9kZSh0cnVlKTtcblxuICB9KVxuXG4iLCIgYXBwLmNvbnRyb2xsZXIoJ2hvbWUnLCBmdW5jdGlvbigkaHR0cCkge1xuICAgIHZhciBzZWxmID0gdGhpcztcbiAgICAkaHR0cC5nZXQoJy9yZXNvdXJjZS8nKS50aGVuKGZ1bmN0aW9uKHJlc3BvbnNlKSB7XG4gICAgICBzZWxmLmdyZWV0aW5nID0gcmVzcG9uc2UuZGF0YS50ZXN0O1xuICAgIH0pXG4gIH0pIiwiYXBwLmRpcmVjdGl2ZSgnbmF2YmFyJywgZnVuY3Rpb24oKXtcbiAgcmV0dXJuIHtcbiAgICByZXN0cmljdDogJ0UnLFxuICAgIHRlbXBsYXRlVXJsOiAnanMvZGlyZWN0aXZlcy9uYXYuaHRtbCcsXG4gICAgbGluazogZnVuY3Rpb24oc2NvcGUpe1xuXG4gICAgfVxuICB9XG59KTsiXSwic291cmNlUm9vdCI6Ii9zb3VyY2UvIn0=
