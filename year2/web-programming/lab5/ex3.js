var count = 0;
var win = 0;
var last;

$("td").click(function () {
    count += 1;
    // $(this).find("p").css("display", "block")
    $(this).find("img").css("display", "block")
    if (count % 2 !== 0) {
        last = $(this);
    } else {
        if (last.find("img").attr('src') !== $(this).find("img").attr('src')) {
            // if (last.text() !== $(this).text()) {
            el = last
            el2 = $(this)
            setTimeout(function () {
                // el.find("p").css("display", "none");
                // el2.find("p").css("display", "none");
                el.find("img").css("display", "none");
                el2.find("img").css("display", "none");
            }, 1000);
        } else {
            win += 1;
            if (win === 8) {
                setTimeout(function () {
                    alert('Ai castigat!')
                }, 300);
            }
        }
    }
})
//
// function clickulet(id) {
//     count += 1;
//     document.getElementById(id).style = "display: block;";
//     if (count % 2 !== 0) {
//         lastid = id;
//     } else {
//         if (document.getElementById(lastid).innerText !== document.getElementById(id).innerText) {
//             idd = lastid
//             setTimeout(function () {
//                 document.getElementById(idd).style = "display: none;";
//                 document.getElementById(id).style = "display: none;";
//             }, 1000);
//         } else {
//             win += 1;
//             if (win === 8) {
//                 setTimeout(function () {
//                     alert('Ai castigat!')
//                 }, 300);
//             }
//         }
//     }
// }
//
// function clickutz(id) {
//     count += 1;
//     document.getElementById(id).style = "height: 100px; display: block;";
//     if (count % 2 !== 0) {
//         lastid = id;
//     } else {
//         if (document.getElementById(lastid).src !== document.getElementById(id).src) {
//             idd = lastid
//             setTimeout(function () {
//                 document.getElementById(idd).style = "height: 100px; display: none;";
//                 document.getElementById(id).style = "height: 100px; display: none;";
//             }, 1000);
//         } else {
//             win += 1;
//             if (win === 8) {
//                 setTimeout(function () {
//                     alert('Ai castigat!')
//                 }, 300);
//             }
//         }
//     }
// }