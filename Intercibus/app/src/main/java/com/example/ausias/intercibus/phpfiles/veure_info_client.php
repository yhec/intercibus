<?php

$response = array();

$idReserva = (int) $_POST['idReserva'];

require_once __DIR__ . '/db_config.php'; 
 
// Connectar a la BBDD MYSQL
$con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD,DB_DATABASE) or die(mysqli_error());

// Buscar el nom de l'usuari que fa la reserva
$aux = mysqli_query($con, "SELECT usuari_id FROM reserva where id = $idReserva");

if ($row = mysqli_fetch_assoc($aux)) {
	$idUsuari = $row['usuari_id'];
	
	// Buscar les dades de l'usuari
	$consulta = mysqli_query($con, "SELECT nom, telefon, email FROM usuari where username = '".$idUsuari."'");
	
	if ($fila = mysqli_fetch_assoc($consulta)) {
		$response['nom'] = $fila['nom'];
		$response['telefon'] = $fila['telefon'];
		$response['email'] = $fila['email'];
		$response["success"] = 1;
		echo json_encode($response);
	}
	else {
		$response["success"] = 0;
		$response["message"] = "No s'ha pogut carregar la informació del client.";
	
		echo json_encode($response);
	}
}
else {
	$response["success"] = 0;
	$response["message"] = "No s'ha pogut carregar la informació del client.";
	
	echo json_encode($response);
}

mysqli_close($con);

?>