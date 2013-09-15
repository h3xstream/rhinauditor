var input = document.search;

function callMe(input) {
    console.info(input);
}

setTimeout("callMe('ok')",1000); //Safe

setTimeout("callMe('"+input+"')",1000); //Danger!


function callback() {
    console.info("Hello!");
}

callback = "test("+input+")";

setTimeout(callback,1337); //Currently unable to differentiate function name from variable containing dynamic code.
