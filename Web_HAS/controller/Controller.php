<?php
require_once (__DIR__.'/../model/Album.php');
require_once (__DIR__.'/../model/Artist.php');
require_once (__DIR__.'/../model/Song.php');
require_once (__DIR__.'/../model/HAS.php');
require_once (__DIR__.'/../model/Playlist.php');
require_once (__DIR__.'/../model/Room.php');
require_once (__DIR__.'/../persistence/PersistenceHAS.php');
require_once (__DIR__.'/../controller/InputValidator.php');

class Controller{
	
	public function __construct(){
		
	}
	
	public function createAlbum($albumName, $genre, $releaseDate, $aArtist) {
		$albumName = InputValidator::validate_input($albumName);
		$genre = InputValidator::validate_input($genre);
		
		$pm = new PersistenceHAS();
		$hm = $pm->loadDataFromStore();
		if ($aArtist == null) {
			throw new Exception ( "Artist does not exist! " );
		} else {
			$myArtist = NULL;
			foreach ( $hm->getArtists () as $artist ) {
				if (strcmp ( $artist->getName (), $aArtist ) == 0) {
					$myArtist = $artist;
					break;
				}
			}
		}
		if ($albumName == null || strlen($albumName) == 0){
			throw new Exception ("Album name cannot be empty! ");
		}else if ($genre == null || strlen($genre) == 0){
			throw new Exception ("Genre cannot be empty! ");
		}else if (!strtotime($releaseDate)){
			throw new Exception ("Date must be in format (YYYY-MM-DD)! ");
		}else{
			$pm = new PersistenceHAS();
			$hm = $pm -> loadDataFromStore();
			
			$album = new Album ($albumName, $genre, $releaseDate, $artist);
			$hm -> addAlbum($album);
			
			$pm->writeDataToStore($hm);
		}
	}
	
	public function createSong($songName, $duration, $position, $aAlbum){
		$songName = InputValidator::validate_input($songName);
		$pm = new PersistenceHAS();
		$hm = $pm->loadDataFromStore();
		if ($aAlbum == null) {
			throw new Exception ( "Album does not exist! " );
		} else {
			$myAlbum = NULL;
			foreach ( $hm->getAlbums () as $album ) {
				if (strcmp ( $album->getName (), $aAlbum ) == 0) {
					$myAlbum = $album;
					break;
				}
			}
		}
		
		if ($songName == null || strlen($songName) == 0){
			throw new Exception ("Song name cannot be empty! ");
		}
		else if($duration <=0 || $duration == null){
			throw new Exception ("Duration needs to be greater than 0! ");
		}
		else if ($position <=0 || $position == null){
			throw new Exception ("Position needs to be greater than 0! ");
		}
		else if ($album == null){
			throw new Exception ("Album does not exist! ");
		}
		else{
			$pm = new PersistenceHAS();
			$hm = $pm -> loadDataFromStore();
				
			$song = new Song ($songName, $duration, $position, $myAlbum);
			$hm -> addSong($song);
				
			$pm->writeDataToStore($hm);
		}
	}
	
	public function createArtist($name){
		$name = InputValidator::validate_input($name);
		if ($name == null || strlen($name) == 0){
			throw new Exception("Artist name cannot be empty!");
		}else{
			$pm = new PersistenceHAS();
			$hm = $pm->loadDataFromStore();
				
			$artist = new Artist($name);
			$hm->addArtist($artist);
				
			$pm->writeDataToStore($hm);
		}
	}
	
	public function createRoom($name, $volume, $mute){
		$name = InputValidator::validate_input($name);
		if ($name == null || strlen($name) == 0){
			throw new Exception("Room name cannot be empty!");
		}else if ($volume > 100 || $volume < 0){
			throw new Exception("Volume must be between 0 and 100.");
		}else{
			$pm = new PersistenceHAS();
			$hm = $pm->loadDataFromStore();
	
			$room = new Room($name, $volume, $mute);
			$hm->addRoom($room);
	
			$pm->writeDataToStore($hm);
		}
	}
	
	public function changeVolume($aname, $volume, $mute){
		$pm = new PersistenceHAS();
		$hm = $pm->loadDataFromStore();
		if ($aname == null) {
			throw new Exception ( "Room does not exist! " );
		} else {
			$name = NULL;
			foreach ( $hm->getRooms() as $room ) {
				if (strcmp ( $room->getName (), $aname ) == 0) {
					$name = $room;
					break;
				}
			}
		}
		
		if ($volume > 100 || $volume < 0){
			throw new Exception("Volume must be between 0 and 100.");
		}else{
			$pm = new PersistenceHAS();
			$hm = $pm->loadDataFromStore();
			
			$name -> setVolume($volume);
			//$hm->changeVolume($volume);
	
			$pm->writeDataToStore($hm);
		}
	}
}