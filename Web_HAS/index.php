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
		
			<form action="addArtist.php" method="post">
		<p>
			Artist: <input type="text" name="artistName" /> <span class="error">			
			<?php
			if (isset ( $_SESSION ['errorArtistName'] ) && ! empty ( $_SESSION ['errorArtistName'] )) {
				echo " * " . $_SESSION ["errorArtistName"];
			}
			?>
			</span>
		</p>
		<p>
			<input type="submit" value="Add Artist" />
		</p>
	</form>

	<form action="addAlbum.php" method="post">
	<p>For creating an album, please fill out the following section above the Add Album button. If no artist exists, use the Add Artist button to create one.</p>
		<p>
			Name of Album: <input type="text" name="albumName" /> <span
				class="error"> </span>
		</p>
		<?php
		echo "<p>Artist: <select name='artistspinner'>";
		foreach ( $hm->getArtists () as $artist ) {
			echo "<option>" . $artist->getName () . "</option>";
		}
		echo "</select><span class='error'>";
		echo "</span></p>";
		?>
		<p>
			Genre: <input type="text" name="genre" /> <span class="error">
			<?php
			if (isset ( $_SESSION ['errorGenre'] ) && ! empty ( $_SESSION ['errorGenre'] )) {
				echo " * " . $_SESSION ["errorGenre"];
			}
			?>
			</span>
		</p>
		<p>
			Release Date: <input type="date" name="releaseDate"
				value="<?php echo date('Y-m-d');?>" /> <span class="error">
			<?php
			if (isset ( $_SESSION ['errorReleaseDate'] ) && ! empty ( $_SESSION ['errorReleaseDate'] )) {
				echo " * " . $_SESSION ["errorReleaseDate"];
			}
			?>
			</span>
		</p>
		<p>
			<input type="submit" value="Add Album" /> <span class="error">			
			<?php
			if (isset ( $_SESSION ['errorAlbumName'] ) && ! empty ( $_SESSION ['errorAlbumName'] )) {
				echo " * " . $_SESSION ["errorAlbumName"];
			}
			?>
			</span>
		</p>
	</form>
	
	<form action="addSong.php" method="post">
	<p>For adding a song to an album, make sure the album has been created first and select it from the drop down menu.</p>
		<?php
		echo "<p>Album: <select name='albumspinner'>";
		foreach ( $hm->getAlbums () as $album ) {
			echo "<option>" . $album->getName () . "</option>";
		}
		echo "</select><span class='error'>";
		echo "</span></p>";
		?>
		
		<p>
			Name of Song: <input type="text" name="songName" /> <span
				class="error"> </span>
		</p>

		<p>
			Duration of Song (seconds): <input type="number" name="duration" /> <span
				class="error"> </span>
		</p>

		<p>
			Position in Album <input type="number" name="position" /> <span
				class="error"> </span>
		</p>

		<p>
			<input type='submit' value='Add Song' /> <span class="error">
			<?php
			if (isset ( $_SESSION ['errorSongName'] ) && ! empty ( $_SESSION ['errorSongName'] )) {
				echo " * " . $_SESSION ["errorSongName"];
			}
			?>
			</span>
		</p>
	</form>
</body>
</html>