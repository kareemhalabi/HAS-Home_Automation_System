/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.23.0-2950f84 modeling language!*/

package ca.mcgill.ecse321.HAS.model;

import java.sql.Date;
import java.util.*;

// line 47 "../../../../../../../../ump/160303721337/model.ump"
// line 130 "../../../../../../../../ump/160303721337/model.ump"
// line 168 "../../../../../../../../ump/160303721337/model.ump"
public class Album extends Playable implements Comparable<Album>
{

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Album Attributes
	private String genre;
	private Date releaseDate;

	// Album Associations
	private List<Song> songs;
	private Artist mainArtist;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Album(String aName, String aGenre, Date aReleaseDate, Artist aMainArtist)
	{
		super(aName);
		genre = aGenre;
		releaseDate = aReleaseDate;
		songs = new ArrayList<Song>();
		boolean didAddMainArtist = setMainArtist(aMainArtist);
		if (!didAddMainArtist)
		{
			throw new RuntimeException("Unable to create album due to mainArtist");
		}
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public boolean setGenre(String aGenre)
	{
		boolean wasSet = false;
		genre = aGenre;
		wasSet = true;
		return wasSet;
	}

	public boolean setReleaseDate(Date aReleaseDate)
	{
		boolean wasSet = false;
		releaseDate = aReleaseDate;
		wasSet = true;
		return wasSet;
	}

	public String getGenre()
	{
		return genre;
	}

	public Date getReleaseDate()
	{
		return releaseDate;
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

	public Artist getMainArtist()
	{
		return mainArtist;
	}

	public static int minimumNumberOfSongs()
	{
		return 0;
	}

	public Song addSong(String aName, int aDuration, int aPosition)
	{
		return new Song(aName, aDuration, aPosition, this);
	}

	public boolean addSong(Song aSong)
	{
		boolean wasAdded = false;
		if (songs.contains(aSong))
		{
			return false;
		}
		Album existingAlbum = aSong.getAlbum();
		boolean isNewAlbum = existingAlbum != null && !this.equals(existingAlbum);
		if (isNewAlbum)
		{
			aSong.setAlbum(this);
		} else
		{
			songs.add(aSong);
		}
		wasAdded = true;
		return wasAdded;
	}

	public boolean removeSong(Song aSong)
	{
		boolean wasRemoved = false;
		// Unable to remove aSong, as it must always have a album
		if (!this.equals(aSong.getAlbum()))
		{
			songs.remove(aSong);
			wasRemoved = true;
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

	public boolean setMainArtist(Artist aMainArtist)
	{
		boolean wasSet = false;
		if (aMainArtist == null)
		{
			return wasSet;
		}

		Artist existingMainArtist = mainArtist;
		mainArtist = aMainArtist;
		if (existingMainArtist != null && !existingMainArtist.equals(aMainArtist))
		{
			existingMainArtist.removeAlbum(this);
		}
		mainArtist.addAlbum(this);
		wasSet = true;
		return wasSet;
	}

	public void delete()
	{
		for (int i = songs.size(); i > 0; i--)
		{
			Song aSong = songs.get(i - 1);
			aSong.delete();
		}
		Artist placeholderMainArtist = mainArtist;
		this.mainArtist = null;
		placeholderMainArtist.removeAlbum(this);
		super.delete();
	}

	public void play()
	{
		for (Song s : songs)
			s.play();
	}

	public String toString()
	{
		String outputString = "";
		return super.toString() + "[" + "genre" + ":" + getGenre() + "]"
				+ System.getProperties().getProperty("line.separator") + "  " + "releaseDate" + "="
				+ (getReleaseDate() != null
						? !getReleaseDate().equals(this) ? getReleaseDate().toString().replaceAll("  ", "    ") : "this"
						: "null")
				+ System.getProperties().getProperty("line.separator") + "  " + "mainArtist = "
				+ (getMainArtist() != null ? Integer.toHexString(System.identityHashCode(getMainArtist())) : "null")
				+ outputString;
	}

	@Override
	public int compareTo(Album o)
	{
		assert (o != null);
		return this.getName().compareTo(o.getName());
	}
}