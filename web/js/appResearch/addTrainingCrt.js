angular.module('exerciceApp').controller('addTrainingCrt',trainingFunction);

trainingFunction.$inject=['$scope','$http'];

//Définir le comportement du controller
function trainingFunction($scope,$http){


    console.log("ctrl training");
    $scope.exerciceList=[];

$scope.trainingToPost={};

    $scope.addExercice= function() {
        var duration= ($scope.exe.Hour * 3600)+($scope.exe.Min*60)+($scope.exe.Sec);
    	$scope.exerciceList.push({
    		title: $scope.exe.Title,
    		duration:duration,
    		desc:$scope.exe.Desc
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
    		sport:$scope.sport,
    		exeList:$scope.exerciceList

    	};
    }
};
