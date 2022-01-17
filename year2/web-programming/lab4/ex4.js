function sortTableHorizontal(id) {
    var table, rows, switching, i, shouldSwitch;
    table = document.getElementById("tbl");
    switching = true;
    while (switching) {
        switching = false;
        rows = table.rows;
        for (i = 1; i < (rows[id].cells.length - 1); i++) {
            shouldSwitch = false;
            var x = rows[id].cells[i];
            var y = rows[id].cells[i + 1];
            var num = /^[0-9]+$/;
            if (x.innerText.match(num) && y.innerText.match(num)) {
                if (parseInt(x.innerText, 10) > parseInt(y.innerText, 10)) {
                    shouldSwitch = true;
                    break;
                }
            } else if (x.innerHTML > y.innerHTML) {
                shouldSwitch = true;
                break;
            }
        }
        if (shouldSwitch) {
            for (var j = 0; j < (rows.length); j++) {
                var aux = rows[j].cells[i].innerText;
                rows[j].cells[i].innerText = rows[j].cells[i + 1].innerText;
                rows[j].cells[i + 1].innerText = aux;
            }
            switching = true;
        }
    }
}

function sortTableVertical(id) {
    var table, rows, switching, i, x, y, shouldSwitch;
    table = document.getElementById("tbl2");
    switching = true;

    while (switching) {
        switching = false;
        rows = table.rows;
        for (i = 1; i < (rows.length - 1); i++) {
            shouldSwitch = false;
            x = rows[i].cells[id];
            y = rows[i + 1].cells[id];
            var num = /^[0-9]+$/;
            if (x.innerText.match(num) && y.innerText.match(num)) {
                if (parseInt(x.innerText, 10) > parseInt(y.innerText, 10)) {
                    shouldSwitch = true;
                    break;
                }
            } else if (x.innerHTML > y.innerHTML) {
                shouldSwitch = true;
                break;
            }
        }
        if (shouldSwitch) {
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            switching = true;
        }
    }
}