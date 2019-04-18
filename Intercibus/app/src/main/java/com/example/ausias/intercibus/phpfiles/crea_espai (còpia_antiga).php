<?php


 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
 
// array for JSON response
$response = array();
 
// check for required fields
//if (isset($_POST['name']) && isset($_POST['price']) && isset($_POST['description'])) {
 
	$nom = $_POST['nom'];
	$username = $_POST['username'];
	$descripcio = $_POST['descripcio'];
    $area =(int)$_POST['area'];
	//falta el tema de fotos eeh!!  EEEHHH!!!!!!
	$direccio = $_POST['direccio'];
	$ubicacio = $_POST['ubicacio'];
	$preu_base = (float)$_POST['preu_base'];
	

//COSES DE SERVEIS
		$barbacoa = (float)$_POST['barbacoa'];
		$piscina = (float)$_POST['piscina'];
		$catering = (float)$_POST['catering'];
		$neteja = (float)$_POST['neteja'];
		$barra_lliure = (float)$_POST['barra_lliure'];
		$musica_directe = (float)$_POST['musica_directe'];

	
	
 
    // include db connect class
    //comentari!!!! require_once __DIR__ . '/db_connect.php';
 
    // connecting to db
  //comentari!!!!!  $db = new DB_CONNECT();
    require_once __DIR__ . '/db_config.php'; 
 
        // Connecting to mysql database
        $con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD,DB_DATABASE) or die(mysqli_error());
 
	// insertar espai


		// mysql inserting a new row
		$result = mysqli_query($con,"INSERT INTO espai(username, nom, descripcio, area,  direccio, ubicacio, preu_base) VALUES('$username', '$nom', '$descripcio', '$area', '$direccio', '$ubicacio','$preu_base')");

$aux = mysqli_query($con,"SELECT id from espai WHERE username = '$username' AND nom='$nom'");
$fila=$aux->fetch_assoc();
$idsel=$fila['id'];


$result2 = mysqli_query($con,"INSERT INTO servei (espai_id, barbacoa, piscina, catering, neteja,  barra_lliure, musica_directe) VALUES($idsel, '$barbacoa', '$piscina', '$catering','$neteja',  '$barra_lliure', '$musica_directe')");

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
    }

 	mysqli_close($con);
/*} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}*/
?>
