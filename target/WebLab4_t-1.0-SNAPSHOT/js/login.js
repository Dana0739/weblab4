function validateUser(name, password) {
    return !(name === "" || name === null || password === "" || password === null);
}

function login() {
    let name = form.username;
    let password = form.password;
    if (validateUser(name, password)) {
        let url = "http://laptop-bmq72mqa:8080/WebLab4_t-1.0-SNAPSHOT/api/users/login/" + name + "/" + password;
        let response = requestAjax(url);
        console.log(response);
    }
    else {
        if (name === "" || name === null) form.errorNameLogin = true;
        if (password === "" || password === null) form.errorPasswordLogin = true;
    }
}

function  ajaxResponse(response) {
    if (response.status == 200)
        alert("success!!!");
}

function register() {
    let name = form.username;
    let password = form.password;
    if (validateUser(name, password)) {
        let url = "http://laptop-bmq72mqa:8080/WebLab4_t-1.0-SNAPSHOT/api/users/register/" + name + "/" + password;
        let response = requestAjax(url);
        console.log(response);
    }
    else {
        if (name === "" || name === null) form.errorNameRequest = true;
        if (password === "" || password === null) form.errorPasswordRequest = true;
    }
}

function requestAjax(url) {
    $.ajax({
        url: url,
        type: "GET",
        error: (jqXHR) => alert("Wasted\nStatus: " + jqXHR.status),
        success: (response, jqXHR) => {
        alert("Wasted\nStatus: " + jqXHR.text);
            ajaxResponse(response);
        }
});
}