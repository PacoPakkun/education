<?php

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    if (isset($_POST['teacher'])) {
        $student = $_POST['studentName'];
        $subject = $_POST['subject'];
        $grade = $_POST['grade'];
        $tId = $_POST['teacherId'];

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

        $name = explode(' ', $student);
        $firtsN = $name[0];
        $lastN = $name[1];

        $stmt = $conn->prepare('SELECT stID FROM students WHERE firstname = ? AND lastname = ?');
        $stmt->bind_param('ss', $firtsN, $lastN);
        $stmt->execute();
        $result = $stmt->get_result();

        if ($result->num_rows > 0) {
            while ($row = $result->fetch_assoc()) {
                $stID = $row['stID'];

                $stmt = $conn->prepare('SELECT sID FROM subjects WHERE sName = ?');
                $stmt->bind_param('s', $subject);
                $stmt->execute();
                $result1 = $stmt->get_result();

                if ($result1->num_rows > 0) {
                    while ($row1 = $result1->fetch_assoc()) {
                        $sID = $row1['sID'];
                        $stmt = $conn->prepare('INSERT INTO grades(stID, sID, tID, grade) VALUES (?, ?, ?, ?)');
                        $stmt->bind_param('iiii', $stID, $sID, $tId, $grade);
                        $stmt->execute();
                    }
                }
            }
        }

        $conn->close();
    }
    ?>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Teacher Page</title>
    </head>
    <body>
    <center>
        <?php
        echo "<h2> Student Management System</h2>";

        $username = test_input($_POST["username"]);
        $password = test_input($_POST["password"]);

        $info = getTeacherInfo($username, $password);
        $id = $info['0'];
        $name = $info['1'];
        ?>
        <form method='post' action='ex3prof.php'>
            <table cellpadding="10">
                <tr>
                    <td>
                        Teacher name:
                    </td>
                    <td>
                        <input type='text' name='teacher' value='<?php echo $name ?>' readonly>
                    </td>
                </tr>
                <tr>
                    <td>
                        Teacher ID:
                    </td>
                    <td>
                        <input type='text' name='teacherId' value='<?php echo $id ?>' readonly>
                    </td>
                </tr>
                <?php
                initStudents($id);
                ?>
                <tr>
                    <td>
                        Grade:
                    </td>
                    <td>
                        <input type='text' name='grade'>
                    </td>
                </tr>
            </table>
            <input type="hidden" name="username" value='<?php echo $_POST["username"] ?>'>
            <input type="hidden" name="password" value='<?php echo $_POST["password"] ?>'>
            <br>
            <input type='submit' name='submitB'>
        </form>
    </center>
    </body>
    </html>
    <?php
}

function test_input($data)
{
    $data = trim($data);
    $data = stripslashes($data);
    $data = htmlspecialchars($data);
    return $data;
}

function getTeacherInfo($u, $p)
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

    $stmt = $conn->prepare('SELECT tID, fullname FROM teachers WHERE username = ? AND password = ?');
    $stmt->bind_param('ss', $u, $p);
    $stmt->execute();
    $result = $stmt->get_result();
    $array = array();
    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            $teacherID = $row['tID'];
            $teacherName = $row['fullname'];
            array_push($array, $teacherID);
            array_push($array, $teacherName);
        }
    }
    return $array;
}

function initStudents($id)
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

    $stmt = $conn->prepare('SELECT firstname, lastname FROM students');
    $stmt->execute();
    $result = $stmt->get_result();
    $select = "<td><select name='studentName'>";

    $text = "<tr><td>Students:</td>";
    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            $name = $row['firstname'] . ' ' . $row['lastname'];
            $select .= "<option> $name </option>";
        }
        $select .= "</select> ";

    }
    $text .= $select . "</td></tr>";
    echo $text;
    echo '<br>';

    $stmt = $conn->prepare('SELECT sID FROM teacher_subject WHERE tID = ?');
    $stmt->bind_param('i', $id);
    $stmt->execute();
    $result1 = $stmt->get_result();
    echo "<tr><td>Subjects:</td>";
    echo "<td><select name='subject'>";
    if ($result1->num_rows > 0) {
        while ($row1 = $result1->fetch_assoc()) {
            $sID = $row1['sID'];
            $stmt1 = $conn->prepare('SELECT sName FROM subjects WHERE sID = ?');
            $stmt1->bind_param('i', $sID);
            $stmt1->execute();
            $result2 = $stmt1->get_result();
            if ($result2->num_rows > 0) {
                while ($row2 = $result2->fetch_assoc()) {
                    $sub = $row2['sName'];
                    echo "<option>" . $sub . "</option>";
                }
            }
        }
    }

    echo "</select></td></tr>";
    $conn->close();

}

?>