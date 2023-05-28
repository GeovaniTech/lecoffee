function openCart() {  
    var cart = document.getElementById("cart");
    var body = document.body;

    if (cart.style.width === "0px") {
        cart.style.right = "0px";
        cart.style.width = "100vw";
        body.classList.add("no-scroll");
    } else {
        cart.style.right = "-100vw";
        cart.style.width = "0px";
        body.classList.remove("no-scroll");
    }
}