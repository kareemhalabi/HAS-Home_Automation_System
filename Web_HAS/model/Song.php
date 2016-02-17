<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.23.0-599796a modeling language!*/

class Song
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Song Attributes
  private $title;
  private $duration;
  private $position;

  //Song Associations
  private $album;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aTitle, $aDuration, $aPosition, $aAlbum)
  {
    $this->title = $aTitle;
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

  public function setTitle($aTitle)
  {
    $wasSet = false;
    $this->title = $aTitle;
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

  public function getTitle()
  {
    return $this->title;
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
    $placeholderAlbum = $this->album;
    $this->album = null;
    $placeholderAlbum->removeSong($this);
  }

}
?>