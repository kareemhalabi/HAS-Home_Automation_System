<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.23.0-599796a modeling language!*/

class Song extends Playable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Song Attributes
  private $duration;
  private $position;

  //Song Associations
  private $ftArtists;
  private $album;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aName, $aDuration, $aPosition, $aAlbum)
  {
    parent::__construct($aName);
    $this->duration = $aDuration;
    $this->position = $aPosition;
    $this->ftArtists = array();
    $didAddAlbum = $this->setAlbum($aAlbum);
    if (!$didAddAlbum)
    {
      throw new Exception("Unable to create song due to album");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function setDuration($aDuration)
  {
    $wasSet = false;
    $this->duration = $aDuration;
    $wasSet = true;
    return $wasSet;
  }

  public function setPosition($aPosition)
  {
    $wasSet = false;
    $this->position = $aPosition;
    $wasSet = true;
    return $wasSet;
  }

  public function getDuration()
  {
    return $this->duration;
  }

  public function getPosition()
  {
    return $this->position;
  }

  public function getFtArtist_index($index)
  {
    $aFtArtist = $this->ftArtists[$index];
    return $aFtArtist;
  }

  public function getFtArtists()
  {
    $newFtArtists = $this->ftArtists;
    return $newFtArtists;
  }

  public function numberOfFtArtists()
  {
    $number = count($this->ftArtists);
    return $number;
  }

  public function hasFtArtists()
  {
    $has = $this->numberOfFtArtists() > 0;
    return $has;
  }

  public function indexOfFtArtist($aFtArtist)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->ftArtists as $ftArtist)
    {
      if ($ftArtist->equals($aFtArtist))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
  }

  public function getAlbum()
  {
    return $this->album;
  }

  public static function minimumNumberOfFtArtists()
  {
    return 0;
  }

  public function addFtArtist($aFtArtist)
  {
    $wasAdded = false;
    if ($this->indexOfFtArtist($aFtArtist) !== -1) { return false; }
    $this->ftArtists[] = $aFtArtist;
    if ($aFtArtist->indexOfSong($this) != -1)
    {
      $wasAdded = true;
    }
    else
    {
      $wasAdded = $aFtArtist->addSong($this);
      if (!$wasAdded)
      {
        array_pop($this->ftArtists);
      }
    }
    return $wasAdded;
  }

  public function removeFtArtist($aFtArtist)
  {
    $wasRemoved = false;
    if ($this->indexOfFtArtist($aFtArtist) == -1)
    {
      return $wasRemoved;
    }

    $oldIndex = $this->indexOfFtArtist($aFtArtist);
    unset($this->ftArtists[$oldIndex]);
    if ($aFtArtist->indexOfSong($this) == -1)
    {
      $wasRemoved = true;
    }
    else
    {
      $wasRemoved = $aFtArtist->removeSong($this);
      if (!$wasRemoved)
      {
        $this->ftArtists[$oldIndex] = $aFtArtist;
        ksort($this->ftArtists);
      }
    }
    $this->ftArtists = array_values($this->ftArtists);
    return $wasRemoved;
  }

  public function addFtArtistAt($aFtArtist, $index)
  {  
    $wasAdded = false;
    if($this->addFtArtist($aFtArtist))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfFtArtists()) { $index = $this->numberOfFtArtists() - 1; }
      array_splice($this->ftArtists, $this->indexOfFtArtist($aFtArtist), 1);
      array_splice($this->ftArtists, $index, 0, array($aFtArtist));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveFtArtistAt($aFtArtist, $index)
  {
    $wasAdded = false;
    if($this->indexOfFtArtist($aFtArtist) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfFtArtists()) { $index = $this->numberOfFtArtists() - 1; }
      array_splice($this->ftArtists, $this->indexOfFtArtist($aFtArtist), 1);
      array_splice($this->ftArtists, $index, 0, array($aFtArtist));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addFtArtistAt($aFtArtist, $index);
    }
    return $wasAdded;
  }

  public function setAlbum($aAlbum)
  {
    $wasSet = false;
    if ($aAlbum == null)
    {
      return $wasSet;
    }
    
    $existingAlbum = $this->album;
    $this->album = $aAlbum;
    if ($existingAlbum != null && $existingAlbum != $aAlbum)
    {
      $existingAlbum->removeSong($this);
    }
    $this->album->addSong($this);
    $wasSet = true;
    return $wasSet;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    $copyOfFtArtists = $this->ftArtists;
    $this->ftArtists = array();
    foreach ($copyOfFtArtists as $aFtArtist)
    {
      $aFtArtist->removeSong($this);
    }
    $placeholderAlbum = $this->album;
    $this->album = null;
    $placeholderAlbum->removeSong($this);
    parent::delete();
  }


  /**
   * Java
   * public void play() {}
   * PHP
   */
   public function play()
  {
    
  }

}
?>