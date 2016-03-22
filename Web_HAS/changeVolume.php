<?php
require_once (__DIR__ . '\controller\Controller.php');

session_start ();

$_SESSION ["errorVolume"] = "";

$c = new Controller ();
try {
	$room = NULL;
	if (isset ( $_POST ['roomspinner'] )) {
		$room = $_POST ['roomspinner'];
	}
	$volume = $_POST ['volume'];
	if ($volume == 0) {
		$c->changeVolume($room, $volume, true);
	} else {
		$c->changeVolume($room, $volume, false );
	}
	
} catch ( Exception $e ) {
	$_SESSION ["errorVolume"] = $e->getMessage ();
}
?>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="refresh" content="0; url=/HAS/selectRoom.php" />
</head>
</html>