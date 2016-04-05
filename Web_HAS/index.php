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
	
<form action="playSong.php" method="post">
<table id="tableID" border="1">
<?php
foreach($songName as $value){
	echo "<tr><td>" . $value . "</td></tr>";
}
?>
</table>

<?php if (count($songName) > 0): ?>
<table>

  <tbody>
<?php foreach ($songName as $row): array_map('htmlentities', $songName); ?>
    <tr>
      <td><?php echo implode('</td><td>', $songName); ?></td>
    </tr>
<?php endforeach; ?>
  </tbody>
</table>
<?php endif; ?>
</form>


</body>
</html>
