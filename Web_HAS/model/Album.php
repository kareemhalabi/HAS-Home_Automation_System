<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.23.0-599796a modeling language!*/

class Album
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Album Attributes
  private $name;
  private $genre;
  private $releaseDate;

  //Album Associations
  private $songs;
  private $artist;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aName, $aGenre, $aReleaseDate, $aArtist)
  {
    $this->name = $aName;
    $this->genre = $aGenre;
    $this->releaseDate = $aReleaseDate;
    $this->songs = array();
    $didAddArtist = $this->setArtist($aArtist);
    if (!$didAddArtist)
    {
      throw new Exception("Unable to create album due to artist");
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

  public function setGenre($aGenre)
  {
    $wasSet = false;
    $this->genre = $aGenre;
    $wasSet = true;
    return $wasSet;
  }

  public function setReleaseDate($aReleaseDate)
  {
    $wasSet = false;
    $this->releaseDate = $aReleaseDate;
    $wasSet = true;
    return $wasSet;
  }

  public function getName()
  {
    return $this->name;
  }

  public function getGenre()
  {
    return $this->genre;
  }

  public function getReleaseDate()
  {
    return $this->releaseDate;
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

  public function getArtist()
  {
    return $this->artist;
  }

  public function isNumberOfSongsValid()
  {
    $isValid = $this->numberOfSongs() >= self::minimumNumberOfSongs();
    return $isValid;
  }

  public static function minimumNumberOfSongs()
  {
    return 1;
  }

  public function addSongVia($aName, $aDuration, $aPosition)
  {
    return new Song($aName, $aDuration, $aPosition, $this);
  }

  public function addSong($aSong)
  {
    $wasAdded = false;
    if ($this->indexOfSong($aSong) !== -1) { return false; }
    $existingAlbum = $aSong->getAlbum();
    $isNewAlbum = $existingAlbum != null && $this !== $existingAlbum;

    if ($isNewAlbum && $existingAlbum->numberOfSongs() <= self::minimumNumberOfSongs())
    {
      return $wasAdded;
    }

    if ($isNewAlbum)
    {
      $aSong->setAlbum($this);
    }
    else
    {
      $this->songs[] = $aSong;
    }
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeSong($aSong)
  {
    $wasRemoved = false;
    //Unable to remove aSong, as it must always have a album
    if ($this === $aSong->getAlbum())
    {
      return $wasRemoved;
    }

    //album already at minimum (1)
    if ($this->numberOfSongs() <= self::minimumNumberOfSongs())
    {
      return $wasRemoved;
    }

    unset($this->songs[$this->indexOfSong($aSong)]);
    $this->songs = array_values($this->songs);
    $wasRemoved = true;
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

  public function setArtist($aArtist)
  {
    $wasSet = false;
    if ($aArtist == null)
    {
      return $wasSet;
    }
    
    $existingArtist = $this->artist;
    $this->artist = $aArtist;
    if ($existingArtist != null && $existingArtist != $aArtist)
    {
      $existingArtist->removeAlbum($this);
    }
    $this->artist->addAlbum($this);
    $wasSet = true;
    return $wasSet;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    foreach ($this->songs as $aSong)
    {
      $aSong->delete();
    }
    $placeholderArtist = $this->artist;
    $this->artist = null;
    $placeholderArtist->removeAlbum($this);
  }

}
?>