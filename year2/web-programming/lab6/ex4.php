<?php

$moves = $_GET['table'];
$array = explode(',', $moves);
if (checkColumns($array) != 222 or checkRows($array) != 222 or checkDiagonals($array) != 222)
    echo 'X';
else {
    $poz = mt_rand(0, 8);
    while ($array[$poz] != '')
        $poz = mt_rand(0, 8);
    $array[$poz] = 'O';
    if (checkColumns($array) == 222 and checkRows($array) == 222 and checkDiagonals($array) == 222)
        echo $poz;
    else {
        if (checkColumns($array) == 'O' or checkRows($array) == 'O' or checkDiagonals($array) == 'O')
            echo 'O' . $poz;
        else
            if (countMoves($array) == 9)
                echo '-1';
    }
}

function countMoves($moves)
{
    $movedMade = 0;
    for ($i = 0; $i < 9; $i++)
        if ($moves[$i] != '')
            $movedMade += 1;
    return $movedMade;
}

function checkColumns($moves)
{
    $ok = 0;
    for ($column = 0; $column < 3; $column++) {
        //check column
        $firstCol = $moves[$column];
        //$ok = 0;
        if ($firstCol == '') {
            //$ok = 0;
        } else {
            $ok = 1;
            for ($idx = $column; $idx < 9; $idx += 3) {
                if ($moves[$idx] != $firstCol) {
                    $ok = 0;
                    break;
                }
            }
            if ($ok == 1)
                return $firstCol;
        }
    }
    if ($ok == 0)
        return 222;
}

function checkRows($moves)
{
    $ok = 0;
    for ($row = 0; $row < 9; $row += 3) {
        //check rows
        $firstRow = $moves[$row];
        //$ok = 0;
        if ($firstRow == '') {
            //$ok = 0;
        } else {
            $ok = 1;
            for ($idx = $row; $idx < $row + sqrt(9); $idx += 1) {
                if ($moves[$idx] != $firstRow) {
                    $ok = 0;
                    break;
                }
            }
            if ($ok == 1)
                return $firstRow;

        }

    }
    if ($ok == 0)
        return 222;
}

function checkDiagonals($moves)
{
    //main diagonal
    $ok = 0;
    $diag = 0;
    $firstValue = $moves[$diag];
    if ($firstValue == '') {

    } else {
        $ok = 1;
        for ($idx = $diag; $idx < 9; $idx += 4) {
            if ($moves[$idx] != $firstValue) {
                $ok = 0;
                break;
            }
        }
        if ($ok == 1)
            return $firstValue;
    }

    //secondary diagonal
    $diag = sqrt(9) - 1;
    $firstValue = $moves[$diag];
    if ($firstValue == '') {

    } else {
        $ok = 1;
        for ($idx = $diag; $idx <= 9 - sqrt(9); $idx += 2) {
            if ($moves[$idx] != $firstValue) {
                $ok = 0;
                break;
            }
        }
        if ($ok == 1)
            return $firstValue;
    }

    if ($ok == 0)
        return 222;
}

