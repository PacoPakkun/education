var current = 1;

var interval = setInterval(function () {
    $('#li' + current).css("display", "none")
    current += 1;
    if (current === 5) {
        current = 1;
    }
    $('#li' + current).css("display", "block")
}, 3000);

$('.before').click(function () {
    $('#li' + current).css("display", "none")
    current -= 1;
    if (current === 0) {
        current = 4;
    }
    $('#li' + current).css("display", "block")
})

$('.after').click(function () {
    $('#li' + current).css("display", "none")
    current += 1;
    if (current === 5) {
        current = 1;
    }
    $('#li' + current).css("display", "block")
})