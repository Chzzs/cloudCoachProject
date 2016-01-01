angular.module('researchApp').controller('researchResultCrt',filterFunction);

filterFunction.$inject=['$scope','$http','$location'];

//Définir le comportement du controller
function filterFunction($scope,$http,$location){
     
   console.log($location.search().search); // get url param to use in http service

    var training1={title:'training1',duration:'8 min'};
    var training2={title:'training2',duration:'2 jours'};
    var trainingListLocal=[training1,training2];

    var exercice1={title:'exercice1',duration:' 3 heures'};
    var exercice2={title:'exercice2',duration:'4 min'};
    var exerciceListLocal=[exercice1,exercice2];

    if(trainingListLocal.length!=0)
    $scope.trainingBool=true;// set as true when data training
    
    if(exerciceListLocal.length!=0)
    $scope.exerciceBool=true;// set as true when data exercice
    
    $scope.trainingList=trainingListLocal;
    $scope.exerciceList=exerciceListLocal;


};
