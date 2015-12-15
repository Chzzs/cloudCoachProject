$(document).ready(function(){
  $("#btnCommitId").click(function(){
	  	$.post("sign",
    		  {
    		    email:$("#inputEmail").val(),
    		    pwd:$("#inputPwd").val(),
    		    username:$("#inputUsername").val(),
    		  },
    		  function(data,status){
    		    alert("Post Done new user added, id: " + data.userid + "\nStatus: " + data.statustx);
    		  });
  		});
});