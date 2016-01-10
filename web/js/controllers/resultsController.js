angular.module('coachMyApp').controller('resultsController', ['$scope', '$http', '$log', '$routeParams', function($scope, $http, $log, $routeParams) {

  var googleId = $routeParams.id;

  $http({
    'method': 'GET',
    'url':'results',
    'params': {
      'googleId': googleId
    }
  }).then(function(response) {
      $log.info(response.data);
      $scope.results = response.data;
  });
}]);
