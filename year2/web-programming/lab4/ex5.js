var current = 1;

var interval = setInterval(function () {
    nextImg()
}, 3000);

function previousImg() {
    document.getElementById('li' + current).style = "display: none;"
    current -= 1;
    if (current === 0) {
        current = 4;
    }
    document.getElementById('li' + current).style = "display: block;"
}

function nextImg() {
    document.getElementById('li' + current).style = "display: none;"
    current += 1;
    if (current === 5) {
        current = 1;
    }
    document.getElementById('li' + current).style = "display: block;"
}