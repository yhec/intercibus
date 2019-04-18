<?php
//comentari!!!session_start();
//comentari!!!! include("connection.php"); //Establishing connection with our database
 // Connecting to mysql database
  require_once __DIR__ . '/db_config.php'; 
        $con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD,DB_DATABASE) or die(mysqli_error());
 
$error = ""; //Variable for storing our errors.

/*if(isset($_POST["submit"]))
{
if(empty($_POST["username"]) || empty($_POST["password"]))
{
$error = "Both fields are required.";
}else
{*/

// Define $username and $password
$username=$_POST['username'];
$password=$_POST['password'];
 
// To protect from MySQL injection
$username = stripslashes($username);
$password = stripslashes($password);
$username = mysqli_real_escape_string($con, $username);
$password = mysqli_real_escape_string($con, $password);
//$password = md5($password);
 
//Check username and password from database
$sql="SELECT username FROM usuari WHERE username='$username' and password=password('$password')";
$result=mysqli_query($con,$sql);
$row=mysqli_fetch_array($result,MYSQLI_ASSOC);
 
//If username and password exist in our database then create a session.
//Otherwise echo error.
 
if(mysqli_num_rows($result) == 1)
{
/*comentari!!!!$_SESSION['username'] = $login_user; // Initializing Session
header("location: home.php"); // Redirecting To Other Page
*/

$response["success"] = 1;
$response["message"] = "Sessió iniciada amb èxit.";
echo json_encode($response);
}else
{

$response["success"] = 0;
$response["message"]= "Incorrect username or password.";
echo json_encode($response);
}
 
//}
//}
 
?>
