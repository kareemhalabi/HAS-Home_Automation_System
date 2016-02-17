<?php
require_once (__DIR__.'/../model/Album.php');
require_once (__DIR__.'/../model/Artist.php');
require_once (__DIR__.'/../model/HAS.php');
require_once (__DIR__.'/../persistence/PersistenceHAS.php');

class Controller{
	
	public function __construct(){
		
	}
	
	public function createAlbum($albumName, $genre, $releaseDate) {
		if ($albumName == null || strlen($albumName) == 0){
			throw new Exception ("Album name cannot be empty! ");
		}else if ($genre == null || strlen($genre) == 0){
			throw new Exception ("Genre cannot be empty! ");
		}else if (!strtotime($releaseDate)){
			throw new Exception ("Date must be in format (YYYY-MM-DD)! ");
		}else{
			$pm = new PersistenceHAS();
			$hm = $pm -> loadDataFromStore();
			
			$album = new Album ($albumName, $genre, $releaseDate);
			$hm -> addAlbum($album);
			
			$pm->writeDataToStore($hm);
		}
	}
}