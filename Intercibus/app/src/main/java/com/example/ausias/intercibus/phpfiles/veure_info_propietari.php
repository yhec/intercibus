<?php

$response = array();

$idReserva = (int) $_POST['idReserva'];

require_once __DIR__ . '/db_config.php'; 
 
// Connectar a la BBDD MYSQL
$con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD,DB_DATABASE) or die(mysqli_error());

// Buscar l'id de l'espai
$aux = mysqli_query($con, "SELECT espai_id FROM reserva where id = $idReserva");

if ($row = mysqli_fetch_assoc($aux)) {
	$idEspai = $row['espai_id'];
	
	// Buscar el propietari d'aquell espai
	$consulta = mysqli_query($con, "SELECT username FROM espai where id = $idEspai");
	if ($fila = mysqli_fetch_assoc($consulta)) {
		$username = $fila['username'];
		
		// Buscar les dades de l'usuari
		$consulta = mysqli_query($con, "SELECT nom, telefon, email FROM usuari where username = '".$username."'");
		if ($resultat = mysqli_fetch_assoc($consulta)) {
			$response['nom'] = $resultat['nom'];
			$response['telefon'] = $resultat['telefon'];
			$response['email'] = $resultat['email'];
			$response["success"] = 1;
			
			echo json_encode($response);
		}
		else {
			$response["success"] = 0;
			$response["message"] = "No s'ha pogut carregar la informació del propietari.";
		
			echo json_encode($response);
		}
	}
	else {
		$response["success"] = 0;
		$response["message"] = "No s'ha pogut carregar la informació del propietari.";
		
		echo json_encode($response);
	}
}
else {
	$response["success"] = 0;
	$response["message"] = "No s'ha pogut carregar la informació del propietari.";
		
	echo json_encode($response);
}

mysqli_close($con);

?>