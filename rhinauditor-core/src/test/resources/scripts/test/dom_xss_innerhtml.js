function createSomething1(value) {
    var element = document.getElementById("result");

    element.innerHTML = "<b>"+value+" created</b>";
}

function updateSomething1(newLine) {
    var element = document.getElementById("result");

    element.innerHTML += "<br/>"+newLine;
}

////Use of the "index syntax" to access innerHTML property

function createSomething2(value) {
    var element = document.getElementById("result");

    element['innerHTML'] = "<b>"+value+" created</b>";
}

function updateSomething2(newLine) {
    var element = document.getElementById("result");

    element['innerHTML'] += "<br/>"+newLine;
}


var constInnerHtml = 'innerHTML';

function createSomething3(value) {
    var element = document.getElementById("result");

    element[constInnerHtml] = "<b>"+value+" created</b>";
}

function updateSomething3(newLine) {
    var element = document.getElementById("result");

    element[constInnerHtml] += "<br/>"+newLine;
}