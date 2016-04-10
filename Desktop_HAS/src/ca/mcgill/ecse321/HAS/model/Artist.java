/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.23.0-2950f84 modeling language!*/

package ca.mcgill.ecse321.HAS.model;

import java.util.*;
import java.sql.Date;

// line 42 "../../../../../../../../ump/160303721337/model.ump"
// line 124 "../../../../../../../../ump/160303721337/model.ump"
// line 163 "../../../../../../../../ump/160303721337/model.ump"
public class Artist implements Comparable<Artist>
{

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Artist Attributes
	private String name;

	// Artist Associations
	private List<Song> songs;
	private List<Album> albums;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Artist(String aName)
	{
		name = aName;
		songs = new ArrayList<Song>();
		albums = new ArrayList<Album>();
	}

	// ------------------------
	// INTERFACE
	// ------------------------

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

	public Song getSong(int index)
	{
		Song aSong = songs.get(index);
		return aSong;
	}

	public List<Song> getSongs()
	{
		List<Song> newSongs = Collections.unmodifiableList(songs);
		return newSongs;
	}

	public int numberOfSongs()
	{
		int number = songs.size();
		return number;
	}

	public boolean hasSongs()
	{
		boolean has = songs.size() > 0;
		return has;
	}

	public int indexOfSong(Song aSong)
	{
		int index = songs.indexOf(aSong);
		return index;
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

	public static int minimumNumberOfSongs()
	{
		return 0;
	}

	public boolean addSong(Song aSong)
	{
		boolean wasAdded = false;
		if (songs.contains(aSong))
		{
			return false;
		}
		songs.add(aSong);
		if (aSong.indexOfFtArtist(this) != -1)
		{
			wasAdded = true;
		} else
		{
			wasAdded = aSong.addFtArtist(this);
			if (!wasAdded)
			{
				songs.remove(aSong);
			}
		}
		return wasAdded;
	}

	public boolean removeSong(Song aSong)
	{
		boolean wasRemoved = false;
		if (!songs.contains(aSong))
		{
			return wasRemoved;
		}

		int oldIndex = songs.indexOf(aSong);
		songs.remove(oldIndex);
		if (aSong.indexOfFtArtist(this) == -1)
		{
			wasRemoved = true;
		} else
		{
			wasRemoved = aSong.removeFtArtist(this);
			if (!wasRemoved)
			{
				songs.add(oldIndex, aSong);
			}
		}
		return wasRemoved;
	}

	public boolean addSongAt(Song aSong, int index)
	{
		boolean wasAdded = false;
		if (addSong(aSong))
		{
			if (index < 0)
			{
				index = 0;
			}
			if (index > numberOfSongs())
			{
				index = numberOfSongs() - 1;
			}
			songs.remove(aSong);
			songs.add(index, aSong);
			wasAdded = true;
		}
		return wasAdded;
	}

	public boolean addOrMoveSongAt(Song aSong, int index)
	{
		boolean wasAdded = false;
		if (songs.contains(aSong))
		{
			if (index < 0)
			{
				index = 0;
			}
			if (index > numberOfSongs())
			{
				index = numberOfSongs() - 1;
			}
			songs.remove(aSong);
			songs.add(index, aSong);
			wasAdded = true;
		} else
		{
			wasAdded = addSongAt(aSong, index);
		}
		return wasAdded;
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
		if (albums.contains(aAlbum))
		{
			return false;
		}
		Artist existingMainArtist = aAlbum.getMainArtist();
		boolean isNewMainArtist = existingMainArtist != null
				&& !this.equals(existingMainArtist);
		if (isNewMainArtist)
		{
			aAlbum.setMainArtist(this);
		} else
		{
			albums.add(aAlbum);
		}
		wasAdded = true;
		return wasAdded;
	}

	public boolean removeAlbum(Album aAlbum)
	{
		boolean wasRemoved = false;
		// Unable to remove aAlbum, as it must always have a mainArtist
		if (!this.equals(aAlbum.getMainArtist()))
		{
			albums.remove(aAlbum);
			wasRemoved = true;
		}
		return wasRemoved;
	}

	public boolean addAlbumAt(Album aAlbum, int index)
	{
		boolean wasAdded = false;
		if (addAlbum(aAlbum))
		{
			if (index < 0)
			{
				index = 0;
			}
			if (index > numberOfAlbums())
			{
				index = numberOfAlbums() - 1;
			}
			albums.remove(aAlbum);
			albums.add(index, aAlbum);
			wasAdded = true;
		}
		return wasAdded;
	}

	public boolean addOrMoveAlbumAt(Album aAlbum, int index)
	{
		boolean wasAdded = false;
		if (albums.contains(aAlbum))
		{
			if (index < 0)
			{
				index = 0;
			}
			if (index > numberOfAlbums())
			{
				index = numberOfAlbums() - 1;
			}
			albums.remove(aAlbum);
			albums.add(index, aAlbum);
			wasAdded = true;
		} else
		{
			wasAdded = addAlbumAt(aAlbum, index);
		}
		return wasAdded;
	}

	public void delete()
	{
		ArrayList<Song> copyOfSongs = new ArrayList<Song>(songs);
		songs.clear();
		for (Song aSong : copyOfSongs)
		{
			aSong.removeFtArtist(this);
		}
		for (int i = albums.size(); i > 0; i--)
		{
			Album aAlbum = albums.get(i - 1);
			aAlbum.delete();
		}
	}

	public String toString()
	{
		String outputString = "";
		return super.toString() + "[" + "name" + ":" + getName() + "]"
				+ outputString;
	}

	@Override
	public int compareTo(Artist o)
	{
		assert (o != null);
		return name.compareTo(o.getName());
	}
}