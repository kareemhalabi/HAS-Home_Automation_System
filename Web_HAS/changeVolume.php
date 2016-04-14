<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HAS</title>
<style>
.smallBox{
	border: 2px solid blue;
	margin: auto;
}
.error {
	color: #FF0000;
}
body { text-align:center; }
.error {
	color: #FF0000;
}
</style>
<script>
function noPlayableFunction(){
	alert("No music is currently playing! First play something to be able to change volume"); 
}
function noRoomFunction(){
	alert("No music is playing now and you don't have any locations to play in yet! Click Edit Rooms to add a Location");
}
</script>
</head>
<body>
	<div class="smallBox" > 
		<h1>Adjust Volume</h1>

	</div>
	<form action="index.php" method="post" style="float: left;">
		<input type="submit" value="Go Home" />
</form>
<br>
<p>

</p>
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
		if($hm->hasRooms()==false){ //If no rooms exist, notify the user when they try to change volume because nothing can play until the system has a Room.
			showRoomError();
		}
		//If there's nothing playing, notify the user when they try to change volume
		if ($hm->getRoom_index(0)->hasPlayable()==false && ($hm->hasRoomGroups()==true && $hm->getRoomGroup_index(0)->hasPlayable()==false)){
			showPlayableError();
		}
		function showRoomError(){
			echo '<script>noRoomFunction()</script>';
			echo '<meta http-equiv="refresh" content="0;url=index.php">';
			exit();
		}
		function showPlayableError(){
			echo '<script>noPlayableFunction()</script>';
			echo '<meta http-equiv="refresh" content="0;url=index.php">';
			exit();
		}
		?>
		<form action="changeRoomVolume.php" method="post">
		<?php
		echo "Enter a new volume for the room";
		?>
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
			Volume (0-100): <input type="number" name="volume" /> <span
				class="error"> </span>
		</p>
		<p>
			<input type="submit" value="Change Room Volume" /> <span class="error"> 
						<?php
						if (isset ( $_SESSION ['errorVolume'] ) && ! empty ( $_SESSION ['errorVolume'] )) {
							echo " * " . $_SESSION ["errorVolume"];
						}
						?>
			</span>
		</p>
	</form>
	
	<form action="changeGroupVolume.php" method="post">
	<?php
		echo "Enter a new volume for the group";
		?>
	<p>For muting a group, set volume to 0.</p>
		<?php
		echo "<p>Group: <select name='groupspinner'>";
		foreach ( $hm->getRoomGroups() as $group ) {
			echo "<option>" . $group->getName() . "</option>";
		}
		echo "</select><span class='error'>";
		echo "</span></p>";
		?>
		<p>
			Volume (0-100): <input type="number" name="volume" /> <span
				class="error"> </span>
		</p>
		<p>
			<input type="submit" value="Change Group Volume" /> <span class="error"> 
						<?php
						if (isset ( $_SESSION ['errorGroupVolume'] ) && ! empty ( $_SESSION ['errorGroupVolume'] )) {
							echo " * " . $_SESSION ["errorGroupVolume"];
						}
						?>
			</span>
		</p>
	</form>
	
	
</body>
</html>
