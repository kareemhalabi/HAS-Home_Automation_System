<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HAS</title>
<style>
.error {
	color: #FF0000;
}
.boxed{
	border: 2px solid red;
	margin: auto;
}
body {
	 text-align:center; 
	}
</style>
</head>
<body>
<div class="boxed" > 
<h1>Please Choose a Location</h1>
</div>
<form action="index.php" method="post" style="float: left;">
		<input type="submit" value="Go Home" />
</form><br>
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
		
		$playlist = NULL;
		if (isset ( $_POST ['playlistspinner'] )) {
			$playlist = $_POST ['playlistspinner'];
		}else{
			header("Location: index.php");
			exit();
		}
		
		$myPlaylist = NULL;
		foreach ( $hm->getPlaylists () as $tempPlaylist ) {
			if (strcmp ( $tempPlaylist->getName (), $playlist ) == 0) {
				$myPlaylist = $tempPlaylist;
				break;
			}
		}
		if($myPlaylist == NULL){
				header("Location: index.php");
				exit();
		}
		$_SESSION['playlist'] = $myPlaylist;
		
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
		
		
		<form action="playPlaylistRG.php" method="post">
		<?php 
		echo "<p>Group: <select name='groupspinner'>";
		foreach ( $hm->getRoomGroups() as $group) {
			echo "<option>" . $group->getName () . "</option>";
		}
		echo "</select><span class='error'>";
		echo "</span></p>";
		?>
		
		<input type="submit" value="Play in Group"/><span class="error">
		<?php 
		if(isset($_SESSION['errorGroup'])&&! empty ( $_SESSION ['errorGroup'] )) {
				echo " * " . $_SESSION ["errorGroup"];                            
			}
		?>
		</span>
		
		</form>
		
		
	
	<?php $pm->writeDataToStore($hm);?>
	</body>
	</html>
