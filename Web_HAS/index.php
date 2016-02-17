<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>HAS</title>
		<style>
			.error{color:#FF0000;}
		</style>
	</head>
	<body>
		<?php 
		//pull data from model folder
		require_once "model/Artist.php";
		require_once "model/Album.php";
		require_once "model/HAS.php";
		require_once "persistence/PersistenceHAS.php";
		
		session_start();
		
		//Retrieve the data from the model
		$pm = new PersistenceHAS();
		$hm = $pm->loadDataFromStore();
		
		?>
		<form action="addAlbum.php" method="post">
			<p>Name of Album: <input type="text" name="albumName"/>
			<span class="error">
			<?php 
				if (isset($_SESSION['errorAlbumName']) && !empty($_SESSION['errorAlbumName'])){
					echo " * " . $_SESSION["errorAlbumName"];
				}
			?>
			</span></p>
			<p>Genre: <input type ="text" name="genre"/>
			<span class="error">
			<?php 
				if (isset($_SESSION['errorGenre']) && !empty($_SESSION['errorGenre'])){
					echo " * " . $_SESSION["errorGenre"];
				}
			?>
			</span></p>
			<p>Release Date: <input type ="date" name="releaseDate" value="<?php echo date('Y-m-d');?>"/>
			<span class="error">
			<?php 
				if (isset($_SESSION['errorReleaseDate']) && !empty($_SESSION['errorReleaseDate'])){
					echo " * " . $_SESSION["errorReleaseDate"];
				}
			?>
			</span></p>
			<p><input type="submit" value="Add Album"/></p> 
		</form>
	</body>
</html>