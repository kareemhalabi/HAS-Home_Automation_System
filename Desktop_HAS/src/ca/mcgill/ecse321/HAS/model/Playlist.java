/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.23.0-2950f84 modeling language!*/

package ca.mcgill.ecse321.HAS.model;

import java.util.*;

// line 73 "../../../../../../../../ump/160303721337/model.ump"
// line 137 "../../../../../../../../ump/160303721337/model.ump"
public class Playlist extends Playable
{

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Playlist Associations
	private List<Song> songs;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Playlist(String aName, Song... allSongs)
	{
		super(aName);
		songs = new ArrayList<Song>();
		boolean didAddSongs = setSongs(allSongs);
		if (!didAddSongs)
		{
			throw new RuntimeException(
					"Unable to create Playlist, must have at least 1 songs");
		}
	}

	// ------------------------
	// INTERFACE
	// ------------------------

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

	public static int minimumNumberOfSongs()
	{
		return 1;
	}

	public boolean addSong(Song aSong)
	{
		boolean wasAdded = false;
		if (songs.contains(aSong))
		{
			return false;
		}
		songs.add(aSong);
		wasAdded = true;
		return wasAdded;
	}

	public boolean removeSong(Song aSong)
	{
		boolean wasRemoved = false;
		if (!songs.contains(aSong))
		{
			return wasRemoved;
		}

		if (numberOfSongs() <= minimumNumberOfSongs())
		{
			return wasRemoved;
		}

		songs.remove(aSong);
		wasRemoved = true;
		return wasRemoved;
	}

	public boolean setSongs(Song... newSongs)
	{
		boolean wasSet = false;
		ArrayList<Song> verifiedSongs = new ArrayList<Song>();
		for (Song aSong : newSongs)
		{
			if (verifiedSongs.contains(aSong))
			{
				continue;
			}
			verifiedSongs.add(aSong);
		}

		if (verifiedSongs.size() != newSongs.length
				|| verifiedSongs.size() < minimumNumberOfSongs())
		{
			return wasSet;
		}

		songs.clear();
		songs.addAll(verifiedSongs);
		wasSet = true;
		return wasSet;
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

	public void delete()
	{
		songs.clear();
		super.delete();
	}

	public void play()
	{
	}

}
