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
	<form action="addRoom.php" method="post">
		<p>
			Name of Room: <input type="text" name="roomName" /> <span
				class="error"> </span>
		</p>
		<p>
			Volume (0-100): <input type="number" name="volume" /> <span class="error">
			</span>
		</p>
		<p>
			<input type='submit' value='Add Room' /> <span class="error">
			<?php
			if (isset ( $_SESSION ['errorRoomName'] ) && ! empty ( $_SESSION ['errorRoomName'] )) {
				echo " * " . $_SESSION ["errorRoomName"];
			}
			?>
			</span>
		</p>
	</form>
	<form action="changeVolume.php" method="post">
		<p>For muting a room, set volume to 0.</p>
		<?php
		echo "<p>Room: <select name='roomspinner'>";
		foreach ( $hm->getRooms () as $room ) {
			echo "<option>" . $room->getName () . "</option>";
		}
		echo "</select><span class='error'>";
		echo "</span></p>";
		?>
		<p>
			Volume (0-100): <input type="number" name="volume" /> <span class="error">

			</span>
		</p>
		<p>
			<input type="submit" value="Change Volume" /> <span class="error"> 
						<?php
			if (isset ( $_SESSION ['errorVolume'] ) && ! empty ( $_SESSION ['errorVolume'] )) {
				echo " * " . $_SESSION ["errorVolume"];
			}
			?>
			</span>
		</p>
	</form>

	<form action="index.php" method="post">
		<input type="submit" value="Home" />
	</form>
</body>
</html>