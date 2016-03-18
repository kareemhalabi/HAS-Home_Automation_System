<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.23.0-599796a modeling language!*/

class RoomGroup
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //RoomGroup Attributes
  private $name;

  //RoomGroup Associations
  private $rooms;
  public $playable; //until PHP 5.3 (setAccessible)

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aName, $allRooms)
  {
    $this->name = $aName;
    $this->rooms = array();
    $didAddRooms = $this->setRooms($allRooms);
    if (!$didAddRooms)
    {
      throw new Exception("Unable to create RoomGroup, must have at least 1 rooms");
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

  public function getPlayable()
  {
    return $this->playable;
  }

  public function hasPlayable()
  {
    $has = $this->playable != null;
    return $has;
  }

  public static function minimumNumberOfRooms()
  {
    return 1;
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
    if ($this->indexOfRoom($aRoom) == -1)
    {
      return $wasRemoved;
    }

    if ($this->numberOfRooms() <= self::minimumNumberOfRooms())
    {
      return $wasRemoved;
    }

    unset($this->rooms[$this->indexOfRoom($aRoom)]);
    $this->rooms = array_values($this->rooms);
    $wasRemoved = true;
    return $wasRemoved;
  }

  public function setRooms($newRooms)
  {
    $wasSet = false;
    $verifiedRooms = array();
    foreach ($newRooms as $aRoom)
    {
      if (array_search($aRoom,$verifiedRooms) !== false)
      {
        continue;
      }
      $verifiedRooms[] = $aRoom;
    }

    if (count($verifiedRooms) != count($newRooms) || count($verifiedRooms) < self::minimumNumberOfRooms())
    {
      return $wasSet;
    }

    $this->rooms = $verifiedRooms;
    $wasSet = true;
    return $wasSet;
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

  public function setPlayable($aNewPlayable)
  {
    $wasSet = false;
    $this->playable = $aNewPlayable;
    $wasSet = true;
    return $wasSet;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    $this->rooms = array();
    $this->playable = null;
  }

}
?>