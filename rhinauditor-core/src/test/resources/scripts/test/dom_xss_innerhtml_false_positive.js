//Different cases that are safe innerHTML use

var el = document.getElementsByName("a")[0];
el.innerHTML = "Constant string no need to review.";

el['innerHTML'] = "Move along ...";

el.innerHTML += "Again";

el.innerHTML = "Look"+ " " + "somewhere" + " else.";
