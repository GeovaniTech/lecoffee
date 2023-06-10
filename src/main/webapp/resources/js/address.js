function openAddress() {  
    var address = document.getElementById("address");
    var body = document.body;

    if (address.style.width === "0px") {
        address.style.right = "0px";
        address.style.width = "100vw";
        body.classList.add("no-scroll");
    } else {
        address.style.right = "-100vw";
        address.style.width = "0px";
        body.classList.remove("no-scroll");
    }
}