<?php

require_once (__DIR__ . '\controller\Controller.php');

session_start();
$_SESSION["errorSong"] = "";
$c = new Controller();
try{
	$song = null;
	if (isset($_POST['songspinner'])){
		$song = $_POST['songspinner'];
	}
	
	$c->deleteSong($song);
}catch(Exception $e){
	$_SESSION["errorSong"] = $e->getMessage();
}
?>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="refresh" content="0; url=/HAS/addMusic.php" />
</head>
</html>