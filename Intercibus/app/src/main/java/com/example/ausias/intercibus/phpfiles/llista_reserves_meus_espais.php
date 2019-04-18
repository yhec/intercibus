<?php

$response = array();

$id = (int) $_POST['username'];

require_once __DIR__ . '/db_config.php'; 
 
// Connectar a la BBDD MYSQL
$con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD,DB_DATABASE) or die(mysqli_error());

// Recollim espais usuari
$auxespais = mysqli_query($con, "SELECT id, nom FROM espai where username = $username");

// variable per comptar els espais
$i = 0;
while ($rowespais = mysqli_fetch_assoc($auxespais)) {
	$consultareserv = mysqli_query($con, "SELECT * FROM reserva WHERE espai_id = ".$rowespais['id']);
	if ($filareserv = mysqli_fetch_assoc($consultareserv)) {
		$response["nom".$i] = $rowespais['nom'];
		$response["reserva".$i] = $filareserv['id'];
		$response["entrada".$i] = $filareserv['data_entrada'];
		$response["sortida".$i] = $filareserv['data_sortida'];
		$i = $i + 1;
	}
	
}


if($auxespais){
	$response["success"] = 1;
	$respone["num_reserves"]= $i;
	
	echo json_encode($response);
		
}
else {
	$response["success"] = 0;
	$response["message"] = "No s'ha pogut carregar l'espai.";
	
	echo json_encode($response);
}
mysqli_close($con);

?>
