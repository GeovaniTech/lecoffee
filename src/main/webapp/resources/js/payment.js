function openPayment() {  
    var payment = document.getElementById("payment");
    var body = document.body;

    if (payment.style.width === "0px") {
        payment.style.right = "0px";
        payment.style.width = "100vw";
        body.classList.add("no-scroll");
    } else {
        payment.style.right = "-100vw";
        payment.style.width = "0px";
        body.classList.remove("no-scroll");
    }
}