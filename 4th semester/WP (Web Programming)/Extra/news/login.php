<?php
    $connection = new mysqli("localhost", "root", "", "news");
    if ($connection->connect_error) {
        die('Could not connect: ' . $connection->connect_error);
    }

    if (isset($_POST["name"], $_POST["password"])) {
        $name = $_POST["name"];
        $password = $_POST["password"];
    
        $sql = "SELECT * FROM producers WHERE name LIKE '" . $name . "' AND password LIKE '" . $password . "'";
    
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