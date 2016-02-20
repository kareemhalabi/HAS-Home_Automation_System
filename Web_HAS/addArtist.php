<?php
require_once (__DIR__.'\controller\Controller.php');

session_start();

$_SESSION["errorArtistName"] = "";
$c = new Controller();
try{
	$artist = $_POST['artistName'];
	$c->createArtist($artist);
}catch(Exception $e){
	$_SESSION["errorArtistName"] = $e->getMessage();
}
?>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="refresh" content="0; url=/HAS/" />
		</head>
</html>