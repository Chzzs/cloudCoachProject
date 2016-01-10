$.get('welcome', function(data,status){
  if(JSON.parse(data).message==null) {
    console.warn('no message parsed...');
  } else {
    $('#lead').text(JSON.parse(data).message);
  }
});

function post(){
    var message='Ceci est un message du datastore avec optimisation memory cache';
    $.ajax('welcome',{
        'data': {'message': message},
        'type': 'POST',
        'processData': false,
        'contentType': 'application/json'
    });
}
