
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HAS</title>
<style>
p { text-align:center; }
.error {
	color: #FF0000;
}
</style>
</head>
<body>
	<form action="index.php" method="post">
		<b><input type="submit" value="Go Home" /></b>
	</form>
		
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
				
	<form action="addPlaylist.php" method="post">
		
		<p>
			<h1 align="center">Add a New Playlist</h1>
		
		
		<p>
			Name of your playlist: <input type="text" name="playlistName" /> <span
				class="error">		</span>
				
		</p>
			<?php
			echo "<p>Song: <select name='songSpinner'>";
			foreach ( $hm->getSongs () as $song ) {
				echo "<option>" . $song->getName () . "</option>";
			}
			echo "</select><span class='error'>";
			echo "</span></p>";
			?>
			
			
			
		
		<p>
			<input type="submit" value="Add Playlist" /> <span class="error">			
			<?php
			if (isset ( $_SESSION ['errorPlaylistName'] ) && ! empty ( $_SESSION ['errorPlaylistName'] )) {
				echo " * " . $_SESSION ["errorPlaylistName"];
			}
			?>
			</span>
		</p>


	</form>
	
	<form action="addSongToPlaylist.php" method="post">
		<h1 align="center">Manage Your Playlists</h1>
		<p> Choose a Song to Add
		<?php
		echo "<p>Song: <select name='songSpinner'>";
		foreach ( $hm->getSongs () as $song ) {
			echo "<option>" . $song->getName () . "</option>";
		}
		echo "</select><span class='error'>";
		echo "</span></p>";
		?>
	
		<p>Choose a Playlist to add this song to:</p>
	<?php
	echo "<p>Playlist: <select name='playlistSpinner'>";
	foreach ( $hm->getPlaylists () as $playlist ) {
		echo "<option>" . $playlist->getName () . "</option>";
	}
	echo "</select><span class='error'>";
	echo "</span></p>";
	
	?>
	<p>
			<input type="submit" value="Add Song to Playlist" /> <span class="error">			
			<?php
			if (isset ( $_SESSION ['errorSongToPlaylist'] ) && ! empty ( $_SESSION ['errorSongToPlaylist'] )) {
				echo " * " . $_SESSION ["errorSongToPlaylist"];
			}
			?>
			</span>
		</p>


	</form>
</body>
</html>
