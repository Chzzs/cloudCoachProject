angular.module('coachMyApp',['ngRoute']);

angular.module('coachMyApp').config(function($routeProvider) {
  $routeProvider
  .when('/', {
    templateUrl: 'static/index.html',
    controller: 'indexController'
  }).when('/home', {
    templateUrl: 'static/home.html'
  }).when('/search/:query', {
    templateUrl: 'static/search.html',
    controller: 'searchController'
  }).when('/add', {
    templateUrl: 'static/add.html',
    controller: 'trainingsController'
  }).otherwise({
    redirectTo: '/'
  })
});

angular.module('coachMyApp').run(['$rootScope', '$location', function($rootScope, $location) {


  $rootScope.$on('$routeChangeStart', function(event) {
      var localUser = sessionStorage.getItem("localUser");

      if($location.path() != '/' && localUser == null) {
        $location.path('/');
        alert('You need to login...');
      }
  });
}]);
