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
		require_once "model/RoomGroup.php";
		require_once "persistence/PersistenceHAS.php";

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
$name = $myRoom->getName();

?>

<h3>The playlist is now playing in <?php echo $name?>.</h3>

<style>
    #div{
      border:1px solid #000;
      font-family:arial;
      height:100px;
      width:200px;
    }
    ul{
      list-style:none;
      max-height:100px;
      margin:0;
      overflow:auto;
      padding:0;
      text-indent:10px;
    }
    li{
      line-height:25px;
    }
    li:nth-child(even){
      background:#eee;
    }
    </style>

	<p>Songs: <select name='songlist'>
    <div id="div"> 
    <ul>

        <?php
        
        foreach ($hm->getSongs() as $song) {
        ?>
            <li><?php echo $song->getName(); ?></li>

        <?php
        echo "</select></p>";
        }
        ?>

    </ul>
</div>
</select>
</body>
</html>