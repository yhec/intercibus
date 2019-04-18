<?php

$response = array();

$id = (int) $_POST['username'];

require_once __DIR__ . '/db_config.php'; 
 
// Connectar a la BBDD MYSQL
$con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD,DB_DATABASE) or die(mysqli_error());

// Recollim espais usuari
$auxreserves = mysqli_query($con, "SELECT * FROM reserva where usuari_id = $username");

// variable per comptar els espais
$i = 0;
while ($rowreserves = mysqli_fetch_assoc($auxreserves)) {
	$consultaesp = mysqli_query($con, "SELECT nom FROM espai WHERE id = ".$rowreserves['espai_id']);
	if ($filaesp = mysqli_fetch_assoc($consultaesp)) {
		$response["nom".$i] = $filaesp['nom'];
		$response["reserva".$i] = $rowreserves['id'];
		$response["entrada".$i] = $rowreserves['data_entrada'];
		$response["sortida".$i] = $rowreserves['data_sortida'];
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
