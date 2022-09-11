<?php 
    $connection = new mysqli("localhost", "root", "", "exam_model");
    if ($connection->connect_error) {
        die('Could not connect: ' . $connection->connect_error);
    }

    if (isset($_POST["userid"])) {
        $userId = $_POST["userid"];

        $sql = "SELECT * FROM assets WHERE userid=" . $userId;

        $result = $connection->query($sql);

        $data = array();

        if ($result->num_rows > 0) {
            $index = 0;
            while($row = $result->fetch_assoc()){
                $data[$index] = $row;
                $index += 1;
            }
        }

        $connection->close();

        echo json_encode($data);
    } 
    else {
        echo json_encode(null);
    }
?>