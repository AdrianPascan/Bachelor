<?php 
    $connection = new mysqli("localhost", "root", "", "news");
    if ($connection->connect_error) {
        die('Could not connect: ' . $connection->connect_error);
    }

    if (isset($_POST["title"], $_POST["text"], $_POST["producerid"], $_POST["date"], $_POST["cathegory"])) {
        $title = $_POST["title"];
        $text = $_POST["text"];
        $producerid = $_POST["producerid"];
        $date = $_POST["date"];
        $cathegory = $_POST["cathegory"];

        $sql = "INSERT INTO news(title, text, producerid, date, cathegory) VALUES (" .
                    "'" . $title . "', " .
                    "'" . $text . "', " .
                    $producerid . "," .
                    "'" . $date . "', " .
                    "'" . $cathegory . "')";

        $connection->query($sql);

        $connection->close();

        echo json_encode(true);
    }
    else {
        echo json_encode(false);
    }
?>