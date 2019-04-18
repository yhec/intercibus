<?php

$response = array();

$id = (int) $_POST['id'];

require_once __DIR__ . '/db_config.php'; 
 
// Connectar a la BBDD MYSQL
$con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD,DB_DATABASE) or die(mysqli_error());

// Recollim els serveis amb el seu preu
$aux = mysqli_query($con, "SELECT id_servei, preu FROM servei_espai where id_espai = $id");

// variable per comptar els serveis
$i = 0;
while ($row = mysqli_fetch_assoc($aux)) {
	$consulta = mysqli_query($con, "SELECT nom FROM servei WHERE id = ".$row['id_servei']);
	if ($filaNom = mysqli_fetch_assoc($consulta)) {
		$response["servei".$i] = $filaNom['nom'];
	}
	$response["preu".$i] = $row['preu'];
	$i = $i + 1;
}

/* Recollim la imatge de portada */
if ($aux) {
	$response["num_serveis"] = $i;
	$aux = mysqli_query($con, "SELECT foto FROM fotos_espai where espai_id = $id AND portada = 1");
	if ($row = mysqli_fetch_assoc($aux)) {
		$response["foto"] = $row['foto'];
	}
}

if($aux){
	$response["success"] = 1;
	
	echo json_encode($response);
		
}
else {
	$response["success"] = 0;
	$response["message"] = "No s'ha pogut carregar l'espai.";
	
	echo json_encode($response);
}

mysqli_close($con);

?>