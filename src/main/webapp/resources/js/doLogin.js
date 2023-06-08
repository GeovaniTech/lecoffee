function openDoLogin() {  
    var doLogin = document.getElementById("doLogin");
    var img = document.getElementById("wave");
    var body = document.body;

    if (doLogin.style.width === "0px") {
        doLogin.style.right = "0px";
        doLogin.style.width = "100vw";
        body.classList.add("no-scroll");
    } else {
        doLogin.style.right = "-100vw";
        doLogin.style.width = "0px";
        body.classList.remove("no-scroll");
    }
}