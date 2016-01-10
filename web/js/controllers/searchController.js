angular.module('coachMyApp').controller('searchController', ['$scope', '$http', '$log', '$routeParams', function($scope, $http, $log, $routeParams) {
  $log.info($routeParams);

  var query = $routeParams.query;
  $http({
    'method': 'GET',
    'url': 'search',
    'params': { 'query': query }
  }).then(function(response){
    $log.info(response);
    $scope.exercises = response.data.exercises;
    $scope.trainings = response.data.trainings;
  });
}]);
