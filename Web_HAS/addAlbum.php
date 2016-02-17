<?php
require_once (__DIR__.'\controller\Controller.php');

session_start();

$_SESSION["errorAlbumName"] = "";

$c = new Controller();
try{
	$albumName = $_POST['albumName'];
	$genre = $_POST['genre'];
	$releaseDate = date('Y-m-d', strtotime($_POST['releaseDate']));
	$c->createAlbum($albumName, $genre, $releaseDate);
}catch (Exception $e){
	$_SESSION["errorAlbumName"] = $e->getMessage();
}

?>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv ="refresh" content ="0; url=/HAS/" />
	</head>
</html>