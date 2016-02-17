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
  private $artists;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aName, $aGenre, $aReleaseDate)
  {
    $this->name = $aName;
    $this->genre = $aGenre;
    $this->releaseDate = $aReleaseDate;
    $this->songs = array();
    $this->artists = array();
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

  public static function minimumNumberOfSongs()
  {
    return 0;
  }

  public function addSongVia($aTitle, $aDuration, $aPosition)
  {
    return new Song($aTitle, $aDuration, $aPosition, $this);
  }

  public function addSong($aSong)
  {
    $wasAdded = false;
    if ($this->indexOfSong($aSong) !== -1) { return false; }
    $existingAlbum = $aSong->getAlbum();
    $isNewAlbum = $existingAlbum != null && $this !== $existingAlbum;
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
    if ($this !== $aSong->getAlbum())
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

  public static function minimumNumberOfArtists()
  {
    return 0;
  }

  public function addArtist($aArtist)
  {
    $wasAdded = false;
    if ($this->indexOfArtist($aArtist) !== -1) { return false; }
    $this->artists[] = $aArtist;
    if ($aArtist->indexOfAlbum($this) != -1)
    {
      $wasAdded = true;
    }
    else
    {
      $wasAdded = $aArtist->addAlbum($this);
      if (!$wasAdded)
      {
        array_pop($this->artists);
      }
    }
    return $wasAdded;
  }

  public function removeArtist($aArtist)
  {
    $wasRemoved = false;
    if ($this->indexOfArtist($aArtist) == -1)
    {
      return $wasRemoved;
    }

    $oldIndex = $this->indexOfArtist($aArtist);
    unset($this->artists[$oldIndex]);
    if ($aArtist->indexOfAlbum($this) == -1)
    {
      $wasRemoved = true;
    }
    else
    {
      $wasRemoved = $aArtist->removeAlbum($this);
      if (!$wasRemoved)
      {
        $this->artists[$oldIndex] = $aArtist;
        ksort($this->artists);
      }
    }
    $this->artists = array_values($this->artists);
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
    $copyOfArtists = $this->artists;
    $this->artists = array();
    foreach ($copyOfArtists as $aArtist)
    {
      $aArtist->removeAlbum($this);
    }
  }

}
?>