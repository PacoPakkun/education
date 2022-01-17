<?php

function displayImages()
{
    $servername = "localhost";
    $username = "root";
    $password = "";
    $conn = new mysqli($servername, $username, $password);
    if ($conn->connect_error) {
        die("Connection error");
    }
    $sql = "USE web";
    if ($conn->query($sql) !== TRUE) {
        die("Cannot execute query");
    }

    echo '<p>Username: ' . $_GET['profile'] . '</p>';
    $stmt = $conn->prepare("SELECT img FROM images where username = ?");
    $stmt->bind_param('s', $_GET['profile']);
    $stmt->execute();

    $result = $stmt->get_result();
    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            echo '<img style="margin:10px" height="200" width="200" src=images/' . $row['img'] . '     alt="imaginee">';
        }
    }
}

function isRegistered()
{
    $servername = "localhost";
    $username = "root";
    $password = "";
    $conn = new mysqli($servername, $username, $password);
    if ($conn->connect_error) {
        die("Connection error");
    } else {
        // echo 'Connected to server';
    }
    $sql = "USE web";
    if ($conn->query($sql) === TRUE) {
        //   echo 'connected to web';
    } else {
        die("Cannot execute query");
    }

    $stmt = $conn->prepare("SELECT * FROM users WHERE username = ? AND password = ?");
    $stmt->bind_param('ss', $_COOKIE['userN'], $_COOKIE['password']);
    $stmt->execute();

    $result = $stmt->get_result();

    if ($result->num_rows > 0)
        return TRUE;
    else
        return FALSE;
}

if (isset($_COOKIE['userN']) and isset($_COOKIE['password']) and isset($_GET['profile'])) {
    if (isRegistered()) {
        displayImages();
    } else
        echo 'Error!';
}
?>


