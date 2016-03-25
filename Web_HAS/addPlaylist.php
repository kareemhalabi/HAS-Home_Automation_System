<?php
//KNOWN BUG: CANNOT CREATE A PLAYLIST WITH A SONG THAT IS ALREADY IN A PLAYLIST!!WTF!!

require_once (__DIR__ . '\controller\Controller.php');

session_start ();
$_SESSION ["errorPlaylistName"] = "";
$c = new Controller ();
try{
	$song = null;
	if (isset ( $_POST ['songSpinner'] )) {
			$song = $_POST ['songSpinner'];
	}
	$playlistName = $_POST['playlistName'];
	$c->createPlaylist($playlistName, $song);
}
catch (Exception $e){
	$_SESSION ["errorPlaylistName"] = $e->getMessage ();
}
?>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="refresh" content="0; url=/HAS/playlistView.php" />
</head>
</html>
