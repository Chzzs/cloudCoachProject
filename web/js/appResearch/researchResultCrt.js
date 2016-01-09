angular.module('researchApp').controller('researchResultCrt',filterFunction);

filterFunction.$inject=['$scope','$http','$location'];

//Définir le comportement du controller
function filterFunction($scope,$http,$location){

  $http({
   method: 'GET',
   url: '/search?query='+$location.search().query
   }).then(function successCallback(response) {
console.log('received :');
console.log(response.data);
   $scope.trainingList=response.data.trainings;
    $scope.exerciceList=response.data.exercises;

   }, function errorCallback(response) {
    // called asynchronously if an error occurs
   // or server returns response with an error status.
  });

};



