<?php
require_once (__DIR__ . '\controller\Controller.php');

session_start ();

$_SESSION ["errorRoomToGroup"] = "";
$c = new Controller ();
try {
	$room = NULL;
	if (isset ( $_POST ['roomspinner'] )) {
		$room = $_POST ['roomspinner'];
	}
	$group = NULL;
	if (isset ( $_POST ['groupspinner'] )) {
		$group = $_POST ['groupspinner'];
	}
	$c->addRoomToGroup($group, $room);
} catch ( Exception $e ) {
	$_SESSION ["errorRoomToGroup"] = $e->getMessage ();
}
?>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="refresh" content="0; url=/HAS/selectRoom.php" />
</head>
</html>