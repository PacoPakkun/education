<?php

echo "<!DOCTYPE html>
<html lang=\"en\">
<head>
    <meta charset=\"UTF-8\">
    <title>Trains</title>

    <style>
        table{
            border: 1px solid black;
        }
        td{
            width: 150px; 
            text-align: center;
        }
    </style>
</head>
<body><center>";

$start = $stop = '';
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $start = test_input($_POST["startL"]);
    $stop = test_input($_POST["endL"]);
}

function test_input($data) {
    $data = trim($data);
    $data = stripslashes($data);
    $data = htmlspecialchars($data);
    return $data;
}

getTrains($start, $stop);
echo "</center></body>
</html>";

function getTrains($from, $to)
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

    if (isset($_POST['direct'])) {
        $stmt = $conn->prepare('SELECT * FROM trains WHERE start = ? AND stop = ?');
        $stmt->bind_param('ss', $from, $to);
        $stmt->execute();
        $result = $stmt->get_result();
        if ($result->num_rows > 0) {
            echo "Direct trains from <strong>$from</strong> to <strong>$to</strong> : <br><br>";
            $table = "<table border='1px'> <tr><th>Train Number</th> <th> Start</th> 
                <th>Stop</th> <th>Departure Hour</th> <th>Arriving Hour</th> </tr>";

            while ($row = $result->fetch_assoc()) {
                $table .= "<tr> <td>" . $row["trainNo"] . "</td>"
                    . "<td>" . $row["start"] . " </td>" . "<td>" . $row["stop"] . " </td>"
                    . "<td>" . $row["dHour"] . " </td>" . "<td>" . $row["aHour"] . " </td></tr>";
            }
            $table .= "</table>";
            echo $table;
        } else {
            echo "There is no direct train for this line.";
        }
    }
    echo "<br>";

    if (isset($_POST['inter'])) {

        $stmt = $conn->prepare('SELECT T1.trainNo, T1.start,T1.stop,T1.dHour,T1.aHour,T2.stop as stop2,T2.dHour as d, T2.aHour as a FROM trains T1 INNER JOIN trains T2 ON T1.stop = t2.start WHERE t1.start = ? AND t2.stop = ?');
        $stmt->bind_param('ss', $from, $to);
        $stmt->execute();

        $result1 = $stmt->get_result();
        if ($result1->num_rows > 0) {
            echo "Inter trains from <strong>$from</strong> to <strong>$to</strong> : <br><br>";
            $i = 1;
            $table = '<table border="1px"><tr><th>Line</th><th>First train</th><th>Second train</th></tr>';
            while ($row1 = $result1->fetch_assoc()) {
                $table .= '<tr><th>' . $i . '</th><td>' . $row1['start'] . ' - ' . $row1['stop'] . '<br>' . $row1['dHour'] . ' - ' . $row1['aHour']
                    . '</td>' . '<td>' . $row1['stop'] . ' - ' . $row1['stop2'] . '<br>' . $row1['d'] . ' - ' . $row1['a']
                    . '</td></tr>';
                $i += 1;
            }
            $table .= '</table>';
            echo $table;
        } else {
            echo "There are no inter trains for this line.";
        }
    }
    $conn->close();
}

?>