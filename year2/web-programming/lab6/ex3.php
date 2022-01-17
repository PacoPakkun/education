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

if (!isset($_GET['id'])) {
    $sql = "SELECT id FROM people";
    $people = '';
    $result = $conn->query($sql);
    while ($row = $result->fetch_assoc()) {
        $people .= $row["id"] . ",";
    }
    echo $people;

} else {
    if (!isset($_GET['nume'])) {
        $sql = "SELECT * FROM people WHERE id=" . $_GET['id'];
        $person = '';
        $result = $conn->query($sql);
        while ($row = $result->fetch_assoc()) {
            $person .= $row["nume"] . "," . $row["prenume"] . "," . $row["telefon"] . "," . $row["email"];
        }
        echo $person;

    } else {
        $id = $_GET['id'];
        $nume = $_GET['nume'];
        $prenume = $_GET['prenume'];
        $tel = $_GET['tel'];
        $email = $_GET['email'];

        $sql = "UPDATE people SET nume = '" . $nume . "', prenume = '" . $prenume . "',telefon='" . $tel . "',email='" . $email . "' WHERE id=" . $id;
        if ($conn->query($sql) === TRUE) {
            echo "Record updated successfully";
        } else {
            echo "Error updating record: " . $conn->error;
        }
    }
}

$conn->close();
