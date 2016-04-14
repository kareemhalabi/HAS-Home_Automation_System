package ca.mcgill.ecse321.HAS.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import ca.mcgill.ecse321.HAS.model.Album;
import ca.mcgill.ecse321.HAS.model.Artist;
import ca.mcgill.ecse321.HAS.model.HAS;
import ca.mcgill.ecse321.HAS.model.Playable;
import ca.mcgill.ecse321.HAS.model.Playlist;
import ca.mcgill.ecse321.HAS.model.Room;
import ca.mcgill.ecse321.HAS.model.RoomGroup;
import ca.mcgill.ecse321.HAS.model.Song;
import ca.mcgill.ecse321.HAS.persistence.PersistenceXStream;

public class HASController
{

	/**
	 * Creates an artist object within the HAS system
	 * 
	 * @param artName
	 *            The name of the new artist
	 * @throws InvalidInputException
	 */
	public void createArtist(String artName) throws InvalidInputException
	{
		if (artName == null || artName.trim().length() == 0)
			throw new InvalidInputException("Artist name cannot be empty! ");

		Artist art = new Artist(artName);
		HAS h = HAS.getInstance();
		h.addArtist(art);

		sortArtists();
		PersistenceXStream.saveToXMLwithXStream(h);
	}

	/**
	 * Creates an album object within the HAS system, linking it to its artist
	 * 
	 * @param name
	 *            Album name
	 * @param genre
	 *            Album genre
	 * @param releaseDate
	 *            Album release date
	 * @param ar
	 *            Album artist
	 * @throws InvalidInputException
	 */
	public void createAlbum(String name, String genre, Date releaseDate,
							Artist ar) throws InvalidInputException
	{
		java.util.Calendar cal = Calendar.getInstance();
		java.util.Date utilDate = new java.util.Date();
		cal.setTime(utilDate);
		java.sql.Date sqlDate = new java.sql.Date(cal.getTime().getTime());

		String error = "";
		if (name == null || name.trim().length() == 0)
			error += "Album name cannot be empty! ";
		if (genre == null || genre.trim().length() == 0)
			error += "Genre name cannot be empty! ";
		if (releaseDate == null)
			error += "Release date cannot be empty! ";
		else if (releaseDate.after(sqlDate) == true)
			error += "Release date cannot be in the future! ";
		if (ar == null)
			error += "Album must have an artist! ";
		if (error.trim().length() > 0)
			throw new InvalidInputException(error);

		Album a = new Album(name, genre, releaseDate, ar);
		HAS h = HAS.getInstance();
		h.addAlbum(a);

		sortAlbums();

		PersistenceXStream.saveToXMLwithXStream(h);
	}

	/**
	 * Creates a song within the system
	 * 
	 * @param a
	 *            Album to which the song is affiliated
	 * @param aName
	 *            Name of the song
	 * @param aDuration
	 *            Duration of the song in seconds
	 * @param aPosition
	 *            Position of the song within the album
	 * @param ftArtists
	 *            List of featured artists
	 * @throws InvalidInputException
	 */
	public void addSongtoAlbum(	Album a, String aName, int aDuration,
								int aPosition, List<Artist> ftArtists)
										throws InvalidInputException
	{
		HAS h = HAS.getInstance();
		String error = "";

		if (a == null)
			error += "Song must belong to an album! ";
		if (aName == null || aName.trim().length() == 0)
			error += "Song must have a name! ";
		if (aDuration <= 0)
			error += "Song must have a duration! ";
		if (aPosition <= 0)
			error += "Song must have a position! ";
		if (a != null)
		{
			for (Song s : a.getSongs())
			{
				if (aPosition == s.getPosition())
					error += "A song already occupies this position, please choose another position!";
			}
		}

		if (error.trim().length() > 0)
			throw new InvalidInputException(error);

		Song newSong = new Song(aName, aDuration, aPosition, a);
		h.addSong(newSong);

		if (ftArtists != null)
		{
			for (Artist ftar : ftArtists)
				addFeaturedArtist(newSong, ftar);
		}

		sortSongs(a);

		PersistenceXStream.saveToXMLwithXStream(h);
	}

	/**
	 * Adds a featured artist to a song
	 * 
	 * @param song
	 *            The song
	 * @param ar
	 *            The featured artist
	 * @throws InvalidInputException
	 */
	public void addFeaturedArtist(Song song, Artist ar)
			throws InvalidInputException
	{
		HAS h = HAS.getInstance();
		String error = "";
		if (song == null)
			error += "Must select a song to add a featured artist!";
		if (ar == null)
			error += "Must select a featured artist!";
		if(ar == song.getAlbum().getMainArtist())
			error += "Album artist cannot be a featured artist!";

		if (error.trim().length() > 0)
			throw new InvalidInputException(error);

		song.addFtArtist(ar);
		PersistenceXStream.saveToXMLwithXStream(h);
	}

	/**
	 * Creates a playlist within the HAS system
	 * 
	 * @param name
	 *            name of the playlist
	 * @param songs
	 *            List of songs that will be part of the playlist
	 * @throws InvalidInputException
	 */
	public void createPlaylist(String name, List<Song> songs)
			throws InvalidInputException
	{
		HAS h = HAS.getInstance();

		String error = "";

		if (name == null || name.trim().length() == 0)
			error += "Playlist must have a name!";
		if (songs == null || songs.size() == 0)
			error += "Playlist must have at least one song!";

		if (error.trim().length() > 0)
			throw new InvalidInputException(error);

		Song initialSong = songs.get(0);
		Playlist newPlaylist = new Playlist(name, initialSong);

		List<Song> modifiedSongs = new ArrayList<Song>();
		for (Song s : songs)
			modifiedSongs.add(s);

		h.addPlaylist(newPlaylist);
		addSongtoPlaylist(newPlaylist, modifiedSongs);

		sortPlaylists();

		PersistenceXStream.saveToXMLwithXStream(h);
	}

	/**
	 * Adds songs to an already existing playlist
	 * 
	 * @param p
	 *            Playlist to which the songs will be added
	 * @param songs
	 *            List of songs that are to be added to the playlist
	 * @throws InvalidInputException
	 */
	public void addSongtoPlaylist(Playlist p, List<Song> songs)
			throws InvalidInputException
	{
		HAS h = HAS.getInstance();

		String error = "";

		if (p == null)
			error += "A playlist must be selected!";
		if (songs == null || songs.size() == 0)
			error += "Must select at least one song to add to playlist!";

		if (error.trim().length() > 0)
			throw new InvalidInputException(error);

		for (Song song : songs)
			p.addSong(song);

		PersistenceXStream.saveToXMLwithXStream(h);
	}

	/**
	 * Creates a new room within the HAS system
	 * 
	 * @param name
	 *            Name of the new room
	 * @throws InvalidInputException
	 */
	public void createRoom(String name) throws InvalidInputException
	{
		HAS h = HAS.getInstance();

		String error = "";

		int volume = 50;
		boolean mute = true;

		if (name == null || name.trim().length() == 0)
			error += "Room must have a name!";
		if (error.trim().length() > 0)
			throw new InvalidInputException(error);

		Room newRoom = new Room(name, volume, mute);
		h.addRoom(newRoom);

		sortRooms();

		PersistenceXStream.saveToXMLwithXStream(h);
	}

	/**
	 * Creates a room group
	 * 
	 * @param name
	 *            Name of the new room group
	 * @param rooms
	 *            List of rooms that are to be added to the new room group
	 * @throws InvalidInputException
	 */
	public void createRoomGroup(String name, List<Room> rooms)
			throws InvalidInputException
	{
		HAS h = HAS.getInstance();
		int volume = 50;
		boolean mute = true;

		String error = "";

		if (name == null || name.trim().length() == 0)
			error += "Room Group must have a name!";
		if (rooms == null || rooms.size() == 0)
			error += "Room Group must have at least one room!";

		if (error.trim().length() > 0)
			throw new InvalidInputException(error);

		Room initialRoom = rooms.get(0);
		RoomGroup newRG = new RoomGroup(name, volume, mute, initialRoom);
		h.addRoomGroup(newRG);

		List<Room> modifiedRooms = new ArrayList<Room>();
		for (Room r : rooms)
			modifiedRooms.add(r);

		modifiedRooms.remove(0);
		addRoomToRoomGroup(newRG, modifiedRooms);

		sortRoomGroups();

		PersistenceXStream.saveToXMLwithXStream(h);
	}

	/**
	 * Adds rooms to an already existing room group
	 * 
	 * @param rG
	 *            Room group to which the songs will be added
	 * @param rooms
	 *            List of rooms that will be added
	 * @throws InvalidInputException
	 */
	public void addRoomToRoomGroup(RoomGroup rG, List<Room> rooms)
			throws InvalidInputException
	{
		HAS h = HAS.getInstance();

		String error = "";

		if (rG == null)
			error += "Must select a room group!";
		if (rooms == null)
			error += "Must select at least one room to add to room group!";
		if (error.trim().length() > 0)
			throw new InvalidInputException(error);

		for (Room room : rooms)
			rG.addRoom(room);

		PersistenceXStream.saveToXMLwithXStream(h);
	}

	/**
	 * Sets the volume of a room
	 * 
	 * @param room
	 *            Room whose volume will be changed
	 * @param volumeLevel
	 *            Desired volume level
	 * @throws InvalidInputException
	 */
	public void setRoomVolumeLevel(Room room, int volumeLevel)
			throws InvalidInputException
	{
		HAS h = HAS.getInstance();
		String error = "";

		if (room == null)
			error += "Must select a room to set the volume in!";
		if (volumeLevel < 0)
			error += "Must select a positive volume level!";
		if (error.trim().length() > 0)
			throw new InvalidInputException(error);

		if (volumeLevel > 100)
			volumeLevel = 100;

		if (volumeLevel == 0)
		{
			room.setMute(true);
			room.setVolume(volumeLevel);
		}

		else
		{
			room.setMute(false);
			room.setVolume(volumeLevel);
		}

		PersistenceXStream.saveToXMLwithXStream(h);
	}

	/**
	 * Sets the volume of a room group
	 * 
	 * @param rg
	 *            Room Group whose volume is to be changed
	 * @param volumeLevel
	 *            Desired volume level
	 * @throws InvalidInputException
	 */
	public void setRoomGroupVolumeLevel(RoomGroup rg, int volumeLevel)
			throws InvalidInputException
	{
		HAS h = HAS.getInstance();
		String error = "";

		if (rg == null)
			error += "Must select a room group to set the volume in!";
		if (volumeLevel < 0)
			error += "Must select a positive volume level!";
		if (error.trim().length() > 0)
			throw new InvalidInputException(error);

		if (volumeLevel > 100)
			volumeLevel = 100;

		rg.setVolume(volumeLevel);

		if (volumeLevel == 0)
		{
			for (Room r : rg.getRooms())
			{
				r.setMute(true);
				r.setVolume(0);
			}

		}

		else
		{
			for (Room r : rg.getRooms())
			{
				r.setMute(false);
				r.setVolume(volumeLevel);
			}
		}

		PersistenceXStream.saveToXMLwithXStream(h);
	}

	/**
	 * Sets a room to mute
	 * 
	 * @param room
	 *            Room that will be muted
	 * @param mute
	 *            Boolean that determines if the room is muted
	 * @throws InvalidInputException
	 */
	public void setRoomMute(Room room, boolean mute)
			throws InvalidInputException
	{
		HAS h = HAS.getInstance();
		String error = "";

		if (room == null)
			error += "Must select a room to mute!";

		if (error.trim().length() > 0)
			throw new InvalidInputException(error);

		room.setMute(mute);

		PersistenceXStream.saveToXMLwithXStream(h);
	}

	/**
	 * Mutes all rooms in a room group
	 * 
	 * @param rg
	 *            Room Group to be muted
	 * @param mute
	 *            Boolean that determines if the room is muted
	 * @throws InvalidInputException
	 */
	public void setRoomGroupMute(RoomGroup rg, boolean mute)
			throws InvalidInputException
	{
		HAS h = HAS.getInstance();
		String error = "";

		if (rg == null)
			error += "Must select a room group to mute!";

		if (error.trim().length() > 0)
			throw new InvalidInputException(error);

		rg.setMute(mute);
		for (Room r : rg.getRooms())
			r.setMute(mute);

		PersistenceXStream.saveToXMLwithXStream(h);
	}

	/**
	 * Sorts the artists contained in the HAS alphabetically
	 */
	public void sortArtists()
	{
		HAS h = HAS.getInstance();
		List<Artist> artists = h.getArtists();

		if (artists.size() > 1)

		{
			List<Artist> sortedArtists = new ArrayList<Artist>();
			for (Artist a : artists)
				sortedArtists.add(a);

			Collections.sort(sortedArtists);
			for (Artist a : sortedArtists)
			{
				h.removeArtist(a);
			}
			for (Artist a : sortedArtists)
			{
				h.addArtist(a);
			}
		}

		PersistenceXStream.saveToXMLwithXStream(h);
	}

	/**
	 * Sorts the albums within the HAS alphabetically
	 */
	public void sortAlbums()
	{
		HAS h = HAS.getInstance();

		List<Album> albums = h.getAlbums();

		if (albums.size() > 1)
		{
			List<Album> sortedAlbums = new ArrayList<Album>();
			for (Album a : albums)
				sortedAlbums.add(a);

			Collections.sort(sortedAlbums);
			for (Album a : sortedAlbums)
			{
				h.removeAlbum(a);
			}
			for (Album a : sortedAlbums)
			{
				h.addAlbum(a);
			}
		}

		PersistenceXStream.saveToXMLwithXStream(h);
	}

	/**
	 * Sorts the songs in an album based on position
	 * 
	 * @param a
	 *            Album to be sorted
	 */
	public void sortSongs(Album a)
	{
		HAS h = HAS.getInstance();

		if (h.getAlbums().contains(a) == true)
		{
			List<Song> songs = a.getSongs();
			if (songs.size() > 1)
			{
				List<Song> sortedSongs = new ArrayList<Song>();
				for (Song s : songs)
					sortedSongs.add(s);

				Collections.sort(sortedSongs);

				for (Song s : sortedSongs)
				{
					s.delete();
				}

				for (Song s : sortedSongs)
				{
					s.setAlbum(a);
				}
			}
		}

		PersistenceXStream.saveToXMLwithXStream(h);
	}

	/**
	 * Sorts the playlists within the HAS system alphabetically
	 */
	public void sortPlaylists()
	{
		HAS h = HAS.getInstance();
		List<Playlist> playlists = h.getPlaylists();

		if (playlists.size() > 1)
		{
			List<Playlist> sortedPlaylists = new ArrayList<Playlist>();
			for (Playlist p : playlists)
				sortedPlaylists.add(p);

			Collections.sort(sortedPlaylists);
			for (Playlist p : sortedPlaylists)
			{
				h.removePlaylist(p);
			}
			for (Playlist p : sortedPlaylists)
			{
				h.addPlaylist(p);
			}
		}

		PersistenceXStream.saveToXMLwithXStream(h);
	}

	/**
	 * Sorts the rooms within the HAS system alphabetically
	 */
	public void sortRooms()
	{
		HAS h = HAS.getInstance();
		List<Room> rooms = h.getRooms();

		if (rooms.size() > 1)
		{
			List<Room> sortedRooms = new ArrayList<Room>();
			for (Room r : rooms)
				sortedRooms.add(r);

			Collections.sort(sortedRooms);
			for (Room r : sortedRooms)
			{
				h.removeRoom(r);
			}
			for (Room r : sortedRooms)
			{
				h.addRoom(r);
			}
		}

		PersistenceXStream.saveToXMLwithXStream(h);
	}

	/**
	 * Sorts the room groups within the HAS system alphabetically
	 */
	public void sortRoomGroups()
	{
		HAS h = HAS.getInstance();
		List<RoomGroup> rgs = h.getRoomGroups();

		if (rgs.size() > 1)
		{
			List<RoomGroup> sortedRgs = new ArrayList<RoomGroup>();
			for (RoomGroup r : rgs)
				sortedRgs.add(r);

			Collections.sort(sortedRgs);
			for (RoomGroup r : sortedRgs)
			{
				h.removeRoomGroup(r);
			}
			for (RoomGroup r : sortedRgs)
			{
				h.addRoomGroup(r);
			}
		}

		PersistenceXStream.saveToXMLwithXStream(h);
	}

	/**
	 * Plays a playable within a single room
	 * 
	 * @param play
	 *            Playable that is to be played
	 * @param room
	 *            Room in which the playable must play
	 * @throws InvalidInputException
	 */
	public void playSingleRoom(Playable play, Room room)
			throws InvalidInputException
	{
		String error = "";
		if (room == null)
			error +=  "A room must be selected! ";

		if (error.trim().length() > 0)
			throw new InvalidInputException(error);

		room.setPlayable(play);
	}

	/**
	 * Plays a playable within a room group
	 * 
	 * @param play
	 *            Playable that is to be played
	 * @param rg
	 *            Room Group in which the playable must play
	 * @throws InvalidInputException
	 */
	public void playRoomGroup(Playable play, RoomGroup rg)
			throws InvalidInputException
	{
		String error = "";
		if (rg == null)
			error += "A room group must be selected! ";

		if (error.trim().length() > 0)
			throw new InvalidInputException(error);

		rg.setPlayable(play);
	}

}
