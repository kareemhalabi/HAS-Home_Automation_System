<?php
require_once (__DIR__ . '/../model/Playable.php');
require_once (__DIR__ . '/../model/Album.php');
require_once (__DIR__ . '/../model/Artist.php');
require_once (__DIR__ . '/../model/Song.php');
require_once (__DIR__ . '/../model/HAS.php');
require_once (__DIR__ . '/../model/Playlist.php');
require_once (__DIR__ . '/../model/Room.php');
require_once (__DIR__ . '/../model/RoomGroup.php');
require_once (__DIR__ . '/../persistence/PersistenceHAS.php');
require_once (__DIR__ . '/../controller/InputValidator.php');
class Controller {
	public function __construct() {
	}
	
	/**
	 * creates an album with user delcared parameters
	 */
	public function createAlbum($albumName, $genre, $releaseDate, $aArtist) {
		// validate input incase of special characters
		$albumName = InputValidator::validate_input ( $albumName );
		$genre = InputValidator::validate_input ( $genre );
		
		// loads Persistence layer to be able to retireve data from the layer
		$pm = new PersistenceHAS ();
		$hm = $pm->loadDataFromStore ();
		if ($aArtist == null) {
			throw new Exception ( "Artist does not exist! " );
		} else { // finds the object that matches the name
			$myArtist = NULL;
			foreach ( $hm->getArtists () as $artist ) {
				if (strcmp ( $artist->getName (), $aArtist ) == 0) {
					$myArtist = $artist;
					break;
				}
			}
		}
		if ($albumName == null || strlen ( $albumName ) == 0) {
			throw new Exception ( "Album name cannot be empty! " );
		} else if ($genre == null || strlen ( $genre ) == 0) {
			throw new Exception ( "Genre cannot be empty! " );
		} else if (! strtotime ( $releaseDate )) {
			throw new Exception ( "Date must be in format (YYYY-MM-DD)! " );
		} else {
			
			$album = new Album ( $albumName, $genre, $releaseDate, $artist );
			$hm->addAlbum ( $album );
			
			$pm->writeDataToStore ( $hm );
		}
	}
	/**
	 * Creates a song inside an album.
	 * @param String $songName
	 * @param Number $duration
	 * @param Number $position
	 * @param String $aAlbum
	 * @throws Exception
	 */
	public function createSong($songName, $duration, $position, $aAlbum) {
		$songName = InputValidator::validate_input ( $songName );
		// loads Persistence layer to be able to retireve data from the layer
		$pm = new PersistenceHAS ();
		$hm = $pm->loadDataFromStore ();
		if ($aAlbum == null) {
			throw new Exception ( "Album does not exist! " );
		} else { // finds the object that matches the name
			$myAlbum = NULL;
			foreach ( $hm->getAlbums () as $album ) {
				if (strcmp ( $album->getName (), $aAlbum ) == 0) {
					$myAlbum = $album;
					break;
				}
			}
		}
		
		if ($songName == null || strlen ( $songName ) == 0) {
			throw new Exception ( "Song name cannot be empty! " );
			foreach($myAlbum->getSongs() as $existingSong){
				if (strcmp($existingSong->getName(), $songName) == 0){
					throw new Exception ("Song already exists!");
				}
			}
		} else if ($duration <= 0 || $duration == null) {
			throw new Exception ( "Duration needs to be greater than 0! " );
		} else if ($position <= 0 || $position == null) {
			throw new Exception ( "Position needs to be greater than 0! " );
		} else if ($myAlbum == null) {
			throw new Exception ( "Album does not exist! " );
		}else {
			
			$song = new Song ( $songName, $duration, $position, $myAlbum );
			$hm->addSong ( $song );
			
			$pm->writeDataToStore ( $hm );
		}
	}
	/**
	 * Creates an Artist
	 * @param String $name
	 * @throws Exception
	 */
	public function createArtist($name) {
		$name = InputValidator::validate_input ( $name );
		if ($name == null || strlen ( $name ) == 0) {
			throw new Exception ( "Artist name cannot be empty!" );
		} else {
			// loads Persistence layer to be able to retireve data from the layer
			$pm = new PersistenceHAS ();
			$hm = $pm->loadDataFromStore ();
			
			$artist = new Artist ( $name );
			$hm->addArtist ( $artist );
			
			$pm->writeDataToStore ( $hm );
		}
	}
	/**
	 * Creates a room.
	 * @param String $name
	 * @param Number $volume
	 * @param Boolean $mute
	 * @throws Exception
	 */
	public function createRoom($name, $volume, $mute) {
		$name = InputValidator::validate_input ( $name );
		if ($name == null || strlen ( $name ) == 0) {
			throw new Exception ( "Room name cannot be empty!" );
		} else if ($volume > 100 || $volume < 0) {
			throw new Exception ( "Volume must be between 0 and 100." );
		} else {
			// loads Persistence layer to be able to retireve data from the layer
			$pm = new PersistenceHAS ();
			$hm = $pm->loadDataFromStore ();
			
			$room = new Room ( $name, $volume, $mute );
			$hm->addRoom ( $room );
			
			$pm->writeDataToStore ( $hm );
		}
	}
	/**
	 * Creates a playlist with a chosen song that already exists
	 * @param String $name
	 * @param String $aSong
	 * @throws Exception
	 */
	public function createPlaylist($name, $aSong) {
		// loads Persistence layer to be able to retireve data from the layer
		$pm = new PersistenceHAS ();
		$hm = $pm->loadDataFromStore ();
		if ($aSong == null) {
			throw new Exception ( "This song does not exist!" );
		} else { // finds the object that matches the name
			$mySong = NULL;
			foreach ( $hm->getsongs () as $song ) {
				if (strcmp ( $song->getName (), $aSong ) == 0) { // Find the song.
					$mySong = $song;
					break;
				}
			}
		}
		$songs = array ();
		$songs [] = $mySong;//addPlaylist takes an array of songs as parameter
		$name = InputValidator::validate_input ( $name );
		if ($name == null || strlen ( $name ) == 0) {
			throw new Exception ( "Playlist name cannot be empty!" );
		} else if ($mySong == NULL) {
			throw new Exception ( "Song does not exist!" );
		} else {
			
			$playlist = new Playlist ( $name, $songs ); // Create a Playlist with the
			$hm->addPlaylist ( $playlist );
			
			$pm->writeDataToStore ( $hm );
		}
	}
	/**
	 * Add an existing song to an existing playlist
	 * @param String $aPlaylist
	 * @param String $aSong
	 * @throws Exception
	 */
	public function addSongToPlaylist($aPlaylist, $aSong) {
		// loads Persistence layer to be able to retireve data from the layer
		$pm = new PersistenceHAS ();
		$hm = $pm->loadDataFromStore ();
		if ($aSong == null) {
			throw new Exception ( "Song does not exist! " );
		} else { // finds the object that matches the name
			$song = null;
			foreach ( $hm->getSongs () as $tempSong ) {
				if (strcmp ( $tempSong->getName (), $aSong ) == 0) {
					$song = $tempSong;
					break;
				}
			}
		}
		
		if ($aPlaylist == null) {
			throw new Exception ( "Playlist does not exist!" );
		} else if ($song == null) {
			throw new Exception ( "Song does not exist!" );
		} else { // finds the object that matches the name
			$playlist = null;
			foreach ( $hm->getPlaylists () as $tempPlaylist ) {
				if (strcmp ( $tempPlaylist->getName (), $aPlaylist ) == 0) {
					$playlist = $tempPlaylist;
					break;
				}
			}
		}
		
		if (! ($playlist == null || $song == null)) {
			$playlist->addSong ( $song );
			
			$pm->writeDataToStore ( $hm );
		}
	}
	/**
	 * Creates a room group with an already existing room
	 * @param String $name
	 * @param String $aRoom
	 * @throws Exception
	 */
	public function createRoomGroup($name, $aRoom) {
		// loads Persistence layer to be able to retireve data from the layer
		$pm = new PersistenceHAS ();
		$hm = $pm->loadDataFromStore ();
		if ($aRoom == null) {
			throw new Exception ( "Room does not exist! " );
		} else { // finds the object that matches the name
			$room = NULL;
			foreach ( $hm->getRooms () as $roomTemp ) {
				if (strcmp ( $roomTemp->getName (), $aRoom ) == 0) {
					$room = $roomTemp;
					break;
				}
			}
		}
		$rooms = array ();
		array_push ( $rooms, $room );
		$name = InputValidator::validate_input ( $name );
		if ($name == null || strlen ( $name ) == 0) {
			throw new Exception ( "Group name cannot be empty!" );
		}
		if ($room == NULL) {
			throw new Exception ( "Room does not exist! " );
		} else {
			
			$group = new RoomGroup ( $name, $rooms );
			$hm->addRoomGroup ( $group );
			$pm->writeDataToStore ( $hm );
		}
	}
	/**
	 * Add an existing room to an existing room group
	 * @param String $aGroup
	 * @param String $aRoom
	 * @throws Exception
	 */
	public function addRoomToGroup($aGroup, $aRoom) {
		// loads Persistence layer to be able to retireve data from the layer
		$pm = new PersistenceHAS ();
		$hm = $pm->loadDataFromStore ();
		if ($aRoom == null) {
			throw new Exception ( "Room does not exist! " );
		} else { // finds the object that matches the name
			$room = NULL;
			foreach ( $hm->getRooms () as $roomTemp ) {
				if (strcmp ( $roomTemp->getName (), $aRoom ) == 0) {
					$room = $roomTemp;
					break;
				}
			}
		}
		
		if ($aGroup == null) {
			throw new Exception ( "Group does not exist! " );
		} else { // finds the object that matches the name
			$group = NULL;
			foreach ( $hm->getRoomGroups () as $groupTemp ) {
				if (strcmp ( $groupTemp->getName (), $aGroup ) == 0) {
					$group = $groupTemp;
					break;
				}
			}
		}
		
		if (in_array ( $room, $group->getRooms () )) {
			throw new Exception ( "Room already exists in this group!" );
		} else if ($group == NULL) {
			throw new Exception ( "Group does not exist!" );
		} else if ($room == NULL) {
			throw new Exception ( "Room does not exist!" );
		} else {
			
			$group->addRoom ( $room );
			
			$pm->writeDataToStore ( $hm );
		}
	}
	/**
	 * Changes the volume of an existing room. If the volume specified is 0, mute.
	 * @param String $aname
	 * @param Number $volume
	 * @param Boolean $mute
	 * @throws Exception
	 */
	public function changeVolume($aname, $volume, $mute) {
		// loads Persistence layer to be able to retireve data from the layer
		$pm = new PersistenceHAS ();
		$hm = $pm->loadDataFromStore ();
		if ($aname == null) {
			throw new Exception ( "Room does not exist! " );
		} else { // finds the object that matches the name
			$room = NULL;
			foreach ( $hm->getRooms () as $temproom ) {
				if (strcmp ( $temproom->getName (), $aname ) == 0) {
					$room = $temproom;
					break;
				}
			}
		}
		
		if ($volume > 100 || $volume < 0) {
			throw new Exception ( "Volume must be between 0 and 100." );
		} else if ($room == NULL) {
			throw new Exception ( "Room does not exist!" );
		} else {
			
			if ($volume == 0) {
				$room->setMute ( true );
			} else {
				$room->setMute ( false );
			}
			$room->setVolume ( $volume );
			
			$pm->writeDataToStore ( $hm );
		}
	}
	/**
	 * Changes volume of an existing room group by changing the volume of each room iside the room group.
	 * @param String $aGroup
	 * @param Number $volume
	 * @param Boolean $mute
	 * @throws Exception
	 */
	public function changeGroupVolume($aGroup, $volume, $mute) {
		// loads Persistence layer to be able to retireve data from the layer
		$pm = new PersistenceHAS ();
		$hm = $pm->loadDataFromStore ();
		if ($aGroup == null) {
			throw new Exception ( "Group does not exist!" );
		} else { // finds the object that matches the name
			$group = null;
			foreach ( $hm->getRoomGroups () as $name ) {
				if (strcmp ( $name->getName (), $aGroup ) == 0) {
					$group = $name;
					break;
				}
			}
		}
		
		if ($volume > 100 || $volume < 0) {
			throw new Exception ( "Volume must be between 0 and 100." );
		} else if ($group == null) {
			throw new Exception ( "Group does not exist!" );
		} else {
			foreach ( $group->getRooms () as $room ) {
				if ($volume == 0) {
					$room->setMute ( true );
				} else {
					$room->setMute ( false );
				}
				$room->setVolume ( $volume );
			}
			
			$pm->writeDataToStore ( $hm );
		}
	}
	/**
	 * Sets a playable into the room specified
	 * @param Room $room
	 * @param Playable $playable
	 */
	public function playPlayableRoom($room, $playable) {
		// loads Persistence layer to be able to retireve data from the layer
		$pm = new PersistenceHAS ();
		$hm = $pm->loadDataFromStore ();
		
		$room->setPlayable ( $playable );
		
		$pm->writeDataToStore ( $hm );
	}
	/**
	 * Sets a playable into the room group specified
	 * @param RoomGroup $roomGroup
	 * @param Playable $playable
	 */
	public function playPlayableRG($roomGroup, $playable) {
		// loads Persistence layer to be able to retireve data from the layer
		$pm = new PersistenceHAS ();
		$hm = $pm->loadDataFromStore ();
		
		$roomGroup->setPlayable ( $playable );
		
		$pm->writeDataToStore ( $hm );
	}
	/**
	 * sorts songs by album name
	 */
	public function sortbyAlbum() {
		$pm = new PersistenceHAS ();
		$hm = $pm->loadDataFromStore ();
		
		//sort each album
		$albums = $hm->getAlbums();
		$sortedSongs = array();
		
	
		usort($albums, array("Album", "cmp_obj"));//built in sorting function
		
		foreach($albums as $album){
			$sortedSongs = array_merge($sortedSongs, $album->getSongs());
		}
		
		return $sortedSongs;
	}
	/**
	 * Sorts songs by artist name
	 */
	public function sortbyArtist() {
		$pm = new PersistenceHAS();
		$hm = $pm->loadDataFromStore();
		
		$artists = $hm->getArtists();
		$sortedAlbums = array();
		$sortedSongs = array();
		
		usort($artists, array("Artist", "cmp_obj")); //built in sorting funcion
		
		foreach($artists as $artist){//sorts albums by artists name
			$sortedAlbums = array_merge($sortedAlbums, $artist->getAlbums());
		}
		foreach($sortedAlbums as $album){//get the songs from the albums 
			$sortedSongs = array_merge($sortedSongs, $album->getSongs());
		}
		
		return $sortedSongs;
	}
}
