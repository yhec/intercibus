<?php

/*
 * El següent codi crea un espai amb els seus extres
 * Tota la informació es llegeix mitjançant un HTTP Post REQUEST
 */
 
// array per respondre al JSON
$response = array();
 
// check for required fields
//if (isset($_POST['name']) && isset($_POST['price']) && isset($_POST['description'])) {
 
	$nom = $_POST['nom'];
	$username = $_POST['username'];
	$descripcio = $_POST['descripcio'];
    $area =(int)$_POST['area'];
	$foto = $_POST['foto'];
	$direccio = $_POST['direccio'];
	$ubicacio = $_POST['ubicacio'];
	$preu_base = (float)$_POST['preu_base'];
	$num_extres = (int) $_POST['num_extres'];

		//COSES DE SERVEIS
/*		$barbacoa = (float)$_POST['barbacoa'];
		$piscina = (float)$_POST['piscina'];
		$catering = (float)$_POST['catering'];
		$neteja = (float)$_POST['neteja'];
		$barra_lliure = (float)$_POST['barra_lliure'];
		$musica_directe = (float)$_POST['musica_directe'];*/

	
	
 
	// include db connect class
	//comentari!!!! require_once __DIR__ . '/db_connect.php';
 
	// connecting to db
	//comentari!!!!!  $db = new DB_CONNECT();
	require_once __DIR__ . '/db_config.php'; 
 
	// Connectar a la BBDD MYSQL
	$con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD,DB_DATABASE) or die(mysqli_error());
 
	// Insertar espai


	// MYSQL insertar fila espai
	$result = mysqli_query($con,"INSERT INTO espai(username, nom, descripcio, area, direccio, ubicacio, preu_base) VALUES('$username', '$nom', '$descripcio', '$area', '$direccio', '$ubicacio','$preu_base')");


$result2 = mysqli_query($con,"INSERT INTO fotos_espai(foto, espai_id, portada) VALUES('$foto',(select id from espai where nom='$nom'),1)");

	// Espai insertat correctament
	if ($result && $result2) {
		$aux = mysqli_query($con,"SELECT id from espai WHERE username = '$username' AND nom='$nom'");
		$fila=$aux->fetch_assoc();
		$idEspai=$fila['id'];
		

		for ($i = 0; $i < $num_extres; $i ++) {

				$aux = "extra".$i;

				$nomExtra = $_POST[$aux];

				$aux = "preu".$i;
				$preuExtra = (float)$_POST[$aux];
				
				
				$aux = mysqli_query($con, "SELECT id FROM servei WHERE nom = '$nomExtra'");
				$fila = $aux->fetch_assoc();
				$idExtra = $fila['id'];
				// l'extra no està creat
				if ($idExtra==NULL) {
					$aux = mysqli_query($con, "INSERT INTO servei (nom) VALUES ('$nomExtra')");
					$aux = mysqli_query($con, "SELECT id FROM servei WHERE nom = '$nomExtra'");
					
					$fila = $aux->fetch_assoc();
					$idExtra = $fila['id'];
				
					
				}
				// Inserim l'extra de l'espai amb el seu preu
				$aux = mysqli_query($con, "INSERT INTO servei_espai (id_espai, id_servei, preu) VALUES ('$idEspai', '$idExtra', '$preuExtra')");
					
		}
	}
	
	

	if($aux){
	
		$response["success"] = 1;
		$response["message"] = "Espai creat correctament.";
	
		echo json_encode($response);
	 }else {
			$response["success"] = 0;
		    $response["message"] = "Error insertant l'espai";
		
			echo json_encode($response);

		}


	
	/*$result2 = mysqli_query($con,"INSERT INTO servei (espai_id, barbacoa, piscina, catering, neteja,  barra_lliure, musica_directe) VALUES($idEspai, '$barbacoa', '$piscina', '$catering','$neteja',  '$barra_lliure', '$musica_directe')");

	//echo json_encode($idsel);


    // check if row inserted or not
    if ($result&&$result2) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Espai creat correctament.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;

        $response["message"] = "Oops! An error occurred.";
 
        // echoing JSON response
        echo json_encode($response);
    }*/

 	mysqli_close($con);
/*} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}*/
?>
