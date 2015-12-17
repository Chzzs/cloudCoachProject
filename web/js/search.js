var localUser = sessionStorage.getItem("localUser");

if(localUser === null){//not logged
console.log("no data in localstorage");
$( "#footerAddTraining" ).hide();
$( "#footerAddTrainingNeedSign" ).show();

}
else{
	var user=JSON.parse(localUser);
	console.log("user id storage detected : "+user.tokenId);
	
	$( "#welcommessage" ).html("Welcome "+user.email);
	
	$( "#footerAddTrainingNeedSign" ).hide();
	$( "#footerAddTraining" ).show();
}