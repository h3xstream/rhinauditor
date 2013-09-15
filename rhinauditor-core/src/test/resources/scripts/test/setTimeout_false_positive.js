function callMe(input) {
    console.info(input);
}

setTimeout("callMe('ok')",1000); //Safe

setTimeout("callMe('ok')"+";test=1;"+"callMe()",1000);

setTimeout(function() {
    console.info("No string evaluation here...");
},1337);

