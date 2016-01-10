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
  }).when('/training/:id', {
    templateUrl: 'static/training.html',
    controller: 'trainingController'
  }).when('/exercise/:id', {
    templateUrl: 'static/exercise.html',
    controller: 'exerciseController'
  }).when('/add', {
    templateUrl: 'static/add.html',
    controller: 'trainingsController'
  }).when('/results/:id', {
    templateUrl: 'static/results.html',
    controller: 'resultsController'
  })
  .otherwise({
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
