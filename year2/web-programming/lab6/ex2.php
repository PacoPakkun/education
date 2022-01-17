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


$offset = ($_GET['page'] - 1) * 3;
$people = '';

$sql = "SELECT * FROM people LIMIT 3 OFFSET " . $offset;
$result = $conn->query($sql);
while ($row = $result->fetch_assoc()) {
    $people .= $row["nume"] . "," . $row["prenume"] . ",". $row["telefon"] . ",". $row["email"] . ";";
}

echo substr($people,0,-1);
$conn->close();