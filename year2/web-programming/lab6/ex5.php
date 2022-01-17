<?php

$directory = "TreeView/D1";
$g = $_GET['g'];
if ($g == "0") {
    getDirectories();
}
if ($g == "1") {
    $dir = $_GET['dir'];
    if ($dir == "D1")
        echo "Fisier1.txt,Fisier2.txt,Fisier3.txt";
    if ($dir == "D2")
        echo "Fisier1.txt,Fisier2.txt,Fisier3.txt";
    if ($dir == "D3")
        echo "Fisier1.txt,Fisier3.txt";
}
if ($g == "2") {
    echo $_GET['file'];
}

function getDirectories()
{
    echo "D1,D2,D3";
}

?>