// The javascripts used in cloud computing platform
// Author: Shiwei Wu, Date: 2013-6-23

// constant variables.
var TRAINING_URL = "training";

var TRAINER = "trained";
var TRAINING = "training";

function myFunction() {
    var x = document.getElementById("demo");  // 找到元素
    x.innerHTML = "Hello JavaScript!";    // 改变内容
}

function changeImage() {
    var element = document.getElementById('myimage')
    if (element.src.match("pets")) {
        element.src = "images/banner-graphic.png";
    } else {
        element.src = "images/pets.png";
    }
}

function getData() {
    var text = "hello world";
    return text;
}

function requestData() {    
    var xmlhttp = new XMLHttpRequest();
    document.write("in request");
    var URL = "http://localhost:8080/";
    // var data
    xmlhttp.open("GET", URL, false);
    // xmlhttp.SetRequestHeader("Content-Type", "application/soap+xml");
    xmlhttp.send(null);
    document.write("data is :");
    document.write(xmlhttp.responseText);
}

function getTrainingStatus() {
    var xmlhttp = new XMLHttpRequest();
    // document.write("in request")
    xmlhttp.open("GET", TRAINING_URL, false);
    xmlhttp.send(null);
    var recText = xmlhttp.responseText;
    // document.write("data is :");
    return recText;
}

function isTraining() {
    var text = getTrainingStatus();
    if (text.match(TRAINING)) {
        return true
    }
    return false
}

function postTrainingRequest() {
    var algorithm = document.getElementById("algorithm").value;
    var parameters = document.getElementById("parameters").value;
    var params = {"algorithm":algorithm, "parameters":parameters};    
    postRequest(params)
    
}

function postRequest(params) {
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", TRAINING_URL, false);
    xmlhttp.setRequestHeader ('Content-Type', 'application/json');
    xmlhttp.send(JSON.stringify(params));
    var recText = xmlhttp.responseText;
    return recText;
}

function clickToUpdateStatus() {
    var x = document.getElementById("status");  // 找到元素
    x.innerHTML = getTrainingStatus();    // 改变内容
}
