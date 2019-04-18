<?php

$response = array();


$provincia = $_POST['provincia'];
$num_serveis = (int) $_POST['num_serveis'];

require_once __DIR__ . '/db_config.php'; 


 
// Connectar a la BBDD MYSQL
$con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD,DB_DATABASE) or die(mysqli_error());


$idesserveis='';	
$resultquery='';

if ($num_serveis == 0){

	$auxi = mysqli_query($con, "SELECT * FROM espai where ubicacio = '$provincia'");

	while ($eo = mysqli_fetch_assoc($auxi)){
	

			$resultquery=$resultquery.":".$eo['id'];
			$resultquery=$resultquery.":".$eo['username'];
			$resultquery=$resultquery.":".$eo['nom'];
			$resultquery=$resultquery.":".$eo['descripcio'];
			$resultquery=$resultquery.":".$eo['area'];
			$resultquery=$resultquery.":".$eo['direccio'];
			$resultquery=$resultquery.":".$eo['ubicacio'];
			$resultquery=$resultquery.":".$eo['preu_base'];


			$id = $eo['id']; 
			$consfoto = mysqli_query($con, "SELECT foto FROM fotos_espai WHERE espai_id in ($id)");
			if ($fila = mysqli_fetch_assoc($consfoto)){
	
			$resultquery=$resultquery.":".$fila['foto'];
		}


}


}else{

	for ($i = 0; $i < $num_serveis; $i ++) {

		$aux ="servei".$i;

		$nomservei = $_POST[$aux];

		$aux = mysqli_query($con, "SELECT id FROM servei WHERE nom = '$nomservei'");
		$auxi=$aux->fetch_assoc();
	
		if ($i!=$num_serveis-1){
			$idesserveis=$idesserveis.$auxi['id'].", ";	
		}
		else {
			$idesserveis=$idesserveis.$auxi['id'];			
		}
	}
			
	/*$response["messageResultat"] = "SELECT id_espai FROM servei_espai WHERE id_servei in ($idesserveis) group by id_espai having count(*)=$num_serveis"; 
		echo json_encode($response);*/



$idEspai = '';
//$primer = TRUE;

$aux = mysqli_query($con, "SELECT id_espai FROM servei_espai WHERE id_servei in ($idesserveis) group by id_espai having count(*)=$num_serveis");
		
while ($row = mysqli_fetch_assoc($aux)){
	
	$auxi = mysqli_query($con, "SELECT * FROM espai WHERE id = $row[id_espai] and ubicacio = '$provincia'");
	$eo=$auxi->fetch_assoc();
	
	// fetch_assoc devuelve null si no obtiene ninguna fila, por lo tanto, no concatenamos si no hay resultado!
	if ($eo != null) {
		$resultquery=$resultquery.":".$eo['id'];
		$resultquery=$resultquery.":".$eo['username'];
		$resultquery=$resultquery.":".$eo['nom'];
		$resultquery=$resultquery.":".$eo['descripcio'];
		$resultquery=$resultquery.":".$eo['area'];
		$resultquery=$resultquery.":".$eo['direccio'];
		$resultquery=$resultquery.":".$eo['ubicacio'];
		$resultquery=$resultquery.":".$eo['preu_base'];


		$id = $eo['id']; 
		$consfoto = mysqli_query($con, "SELECT foto FROM fotos_espai WHERE espai_id in ($id)");
		if ($fila = mysqli_fetch_assoc($consfoto)){
	
			$resultquery=$resultquery.":".$fila['foto'];
		}

		/* Concatenar los id de los espacios para sacar todas las fotos
		if ($primer===TRUE) {
			$primer = FALSE;
			$idEspai = $eo['id'];
		}
		else {
				$idEspai = $idEspai.", ".$eo['id'];
		}*/
	}
	




	// Trozo consulta para sacar foto del espacio
	//$consfoto = mysqli_query($con, "SELECT foto FROM fotos_espai WHERE espai_id = $eo['id']");
	//$resfoto=$consfoto->fetch_assoc();
	//$resultquery=$resultquery.":".$resfoto['foto'];

}

/*

Seleccionem les fotos dels espais
$sortidafoto = '';
$primer = TRUE;
$consfoto = mysqli_query($con, "SELECT foto FROM fotos_espai WHERE espai_id in ($idEspai)");
while ($row = mysqli_fetch_assoc($consfoto)){
	
	if ($primer===TRUE) {
		$sortidafoto = $row['foto'];
		$primer = FALSE;
	}
	else {
		$sortidafoto = $sortidafoto.":".$row['foto'];
	}
}


if($consfoto){*/

}
if($auxi){


	$response["successResultat"] = 1;
		
	$response["messageResultat"] = $resultquery; 
	//$response["fotoResultat"] = $sortidafoto;
	echo json_encode($response);
		
}
else {
	$response["successResultat"] = 0;
	$response["messageResultat"] = "No s'han pogut obtenir suggerements.";
	//$response["fotoResultat"] = "No s'han pogut buscar les fotografies.";
	
	echo json_encode($response);
}

mysqli_close($con);

?>
