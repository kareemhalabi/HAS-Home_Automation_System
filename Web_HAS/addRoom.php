<?php
require_once (__DIR__.'\controller\Controller.php');

session_start();

$_SESSION["errorRoomName"] = "";
$c = new Controller();
try{
	$room = $_POST['roomName'];
	$c->createRoom($room, 50, false);
}catch(Exception $e){
	$_SESSION["errorRoomName"] = $e->getMessage();
}
?>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="refresh" content="0; url=/HAS/" />
		</head>
</html>