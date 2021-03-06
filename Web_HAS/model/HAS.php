<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.23.0-599796a modeling language!*/

class HAS
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static $theInstance = null;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //HAS Associations
  private $rooms;
  private $artists;
  private $albums;
  private $playlists;
  private $songs;
  private $roomGroups;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  private function __construct()
  {
    $this->rooms = array();
    $this->artists = array();
    $this->albums = array();
    $this->playlists = array();
    $this->songs = array();
    $this->roomGroups = array();
  }

  public static function getInstance()
  {
    if(self::$theInstance == null)
    {
      self::$theInstance = new HAS();
    }
    return self::$theInstance;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function getRoom_index($index)
  {
    $aRoom = $this->rooms[$index];
    return $aRoom;
  }

  public function getRooms()
  {
    $newRooms = $this->rooms;
    return $newRooms;
  }

  public function numberOfRooms()
  {
    $number = count($this->rooms);
    return $number;
  }

  public function hasRooms()
  {
    $has = $this->numberOfRooms() > 0;
    return $has;
  }

  public function indexOfRoom($aRoom)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->rooms as $room)
    {
      if ($room->equals($aRoom))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
  }

  public function getArtist_index($index)
  {
    $aArtist = $this->artists[$index];
    return $aArtist;
  }

  public function getArtists()
  {
    $newArtists = $this->artists;
    return $newArtists;
  }

  public function numberOfArtists()
  {
    $number = count($this->artists);
    return $number;
  }

  public function hasArtists()
  {
    $has = $this->numberOfArtists() > 0;
    return $has;
  }

  public function indexOfArtist($aArtist)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->artists as $artist)
    {
      if ($artist->equals($aArtist))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
  }

  public function getAlbum_index($index)
  {
    $aAlbum = $this->albums[$index];
    return $aAlbum;
  }

  public function getAlbums()
  {
    $newAlbums = $this->albums;
    return $newAlbums;
  }

  public function numberOfAlbums()
  {
    $number = count($this->albums);
    return $number;
  }

  public function hasAlbums()
  {
    $has = $this->numberOfAlbums() > 0;
    return $has;
  }

  public function indexOfAlbum($aAlbum)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->albums as $album)
    {
      if ($album->equals($aAlbum))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
  }

  public function getPlaylist_index($index)
  {
    $aPlaylist = $this->playlists[$index];
    return $aPlaylist;
  }

  public function getPlaylists()
  {
    $newPlaylists = $this->playlists;
    return $newPlaylists;
  }

  public function numberOfPlaylists()
  {
    $number = count($this->playlists);
    return $number;
  }

  public function hasPlaylists()
  {
    $has = $this->numberOfPlaylists() > 0;
    return $has;
  }

  public function indexOfPlaylist($aPlaylist)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->playlists as $playlist)
    {
      if ($playlist->equals($aPlaylist))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
  }

  public function getSong_index($index)
  {
    $aSong = $this->songs[$index];
    return $aSong;
  }

  public function getSongs()
  {
    $newSongs = $this->songs;
    return $newSongs;
  }

  public function numberOfSongs()
  {
    $number = count($this->songs);
    return $number;
  }

  public function hasSongs()
  {
    $has = $this->numberOfSongs() > 0;
    return $has;
  }

  public function indexOfSong($aSong)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->songs as $song)
    {
      if ($song->equals($aSong))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
  }

  public function getRoomGroup_index($index)
  {
    $aRoomGroup = $this->roomGroups[$index];
    return $aRoomGroup;
  }

  public function getRoomGroups()
  {
    $newRoomGroups = $this->roomGroups;
    return $newRoomGroups;
  }

  public function numberOfRoomGroups()
  {
    $number = count($this->roomGroups);
    return $number;
  }

  public function hasRoomGroups()
  {
    $has = $this->numberOfRoomGroups() > 0;
    return $has;
  }

  public function indexOfRoomGroup($aRoomGroup)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->roomGroups as $roomGroup)
    {
      if ($roomGroup->equals($aRoomGroup))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
  }

  public static function minimumNumberOfRooms()
  {
    return 0;
  }

  public function addRoom($aRoom)
  {
    $wasAdded = false;
    if ($this->indexOfRoom($aRoom) !== -1) { return false; }
    $this->rooms[] = $aRoom;
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeRoom($aRoom)
  {
    $wasRemoved = false;
    if ($this->indexOfRoom($aRoom) != -1)
    {
      unset($this->rooms[$this->indexOfRoom($aRoom)]);
      $this->rooms = array_values($this->rooms);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addRoomAt($aRoom, $index)
  {  
    $wasAdded = false;
    if($this->addRoom($aRoom))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfRooms()) { $index = $this->numberOfRooms() - 1; }
      array_splice($this->rooms, $this->indexOfRoom($aRoom), 1);
      array_splice($this->rooms, $index, 0, array($aRoom));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveRoomAt($aRoom, $index)
  {
    $wasAdded = false;
    if($this->indexOfRoom($aRoom) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfRooms()) { $index = $this->numberOfRooms() - 1; }
      array_splice($this->rooms, $this->indexOfRoom($aRoom), 1);
      array_splice($this->rooms, $index, 0, array($aRoom));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addRoomAt($aRoom, $index);
    }
    return $wasAdded;
  }

  public static function minimumNumberOfArtists()
  {
    return 0;
  }

  public function addArtist($aArtist)
  {
    $wasAdded = false;
    if ($this->indexOfArtist($aArtist) !== -1) { return false; }
    $this->artists[] = $aArtist;
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeArtist($aArtist)
  {
    $wasRemoved = false;
    if ($this->indexOfArtist($aArtist) != -1)
    {
      unset($this->artists[$this->indexOfArtist($aArtist)]);
      $this->artists = array_values($this->artists);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addArtistAt($aArtist, $index)
  {  
    $wasAdded = false;
    if($this->addArtist($aArtist))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfArtists()) { $index = $this->numberOfArtists() - 1; }
      array_splice($this->artists, $this->indexOfArtist($aArtist), 1);
      array_splice($this->artists, $index, 0, array($aArtist));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveArtistAt($aArtist, $index)
  {
    $wasAdded = false;
    if($this->indexOfArtist($aArtist) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfArtists()) { $index = $this->numberOfArtists() - 1; }
      array_splice($this->artists, $this->indexOfArtist($aArtist), 1);
      array_splice($this->artists, $index, 0, array($aArtist));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addArtistAt($aArtist, $index);
    }
    return $wasAdded;
  }

  public static function minimumNumberOfAlbums()
  {
    return 0;
  }

  public function addAlbum($aAlbum)
  {
    $wasAdded = false;
    if ($this->indexOfAlbum($aAlbum) !== -1) { return false; }
    $this->albums[] = $aAlbum;
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeAlbum($aAlbum)
  {
    $wasRemoved = false;
    if ($this->indexOfAlbum($aAlbum) != -1)
    {
      unset($this->albums[$this->indexOfAlbum($aAlbum)]);
      $this->albums = array_values($this->albums);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addAlbumAt($aAlbum, $index)
  {  
    $wasAdded = false;
    if($this->addAlbum($aAlbum))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfAlbums()) { $index = $this->numberOfAlbums() - 1; }
      array_splice($this->albums, $this->indexOfAlbum($aAlbum), 1);
      array_splice($this->albums, $index, 0, array($aAlbum));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveAlbumAt($aAlbum, $index)
  {
    $wasAdded = false;
    if($this->indexOfAlbum($aAlbum) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfAlbums()) { $index = $this->numberOfAlbums() - 1; }
      array_splice($this->albums, $this->indexOfAlbum($aAlbum), 1);
      array_splice($this->albums, $index, 0, array($aAlbum));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addAlbumAt($aAlbum, $index);
    }
    return $wasAdded;
  }

  public static function minimumNumberOfPlaylists()
  {
    return 0;
  }

  public function addPlaylist($aPlaylist)
  {
    $wasAdded = false;
    if ($this->indexOfPlaylist($aPlaylist) !== -1) { return false; }
    $this->playlists[] = $aPlaylist;
    $wasAdded = true;
    return $wasAdded;
  }

  public function removePlaylist($aPlaylist)
  {
    $wasRemoved = false;
    if ($this->indexOfPlaylist($aPlaylist) != -1)
    {
      unset($this->playlists[$this->indexOfPlaylist($aPlaylist)]);
      $this->playlists = array_values($this->playlists);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addPlaylistAt($aPlaylist, $index)
  {  
    $wasAdded = false;
    if($this->addPlaylist($aPlaylist))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfPlaylists()) { $index = $this->numberOfPlaylists() - 1; }
      array_splice($this->playlists, $this->indexOfPlaylist($aPlaylist), 1);
      array_splice($this->playlists, $index, 0, array($aPlaylist));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMovePlaylistAt($aPlaylist, $index)
  {
    $wasAdded = false;
    if($this->indexOfPlaylist($aPlaylist) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfPlaylists()) { $index = $this->numberOfPlaylists() - 1; }
      array_splice($this->playlists, $this->indexOfPlaylist($aPlaylist), 1);
      array_splice($this->playlists, $index, 0, array($aPlaylist));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addPlaylistAt($aPlaylist, $index);
    }
    return $wasAdded;
  }

  public static function minimumNumberOfSongs()
  {
    return 0;
  }

  public function addSong($aSong)
  {
    $wasAdded = false;
    if ($this->indexOfSong($aSong) !== -1) { return false; }
    $this->songs[] = $aSong;
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeSong($aSong)
  {
    $wasRemoved = false;
    if ($this->indexOfSong($aSong) != -1)
    {
      unset($this->songs[$this->indexOfSong($aSong)]);
      $this->songs = array_values($this->songs);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addSongAt($aSong, $index)
  {  
    $wasAdded = false;
    if($this->addSong($aSong))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfSongs()) { $index = $this->numberOfSongs() - 1; }
      array_splice($this->songs, $this->indexOfSong($aSong), 1);
      array_splice($this->songs, $index, 0, array($aSong));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveSongAt($aSong, $index)
  {
    $wasAdded = false;
    if($this->indexOfSong($aSong) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfSongs()) { $index = $this->numberOfSongs() - 1; }
      array_splice($this->songs, $this->indexOfSong($aSong), 1);
      array_splice($this->songs, $index, 0, array($aSong));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addSongAt($aSong, $index);
    }
    return $wasAdded;
  }

  public static function minimumNumberOfRoomGroups()
  {
    return 0;
  }

  public function addRoomGroup($aRoomGroup)
  {
    $wasAdded = false;
    if ($this->indexOfRoomGroup($aRoomGroup) !== -1) { return false; }
    $this->roomGroups[] = $aRoomGroup;
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeRoomGroup($aRoomGroup)
  {
    $wasRemoved = false;
    if ($this->indexOfRoomGroup($aRoomGroup) != -1)
    {
      unset($this->roomGroups[$this->indexOfRoomGroup($aRoomGroup)]);
      $this->roomGroups = array_values($this->roomGroups);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addRoomGroupAt($aRoomGroup, $index)
  {  
    $wasAdded = false;
    if($this->addRoomGroup($aRoomGroup))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfRoomGroups()) { $index = $this->numberOfRoomGroups() - 1; }
      array_splice($this->roomGroups, $this->indexOfRoomGroup($aRoomGroup), 1);
      array_splice($this->roomGroups, $index, 0, array($aRoomGroup));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveRoomGroupAt($aRoomGroup, $index)
  {
    $wasAdded = false;
    if($this->indexOfRoomGroup($aRoomGroup) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfRoomGroups()) { $index = $this->numberOfRoomGroups() - 1; }
      array_splice($this->roomGroups, $this->indexOfRoomGroup($aRoomGroup), 1);
      array_splice($this->roomGroups, $index, 0, array($aRoomGroup));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addRoomGroupAt($aRoomGroup, $index);
    }
    return $wasAdded;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    $this->rooms = array();
    $this->artists = array();
    $this->albums = array();
    $this->playlists = array();
    $this->songs = array();
    $this->roomGroups = array();
  }

}
?>