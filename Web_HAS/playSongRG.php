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

//pull data from playPlaylist.php
$group = NULL;
if(isset($_POST['groupspinner'])){
	$group = $_POST['groupspinner'];
}

$myGroup = NULL;
foreach($hm->getRoomGroups() as $tempGroup){
	if(strcmp($tempGroup->getName(),$group)==0){
		$myGroup=$tempGroup;
		break;
	}
}
$name = $myGroup->getName();

$song = $_SESSION['song'];

$songName = $song->getName();

$c=new Controller();
$c->playPlayableRG($myGroup, $song);
?>

<h3>The song <?php echo $songName?>, is now playing in the room group <?php echo $name?>.</h3>
<form action="index.php" method="post">
		<input type="submit" value="Home" />
	</form>