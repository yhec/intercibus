<?php


 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
 
// array for JSON response
$response = array();
 
// check for required fields
//if (isset($_POST['name']) && isset($_POST['price']) && isset($_POST['description'])) {
 	$tipo_user = (int)$_POST['tipo_user'];
	$username = $_POST['username'];
	$password = $_POST['password'];
    $nom = $_POST['nom'];
	$telefon = $_POST['telefon'];
	$email = $_POST['email'];

	
	$alta = (int)$_POST['alta'];
	if($tipo_user){

					$nif = $_POST['nif'];
	}else{

   			 $cognom_1 = $_POST['cognom_1'];
   			 $cognom_2 = $_POST['cognom_2'];

}
	
 
    // include db connect class
    //comentari!!!! require_once __DIR__ . '/db_connect.php';
 
    // connecting to db
  //comentari!!!!!  $db = new DB_CONNECT();
    require_once __DIR__ . '/db_config.php'; 
 
        // Connecting to mysql database
        $con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD,DB_DATABASE) or die(mysqli_error());
 
	// insertar usuari empresa
	if($tipo_user){
		// mysql inserting a new row
		$result = mysqli_query($con,"INSERT INTO usuari(username, password, nom, telefon, email,  data_crea, nif, alta, tipo_user) VALUES('$username', password('$password'), '$nom', '$telefon', '$email',  now(),  '$nif', $alta, $tipo_user)");

	}else{
	// insertar usuari particular
	$result = mysqli_query($con,"INSERT INTO usuari(username, password, nom, cognom_1, cognom_2, telefon, email, data_crea, alta, tipo_user) VALUES('$username', password('$password'), '$nom', '$cognom_1', '$cognom_2', '$telefon', '$email', now(), $alta, $tipo_user)");
	}
 
    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Usuari creat correctament.";
 
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
