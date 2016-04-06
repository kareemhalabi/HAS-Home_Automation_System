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
		require_once (__DIR__ . '\controller\Controller.php');

session_start();

//retrieve data from the model
$pm = new PersistenceHAS();
$hm = $pm->loadDataFromStore();

$room = NULL;
if(isset($_POST['roomspinner'])){
	$room = $_POST['roomspinner'];
}

$myRoom = NULL;
foreach($hm->getRooms() as $tempRoom){
	if(strcmp($tempRoom->getName(),$room)==0){
		$myRoom=$tempRoom;
		break;
	}
}
$playlist = $_SESSION['playlist'];

$playlistName = $playlist->getName();

$name = $myRoom->getName();

$c = new Controller();
$c->playPlayableRoom($myRoom, $playlist);

?>

<h3><?php echo $playlistName?> is now playing in the room: <?php echo $name?>.</h3>
<form action="index.php" method="post">
		<input type="submit" value="Home" />
	</form>
</body>
</html>