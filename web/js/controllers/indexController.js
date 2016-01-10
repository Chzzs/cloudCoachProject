angular.module('coachMyApp').controller('indexController', ['$scope', '$http', '$log', function($scope, $http, $log) {

  $scope.lead = 'message par défaut';



  $scope.post = function() {
    var message='Ceci est un message du datastore avec optimisation memory cache';
    $http({
      'method': 'POST',
      'url': 'welcome',
      'data': { 'message' : message}
    });
  };



  $http({
    'method': 'GET',
    'url':'welcome',
  }).then(function(response) {
      $scope.lead = response.data.message;
  });
}]);
