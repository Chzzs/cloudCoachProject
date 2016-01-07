$.get("welcome",
    
        function(data,status){


            if(JSON.parse(data).message==null)
            {
            }
            else
            {
                $("#lead").replaceWith("<p>"+JSON.parse(data).message+"</p>");
            }

        });

/*
function dataPoster() {
    var welcmes="Ceci est un message du datastore avec optimisation memory cache";
$.post("welcome",
{
  message:welcmes,
},

        function(data,status){
          //$("#lead").replaceWith("<p>"+JSON.parse(data).message+"</p>");
        });
};
*/

function dataPoster(){
    var welcmes="Ceci est un message du datastore avec optimisation memory cache";
    $.ajax('welcome',{
        'data':'{"message":"Ceci est un message du datastore avec optimisation memory cache"}', //{action:'x',params:['a','b','c']}
        'type': 'POST',
        'processData': false,
        'contentType': 'application/json' //typically 'application/x-www-form-urlencoded', but the service you are calling may expect 'text/json'... check with the service to see what they expect as content-type in the HTTP header.
    });
}