angular.module('researchApp',[]);
/*
angular.module('researchApp', [])
.config(function($routeProvider, $locationProvider) {
   
    $locationProvider.html5Mode = true;
  });*/

angular.module("researchApp", []).config(function($locationProvider) { $locationProvider.html5Mode(true); }); 
