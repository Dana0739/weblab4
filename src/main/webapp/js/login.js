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
        let url = "http://laptop-bmq72mqa:8080/WebLab4_t-1.0-SNAPSHOT/api/users/login/" + name + "/" + password;
        requestAjax(url);
    }
    else {
        if (name === "" || name === null) form.errorNameLogin = true;
        if (password === "" || password === null) form.errorPasswordLogin = true;
    }
}

async function ajaxResponse(response, jqXHR) {
    let resp = JSON.parse(response);
    if (resp.success.toString() === "200") {
        if (resp.message.toString() === "Пользователь с таким логином уже существует!"
            || resp.message.toString() === "Неверный логин или пароль!"
            || resp.message.toString() === "") {
            alert(resp.message);
            await sleep(10000);
        } else if (resp.message.toString() === "Регистрация успешно выполнена!"
                    || resp.message.toString() === "nullexception") {
            window.location.replace("/main.html");
        } else {
            alert("Server exception: " + resp.message);
            await sleep(10000);
        }
    } else {
        alert(jqXHR.status);
        await sleep(10000);
    }
}

function register() {
    let name = form.username;
    let password = form.password;
    if (validateUser(name, password)) {
        let url = "http://laptop-bmq72mqa:8080/WebLab4_t-1.0-SNAPSHOT/api/users/register/" + name + "/" + password;
        requestAjax(url);
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
            ajaxResponse(response, jqXHR);
        }
})}