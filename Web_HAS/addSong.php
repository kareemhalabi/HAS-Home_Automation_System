<?php
require_once (__DIR__.'\controller\Controller.php');

session_start();

$_SESSION["errorSongName"] = "";
$c = new controller();
try{
	$album = NULL;
	if (isset($_POST['albumspinner'])){
		$album = $_POST['albumspinner'];
	}
	$songName = $_POST['songName'];
	$duration = $_POST['duration'];
	$position = $_POST['position'];
	$c -> createSong($songName, $duration, $position, $album);
}catch (Exception $e){
	$_SESSION["errorSongName"] = $e->getMessage();
}
?>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv ="refresh" content ="0; url=/HAS/" />
	</head>
</html>