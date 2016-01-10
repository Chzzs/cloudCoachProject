angular.module('coachMyApp').controller('navigationController', ['$scope', '$location', '$log', function($scope, $location, $log) {

  $scope.submit = function() {
    $location.path('search/'+$scope.query);
  };

}]);
