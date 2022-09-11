<?php 
    $connection = new mysqli("localhost", "root", "", "wp");
	if ($connection->connect_error) {
		die('Could not connect: ' . $connection->connect_error);
    }
    
    $pid = $_POST["id"];

    if ($pid == NULL) {
        echo "Id not given.";
    } else {
        $sql = "SELECT * FROM cart WHERE pid=" . $pid;
        $result = $connection->query($sql);

        if ($result->num_rows == 0) {
            echo "Your cart does not have a book with id: " . $pid;
        } else {
            $sql = "DELETE FROM `cart` WHERE `cart`.`pid`=" . $pid;
            $result = $connection->query($sql);
        }
    }  

	$connection->close();
?>