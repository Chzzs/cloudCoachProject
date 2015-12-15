$( document ).ready(function() {
	
var localUser = sessionStorage.getItem("localUser");

if(localUser === null){//not logged
console.log("no data in localstorage");
$( "#signoutnav" ).hide();
$( "#signinnav" ).show();
$( "#welcommessage" ).hide();

}
else{
	var user=JSON.parse(localUser);
	console.log("user id storage detected : "+user.tokenId);
	
	$( "#welcommessage" ).html("Welcome "+user.email);
	
	$( "#signinnav" ).hide();
	$( "#signoutnav" ).show();
	$( "#welcommessage" ).show();
 
}
});

function onSignIn(googleUser) {
	  var profile = googleUser.getBasicProfile();
	  var id_token = googleUser.getAuthResponse().id_token;
	  $.post("sign",
  		  {
  		    token:id_token,
  		  },
  		  function(data,status){
  		    //alert("Post Done new user added, id: " + data.userid + "\nStatus: " + data.statustx);
  				$( "#welcommessage" ).html("Welcome "+data.email);
  				$( "#signinnav" ).hide();
  				$( "#signoutnav" ).show();
  				$( "#welcommessage" ).show();
  		    
  		    sessionStorage.setItem("localUser",JSON.stringify(data))
  		  });
	}
function signOut() {
  var auth2 = gapi.auth2.getAuthInstance();//TODO use local storage
  auth2.signOut().then(function () {
    console.log('User signed out.');
    
    $( "#signoutnav" ).hide();
    $( "#signinnav" ).show();
    $( "#welcommessage" ).hide();

    sessionStorage.removeItem("localUser");
  });
}