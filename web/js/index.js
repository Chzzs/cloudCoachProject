
var localUser = sessionStorage.getItem("localUser");

if(localUser === null){
console.log("no data in localstorage");
$( "#signoutnav" ).hide();
$( "#signinnav" ).show();

}
else{
	var user=JSON.parse(localUser);
	console.log("user id storage detected : "+user.tokenId);
	
	$( "#signinnav" ).hide();
	$( "#signoutnav" ).show();
 
}


	function onSignIn(googleUser) {
		  var profile = googleUser.getBasicProfile();
		  var id_token = googleUser.getAuthResponse().id_token;

		  $.post("sign",
	    		  {
	    		    token:id_token,
	    		    
	    		  },
	    		  function(data,status){
	    		    //alert("Post Done new user added, id: " + data.userid + "\nStatus: " + data.statustx);
	    				$( "#signinnav" ).hide();
	    				$( "#signoutnav" ).show();
	    		    
	    		    sessionStorage.setItem("localUser",JSON.stringify(data))
	    		  });
		}

	  function signOut() {
	    var auth2 = gapi.auth2.getAuthInstance();
	    auth2.signOut().then(function () {
	      console.log('User signed out.');
	      
	      $( "#signoutnav" ).hide();
	      $( "#signinnav" ).show();

	      sessionStorage.removeItem("localUser");
	    });
	  }


