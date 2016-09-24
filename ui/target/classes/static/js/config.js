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

