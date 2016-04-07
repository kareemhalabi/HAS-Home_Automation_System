/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.23.0-2950f84 modeling language!*/

package ca.mcgill.ecse321.HAS.model;

import java.util.*;

// line 4 "../../../../../../../../ump/160303721337/model.ump"
// line 100 "../../../../../../../../ump/160303721337/model.ump"
// line 143 "../../../../../../../../ump/160303721337/model.ump"
// line 148 "../../../../../../../../ump/160303721337/model.ump"
public class Song extends Playable implements Comparable<Song>
{

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Song Attributes
	private int duration;
	private int position;

	// Song Associations
	private List<Artist> ftArtists;
	private Album album;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Song(String aName, int aDuration, int aPosition, Album aAlbum)
	{
		super(aName);
		duration = aDuration;
		position = aPosition;
		ftArtists = new ArrayList<Artist>();
		boolean didAddAlbum = setAlbum(aAlbum);
		if (!didAddAlbum)
		{
			throw new RuntimeException("Unable to create song due to album");
		}
	}

	// ------------------------
	// INTERFACE
	// ------------------------

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

	public int getDuration()
	{
		return duration;
	}

	public int getPosition()
	{
		return position;
	}

	public Artist getFtArtist(int index)
	{
		Artist aFtArtist = ftArtists.get(index);
		return aFtArtist;
	}

	public List<Artist> getFtArtists()
	{
		List<Artist> newFtArtists = Collections.unmodifiableList(ftArtists);
		return newFtArtists;
	}

	public int numberOfFtArtists()
	{
		int number = ftArtists.size();
		return number;
	}

	public boolean hasFtArtists()
	{
		boolean has = ftArtists.size() > 0;
		return has;
	}

	public int indexOfFtArtist(Artist aFtArtist)
	{
		int index = ftArtists.indexOf(aFtArtist);
		return index;
	}

	public Album getAlbum()
	{
		return album;
	}

	public static int minimumNumberOfFtArtists()
	{
		return 0;
	}

	public boolean addFtArtist(Artist aFtArtist)
	{
		boolean wasAdded = false;
		if (ftArtists.contains(aFtArtist))
		{
			return false;
		}
		ftArtists.add(aFtArtist);
		if (aFtArtist.indexOfSong(this) != -1)
		{
			wasAdded = true;
		} else
		{
			wasAdded = aFtArtist.addSong(this);
			if (!wasAdded)
			{
				ftArtists.remove(aFtArtist);
			}
		}
		return wasAdded;
	}

	public boolean removeFtArtist(Artist aFtArtist)
	{
		boolean wasRemoved = false;
		if (!ftArtists.contains(aFtArtist))
		{
			return wasRemoved;
		}

		int oldIndex = ftArtists.indexOf(aFtArtist);
		ftArtists.remove(oldIndex);
		if (aFtArtist.indexOfSong(this) == -1)
		{
			wasRemoved = true;
		} else
		{
			wasRemoved = aFtArtist.removeSong(this);
			if (!wasRemoved)
			{
				ftArtists.add(oldIndex, aFtArtist);
			}
		}
		return wasRemoved;
	}

	public boolean addFtArtistAt(Artist aFtArtist, int index)
	{
		boolean wasAdded = false;
		if (addFtArtist(aFtArtist))
		{
			if (index < 0)
			{
				index = 0;
			}
			if (index > numberOfFtArtists())
			{
				index = numberOfFtArtists() - 1;
			}
			ftArtists.remove(aFtArtist);
			ftArtists.add(index, aFtArtist);
			wasAdded = true;
		}
		return wasAdded;
	}

	public boolean addOrMoveFtArtistAt(Artist aFtArtist, int index)
	{
		boolean wasAdded = false;
		if (ftArtists.contains(aFtArtist))
		{
			if (index < 0)
			{
				index = 0;
			}
			if (index > numberOfFtArtists())
			{
				index = numberOfFtArtists() - 1;
			}
			ftArtists.remove(aFtArtist);
			ftArtists.add(index, aFtArtist);
			wasAdded = true;
		} else
		{
			wasAdded = addFtArtistAt(aFtArtist, index);
		}
		return wasAdded;
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
		ArrayList<Artist> copyOfFtArtists = new ArrayList<Artist>(ftArtists);
		ftArtists.clear();
		for (Artist aFtArtist : copyOfFtArtists)
		{
			aFtArtist.removeSong(this);
		}
		Album placeholderAlbum = album;
		this.album = null;
		placeholderAlbum.removeSong(this);
		super.delete();
	}

	/**
	 * Java public void play() {} PHP
	 */
	// line 14 "../../../../../../../../ump/160303721337/model.ump"
	public void play()
	{
	}

	public String toString()
	{
		String outputString = "";
		return super.toString() + "[" + "duration" + ":" + getDuration() + ","
				+ "position" + ":" + getPosition() + "]"
				+ System.getProperties().getProperty("line.separator") + "  "
				+ "album = "
				+ (getAlbum() != null ? Integer.toHexString(
						System.identityHashCode(getAlbum())) : "null")
				+ outputString;
	}

	@Override
	public int compareTo(Song o)
	{
		assert (o != null);
		return position - o.getPosition();
	}
}