function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

function validateUser(name, password) {
    return !(name === "" || name === null || password === "" || password === null);
}

function login() {
    let name = form.username;
    let password = form.password;
    if (validateUser(name, password)) {
        //let sid = document.cookie.match('sid=([^;]*)')[1];
        let url = "/WebLab4_t-1.0-SNAPSHOT/api/users/login";
        requestAjax(url, name, password);
    }
    else {
        if (name === "" || name === null) form.errorNameLogin = true;
        if (password === "" || password === null) form.errorPasswordLogin = true;
    }
}

function doResponse(response) {
    if (response.success === true) {
        window.location.replace("/WebLab4_t-1.0-SNAPSHOT/main.html");
    } else {
        alert("Server exception: " + response.message);
        sleep(10000);
    }
}

function register() {
    let name = form.username;
    let password = form.password;
    if (validateUser(name, password)) {
        //let sid = document.cookie.match('sid=([^;]*)')[1];
        let url = "/WebLab4_t-1.0-SNAPSHOT/api/users/register";
        requestAjax(url, name, password);
    }
    else {
        if (name === "" || name === null) form.errorNameRequest = true;
        if (password === "" || password === null) form.errorPasswordRequest = true;
    }
}

function requestAjax(url, name, password) {
    $.ajax({
        url: url,
        type: "GET",
        data:{
            username: name,
            password: password,
        },
        error: (jqXHR) => alert("Wasted\nStatus: " + jqXHR.status),
        success: (response) => {
            console.log(response);
            //let resp = JSON.parse(response);
            doResponse(response);
        },
        async: false
})}
