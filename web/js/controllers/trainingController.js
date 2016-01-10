angular.module('coachMyApp').controller('trainingController', ['$scope', '$http', '$log','$routeParams', function($scope, $http, $log, $routeParams) {
  $log.info($routeParams);

  var id = $routeParams.id;
  $http({
    'method': 'GET',
    'url': 'trainings',
    'params': { 'id': id }
  }).then(function(response){
    $log.info(response);
    $scope.training = response.data;
    $scope.current = $scope.training.exercises[0];
    $scope.current.index = 0;
  });

  $scope.start = function(index) {
    var duration = $scope.training.exercises[index].duration;
    var ellapsed = 0;
    $log.info(duration)
    var interval = setInterval(function() {
      console.log(ellapsed);
      if(ellapsed++ == duration){
        clearInterval(interval);
        if(index++ <= $scope.training.exercises.length){
          $scope.current = $scope.training.exercises[index];
          $scope.current.index= index;
          var localUser = sessionStorage.getItem("localUser");
          console.log(localUser);
          $http(
            {'method': 'POST',
              'url':'results',
              'data': {
                'googleId': 12,
                'exerciseId': $scope.current.id
              }
          }).then(function (response){

          });
        } else {
          alert('well done !')
        }
      }
    }, 1000);
  }

  $scope.validate = function($index){
    var exercises = $scope.training.exercises;
    if(exercises[$index].status != "done") {
      exercises[$index].status = "done";
      if( $index+ 1 < exercises.length ) {
        exercises[$index+1].status = "todo";
      }
    }


  }

}]);
