// The javascripts used in cloud computing platform
// Author: Shiwei Wu, Date: 2013-6-23

// constant variables.
var TRAINING_URL = "training";
var TASK_URL = "task";
var DATA_URL = "dataset";

var TRAINER = "trained";
var TRAINING = "training";

var MODEL_URL = "result"

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

function skipToTask() {   
    var task = document.getElementById("toText").value;   
    var frm = window.event.srcElement; 
    frm.hid.value = task; 
    return true; 
}

function parseSkipUrl(url) {
    var tmp1=url.split("?")[1]; 
    var tmp2=tmp1.split("=")[1];
    var tmp3 = tmp2.split('&')[0]
    var param=tmp3;
    return param;
}

function putNewTask() {
    var taskname = document.getElementById("new_taskname").value;
    var tasktype = document.getElementById("tasktype").value;
    var description = document.getElementById("description").value;
    var dataset_name = document.getElementById("select_datasets").value;
    var parameters = document.getElementById("parameters").value;
    var params = {"taskname":taskname, "tasktype":tasktype, "description":description, 
                  "dataset_name":dataset_name, "parameters":parameters};
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("PUT", TASK_URL, false);
    xmlhttp.setRequestHeader ('Content-Type', 'application/json');
    xmlhttp.send(JSON.stringify(params));
    var recText = xmlhttp.responseText;
    return recText;
}

// function putNewDataSet() {
//     var taskname = document.getElementById("new_dataset_name").value;
//     var tasktype = document.getElementById("tasktype").value;
//     var params = {"taskname":taskname, "tasktype":tasktype};    
//     var xmlhttp = new XMLHttpRequest();
//     xmlhttp.open("PUT", TASK_URL, false);
//     xmlhttp.setRequestHeader ('Content-Type', 'application/json');
//     xmlhttp.send(JSON.stringify(params));
//     var recText = xmlhttp.responseText;
//     return recText;
// }

function adjustDisplay(tasktype) {
    if (tasktype == 'classification') {        
        display("test_div");
    } else {
        undisplay("test_div");
    }
}

function undisplay(id){   
    var target=document.getElementById(id);   
    target.style.display = "none";
}

function display(id){   
    var target=document.getElementById(id);   
    target.style.display = "";
}

function changeTaskName() {
    document.getElementById("taskname").value = parseSkipUrl(location.href);
    document.getElementById("download_taskname").value = parseSkipUrl(location.href);
}

function getTasks() {
    var tasktype = document.getElementById("select_type").value;
    // alert(tasktype);
    // var params = {"tasktype":tasktype};    
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", TASK_URL + "?tasktype=" + tasktype, false);
    xmlhttp.setRequestHeader ('Content-Type', 'application/json');
    // xmlhttp.send(JSON.stringify(params));
    xmlhttp.send(null);
    if (xmlhttp.status != 200) {
        return;
    }
    var recText = xmlhttp.responseText;    
    var obj = eval ("(" + recText + ")");
    // var tb = document.createElement("table");
    var tb = document.getElementById("table");
    // tb.setAttribute("border", "1");   
    // alert(tb.rows.length);
    if (tb.rows.length > 1) {
        var len = tb.rows.length;
        for (var i = 1; i < len; i++) {
            tb.deleteRow(1);
        }
    }

    for (var i = 0; i < obj.length; i++) {
        var row = i + 1;
        var tr = tb.insertRow(row);        

        var td = tr.insertCell(0);
        var href = document.createElement("a");
        href.setAttribute("href", "operation.html?tasknane=" + obj[i].taskname);
        var taskname = document.createTextNode(obj[i].taskname);
        href.appendChild(taskname);
        td.appendChild(href);
        
        var td = tr.insertCell(1);
        var tasktype = document.createTextNode(obj[i].tasktype);
        td.appendChild(tasktype);
        
        var td = tr.insertCell(2);
        var discription = document.createTextNode(obj[i].description);
        td.appendChild(discription);        

        var td = tr.insertCell(3);
        var href = document.createElement("a");
        href.setAttribute("href", "task/deleter?taskname=" + obj[i].taskname);
        var deleter = document.createTextNode("delete");
        href.appendChild(deleter);
        td.appendChild(href);
    }
    // document.body.appendChild(tb);
}

function getDataSets() {
    var tasktype = document.getElementById("select_type").value;
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", DATA_URL + "?tasktype=" + tasktype, false);
    xmlhttp.setRequestHeader ('Content-Type', 'application/json');
    // xmlhttp.send(JSON.stringify(params));
    xmlhttp.send(null);
    if (xmlhttp.status != 200) {
        return;
    }
    var recText = xmlhttp.responseText;    
    var obj = eval ("(" + recText + ")");

    var tb = document.getElementById("table");
    if (tb.rows.length > 1) {
        var len = tb.rows.length;
        for (var i = 1; i < len; i++) {
            tb.deleteRow(1);
        }
    }

    for (var i = 0; i < obj.length; i++) {
        var row = i + 1;
        var tr = tb.insertRow(row);        

        var td = tr.insertCell(0);
        // var href = document.createElement("a");
        // href.setAttribute("href", "operation.html?tasknane=" + obj[i].dataset_name);
        var dataset_name = document.createTextNode(obj[i].dataset_name);
        // href.appendChild(taskname);
        td.appendChild(dataset_name);
        
        var td = tr.insertCell(1);
        var tasktype = document.createTextNode(obj[i].tasktype);
        td.appendChild(tasktype);        

        var td = tr.insertCell(2);
        var href = document.createElement("a");
        href.setAttribute("href", "dataset/downloader?dataset_name=" + obj[i].dataset_name);
        var download = document.createTextNode("download");
        href.appendChild(download);
        td.appendChild(href);

        var td = tr.insertCell(3);
        var href = document.createElement("a");
        href.setAttribute("href", "dataset/deleter?dataset_name=" + obj[i].dataset_name);
        var deleter = document.createTextNode("delete");
        href.appendChild(deleter);
        td.appendChild(href);
    }
    // document.body.appendChild(tb);
}

function getSelectDataSets(tasktype) {
    // var tasktype = document.getElementById("select_type").value;
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", DATA_URL + "?tasktype=" + tasktype, false);
    xmlhttp.setRequestHeader ('Content-Type', 'application/json');
    // xmlhttp.send(JSON.stringify(params));
    xmlhttp.send(null);
    if (xmlhttp.status != 200) {
        return;
    }
    var recText = xmlhttp.responseText;    
    var obj = eval ("(" + recText + ")");

    var select = document.getElementById("select_datasets");
    // alert(select.length);
    if (select.length > 0) {
        var len = select.length;
        for (var i = 0; i < len; i++) {
            select.remove(i);
        }
    }

    for (var i = 0; i < obj.length; i++) {
        // var dataset_name = document.createTextNode(obj[i].dataset_name);
        var option = new Option(obj[i].dataset_name, obj[i].dataset_name);
        // alert(dataset_name);
        // select.appendChild(dataset_name);        
        select.options[i] = option;
    }
}

function write_td(content) {
    ducument.write("<td>");
    document.write(content);
    ducument.write("</td>");
}

function postTraining() {
    var taskname = parseSkipUrl(location.href);
    var params = {"taskname": taskname};    
    postRequest(params);
    // alert(rec);
    var x = document.getElementById("status");  // 找到元素
    x.innerHTML = "training";
}

function downloadResult() {
    var taskname = parseSkipUrl(location.href);    
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", MODEL_URL + "?taskname=" + taskname, false);
    xmlhttp.setRequestHeader ('Content-Type', 'application/json');
    // xmlhttp.send(JSON.stringify(params));
    xmlhttp.send(null);
    if (xmlhttp.status != 200) {
        return;
    }    
}