<?php

$servername = "localhost";
$username = "root";
$password = "";
$q = "";
$conn = new mysqli($servername, $username, $password);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
$q .= "Connected successfully\n";
$sql = "USE web";
if ($conn->query($sql) === TRUE) {
    $q .= "Database changed\n";
} else {
    $q .= "Error using db\n";
}


if ($_GET['city'] == '') {
    $start = "";
    $stop = "";

    $sql = "SELECT DISTINCT start FROM cities";
    $result = $conn->query($sql);
    while ($row = $result->fetch_assoc()) {
        $start .= $row["start"] . ",";
    }

    $sql = "SELECT DISTINCT stop FROM cities";
    $result = $conn->query($sql);
    while ($row = $result->fetch_assoc()) {
        $stop .= $row["stop"] . ",";
    }

    $cities = $start . ";" . $stop;


} else {
    $stop = "";
    $city = $_GET['city'];
    $sql = "SELECT DISTINCT stop FROM cities WHERE start = '" . $city . "'";
    $result = $conn->query($sql);
    while ($row = $result->fetch_assoc()) {
        $stop .= $row["stop"] . ",";
    }
    $cities = $stop;
}

echo $cities;
$conn->close();

//if ($_GET['city'] == '') {
//    $start_cities = array();
//    $end_cities = array();
//
//    $fh = fopen('ex1.txt', 'r');
//    while ($line = fgets($fh)) {
//        $split = explode(',', substr($line, 1, -3));
//        if (!in_array($split[0], $start_cities)) {
//            array_push($start_cities, $split[0]);
//        }
//        if (!in_array($split[1], $end_cities)) {
//            array_push($end_cities, $split[1]);
//        }
//    }
//    fclose($fh);
//    echo implode(',', $start_cities) . ';' . implode(',', $end_cities);
//} else {
//    $city = $_GET['city'];
//    $cities = array();
//
//    $fh = fopen('ex1.txt', 'r');
//    while ($line = fgets($fh)) {
//        $split = explode(',', substr($line, 1, -3));
//        if ($split[0] == $city) {
//            array_push($cities, $split[1]);
//        }
//    }
//    fclose($fh);
//    echo implode(',', $cities);
//}
