<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.23.0-599796a modeling language!*/

class Song
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Song Attributes
  private $name;
  private $duration;
  private $position;

  //Song Associations
  private $album;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aName, $aDuration, $aPosition, $aAlbum)
  {
    $this->name = $aName;
    $this->duration = $aDuration;
    $this->position = $aPosition;
    $didAddAlbum = $this->setAlbum($aAlbum);
    if (!$didAddAlbum)
    {
      throw new Exception("Unable to create song due to album");
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

  public function getName()
  {
    return $this->name;
  }

  public function getDuration()
  {
    return $this->duration;
  }

  public function getPosition()
  {
    return $this->position;
  }

  public function getAlbum()
  {
    return $this->album;
  }

  public function setAlbum($aAlbum)
  {
    $wasSet = false;
    //Must provide album to song
    if ($aAlbum == null)
    {
      return $wasSet;
    }

    if ($this->album != null && $this->album->numberOfSongs() <= Album::minimumNumberOfSongs())
    {
      return $wasSet;
    }

    $existingAlbum = $this->album;
    $this->album = $aAlbum;
    if ($existingAlbum != null && $existingAlbum != $aAlbum)
    {
      $didRemove = $existingAlbum->removeSong($this);
      if (!$didRemove)
      {
        $this->album = $existingAlbum;
        return $wasSet;
      }
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
    $placeholderAlbum = $this->album;
    $this->album = null;
    $placeholderAlbum->removeSong($this);
  }

}
?>