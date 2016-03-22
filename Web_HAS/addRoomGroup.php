<?php
require_once (__DIR__ . '\controller\Controller.php');

session_start ();

$_SESSION ["errorRoomGroup"] = "";
$c = new Controller ();
try {
	$room = NULL;
	if (isset ( $_POST ['roomspinner'] )) {
		$room = $_POST ['roomspinner'];
	}
	$groupName = $_POST['groupName'];
	$c->createRoomGroup($groupName, $room);
} catch ( Exception $e ) {
	$_SESSION ["errorRoomGroup"] = $e->getMessage ();
}
?>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="refresh" content="0; url=/HAS/selectRoom.php" />
</head>
</html>