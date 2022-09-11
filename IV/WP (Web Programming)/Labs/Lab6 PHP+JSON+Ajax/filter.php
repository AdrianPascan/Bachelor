<?php 
    $connection = new mysqli("localhost", "root", "", "wp");
	if ($connection->connect_error) {
		die('Could not connect: ' . $connection->connect_error);
	}

    $cathegory = $_POST["cathegory"];

	$sql = "SELECT * FROM products";
	if ($cathegory != NULL) {
		$sql .= " WHERE cathegory like '" . $cathegory . "'";
	}

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
?>