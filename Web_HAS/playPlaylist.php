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
		
		$playlist = NULL;
		if (isset ( $_POST ['playlistspinner'] )) {
			$playlist = $_POST ['playlistspinner'];
		}
		
		$myPlaylist = NULL;
		foreach ( $hm->getPlaylists () as $tempPlaylist ) {
			if (strcmp ( $tempPlaylist->getName (), $playlist ) == 0) {
				$myPlaylist = $tempPlaylist;
				break;
			}
		}
		$name = $myPlaylist->getName();
		?>
		
		<form action="playPlaylistRoom.php" method="post">
		<?php
		echo "Which room or group of rooms would you like to play the playlist: {$name}";
		?>
		<?php 
		echo "<p>Room: <select name='roomspinner'>";
		foreach($hm->getRooms()as$room){
			echo "<option>" . $room->getName() . "</option>";
		}
		echo "</select><span class='error'>";
		echo "</span></p>";
		?>
		<input type="submit" value="Play in Room"/><span class="error">
		<?php 
		if(isset($_SESSION['errorRoom'])&&! empty ( $_SESSION ['errorRoom'] )) {
				echo " * " . $_SESSION ["errorRoom"];                            
			}
		?>
		</span>
		</form>
		
		
	<form action="index.php" method="post">
		<input type="submit" value="Home" />
	</form>