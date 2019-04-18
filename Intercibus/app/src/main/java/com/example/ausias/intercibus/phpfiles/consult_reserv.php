<?php

$response = array();


$id_espai = $_POST['id_espai'];


require_once __DIR__ . '/db_config.php'; 


 
// Connectar a la BBDD MYSQL
$con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD,DB_DATABASE) or die(mysqli_error());

$aux = mysqli_query($con, "SELECT data_entrada, data_sortida FROM reserva WHERE espai_id = $id_espai");

$result = "";

$primer = 1;
while ($row= mysqli_fetch_assoc($aux)){
		if ($primer == 1) {
			$result=$row['data_entrada'];
			$primer = 0;
		}
		else
			$result = $result.'#'.$row['data_entrada'];
		
		$result=$result.'#'.$row['data_sortida'];
}

if($aux){
	$response["success"] = 1;
	// Si primer es 0, és que s'ha trobat, com a mínim, una reserva
	if ($primer == 0) {
	    $response["message"] = $result;
	}
	else {
	    $response["message"] = "No hi han reserves.";
	}

	echo json_encode($response);
		
}
else {
	$response["success"] = 0;
	$response["message"] = "No s'han pogut obtenir les reserves.";
		
	echo json_encode($response);
}

mysqli_close($con);

?>
