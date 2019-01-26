function InputRadioClick(param, val) {
    if (param === 'x') {
        form.x = val;
    }
    if (param === 'r') {
        form.y = val;
    }
}

function logoutRequest() {

}

function requestAjax(url, data) {
    $.ajax({
        url: url,
        type: "POST",
        data: data,
        error: (jqXHR) => alert("Wasted\nStatus: " + jqXHR.status),
        success: (response) => {
            console.log(response);
            return response;
        }
    });
}