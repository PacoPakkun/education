<?php

$us = $_COOKIE["username"];
echo "<p> Username: $us </p> ";
echo "<p> Select the photo you want to upload to your profile </p> ";
echo '<form method="post" enctype="multipart/form-data"><input type="file" name="image" id="image"><br><input type="submit" name="upload" value="Upload"><br></form><br>';

echo '<form method="post">';
userImages();
echo '<input type="submit" name="delete" value="Delete"><br>';
echo '</form><br><p>Profiles: </p><br>';
showFriends();


if (isset($_POST['upload'])) {
    if (getimagesize($_FILES['image']['tmp_name']) == FALSE) {
        echo 'Please select an image!';
    } else {
        $image = addslashes($_FILES['image']['tmp_name']);
        $name = addslashes($_FILES['image']['name']);
        $img = base64_encode(file_get_contents($image));
        uploadPhoto($name, $img);
    }
}

if (isset($_POST['delete'])) {
    if ($_POST['whatToDelete'] != 'Delete Image') {
        $servername = "localhost";
        $username = "root";
        $password = "";
        $conn = new mysqli($servername, $username, $password);
        if ($conn->connect_error) {
            die("Connection error");
        } else {
            echo 'Connected to server';
        }
        $sql = "USE web";
        if ($conn->query($sql) === TRUE) {
            echo 'connected to web';
        } else {
            die("Cannot execute query");
        }
        $stmt = $conn->prepare("DELETE FROM images WHERE img = ? AND username = ?");
        $stmt->bind_param('ss', $_POST['whatToDelete'], $us);
        $stmt->execute();
    }
}


function userImages()
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

    $stmt = $conn->prepare("SELECT img FROM images where username = ?");
    $stmt->bind_param('s', $_COOKIE['username']);
    $stmt->execute();
    $result = $stmt->get_result();
    if ($result->num_rows > 0) {
        echo '<select name="whatToDelete">';
        echo '<option selected value="Delete Image">Delete Image</option>';
        while ($row = $result->fetch_assoc()) {
            echo '<option value="' . $row['img'] . '">' . $row['img'] . ' </option>';
        }
        echo '</select>';
    }

}

function uploadPhoto($name, $img)
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

    $stmt = $conn->prepare("INSERT INTO images(username, img) VALUES (?,?)");
    $stmt->bind_param('ss', $_COOKIE['username'], $name);
    $stmt->execute();
    echo '<p>Image saved</p>';

}

function showFriends()
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

    $stmt = $conn->prepare("SELECT username FROM users");
    $stmt->execute();
    $result = $stmt->get_result();
    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            echo '<a href="profile.php?profile=' . $row['username'] . '">' . $row['username'] . '</a> <br>';
        }
    }
}


?>