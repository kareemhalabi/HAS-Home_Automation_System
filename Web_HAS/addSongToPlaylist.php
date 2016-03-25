<?php
require_once (__DIR__ . '\controller\Controller.php');

session_start ();

$_SESSION ["errorSongToPlaylist"] = "";
$c = new Controller ();

try{
	$song = null;
	if (isset ( $_POST ['songSpinner'] )) {
		$song = $_POST ['songSpinner'];
	}
	
	$playlist = null;
	if (isset ( $_POST ['playlistSpinner'] )) {
		$playlist = $_POST ['playlistSpinner'];
	}
	$c->addSongToPlaylist($playlist, $song);
}
catch (Exception $e){
	$_SESSION ["errorSongToPlaylist"] = $e->getMessage ();
}
?>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="refresh" content="0; url=/HAS/playlistView.php" />
</head>
</html>
