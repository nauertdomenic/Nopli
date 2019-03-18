var myJson;

function laden() {
  fetch('http://localhost:8443/projectAnimal/RestAPI/tierliste',{ method: "GET", // *GET, POST, PUT, DELETE, etc.
  mode: "no-cors"})
  .then(function(response) {
    return response.json();
  })
  .then(function(myJson) {
    console.log(JSON.stringify(myJson));
  });
}
