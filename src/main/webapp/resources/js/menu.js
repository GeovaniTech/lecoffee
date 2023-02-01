function openMenu() {  
    if(document.getElementById("header").style.width == "0px") {
        document.getElementById("header").style.left = "0px";
        document.getElementById("header").style.width = "250px";
        document.getElementById("body-main").style.marginLeft = "250px"
        document.getElementById("top-header").style.paddingLeft = "250px"
    } else {
        document.getElementById("header").style.left = "-300px";
        document.getElementById("header").style.width = "0px";
        document.getElementById("body-main").style.marginLeft = "0px"
        document.getElementById("top-header").style.paddingLeft = "0px"
    }
}