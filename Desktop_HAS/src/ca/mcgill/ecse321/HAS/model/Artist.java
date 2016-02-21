/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse321.HAS.model;
import java.util.*;
import java.sql.Date;

// line 27 "../../../../../HAS_Domain_Model.ump"
// line 68 "../../../../../HAS_Domain_Model.ump"
public class Artist
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Artist Attributes
  private String name;

  //Artist Associations
  private List<Album> albums;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Artist(String aName)
  {
    name = aName;
    albums = new ArrayList<Album>();
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

  public String getName()
  {
    return name;
  }

  public Album getAlbum(int index)
  {
    Album aAlbum = albums.get(index);
    return aAlbum;
  }

  public List<Album> getAlbums()
  {
    List<Album> newAlbums = Collections.unmodifiableList(albums);
    return newAlbums;
  }

  public int numberOfAlbums()
  {
    int number = albums.size();
    return number;
  }

  public boolean hasAlbums()
  {
    boolean has = albums.size() > 0;
    return has;
  }

  public int indexOfAlbum(Album aAlbum)
  {
    int index = albums.indexOf(aAlbum);
    return index;
  }

  public static int minimumNumberOfAlbums()
  {
    return 0;
  }

  public Album addAlbum(String aName, String aGenre, Date aReleaseDate)
  {
    return new Album(aName, aGenre, aReleaseDate, this);
  }

  public boolean addAlbum(Album aAlbum)
  {
    boolean wasAdded = false;
    if (albums.contains(aAlbum)) { return false; }
    Artist existingArtist = aAlbum.getArtist();
    boolean isNewArtist = existingArtist != null && !this.equals(existingArtist);
    if (isNewArtist)
    {
      aAlbum.setArtist(this);
    }
    else
    {
      albums.add(aAlbum);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAlbum(Album aAlbum)
  {
    boolean wasRemoved = false;
    //Unable to remove aAlbum, as it must always have a artist
    if (!this.equals(aAlbum.getArtist()))
    {
      albums.remove(aAlbum);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addAlbumAt(Album aAlbum, int index)
  {  
    boolean wasAdded = false;
    if(addAlbum(aAlbum))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAlbums()) { index = numberOfAlbums() - 1; }
      albums.remove(aAlbum);
      albums.add(index, aAlbum);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAlbumAt(Album aAlbum, int index)
  {
    boolean wasAdded = false;
    if(albums.contains(aAlbum))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAlbums()) { index = numberOfAlbums() - 1; }
      albums.remove(aAlbum);
      albums.add(index, aAlbum);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAlbumAt(aAlbum, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=albums.size(); i > 0; i--)
    {
      Album aAlbum = albums.get(i - 1);
      aAlbum.delete();
    }
  }


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "name" + ":" + getName()+ "]"
     + outputString;
  }
}