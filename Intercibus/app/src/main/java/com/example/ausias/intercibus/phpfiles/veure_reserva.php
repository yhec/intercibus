<?php

$response = array();

$id = (int) $_POST['reserva_id'];

require_once __DIR__ . '/db_config.php'; 
 
// Connectar a la BBDD MYSQL
$con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD,DB_DATABASE) or die(mysqli_error());

// Recollim una reserva en concret
$auxreserva = mysqli_query($con, "SELECT * FROM reserva where id = $id");
if ($reserva = mysqli_fetch_assoc($auxreserva)) {
		//$response["reserva"] = $reserva['id'];
		$response["preutotal"] = $reserva['preu_total'];
		$response["entrada"] = $reserva['data_entrada'];
		$response["sortida"] = $reserva['data_sortida'];

	$auxespai = mysqli_query($con, "SELECT * FROM espai where id = ".$reserva['espai_id']);
	if ($espai = mysqli_fetch_assoc($auxespai)) {
		$response["espai"] = $espai['nom'];
		
		$idf = $espai['id']; 
		$consfoto = mysqli_query($con, "SELECT foto FROM fotos_espai WHERE espai_id = $idf");
		if ($fila = mysqli_fetch_assoc($consfoto)){
			$response["foto"] = $fila['foto'];
		}
	}

	$auxserveis =  mysqli_query($con, "SELECT servei_id FROM reserva_servei WHERE reserva_id = $id");
	$i = 0;
	while ($rowserveis = mysqli_fetch_assoc($auxserveis)) {
		$consultaservei = mysqli_query($con, "SELECT nom FROM servei WHERE id = ".$rowserveis['servei_id']);
		if ($filaservei = mysqli_fetch_assoc($consultaservei)) {
			//$response["id".$i] = $filaservei['id'];
			$response["nom".$i] = $filaservei['nom'];
			//$response["preu".$i] = $rowserveis['preu'];
		}
		$preuServei = mysqli_query($con, "SELECT preu FROM servei_espai WHERE id_espai = $idf AND id_servei = ".$rowserveis['servei_id']);
		if ($filaservei = mysqli_fetch_assoc($preuServei)) {$response["hol"]="hola";
			$response["preu".$i] = $filaservei['preu'];
			$i = $i + 1;
		}
	}	
}


if($auxserveis || $i == 0){
	$response["success"] = 1;
	$response["num_serveis"]= $i;
	
	echo json_encode($response);
		
}
else {
	$response["success"] = 0;
	$response["message"] = "No s'ha pogut carregar la reserva.";
	
	echo json_encode($response);
}
mysqli_close($con);

?>
