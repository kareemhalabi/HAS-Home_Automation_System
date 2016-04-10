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
   
    border: 3px solid blue;
}
</style>
</head>
<div class="boxed" > 
<h1>Manage My Music</h1>

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
		require_once "persistence/PersistenceHAS.php";
		
		session_start ();
		
		// Retrieve the data from the model
		$pm = new PersistenceHAS ();
		$hm = $pm->loadDataFromStore ();
		
		?>
				
			<form action="addArtist.php" method="post">
		<h3>Enter a New Artist:</h3>
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
	<h3>Enter a New Album:</h3>
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
	<h3>Enter a New Song:</h3>
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
	
		