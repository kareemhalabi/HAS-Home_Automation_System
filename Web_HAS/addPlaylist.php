<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HAS</title>
<style>
.error {
	color: #FF0000;
}
</style>
</head>
<body>
		<?php
		// pull data from model folder
		require_once "model/Artist.php";
		require_once "model/Album.php";
		require_once "model/HAS.php";
		require_once "model/Song.php";
		require_once "model/Playlist.php";
		require_once "model/Room.php";
		require_once "persistence/PersistenceHAS.php";
		
		session_start ();
		
		// Retrieve the data from the model
		$pm = new PersistenceHAS ();
		$hm = $pm->loadDataFromStore ();
		
		?>
		//Select name for playlist
		
			<form action ="addSongToPlaylist.php" method ="post">
		<p>
			Artist: <input type ="text" name="artistName" /> <span class="error">	
			
			</form>



<?php
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
<meta http-equiv="refresh" content="0; url=/HAS/selectRoom.php" />
</head>
</html>
