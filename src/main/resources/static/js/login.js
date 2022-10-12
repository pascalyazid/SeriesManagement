$(document).ready(function (){
    //isLoggedInL();
    $("#loginForm").submit(sendLogin);
    $("#logoff").click(sendLogout);
});

/**
 * Checks if the user is logged in, if yes -> index.html
 */
function isLoggedInL() {
    $
        .ajax({
            url: "/SeriesManagement/user/loggedIn",
            type: "GET",
            dataType: "json",
        })
        .done(function () {
            window.location.href = "./index.html";
        })
        .fail(function (xhr, status, errorThrown){
            if(xhr.status == 401) {
                window.location.href = "login.html";
            } else{
                console.log("An Error Occurred")
            }
        })
}

/**
 * Funktion zum Anmelden des Users
 * @param form
 */
function sendLogin(form){
    form.preventDefault();
    console.log($("#loginForm").serialize());
    $
        .ajax({
            url: "/SeriesManagement/user/login",
            dataType: "text",
            type: "POST",
            data: $("#loginForm").serialize()
        })
        .done(function () {
            $("message").text("Login successfull");
            window.location.href = "./index.html";
        })
        .fail(function (xhr, status, errorThrown){
            if(xhr.status == 401) {
                let text = document.getElementById("error");
                text.style.color = "red";
                text.innerHTML = "Invalid Credentials";
            } else{
                alert("An error occured");
            }
        })
}
function sendLogout(){
    $
        .ajax({
            url: "/SeriesManagement/user/logout",
            dataType: "text",
            type: "DELETE",
        })
        .done(function () {
            window.location.href = "./login.html";
        })
        .fail(function (xhr, status, errorThrown){

        })
}