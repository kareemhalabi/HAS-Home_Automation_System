/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse321.HAS.model;

// line 3 "../../../../../HAS_Domain_Model.ump"
// line 49 "../../../../../HAS_Domain_Model.ump"
public class Song
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Song Attributes
  private String name;
  private int duration;
  private int position;

  //Song Associations
  private Album album;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Song(String aName, int aDuration, int aPosition, Album aAlbum)
  {
    name = aName;
    duration = aDuration;
    position = aPosition;
    boolean didAddAlbum = setAlbum(aAlbum);
    if (!didAddAlbum)
    {
      throw new RuntimeException("Unable to create song due to album");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setDuration(int aDuration)
  {
    boolean wasSet = false;
    duration = aDuration;
    wasSet = true;
    return wasSet;
  }

  public boolean setPosition(int aPosition)
  {
    boolean wasSet = false;
    position = aPosition;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public int getDuration()
  {
    return duration;
  }

  public int getPosition()
  {
    return position;
  }

  public Album getAlbum()
  {
    return album;
  }

  public boolean setAlbum(Album aAlbum)
  {
    boolean wasSet = false;
    //Must provide album to song
    if (aAlbum == null)
    {
      return wasSet;
    }

    if (album != null && album.numberOfSongs() <= Album.minimumNumberOfSongs())
    {
      return wasSet;
    }

    Album existingAlbum = album;
    album = aAlbum;
    if (existingAlbum != null && !existingAlbum.equals(aAlbum))
    {
      boolean didRemove = existingAlbum.removeSong(this);
      if (!didRemove)
      {
        album = existingAlbum;
        return wasSet;
      }
    }
    album.addSong(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Album placeholderAlbum = album;
    this.album = null;
    placeholderAlbum.removeSong(this);
  }


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "duration" + ":" + getDuration()+ "," +
            "position" + ":" + getPosition()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "album = "+(getAlbum()!=null?Integer.toHexString(System.identityHashCode(getAlbum())):"null")
     + outputString;
  }
}