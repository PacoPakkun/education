<!DOCTYPE HTML>
<html>
<head>
    <title>EX 5</title>
</head>
<body>
    <center>
        <h2>Account login</h2>
        <br>
        <form method="post">
            <table cellpadding="10">
                <tr>
                    <td>
                        Username:
                    </td>
                    <td>
                        <input type="text" name="username">
                    </td>
                </tr>
                <tr>
                    <td>
                        Password:
                    </td>
                    <td>
                        <input type="text" name="password">
                    </td>
                </tr>
            </table>
            <input type="submit" name="submit" value="Login">
        </form>
    </center>
    <?php
    if (isset($_POST['username']) && isset($_POST['password']) and isset($_POST['submit'])) {
        $user = $_POST['username'];
        $pass = $_POST['password'];

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

        $stmt = $conn->prepare("SELECT * FROM users WHERE username = ? and password = ?");
        $stmt->bind_param('ss', $user, $pass);
        $stmt->execute();
        $result = $stmt->get_result();

        if ($result->num_rows > 0) {
            setcookie("username", $_POST['username']);
            setcookie("password", $_POST['password']);
            header('Location:uploadPhoto.php');
        } else {
            echo "<p>wrong credentials! </p><br>";
        }
    }
    ?>
</body>
</html>