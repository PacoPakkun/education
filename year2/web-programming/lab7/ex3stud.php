<?php

echo "<!DOCTYPE html>
<html lang=\"en\">
<head>
    <meta charset=\"UTF-8\">
    <title>Student Page</title>
</head>
<body><center>";

echo "<h2> Welcome to your Academic Info!</h2>";

$firstname = $lastname = '';
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $firstname = test_input($_POST["firstname"]);
    $lastname = test_input($_POST["lastname"]);
    getGrades($firstname, $lastname);
}

function test_input($data)
{
    $data = trim($data);
    $data = stripslashes($data);
    $data = htmlspecialchars($data);
    return $data;
}

echo "</center></body></html>";

function getGrades($firstname, $lastname)
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

    echo "Logged in as " . $firstname . " " . $lastname . "<br><br>";

    $table = "<table cellpadding='10' border='1px'> <tr> <th>Subject</th> <th>Teacher</th> <th>Grade</th> </tr> ";

    $stmt = $conn->prepare('SELECT stID FROM students WHERE firstname = ? AND lastname = ?');
    $stmt->bind_param('ss', $firstname, $lastname);
    $stmt->execute();
    $result = $stmt->get_result();


    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            $studID = $row['stID'];
        }

        $stmt = $conn->prepare('SELECT sID,tID,grade FROM grades WHERE stID = ?');
        $stmt->bind_param('i', $studID);
        $stmt->execute();
        $result1 = $stmt->get_result();
        if ($result1->num_rows > 0) {
            while ($row1 = $result1->fetch_assoc()) {
                $subID = $row1['sID'];
                $teacherID = $row1['tID'];
                $grade = $row1['grade'];

                $stmt = $conn->prepare('SELECT sName FROM subjects WHERE sID = ?');
                $stmt->bind_param('i', $subID);
                $stmt->execute();
                $result2 = $stmt->get_result();
                $subject = '';
                $teacher = '';
                if ($result2->num_rows > 0) {
                    while ($row2 = $result2->fetch_assoc()) {
                        $subject .= $row2['sName'];
                    }
                }
                $stmt = $conn->prepare('SELECT fullname FROM teachers WHERE tID = ?');
                $stmt->bind_param('i', $teacherID);
                $stmt->execute();
                $result2 = $stmt->get_result();
                if ($result2->num_rows > 0) {
                    while ($row2 = $result2->fetch_assoc()) {
                        $teacher .= $row2['fullname'];
                    }
                }
                $table .= "<tr><td>$subject </td><td>$teacher</td> <td>$grade</td> </tr>";
            }
        }
    } else {
        echo "<br>There is no student with these credentials.<br>";
    }

    $table .= "</table>";
    echo $table;
}

?>