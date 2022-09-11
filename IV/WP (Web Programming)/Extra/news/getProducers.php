<?php 
    $connection = new mysqli("localhost", "root", "", "news");
    if ($connection->connect_error) {
        die('Could not connect: ' . $connection->connect_error);
    }

    $sql = "SELECT id, name FROM producers";

    $result = $connection->query($sql);

    $data = array();

    if ($result->num_rows > 0) {
        while($row = $result->fetch_assoc()){
            $data[$row["id"]] = $row;
        }
    }

    $connection->close();

    echo json_encode($data);
?>