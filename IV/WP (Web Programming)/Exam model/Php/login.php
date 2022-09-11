<?php
    $connection = new mysqli("localhost", "root", "", "exam_model");
    if ($connection->connect_error) {
        die('Could not connect: ' . $connection->connect_error);
    }

    if (isset($_POST["username"], $_POST["password"])) {
        $username = $_POST["username"];
        $password = $_POST["password"];
    
        $sql = "SELECT * FROM users WHERE username LIKE '" . $username . "' AND password LIKE '" . $password . "'";
    
        $result = $connection->query($sql);
    
        $user = NULL;
    
        if ($result->num_rows > 0) {
            $user = $result->fetch_assoc();
        }
    
        $connection->close();

        echo json_encode($user);
    }
    else {
        echo json_encode(null);
    }
?>