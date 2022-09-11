<?php 
    $connection = new mysqli("localhost", "root", "", "wp");
	if ($connection->connect_error) {
		die('Could not connect: ' . $connection->connect_error);
	}

	$sql = "SELECT * FROM cart";

	$result = $connection->query($sql);

	if ($result->num_rows > 0) {
        $text = "<p><i>YOUR ITEMS:</i><br>";
        
		while($row = $result->fetch_assoc()){
			$text .= "<br>Book " . $row["pid"] . " -> " . $row["quantity"]; 
        }
        
        $text .= "</p>";

        echo $text;
	} else {
        echo "<p><i>Your cart is empty...</i></p>";
    }

	$connection->close();
?>