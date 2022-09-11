<?php 
    $connection = new mysqli("localhost", "root", "", "news");
    if ($connection->connect_error) {
        die('Could not connect: ' . $connection->connect_error);
    }

    if (isset($_POST["id"], $_POST["title"], $_POST["text"], $_POST["producerid"], $_POST["date"], $_POST["cathegory"])) {
        $id = $_POST["id"];
        $title = $_POST["title"];
        $text = $_POST["text"];
        $producerid = $_POST["producerid"];
        $date = $_POST["date"];
        $cathegory = $_POST["cathegory"];

        $sql = "UPDATE news SET " . 
                    "title='" . $title . "', " .
                    "text='" . $text . "', " .
                    "producerid=" . $producerid . ", " .
                    "date='" . $date . "', " .
                    "cathegory='" . $cathegory . "' " .
                    "WHERE id=" . $id;

        $connection->query($sql);

        $connection->close();

        echo json_encode(true);
    }
    else {
        echo json_encode(false);
    }
?>