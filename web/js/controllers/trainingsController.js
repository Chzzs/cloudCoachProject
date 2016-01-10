angular.module('coachMyApp').controller('trainingsController',trainingFunction);

trainingFunction.$inject=['$scope','$http', '$window'];

function trainingFunction($scope,$http, $window){
	var localUser = $window.sessionStorage.localUser;
	var googleId = JSON.parse(localUser).tokenId;

	$scope.exercises = [];

	$scope.training = {
		title: '',
		duration: 0,
		description: '',
	};


	$scope.trainingToPost={};

    $scope.addExercise= function() {
			var duration = Number($scope.hours * 3600)+ Number($scope.minutes * 60)+ Number($scope.seconds);
			var exercise = $scope.exercise;
				exercise.duration= duration;
				exercise.repetition = 1;
	    	$scope.exercises.push(exercise);
			$scope.exercise = {
					title: '',
					description: '',
					duration: 0,
					repetition: 0
			}
    };

    $scope.deleteExercise= function(index){
    	$scope.exercises.splice(index,1);
    };

    $scope.postTraining=function(){
		var training = $scope.training;
		training.exercises = $scope.exercises;
		$http({
			'method': 'POST',
			'url': '../trainings',
			'data': {
				'training': training,
				'googleId': '12'
			}
		}).then(function(response){
		});

    };
};
