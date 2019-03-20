
function laden() {
  fetch('https://localhost:8443/projectAnimal/RestAPI/tierliste')
  .then(function(response) {
    return response.json();
  })
  .then(function(myJson) {
    console.log(JSON.stringify(myJson));
  });
}
$(document).ready(function(){
  $("button").click(function(){
    $.getJSON("https://localhost:8443/projectAnimal/RestAPI/tierliste", function(result){
      $.each(result.tierartname, function(i, field){
        $("div").append(field + " ");
      });
    });
  });
});