$('.horizontal').click(function () {
    const cols = [];
    for (var i = 2; i <= 4; i++) {
        var col_Array = $('#tbl td:nth-child(' + i + ')').map(function () {
            return $(this);
        }).get();
        cols.push(col_Array);
    }

    const index = parseInt($(this).attr('id'), 10);

    cols.sort(function (colA, colB) {
        let cellA = colA[index].text();
        let cellB = colB[index].text();

        if ($.isNumeric(cellA) && $.isNumeric(cellB)) {
            cellA = parseInt(cellA, 10)
            cellB = parseInt(cellB, 10)
        }

        switch (true) {
            case cellA > cellB:
                return 1;
            case cellA < cellB:
                return -1;
            case cellA === cellB:
                return 0;
        }
    });

    [].forEach.call(cols, function (col) {
        [].forEach.call(col, function (el) {
            $('#tbl').find(el).remove()
        });
    });

    cols.forEach(function (newCol) {
        $('#tbl').find('tr:nth-child(1)').append(newCol[0]);
        $('#tbl').find('tr:nth-child(2)').append(newCol[1]);
        $('#tbl').find('tr:nth-child(3)').append(newCol[2]);
    });
})

$('.vertical').click(function () {
    const rows = [];
    $('#tbl2').find('tr').each(function () {
        rows.push($(this))
    })
    rows.shift()
    const index = parseInt($(this).attr('id'), 10);

    rows.sort(function (rowA, rowB) {
        let cellA = rowA.find('td').toArray()[index].innerText;
        let cellB = rowB.find('td').toArray()[index].innerText;

        var num = /^[0-9]+$/;
        if (cellA.match(num) && cellB.match(num)) {
            cellA = parseInt(cellA, 10)
            cellB = parseInt(cellB, 10)
        }

        switch (true) {
            case cellA > cellB:
                return 1;
            case cellA < cellB:
                return -1;
            case cellA === cellB:
                return 0;
        }
    });

    [].forEach.call(rows, function (row) {
        $('#tbl2').find(row).remove()
    });

    rows.forEach(function (newRow) {
        $('#tbl2').append(newRow);
    });
})

// function sortTableHorizontal(id) {
//     var table, rows, switching, i, shouldSwitch;
//     table = document.getElementById("tbl");
//     switching = true;
//     while (switching) {
//         switching = false;
//         rows = table.rows;
//         for (i = 1; i < (rows[id].cells.length - 1); i++) {
//             shouldSwitch = false;
//             var x = rows[id].cells[i];
//             var y = rows[id].cells[i + 1];
//             var num = /^[0-9]+$/;
//             if (x.innerText.match(num) && y.innerText.match(num)) {
//                 if (parseInt(x.innerText, 10) > parseInt(y.innerText, 10)) {
//                     shouldSwitch = true;
//                     break;
//                 }
//             } else if (x.innerHTML > y.innerHTML) {
//                 shouldSwitch = true;
//                 break;
//             }
//         }
//         if (shouldSwitch) {
//             for (var j = 0; j < (rows.length); j++) {
//                 var aux = rows[j].cells[i].innerText;
//                 rows[j].cells[i].innerText = rows[j].cells[i + 1].innerText;
//                 rows[j].cells[i + 1].innerText = aux;
//             }
//             switching = true;
//         }
//     }
// }
//
// function sortTableVertical(id) {
//     var table, rows, switching, i, x, y, shouldSwitch;
//     table = document.getElementById("tbl2");
//     switching = true;
//
//     while (switching) {
//         switching = false;
//         rows = table.rows;
//         for (i = 1; i < (rows.length - 1); i++) {
//             shouldSwitch = false;
//             x = rows[i].cells[id];
//             y = rows[i + 1].cells[id];
//             var num = /^[0-9]+$/;
//             if (x.innerText.match(num) && y.innerText.match(num)) {
//                 if (parseInt(x.innerText, 10) > parseInt(y.innerText, 10)) {
//                     shouldSwitch = true;
//                     break;
//                 }
//             } else if (x.innerHTML > y.innerHTML) {
//                 shouldSwitch = true;
//                 break;
//             }
//         }
//         if (shouldSwitch) {
//             rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
//             switching = true;
//         }
//     }
// }