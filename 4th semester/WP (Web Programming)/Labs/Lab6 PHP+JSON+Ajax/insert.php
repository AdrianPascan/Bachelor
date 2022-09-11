<?php 
    $connection = new mysqli("localhost", "root", "", "wp");
	if ($connection->connect_error) {
		die('Could not connect: ' . $connection->connect_error);
    }
    
    $pid = $_POST["id"];
    $quantity = $_POST["quantity"];

    if ($pid == NULL || $quantity == NULL) {
        echo "Id and/or quantity not given.";
    } else {
        $sql = "SELECT * FROM products WHERE id=" . $pid;
        $result = $connection->query($sql);

        if ($result->num_rows == 0) {
            echo "Invalid id for book: " . $pid;
        } else {
            $sql = "SELECT * FROM cart WHERE pid=" . $pid;
            $result = $connection->query($sql);

            if ($result->num_rows > 0) {
                $row = $result->fetch_assoc();
                $newQuantity = $row["quantity"] + $quantity;

                $sql = "UPDATE `cart` SET `quantity`=" . $newQuantity . " WHERE `pid`=" . $pid;
                $result = $connection->query($sql);
            } else {
                $sql = "INSERT INTO `cart` (`id`, `pid`, `quantity`) VALUES (NULL, '" . $pid . "', '" . $quantity . "')";
                $result = $connection->query($sql);
            }
        }
    }  

	$connection->close();
?>