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

/*
 * TODO: Play function
 */
public class HASController
{
	public HASController()
	{
	}

	public void createArtist(String artName) throws InvalidInputException
	{
		if (artName == null || artName.trim().length() == 0)
			throw new InvalidInputException("Artist name cannot be empty! ");

		Artist art = new Artist(artName);
		HAS h = HAS.getInstance();
		h.addArtist(art);
		PersistenceXStream.saveToXMLwithXStream(h);
	}

	public void createAlbum(String name, String genre, Date releaseDate, Artist ar) throws InvalidInputException
	{
		// check that the input is valid for this
		java.util.Calendar cal = Calendar.getInstance();
		java.util.Date utilDate = new java.util.Date();
		cal.setTime(utilDate);
		java.sql.Date sqlDate = new java.sql.Date(cal.getTime().getTime());

		String error = "";
		if (name == null || name.trim().length() == 0)
			error = error + "Album name cannot be empty! ";
		if (genre == null || genre.trim().length() == 0)
			error = error + "Genre name cannot be empty! ";
		if (releaseDate == null)
			error = error + "Release date cannot be empty! ";
		else if (releaseDate.after(sqlDate) == true)
			error = error + "Release date cannot be in the future! ";
		if (ar == null)
			error = error + "Album must have an artist! ";
		if (error.length() > 0)
			throw new InvalidInputException(error);

		Album a = new Album(name, genre, releaseDate, ar);
		HAS h = HAS.getInstance();
		h.addAlbum(a);
		PersistenceXStream.saveToXMLwithXStream(h);
	}

	public void addSongtoAlbum(Album a, String aName, int aDuration, int aPosition, List<Artist> ftArtists) throws InvalidInputException
	{
		HAS h = HAS.getInstance();

		String error = "";

		// checks if the album exists
		if (a == null)
			error = error + "Song must belong to an album! ";
		else if (!h.getAlbums().contains(a))
			error = error + "Album does not exist! ";

		// checks the name
		if (aName == null || aName.trim().length() == 0)
			error = error + "Song must have a name! ";

		// if integer field is left without a number in there, what will it
		// give?
		if (aDuration <= 0)
			error = error + "Song must have a duration! ";
		if (aPosition <= 0)
			error = error + "Song must have a position! ";

		if (error.length() > 0)
			throw new InvalidInputException(error);

		Song newSong = new Song(aName, aDuration, aPosition, a);
		h.addSong(newSong);
		
		//TODO TEST THIS
		if(ftArtists != null)
		{
			for(Artist ftar: ftArtists)
				addFeaturedArtist(newSong, ftar);
		}
		
		sortSongs(a);

		PersistenceXStream.saveToXMLwithXStream(h);
	}

	public void createPlaylist(String name, Song song1) throws InvalidInputException
	{
		HAS h = HAS.getInstance();

		String error = "";

		if (name == null || name.trim().length() == 0)
			error = error + "Playlist must have a name!";
		if (song1 == null)
			error = error + "Playlist must have at least one song!";

		if (error.length() > 0)
			throw new InvalidInputException(error);

		Playlist newPlaylist = new Playlist(name, song1);
		h.addPlaylist(newPlaylist);

		PersistenceXStream.saveToXMLwithXStream(h);
	}

	public void addSongtoPlaylist(Playlist p, Song song) throws InvalidInputException
	{
		HAS h = HAS.getInstance();

		String error = "";

		if (p == null)
			error = error + "A playlist must be selected!";
		if (song == null)
			error = error + "A song must be selected!";

		if (error.length() > 0)
			throw new InvalidInputException(error);

		p.addSong(song);

		PersistenceXStream.saveToXMLwithXStream(h);
	}

	// When room is created, the volume is automatically set to 5.
	public void createRoom(String name) throws InvalidInputException
	{
		HAS h = HAS.getInstance();

		String error = "";

		int volume = 5;
		boolean mute = true;

		if (name == null || name.trim().length() == 0)
			error = error + "Room must have a name!";
		if (error.length() > 0)
			throw new InvalidInputException(error);

		Room newRoom = new Room(name, volume, mute);
		h.addRoom(newRoom);

		PersistenceXStream.saveToXMLwithXStream(h);
	}

	public void createRoomGroup(String name, Room initialRoom) throws InvalidInputException
	{
		HAS h = HAS.getInstance();

		String error = "";

		if (name == null || name.trim().length() == 0)
			error = error + "Room Group must have a name!";
		if (initialRoom == null)
			error = error + "Room Group must have at least one room!";

		if (error.length() > 0)
			throw new InvalidInputException(error);

		RoomGroup newRG = new RoomGroup(name, initialRoom);
		h.addRoomGroup(newRG);

		PersistenceXStream.saveToXMLwithXStream(h);
	}

	public void addRoomToRoomGroup(RoomGroup rG, Room room) throws InvalidInputException
	{
		HAS h = HAS.getInstance();

		String error = "";

		if (rG == null)
			error = error + "Must select a room group!";
		if (room == null)
			error = error + "Must select a room to add to room group!";
		if (error.length() > 0)
			throw new InvalidInputException(error);

		rG.addRoom(room);
		PersistenceXStream.saveToXMLwithXStream(h);
	}

	public void setRoomVolumeLevel(Room room, int volumeLevel) throws InvalidInputException
	{
		HAS h = HAS.getInstance();
		String error = "";

		if (room == null)
			error = error + "Must select a room to set the volume in!";
		if (error.length() > 0)
			throw new InvalidInputException(error);

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

	// TODO setMute view will need to check for the mute - CHECK BOX IN THE VIEW
	public void setMute(Room room, boolean mute) throws InvalidInputException
	{
		HAS h = HAS.getInstance();
		String error = "";

		if (room == null)
			error = error + "Must select a room to mute!";

		if (error.length() > 0)
			throw new InvalidInputException(error);

		if (mute == true)
			room.setMute(true);
		else
			room.setMute(false);

		PersistenceXStream.saveToXMLwithXStream(h);
	}

	public void sortArtists()
	{
		HAS h = HAS.getInstance();
		List<Artist> artists = h.getArtists();
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

		PersistenceXStream.saveToXMLwithXStream(h);
	}

	public void sortAlbums()
	{
		HAS h = HAS.getInstance();
		List<Album> albums = h.getAlbums();
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

		PersistenceXStream.saveToXMLwithXStream(h);
	}

	public void addFeaturedArtist(Song song, Artist ar) throws InvalidInputException
	{
		HAS h = HAS.getInstance();
		String error = "";
		if (song == null)
			error = error + "Must select a song to add a featured artist!";
		if (ar == null)
			error = error + "Must select a featured artist!";

		if (error.length() > 0)
			throw new InvalidInputException(error);

		song.addFtArtist(ar);
		PersistenceXStream.saveToXMLwithXStream(h);
	}

	public void sortSongs(Album a)
	{
		HAS h = HAS.getInstance();
		
		if (h.getAlbums().contains(a) == true)
		{
			List<Song> songs = a.getSongs();
			List<Song> sortedSongs = new ArrayList<Song>();
			for (Song s : songs)
				sortedSongs.add(s);

			Collections.sort(sortedSongs);
			
			for (Song s : sortedSongs)
			{
				s.delete();//Songs are not being removed
				a.removeSong(s);
			}
			
			for (Song s : sortedSongs)
			{
				a.addSong(s);
			}
		}

		PersistenceXStream.saveToXMLwithXStream(h);
	}
	
	public void play(Playable play)
	{
		play.play();
	}
}
