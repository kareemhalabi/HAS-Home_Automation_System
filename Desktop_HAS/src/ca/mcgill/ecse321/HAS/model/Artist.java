/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse321.HAS.model;
import java.util.*;

// line 26 "../../../../../HAS_domain_model.ump"
// line 67 "../../../../../HAS_domain_model.ump"
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

  public boolean addAlbum(Album aAlbum)
  {
    boolean wasAdded = false;
    if (albums.contains(aAlbum)) { return false; }
    albums.add(aAlbum);
    if (aAlbum.indexOfArtist(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aAlbum.addArtist(this);
      if (!wasAdded)
      {
        albums.remove(aAlbum);
      }
    }
    return wasAdded;
  }

  public boolean removeAlbum(Album aAlbum)
  {
    boolean wasRemoved = false;
    if (!albums.contains(aAlbum))
    {
      return wasRemoved;
    }

    int oldIndex = albums.indexOf(aAlbum);
    albums.remove(oldIndex);
    if (aAlbum.indexOfArtist(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aAlbum.removeArtist(this);
      if (!wasRemoved)
      {
        albums.add(oldIndex,aAlbum);
      }
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
    ArrayList<Album> copyOfAlbums = new ArrayList<Album>(albums);
    albums.clear();
    for(Album aAlbum : copyOfAlbums)
    {
      aAlbum.removeArtist(this);
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