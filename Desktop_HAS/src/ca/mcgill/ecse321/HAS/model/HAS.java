/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.23.0-2950f84 modeling language!*/

package ca.mcgill.ecse321.HAS.model;

import java.util.*;

// line 17 "../../../../../../../../ump/160303721337/model.ump"
// line 107 "../../../../../../../../ump/160303721337/model.ump"
// line 153 "../../../../../../../../ump/160303721337/model.ump"
public class HAS
{

	// ------------------------
	// STATIC VARIABLES
	// ------------------------

	private static HAS theInstance = null;

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// HAS Associations
	private List<Room> rooms;
	private List<Artist> artists;
	private List<Album> albums;
	private List<Playlist> playlists;
	private List<Song> songs;
	private List<RoomGroup> roomGroups;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	private HAS()
	{
		rooms = new ArrayList<Room>();
		artists = new ArrayList<Artist>();
		albums = new ArrayList<Album>();
		playlists = new ArrayList<Playlist>();
		songs = new ArrayList<Song>();
		roomGroups = new ArrayList<RoomGroup>();
	}

	public static HAS getInstance()
	{
		if (theInstance == null)
		{
			theInstance = new HAS();
		}
		return theInstance;
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public Room getRoom(int index)
	{
		Room aRoom = rooms.get(index);
		return aRoom;
	}

	public List<Room> getRooms()
	{
		List<Room> newRooms = Collections.unmodifiableList(rooms);
		return newRooms;
	}

	public int numberOfRooms()
	{
		int number = rooms.size();
		return number;
	}

	public boolean hasRooms()
	{
		boolean has = rooms.size() > 0;
		return has;
	}

	public int indexOfRoom(Room aRoom)
	{
		int index = rooms.indexOf(aRoom);
		return index;
	}

	public Artist getArtist(int index)
	{
		Artist aArtist = artists.get(index);
		return aArtist;
	}

	public List<Artist> getArtists()
	{
		List<Artist> newArtists = Collections.unmodifiableList(artists);
		return newArtists;
	}

	public int numberOfArtists()
	{
		int number = artists.size();
		return number;
	}

	public boolean hasArtists()
	{
		boolean has = artists.size() > 0;
		return has;
	}

	public int indexOfArtist(Artist aArtist)
	{
		int index = artists.indexOf(aArtist);
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

	public Playlist getPlaylist(int index)
	{
		Playlist aPlaylist = playlists.get(index);
		return aPlaylist;
	}

	public List<Playlist> getPlaylists()
	{
		List<Playlist> newPlaylists = Collections.unmodifiableList(playlists);
		return newPlaylists;
	}

	public int numberOfPlaylists()
	{
		int number = playlists.size();
		return number;
	}

	public boolean hasPlaylists()
	{
		boolean has = playlists.size() > 0;
		return has;
	}

	public int indexOfPlaylist(Playlist aPlaylist)
	{
		int index = playlists.indexOf(aPlaylist);
		return index;
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

	public RoomGroup getRoomGroup(int index)
	{
		RoomGroup aRoomGroup = roomGroups.get(index);
		return aRoomGroup;
	}

	public List<RoomGroup> getRoomGroups()
	{
		List<RoomGroup> newRoomGroups = Collections
				.unmodifiableList(roomGroups);
		return newRoomGroups;
	}

	public int numberOfRoomGroups()
	{
		int number = roomGroups.size();
		return number;
	}

	public boolean hasRoomGroups()
	{
		boolean has = roomGroups.size() > 0;
		return has;
	}

	public int indexOfRoomGroup(RoomGroup aRoomGroup)
	{
		int index = roomGroups.indexOf(aRoomGroup);
		return index;
	}

	public static int minimumNumberOfRooms()
	{
		return 0;
	}

	public boolean addRoom(Room aRoom)
	{
		boolean wasAdded = false;
		if (rooms.contains(aRoom))
		{
			return false;
		}
		rooms.add(aRoom);
		wasAdded = true;
		return wasAdded;
	}

	public boolean removeRoom(Room aRoom)
	{
		boolean wasRemoved = false;
		if (rooms.contains(aRoom))
		{
			rooms.remove(aRoom);
			wasRemoved = true;
		}
		return wasRemoved;
	}

	public boolean addRoomAt(Room aRoom, int index)
	{
		boolean wasAdded = false;
		if (addRoom(aRoom))
		{
			if (index < 0)
			{
				index = 0;
			}
			if (index > numberOfRooms())
			{
				index = numberOfRooms() - 1;
			}
			rooms.remove(aRoom);
			rooms.add(index, aRoom);
			wasAdded = true;
		}
		return wasAdded;
	}

	public boolean addOrMoveRoomAt(Room aRoom, int index)
	{
		boolean wasAdded = false;
		if (rooms.contains(aRoom))
		{
			if (index < 0)
			{
				index = 0;
			}
			if (index > numberOfRooms())
			{
				index = numberOfRooms() - 1;
			}
			rooms.remove(aRoom);
			rooms.add(index, aRoom);
			wasAdded = true;
		} else
		{
			wasAdded = addRoomAt(aRoom, index);
		}
		return wasAdded;
	}

	public static int minimumNumberOfArtists()
	{
		return 0;
	}

	public boolean addArtist(Artist aArtist)
	{
		boolean wasAdded = false;
		if (artists.contains(aArtist))
		{
			return false;
		}
		artists.add(aArtist);
		wasAdded = true;
		return wasAdded;
	}

	public boolean removeArtist(Artist aArtist)
	{
		boolean wasRemoved = false;
		if (artists.contains(aArtist))
		{
			artists.remove(aArtist);
			wasRemoved = true;
		}
		return wasRemoved;
	}

	public boolean addArtistAt(Artist aArtist, int index)
	{
		boolean wasAdded = false;
		if (addArtist(aArtist))
		{
			if (index < 0)
			{
				index = 0;
			}
			if (index > numberOfArtists())
			{
				index = numberOfArtists() - 1;
			}
			artists.remove(aArtist);
			artists.add(index, aArtist);
			wasAdded = true;
		}
		return wasAdded;
	}

	public boolean addOrMoveArtistAt(Artist aArtist, int index)
	{
		boolean wasAdded = false;
		if (artists.contains(aArtist))
		{
			if (index < 0)
			{
				index = 0;
			}
			if (index > numberOfArtists())
			{
				index = numberOfArtists() - 1;
			}
			artists.remove(aArtist);
			artists.add(index, aArtist);
			wasAdded = true;
		} else
		{
			wasAdded = addArtistAt(aArtist, index);
		}
		return wasAdded;
	}

	public static int minimumNumberOfAlbums()
	{
		return 0;
	}

	public boolean addAlbum(Album aAlbum)
	{
		boolean wasAdded = false;
		if (albums.contains(aAlbum))
		{
			return false;
		}
		albums.add(aAlbum);
		wasAdded = true;
		return wasAdded;
	}

	public boolean removeAlbum(Album aAlbum)
	{
		boolean wasRemoved = false;
		if (albums.contains(aAlbum))
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

	public static int minimumNumberOfPlaylists()
	{
		return 0;
	}

	public boolean addPlaylist(Playlist aPlaylist)
	{
		boolean wasAdded = false;
		if (playlists.contains(aPlaylist))
		{
			return false;
		}
		playlists.add(aPlaylist);
		wasAdded = true;
		return wasAdded;
	}

	public boolean removePlaylist(Playlist aPlaylist)
	{
		boolean wasRemoved = false;
		if (playlists.contains(aPlaylist))
		{
			playlists.remove(aPlaylist);
			wasRemoved = true;
		}
		return wasRemoved;
	}

	public boolean addPlaylistAt(Playlist aPlaylist, int index)
	{
		boolean wasAdded = false;
		if (addPlaylist(aPlaylist))
		{
			if (index < 0)
			{
				index = 0;
			}
			if (index > numberOfPlaylists())
			{
				index = numberOfPlaylists() - 1;
			}
			playlists.remove(aPlaylist);
			playlists.add(index, aPlaylist);
			wasAdded = true;
		}
		return wasAdded;
	}

	public boolean addOrMovePlaylistAt(Playlist aPlaylist, int index)
	{
		boolean wasAdded = false;
		if (playlists.contains(aPlaylist))
		{
			if (index < 0)
			{
				index = 0;
			}
			if (index > numberOfPlaylists())
			{
				index = numberOfPlaylists() - 1;
			}
			playlists.remove(aPlaylist);
			playlists.add(index, aPlaylist);
			wasAdded = true;
		} else
		{
			wasAdded = addPlaylistAt(aPlaylist, index);
		}
		return wasAdded;
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
		wasAdded = true;
		return wasAdded;
	}

	public boolean removeSong(Song aSong)
	{
		boolean wasRemoved = false;
		if (songs.contains(aSong))
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

	public static int minimumNumberOfRoomGroups()
	{
		return 0;
	}

	public boolean addRoomGroup(RoomGroup aRoomGroup)
	{
		boolean wasAdded = false;
		if (roomGroups.contains(aRoomGroup))
		{
			return false;
		}
		roomGroups.add(aRoomGroup);
		wasAdded = true;
		return wasAdded;
	}

	public boolean removeRoomGroup(RoomGroup aRoomGroup)
	{
		boolean wasRemoved = false;
		if (roomGroups.contains(aRoomGroup))
		{
			roomGroups.remove(aRoomGroup);
			wasRemoved = true;
		}
		return wasRemoved;
	}

	public boolean addRoomGroupAt(RoomGroup aRoomGroup, int index)
	{
		boolean wasAdded = false;
		if (addRoomGroup(aRoomGroup))
		{
			if (index < 0)
			{
				index = 0;
			}
			if (index > numberOfRoomGroups())
			{
				index = numberOfRoomGroups() - 1;
			}
			roomGroups.remove(aRoomGroup);
			roomGroups.add(index, aRoomGroup);
			wasAdded = true;
		}
		return wasAdded;
	}

	public boolean addOrMoveRoomGroupAt(RoomGroup aRoomGroup, int index)
	{
		boolean wasAdded = false;
		if (roomGroups.contains(aRoomGroup))
		{
			if (index < 0)
			{
				index = 0;
			}
			if (index > numberOfRoomGroups())
			{
				index = numberOfRoomGroups() - 1;
			}
			roomGroups.remove(aRoomGroup);
			roomGroups.add(index, aRoomGroup);
			wasAdded = true;
		} else
		{
			wasAdded = addRoomGroupAt(aRoomGroup, index);
		}
		return wasAdded;
	}

	public void delete()
	{
		rooms.clear();
		artists.clear();
		albums.clear();
		playlists.clear();
		songs.clear();
		roomGroups.clear();
	}

}