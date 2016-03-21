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
	<form action="addMusic.php" method="post">
		<input type="submit" value="Add Music" />
		<p>
		
		</p>
	</form>

	<form action="selectRoom.php" method="post">
		<input type="submit" value="Select Room(s)" />
	</form>
</body>
</html>