angular.module('coachMyApp').controller('resultDetailCrt',resultDetail);

resultDetail.$inject=['$scope','$http', '$window'];

function resultDetail($scope,$http, $window){

    var data='{"training":{"title":"glandage aigue","desc":"Ce training permet de rien glander en toute efficacite","exeList":[{"title":"Rien foutre","duration":3662,"desc":"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum justo justo, cursus dignissim dapibus eget, elementum volutpat risus. Pellentesque vel odio lacinia, placerat ex vitae, semper orci. Morbi gravida pharetra augue at ullamcorper. Nulla viverra maximus erat sit amet placerat. Etiam placerat eros viverra tristique varius. Integer mollis sed nibh ut rhoncus. Fusce commodo, arcu eget varius vulputate, odio dolor interdum metus, ut porta nunc ante ut lectus. Nunc at leo eu ante malesuada consectetur. Nulla quam mauris, tempus nec finibus a, convallis eget ex. Duis eu arcu sit amet tortor gravida venenatis et eget felis. Nunc a nisi mi. Curabitur sem odio, tincidunt id vestibulum fringilla, semper sit amet augue. Nulla feugiat vel nunc vitae posuere. Nam finibus sodales lacus, non dapibus orci dignissim at. Sed ullamcorper at enim in dignissim. Curabitur vel cursus quam."},{"title":" aller aux toilettes","duration":7262,"desc":"fdfjgd"}]}}'
    var response=JSON.parse(data);

    console.log(response.training.title);
    $scope.training=response.training;

};
