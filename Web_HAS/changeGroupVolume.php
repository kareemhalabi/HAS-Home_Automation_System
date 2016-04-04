<?php
require_once (__DIR__. '\controller\Controller.php');

session_start();

$$_SESSION ["errorGroupVolume"] = "";

$c = new Controller();
try{
	$group = NULL;
	if (isset ($_POST ['groupspinner'])){
		$group = $_POST ['groupspinner'];
	}
	$volume = $_POST ['volume'];
	/*
		if ($volume == 0) {
		$c->changeGroupVolume($group, $volume, true);
	} else {
		$c->changeGroupVolume($group, $volume, false );
	}
	*/
	$c->changeGroupVolume($group, $volume, false);
	
	
} catch ( Exception $e ) {
	$_SESSION ["errorGroupVolume"] = $e->getMessage ();
}
?>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="refresh" content="0; url=/HAS/changeVolume.php" />
</head>
</html>