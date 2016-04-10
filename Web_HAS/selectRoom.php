<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HAS</title>
<style>
.error {
	color: #FF0000;
}
div, h3, p { text-align:center; }
.error {
	color: #FF0000;
}
.boxed {
  	border: 2px solid blue;
}
</style>
</head>
<div class="boxed" > 
<h1>Manage Rooms</h1>

</div>
<body>
<form action="index.php" method="post">
		<input type="submit" value="Go Home" />
	</form>
		<?php
		// pull data from model folder
		require_once "model/Playable.php";
		require_once "model/Artist.php";
		require_once "model/Album.php";
		require_once "model/HAS.php";
		require_once "model/Song.php";
		require_once "model/Playlist.php";
		require_once "model/Room.php";
		require_once "model/RoomGroup.php";
		require_once "persistence/PersistenceHAS.php";
		
		session_start ();
		
		// Retrieve the data from the model
		$pm = new PersistenceHAS ();
		$hm = $pm->loadDataFromStore ();
		
		?>
	<form action="addRoom.php" method="post">
	<h3>Enter a New Room:</h3>
		<p>
			Name of Room: <input type="text" name="roomName" /> <span
				class="error"> </span>
		</p>
		<p>
			Volume (0-100): <input type="number" name="volume" /> <span
				class="error"> </span>
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
	
	<form action="addRoomGroup.php" method="post">
	<h3>Enter a New Room Group:</h3>
		<p>
			Name of Room Group: <input type="text" name="groupName" /> <span
				class="error"> </span>
		</p>
		<?php
		echo "<p>Room to Add: <select name='roomspinner'>";
		foreach ( $hm->getRooms () as $room ) {
			echo "<option>" . $room->getName () . "</option>";
		}
		echo "</select><span class='error'>";
		echo "</span></p>";
		?>
		<p>
			<input type='submit' value='Create Room Group' /> <span class="error">
			<?php
			if (isset ( $_SESSION ['errorRoomGroup'] ) && ! empty ( $_SESSION ['errorRoomGroup'] )) {
				echo " * " . $_SESSION ["errorRoomGroup"];
			}
			?>
			</span>
		</p>
	</form>
	
	<form action="addRoomToGroup.php" method="post">
	<h3>Add Room to Group:</h3>
		<?php 
		echo "<p>Group: <select name='groupspinner'>";
		foreach ( $hm->getRoomGroups() as $group) {
			echo "<option>" . $group->getName () . "</option>";
		}
		echo "</select><span class='error'>";
		echo "</span></p>";
		?>
		<?php
		echo "<p>Add Room to Group: <select name='roomspinner'>";
		foreach ( $hm->getRooms () as $room ) {
			echo "<option>" . $room->getName () . "</option>";
		}
		echo "</select><span class='error'>";
		echo "</span></p>";
		?>
		<p>
			<input type='submit' value='Add Room to Group' /> <span class="error">
			<?php
			if (isset ( $_SESSION ['errorRoomToGroup'] ) && ! empty ( $_SESSION ['errorRoomToGroup'] )) {
				echo " * " . $_SESSION ["errorRoomToGroup"];
			}
			?>
			</span>
		</p>
	</form>
	
</body>
</html>