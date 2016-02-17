<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.23.0-599796a modeling language!*/

class Room
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Room Attributes
  private $name;
  private $volume;
  private $mute;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aName, $aVolume, $aMute)
  {
    $this->name = $aName;
    $this->volume = $aVolume;
    $this->mute = $aMute;
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

  public function setVolume($aVolume)
  {
    $wasSet = false;
    $this->volume = $aVolume;
    $wasSet = true;
    return $wasSet;
  }

  public function setMute($aMute)
  {
    $wasSet = false;
    $this->mute = $aMute;
    $wasSet = true;
    return $wasSet;
  }

  public function getName()
  {
    return $this->name;
  }

  public function getVolume()
  {
    return $this->volume;
  }

  public function getMute()
  {
    return $this->mute;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {}

}
?>