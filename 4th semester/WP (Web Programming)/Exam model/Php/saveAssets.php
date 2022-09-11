<?php 
    $connection = new mysqli("localhost", "root", "", "exam_model");
    if ($connection->connect_error) {
        die('Could not connect: ' . $connection->connect_error);
    }

    if (isset($_POST["userid"], $_POST["assets"])) {
        $userId = $_POST["userid"];
        $assets = $_POST["assets"];

        $sql = "INSERT INTO assets(userid, name, description, value) VALUES ";
        foreach ($assets as $index => $asset) {
            $sql = $sql . "(" . $userId . ", '" . $asset["name"] . "', '" . $asset["description"] . "', " . $asset["value"] . "),";
        }
        $sql = substr($sql, 0, -1);

        $result = $connection->query($sql);

        $connection->close();

        echo json_encode(true);
    } 
    else {
        echo json_encode(false);
    }
?>