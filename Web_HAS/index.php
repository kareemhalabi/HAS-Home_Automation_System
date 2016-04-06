<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script>
$(document).ready(function(){
$("#table tr").click(function(){
	   $(this).addClass('selected').siblings().removeClass('selected');    
	   var value=$(this).find('td:first').html();
	   window.location.href="playSong.php?name="+$(this).find('td:first').html();    
	});

});
</script>
<style>
td {border: 1px #DDD solid; padding: 5px; cursor: pointer;}

.selected {
    background-color: blue;
    color: #FFF;
}
}
</style>
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
		
		
	//	$songs = array();
		//$songs = $hm->getSongs();
		
		$songName = array();
		foreach ($hm->getSongs() as $songs){
			array_push($songName, $songs->getName());
		}
		
		?>
	<form action="addMusic.php" method="post">
		<input type="submit" value="Add Music" />
		<p></p>
	</form>

	<form action="selectRoom.php" method="post">
		<input type="submit" value="Create Rooms" />
		<p></p>
	</form>

	<form action="changeVolume.php" method="post">
		<input type="submit" value="Change Volume" />
		<p></p>
	</form>

	<form action="playlistView.php" method="post">
		<input type="submit" value="Playlists" />
	</form>


	<form action="playAlbum.php" method="post">
	<?php
	echo "<p>Albums: <select name='albumspinner'>";
	foreach ( $hm->getAlbums () as $album ) {
		echo "<option>" . $album->getName () . "</option>";
	}
	echo "</select><span class='error'>";
	echo "</span></p>";
	?>
	<input type="submit" value="Play Album"><span class="error">			
			<?php
			if (isset ( $_SESSION ['errorAlbum'] ) && ! empty ( $_SESSION ['errorAlbum'] )) {
				echo " * " . $_SESSION ["errorAlbum"];
			}
			?>
			</span>
	</form>

	<form action="playPlaylist.php" method="post">
	<?php
	echo "<p>Playlists: <select name='playlistspinner'>";
	foreach ( $hm->getPlaylists () as $playlist ) {
		echo "<option>" . $playlist->getName () . "</option>";
	}
	echo "</select></span></p>";
	?>
	<input type="submit" value="Play Playlist"> <span class="error">			
			<?php
			if (isset ( $_SESSION ['errorPlaylist'] ) && ! empty ( $_SESSION ['errorPlaylist'] )) {
				echo " * " . $_SESSION ["errorPlaylist"];
			}
			?>
			</span>
	</form>

<p>

</p>
	


<?php if (count($songName) > 0): ?>
<table id="table">

  <tbody>
<?php foreach ($songName as $row): ?>
      <?php echo "<tr><td>" . $row . "</td></tr>"; ?>
<?php endforeach; ?>
  </tbody>
</table>
<?php endif; ?>


</body>
</html>
