
function displayText(value) {
    document.write("Insert DOM XSS here -> " + value + " <-");
}

document.write("Hello");

displayText("Hi!");

function displayText2(value) {
    //This syntax is ignored for the moment..
    document['write']("Insert DOM XSS here -> " + value + " <-");
}