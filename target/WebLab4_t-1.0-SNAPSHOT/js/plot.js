function init(){
    drawAllPoints(1, false);
    document.getElementById("canvas").onclick = function(event) {clickCanvas(event)}
}

function drawAllPoints(r, r_changed) {
    if (!r_changed) {
        var r = parseInt(document.getElementById("form:R_text").innerHTML);
    }
    var x, y, color;
    var counter=0;
    var table = document.getElementById('beanTable');
    drawCanvas('canvas', r);
    if (!(table===null)) {
        table.querySelectorAll('td').forEach(function (e) {
            switch (counter) {
                case 0:
                    x = e.innerHTML;
                    break;
                case 1:
                    y = e.innerHTML;
                    break;
                case 2:
                    break;
                case 3:
                    if (e.innerText == "❌") {
                        color = "red";
                    } else {
                        color = "green";
                    }
                    drawPoint(x, y, r, color);
                    counter -= 4;
                    break;
            }
            counter++;
        });
    }
}

function drawCanvas(id, r){
    var canvas = document.getElementById(id),
        context = canvas.getContext("2d");
//очистка
    context.clearRect(0, 0, canvas.width, canvas.height);
//треугольник
    context.beginPath();
    context.moveTo(150, 150);
    context.lineTo(150, 280);
    context.lineTo(215,150);
    context.lineTo(150, 150);
    context.closePath();
    context.strokeStyle = "#DEB887";
    context.fillStyle = "#DEB887";
    context.fill();
    context.stroke();

//прямоугольник
    context.beginPath();
    context.rect(150, 20, 65, 130);
    context.closePath();
    context.strokeStyle = "#DEB887";
    context.fillStyle = "#DEB887";
    context.fill();
    context.stroke();

//сектор
    context.beginPath();
    context.moveTo(150, 150);
    context.arc(150, 150, 130, Math.PI/2, Math.PI, false);
    context.closePath();
    context.strokeStyle = "#DEB887";
    context.fillStyle = "#DEB887";
    context.fill();
    context.stroke();

//отрисовка осей
    context.beginPath();
    context.font = "12px Verdana";
    context.moveTo(150, 0); context.lineTo(150, 300);
    context.moveTo(150, 0); context.lineTo(145, 12);
    context.moveTo(150, 0); context.lineTo(155, 12);
    context.closePath();
    context.strokeStyle = "black";
    context.fillStyle = "black";
    context.stroke();

    context.beginPath();
    context.fillText("Y", 160, 10);
    context.moveTo(0, 150);
    context.lineTo(300, 150);
    context.moveTo(300, 150);
    context.lineTo(288, 145);
    context.moveTo(300, 150);
    context.lineTo(288, 155);
    context.fillText("X", 290, 135);
    context.closePath();
    context.strokeStyle = "black";
    context.fillStyle = "black";
    context.stroke();

//деления X
    context.beginPath();
    context.moveTo(145, 20);
    context.lineTo(155, 20);
    context.moveTo(145, 85);
    context.lineTo(155, 85);
    context.moveTo(145, 215);
    context.lineTo(155, 215);
    context.moveTo(145, 280);
    context.lineTo(155, 280);
    if (r === 0){
        context.fillText("R", 160, 25);
        context.fillText("R/2", 160, 90);
        context.fillText("-R/2", 160, 220);
        context.fillText("-R", 160, 285);
    } else {
        context.fillText(r, 160, 25);
        context.fillText((r / 2), 160, 90);
        context.fillText(-(r / 2), 160, 220);
        context.fillText(-r, 160, 285);
    }

//деления Y
    context.moveTo(20, 145);
    context.lineTo(20, 155);
    context.moveTo(85, 145);
    context.lineTo(85, 155);
    context.moveTo(215, 145);
    context.lineTo(215, 155);
    context.moveTo(280, 145);
    context.lineTo(280, 155);
    if (r===0){
        context.fillText("-R", 12, 140);
        context.fillText("-R/2", 70, 140);
        context.fillText("R/2", 205, 140);
        context.fillText("R", 275, 140);
    } else {
        context.fillText(-r, 12, 140);
        context.fillText(-(r / 2), 70, 140);
        context.fillText((r / 2), 205, 140);
        context.fillText(r, 275, 140);
    }

    context.closePath();
    context.strokeStyle = "black";
    context.fillStyle = "black";
    context.stroke();
}

function clickCanvas(event){
    removeError();
    var r = parseInt(document.getElementById("form:R_text").innerHTML);
    var canvas = document.getElementById('canvas');
    var br = canvas.getBoundingClientRect();
    var left = br.left;
    var top = br.top;
    event = window.event || event;
    //var event = window.event;
    var x = event.clientX-left;
    var y = event.clientY-top;
    var size = canvas.height;
    if (r>0) {
        x = Math.round((x - size / 2) * r * 10 / 2 / 65) / 10;
        y = Math.round((-y + size / 2) * r * 10 / 2 / 65) / 10;
        drawCanvas('canvas', r);
        document.getElementById("form:x_hidden").value = x;
        document.getElementById("form:Y").value = y;
        yVal = y;
        drawPoint(x, y, r);
        document.getElementById('form:validationButton').click();
        drawAllPoints(1, false);
    }
}

function drawPoint(x,y,r,color){
    var color;
    var canvas = document.getElementById('canvas'),
        ctx = canvas.getContext("2d");
    if (color === "green") {
        color = 'green';
    } else {
        color = 'red';
    }
    ctx.beginPath();
    ctx.arc(150+x*130/r, 150-y*130/r, 5, 0, 2*Math.PI);
    ctx.fillStyle = color;
    ctx.fill();
    ctx.closePath();
}

function isArea(x, y, r) {
    if (
        ((x >= 0) && (y <= 0) && (y >= x*2 - r)) ||
        ((x <= 0) && (y <= 0) && ((x * x + y * y) <= (r * r))) ||
        ((x >= 0) && (y >= 0) && (x <= r/2) && (y <= r))
    ) {
        return true;
    }
    return false;

}