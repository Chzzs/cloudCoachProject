$( document ).ready(function() {
	
var localUser = sessionStorage.getItem("localUser");
var user;
if(localUser === null){//not logged
console.log("no data in localstorage");
$( "#signoutnav" ).hide();
$( "#signinnav" ).show();
$( "#welcommessage" ).hide();

}
else{
	user=JSON.parse(localUser);
	console.log("user id storage detected : "+user.tokenId);
	
	$( "#welcommessage" ).html("Welcome "+user.email);
	
	$( "#signinnav" ).hide();
	$( "#signoutnav" ).show();
	$( "#welcommessage" ).show();
 
}
});



$.get("training",
  		  {
  		    googleid:user.tokenId
  		  },
  		  function(data,status){
  		
  		    console.log(data);
  		  });