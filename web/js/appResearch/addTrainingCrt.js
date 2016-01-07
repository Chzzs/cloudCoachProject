angular.module('exerciceApp').controller('addTrainingCrt',trainingFunction);

trainingFunction.$inject=['$scope','$http'];

//Définir le comportement du controller
function trainingFunction($scope,$http){


    console.log("ctrl training");
    $scope.exerciceList=[];

$scope.trainingToPost={};

    $scope.addExercice= function() {
        var duration= Number(($scope.exe.hour * 3600))+Number(($scope.exe.min*60))+Number(($scope.exe.sec));
		console.log(duration);
    	$scope.exerciceList.push({
    		title: $scope.exe.title,
    		duration:duration,
    		desc:$scope.exe.desc
    	})
    };

    $scope.deleteExercice= function(idx){
    	$scope.exerciceList.splice(idx,1);
    };

    $scope.postTraining=function(){
    	console.log("stuff");
    	$scope.trainingToPost={
    		title:$scope.training.title,
    		desc:$scope.training.desc,
    		sport:$scope.training.sport,
    		exeList:$scope.exerciceList

    	};
    };
};
