function openCart() {  
    if(document.getElementById("cart").style.width == "0px") {
        document.getElementById("cart").style.right = "0px";
        document.getElementById("cart").style.width = "100vw";
    } else {
        document.getElementById("cart").style.right = "-100vw";
        document.getElementById("cart").style.width = "0px";
    }
}