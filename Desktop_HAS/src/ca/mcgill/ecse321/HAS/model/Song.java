/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse321.HAS.model;

// line 3 "../../../../../HAS_domain_model.ump"
// line 48 "../../../../../HAS_domain_model.ump"
public class Song
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Song Attributes
  private String title;
  private int duration;
  private int position;

  //Song Associations
  private Album album;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Song(String aTitle, int aDuration, int aPosition, Album aAlbum)
  {
    title = aTitle;
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

  public boolean setTitle(String aTitle)
  {
    boolean wasSet = false;
    title = aTitle;
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

  public String getTitle()
  {
    return title;
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
    if (aAlbum == null)
    {
      return wasSet;
    }

    Album existingAlbum = album;
    album = aAlbum;
    if (existingAlbum != null && !existingAlbum.equals(aAlbum))
    {
      existingAlbum.removeSong(this);
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
            "title" + ":" + getTitle()+ "," +
            "duration" + ":" + getDuration()+ "," +
            "position" + ":" + getPosition()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "album = "+(getAlbum()!=null?Integer.toHexString(System.identityHashCode(getAlbum())):"null")
     + outputString;
  }
}