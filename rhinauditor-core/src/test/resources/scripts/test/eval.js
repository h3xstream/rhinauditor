
function callMe(input) {
    console.info(input);
}

eval("callMe('ok')");

eval("callMe('"+document.search+"')");