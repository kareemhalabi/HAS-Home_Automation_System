<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script>
$(document).ready(function(){
$("#table tr").click(function(){
	   $(this).addClass('selected').siblings().removeClass('selected');    
	   var value=$(this).find('td:first').html();
	   if (!(value == "undefined")){
			
	   }
	   window.location.href="playSong.php?name="+$(this).find('td:first').html();    
	});

});
</script>

<style>
body { text-align:center; }
.error {
	color: #FF0000;
}
.smallBox{
	border: 2px solid blue;
	width: 200px;
	margin: auto;
}
der: 1px #DDD solid; padding: 5px; cursor: pointer;}

.selected {
    background-color: blue;
    color: #FFF;
}

tr:hover{
	background-color: blue;
	color: #FFF;
}
.boxed {
  	border: 2px solid blue;
}
table {
    border: 1px solid blue;
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
<div class="boxed" > 
<h1>WELCOME TO HAS ONLINE</h1>

</div>
<body>

<p> What would you like to do? </p>

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
		require_once (__DIR__ . '\controller\Controller.php');
		
		session_start ();
		
		// Retrieve the data from the model
		$pm = new PersistenceHAS ();
		$hm = $pm->loadDataFromStore ();
		
		
		$songName = array();
		foreach ($hm->getSongs() as $songs){
			array_push($songName, $songs->getName());
		}
		
		$c = new Controller();
		$songarray = array();
		
		?>
	<form action="addMusic.php" method="post">
		<input type="submit" value="Edit Music" />
		<p></p>
	</form>

	<form action="selectRoom.php" method="post">
		<input type="submit" value="Edit Rooms" />
		<p></p>
	</form>

	<form action="changeVolume.php" method="post">
		<input type="submit" value="Change Volume" />
		<p></p>
	</form>

	<form action="playlistView.php" method="post">
		<input type="submit" value="Edit Playlists" />
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


	<br>
	

<form name="songlist">
<?php if (count($songName) > 0): ?>
<div class="smallBox">
	<h2>
	My Music Library</h2>
	</div>
	
<table id="table" align="center" >
<tr><th>Song</th><th>Album</th><th>Artist</th><th>Duration(sec)</th><th>Genre</th></tr>
  <tbody>
<?php 
if(count($songarray)==0){
	$songarray = $c->sortbyAlbum();
}
foreach ($songarray as $song): ?>
      <?php echo "<tr><td>" . $song->getName() . "</td><td>" . $song->getAlbum()->getName() . "</td><td>" . $song->getAlbum()->getMainArtist()->getName() . "</td><td>" . $song->getDuration() . "</td><td>" . $song->getAlbum()->getGenre() . "</td></tr>"; ?>
<?php endforeach; ?>
  </tbody>
</table>
<?php endif; ?>
</form>

<br>

<form name="currentPlayables">
<?php if (count($hm->getRooms()) > 0):?>
<div class="smallBox">
<h2>
Currently Playing</h2>
</div>
<?php 
foreach($hm->getRooms() as $room){
	//DOESNT WORK
	if($room->hasPlayable()){
		echo "<p>" . $room->getName() . ": " . $room->getPlayable()->getName() . "</p>";
	}else{
		echo "<p>" . $room->getName() . ": Nothing </p>";
	}
}
endif;
?>
<?php if (count($hm->getRoomGroups()) > 0):?>
<?php 
foreach($hm->getRoomGroups() as $group){
	//DOESNT WORK
	if($group->hasPlayable()){
		echo "<p>" . $group->getName() . ": " . $group->getPlayable()->getName() . "</p>";
	}else{
		echo "<p>" . $group->getName() . ": Nothing </p>";
	}
}
endif;
?>
</form>
<?php $pm->writeDataToStore($hm);?>
</body>
</html>
