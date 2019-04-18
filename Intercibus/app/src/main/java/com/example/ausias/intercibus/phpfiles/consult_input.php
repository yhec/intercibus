<?php

$response = array();

require_once __DIR__ . '/db_config.php'; 


 
// Connectar a la BBDD MYSQL
$con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD,DB_DATABASE) or die(mysqli_error());


$sql="SELECT nom FROM servei";
$result=mysqli_query($con,$sql);

if($result){
$aux= "";
	
		$response["successLlista"] = 1;
		while ($row= mysqli_fetch_assoc($result)){
		$aux=$aux.':'.$row['nom'];

			
}
		$response["messageLlista"] = $aux; 
		echo json_encode($response);
		
	}else {
			$response["successLlista"] = 0;
		    $response["messageLlista"] = "No s'han pogut obtenir suggerements.";
		
			echo json_encode($response);

		}


mysqli_close($con);

?>
