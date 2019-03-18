var myJson;

function laden() {


  var myJSON = JSON.stringify(myJson);
  document.getElementById("demo").innerHTML = myJSON;
}

const userAction = async () => {
  const response = await fetch('https://localhost:8443/projectAnimal/RestAPI/tierliste');
  myJson = await response.json(); //extract JSON from the http response
  // do something with myJson
}