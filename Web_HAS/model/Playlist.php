<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.23.0-599796a modeling language!*/

class Playlist implements Playable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Playlist Attributes
  private $name;

  //Playlist Associations
  private $songs;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aName, $allSongs)
  {
    $this->name = $aName;
    $this->songs = array();
    $didAddSongs = $this->setSongs($allSongs);
    if (!$didAddSongs)
    {
      throw new Exception("Unable to create Playlist, must have at least 1 songs");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function setName($aName)
  {
    $wasSet = false;
    $this->name = $aName;
    $wasSet = true;
    return $wasSet;
  }

  public function getName()
  {
    return $this->name;
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

  public static function minimumNumberOfSongs()
  {
    return 1;
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
    if ($this->indexOfSong($aSong) == -1)
    {
      return $wasRemoved;
    }

    if ($this->numberOfSongs() <= self::minimumNumberOfSongs())
    {
      return $wasRemoved;
    }

    unset($this->songs[$this->indexOfSong($aSong)]);
    $this->songs = array_values($this->songs);
    $wasRemoved = true;
    return $wasRemoved;
  }

  public function setSongs($newSongs)
  {
    $wasSet = false;
    $verifiedSongs = array();
    foreach ($newSongs as $aSong)
    {
      if (array_search($aSong,$verifiedSongs) !== false)
      {
        continue;
      }
      $verifiedSongs[] = $aSong;
    }

    if (count($verifiedSongs) != count($newSongs) || count($verifiedSongs) < self::minimumNumberOfSongs())
    {
      return $wasSet;
    }

    $this->songs = $verifiedSongs;
    $wasSet = true;
    return $wasSet;
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

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    $this->songs = array();
  }

  public function play()
  {
          return "";
  }

}
?>