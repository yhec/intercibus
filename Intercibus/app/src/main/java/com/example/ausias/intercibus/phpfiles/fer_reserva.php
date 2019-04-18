<?php

$response = array();


$id_espai = (int) $_POST['id_espai'];
$entrada = $_POST['entrada'];
$sortida = $_POST['sortida'];
$preuTotal = (float) $_POST['preuTotal'];
$username = $_POST['username'];
$numServeis = (int) $_POST['num_serveis'];

$idReserva = 0;

require_once __DIR__ . '/db_config.php'; 


// Connectar a la BBDD MYSQL
$con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD,DB_DATABASE) or die(mysqli_error());

$resultatInsert = mysqli_query($con, "INSERT INTO reserva (espai_id, preu_total, data_entrada, data_sortida, usuari_id) VALUES (".$id_espai.", ".$preuTotal.", '".$entrada."', '".$sortida."', '".$username."')");

if ($resultatInsert) {
	$aux = mysqli_query($con, "SELECT id FROM reserva WHERE espai_id = $id_espai AND preu_total = $preuTotal AND data_entrada = '$entrada' AND data_sortida = '$sortida' AND usuari_id = '$username'");
	if ($row = mysqli_fetch_assoc($aux)) {
		$idReserva = $row['id'];
	}

	if ($aux) {

		for ($i = 0; $i < $numServeis; $i ++) {
			$servei = $_POST['servei'.$i];
			//$preu = (float) $_POST['preu'.$i];
			$resultat = mysqli_query($con, "SELECT id FROM servei WHERE nom = '$servei'");
			if ($row = mysqli_fetch_assoc($resultat)) {
				$resultat = mysqli_query($con, "INSERT INTO reserva_servei (reserva_id, servei_id) VALUES ($idReserva, ".$row['id'].")");
			}
		}
		
		if ($resultat) {
		
			$response["success"] = 1;
			$response["message"] = "Reserva introduïda correctament.";
			echo json_encode($response);
		}
		
		else {
			$response["success"] = 0;
			$response["message"] = "No s'ha pogut reservar l'espai.";
				
			echo json_encode($response);
		}
	}
	else {
	$response["success"] = 0;
	$response["message"] = "No s'ha pogut reservar l'espai.";
		
	echo json_encode($response);
	}
}
else {
	$response["success"] = 0;
	$response["message"] = "No s'ha pogut reservar l'espai.";
		
	echo json_encode($response);
}

mysqli_close($con);

?>
