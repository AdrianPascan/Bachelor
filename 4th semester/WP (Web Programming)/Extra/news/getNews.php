<?php 
    $connection = new mysqli("localhost", "root", "", "news");
    if ($connection->connect_error) {
        die('Could not connect: ' . $connection->connect_error);
    }

    $earliestDate = $_POST["earliestDate"];
    $latestDate = $_POST["latestDate"];
    $cathegory = $_POST["cathegory"];

    $criteria = false;

    $sql = "SELECT n.id, n.text, n.title, n.date, n.cathegory, p.id as producerid, p.name as producer FROM news n INNER JOIN producers p ON n.producerid = p.id";
    if (!empty($earliestDate)) {
        $criteria = true;
        $sql .= " WHERE n.date >= '" . $earliestDate . "'";
    }
    if (!empty($latestDate)) {
        if ($criteria) {
            $sql .= " AND n.date <= '" . $latestDate . "'";
        } else {
            $sql .= " WHERE n.date <= '" . $latestDate . "'"; 
        }
    }
    if (!empty($cathegory)) {
        if ($criteria) {
            $sql .= " AND cathegory LIKE '" . $cathegory . "'";
        } else {
            $sql .= " WHERE cathegory LIKE '" . $cathegory . "'";
        }
    }

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