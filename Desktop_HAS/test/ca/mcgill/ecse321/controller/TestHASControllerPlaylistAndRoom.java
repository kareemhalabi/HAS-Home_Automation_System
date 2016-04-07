package ca.mcgill.ecse321.controller;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.HAS.controller.HASController;
import ca.mcgill.ecse321.HAS.controller.InvalidInputException;
import ca.mcgill.ecse321.HAS.model.Album;
import ca.mcgill.ecse321.HAS.model.Artist;
import ca.mcgill.ecse321.HAS.model.HAS;
import ca.mcgill.ecse321.HAS.model.Room;
import ca.mcgill.ecse321.HAS.model.Song;
import ca.mcgill.ecse321.HAS.persistence.PersistenceXStream;

public class TestHASControllerPlaylistAndRoom
{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		PersistenceXStream.setFilename("test" + File.separator + "ca" + File.separator + "mcgill" + File.separator
				+ "ecse321" + File.separator + "controller" + File.separator + "data.xml");
		PersistenceXStream.setAlias("HAS", HAS.class);
		PersistenceXStream.setAlias("album", Album.class);
		PersistenceXStream.setAlias("artist", Artist.class);
	}

	@Before
	public void setUp() throws Exception
	{
		HAS h = HAS.getInstance();
		assertEquals(0, h.getPlaylists().size());

		HASController hc = new HASController();

		String name = "Flume";
		String genre = "Indie";
		String artName = "Oscar";
		@SuppressWarnings("deprecation")
		Date d1 = new Date(107, 01, 25);

		Artist ar1 = new Artist(artName);

		try
		{
			hc.createAlbum(name, genre, d1, ar1);
		} catch (InvalidInputException e)
		{
			// check that no error has occurred in the creation of the album
			fail();
		}

		HAS h2 = (HAS) PersistenceXStream.loadFromXMLwithXStream();

		// check file contents
		checkResultAlbum(h2, name, genre, artName, d1);

		// new song 1 on album 1
		String testSongName1 = "testName";
		String testSongName2 = "testName2";
		int songDuration1 = 213;
		int songDuration2 = 123;
		int songPosition1 = 1;
		int songPosition2 = 2;

		// add song to an album
		try
		{
			hc.addSongtoAlbum(h.getAlbum(0), testSongName1, songDuration1, songPosition1, null);
		}

		catch (InvalidInputException e)
		{
			fail();
		}

		try
		{
			hc.addSongtoAlbum(h.getAlbum(0), testSongName2, songDuration2, songPosition2, null);
		}

		catch (InvalidInputException e)
		{
			fail();
		}
	}

	@After
	public void tearDown() throws Exception
	{
		HAS h = HAS.getInstance();
		h.delete();
	}

	@Test
	public void testCreatePlaylistManySongs()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();

		assertEquals(0, h.getPlaylists().size());

		String playlistName = "Playlist1";
		List<Song> songs = h.getAlbum(0).getSongs();

		try
		{
			hc.createPlaylist(playlistName, songs);
		} catch (InvalidInputException e)
		{
			fail();
		}

		assertEquals(1, h.getPlaylists().size());
		assertEquals("Playlist1", h.getPlaylist(0).getName());
	}

	@Test
	public void testCreatePlaylistNoName()
	{
		HAS h = HAS.getInstance();
		assertEquals(0, h.getPlaylists().size());

		HASController hc = new HASController();

		String error = "";
		String playlistName = "";
		
		List<Song> songs = h.getAlbum(0).getSongs();

		try
		{
			hc.createPlaylist(playlistName, songs);
		} catch (InvalidInputException e)
		{
			error = e.getMessage();
		}

		assertEquals("Playlist must have a name!", error);
	}

	@Test
	public void testCreatePlaylistNoSong()
	{
		HAS h = HAS.getInstance();
		assertEquals(0, h.getPlaylists().size());

		HASController hc = new HASController();

		String error = "";

		String playlistName = "Name";
		List<Song> songs = new ArrayList<Song>();

		try
		{
			hc.createPlaylist(playlistName, songs);
		} catch (InvalidInputException e)
		{
			error = e.getMessage();
		}

		assertEquals("Playlist must have at least one song!", error);
	}

	@Test
	public void testAddSongToPlaylist()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();

		String playlistName = "Playlist1";
		
		List<Song> songs = new ArrayList<Song>();
		for(Song s: h.getSongs())
			songs.add(s);
		
		songs.remove(0);

		try
		{
			hc.createPlaylist(playlistName, songs);
		} catch (InvalidInputException e)
		{
			fail();
		}

		List<Song> newSongs = new ArrayList <Song>();
		newSongs.add(h.getAlbum(0).getSong(0));
		
		try
		{
			hc.addSongtoPlaylist(h.getPlaylist(0), newSongs);
		} catch (InvalidInputException e)
		{
			fail();
		}

		assertEquals(2, h.getPlaylist(0).getSongs().size());
		assertEquals("testName2", h.getPlaylist(0).getSong(0).getName());
		assertEquals(123, h.getPlaylist(0).getSong(0).getDuration());
		assertEquals(2, h.getPlaylist(0).getSong(0).getPosition());
		
		assertEquals("testName", h.getPlaylist(0).getSong(1).getName());
		assertEquals(213, h.getPlaylist(0).getSong(1).getDuration());
		assertEquals(1, h.getPlaylist(0).getSong(1).getPosition());
	}

	@Test
	public void testAddSongToPlaylistNoPlaylist()

	{
		HAS h = HAS.getInstance();
		assertEquals(0, h.getPlaylists().size());

		HASController hc = new HASController();
		String error = "";
		
		List<Song> songs = new ArrayList<Song>();
		songs.add(h.getAlbum(0).getSong(1));

		try
		{
			hc.addSongtoPlaylist(null, songs);
		} catch (InvalidInputException e)
		{
			error = e.getMessage();
		}

		assertEquals("A playlist must be selected!", error);

	}

	@Test
	public void testAddSongToPlaylistNoSong()

	{
		HAS h = HAS.getInstance();
		assertEquals(0, h.getPlaylists().size());

		HASController hc = new HASController();

		String error = "";

		String playlistName = "Playlist1";
		List<Song> songs = h.getAlbum(0).getSongs();
		
		try
		{
			hc.createPlaylist(playlistName, songs);
		} catch (InvalidInputException e)
		{
			fail();
		}

		try
		{
			hc.addSongtoPlaylist(h.getPlaylist(0), null);
		} catch (InvalidInputException e)
		{
			error = e.getMessage();
		}

		assertEquals("Must select at least one song to add to playlist!", error);
	}

	@Test
	public void testCreateRoom()
	{
		HAS h = HAS.getInstance();
		assertEquals(0, h.getRooms().size());

		HASController hc = new HASController();

		String name = "RoomName";
		try
		{
			hc.createRoom(name);
		} catch (InvalidInputException e)
		{
			fail();
		}

		assertEquals(1, h.getRooms().size());
		assertEquals("RoomName", h.getRoom(0).getName());
	}

	@Test
	public void testCreateRoomNoName()
	{
		HAS h = HAS.getInstance();
		assertEquals(0, h.getRooms().size());

		HASController hc = new HASController();

		String name = "";
		String error = "";
		try
		{
			hc.createRoom(name);
		} catch (InvalidInputException e)
		{
			error = e.getMessage();
		}

		assertEquals("Room must have a name!", error);
		assertEquals(0, h.getRooms().size());
	}

	@Test
	public void testCreateRoomGroupOneRoom()
	{
		HAS h = HAS.getInstance();
		assertEquals(0, h.getRooms().size());

		HASController hc = new HASController();

		String name = "RoomName";
		try
		{
			hc.createRoom(name);
		} catch (InvalidInputException e)
		{
			fail();
		}

		String groupRoomName = "Group 1";
		
		List<Room> rooms = h.getRooms();

		try
		{
			hc.createRoomGroup(groupRoomName, rooms);
		} catch (InvalidInputException e)
		{
			fail();
		}

		assertEquals("Group 1", h.getRoomGroup(0).getName());
		assertEquals("RoomName", h.getRoomGroup(0).getRoom(0).getName());
	}
	
	@Test
	public void testCreateRoomGroupMultipleRooms()
	{
		HAS h = HAS.getInstance();
		assertEquals(0, h.getRooms().size());

		HASController hc = new HASController();

		String name = "RoomName";
		try
		{
			hc.createRoom(name);
		} catch (InvalidInputException e)
		{
			fail();
		}
		
		try
		{
			hc.createRoom("Kitchen");
		} catch (InvalidInputException e)
		{
			fail();
		}

		String groupRoomName = "Group 1";
		
		List<Room> rooms = h.getRooms();

		try
		{
			hc.createRoomGroup(groupRoomName, rooms);
		} catch (InvalidInputException e)
		{
			fail();
		}

		assertEquals("Group 1", h.getRoomGroup(0).getName());
		assertEquals("RoomName", h.getRoomGroup(0).getRoom(0).getName());
	}

	@Test
	public void testCreateRoomGroupNoName()
	{
		HAS h = HAS.getInstance();
		assertEquals(0, h.getRooms().size());

		HASController hc = new HASController();

		String name = "RoomName";
		String error = "";
		String groupRoomName = "";

		try
		{
			hc.createRoom(name);
		} catch (InvalidInputException e)
		{
			fail();
		}

		List<Room> rooms = h.getRooms();
		
		try
		{
			hc.createRoomGroup(groupRoomName, rooms);
		} catch (InvalidInputException e)
		{
			error = e.getMessage();
		}

		assertEquals("Room Group must have a name!", error);
		assertEquals(0, h.getRoomGroups().size());
	}

	@Test
	public void testCreateRoomGroupNoInitialRoom()
	{
		HAS h = HAS.getInstance();
		assertEquals(0, h.getRooms().size());

		HASController hc = new HASController();

		String error = "";
		String groupRoomName = "Group 1";
		List<Room> rooms = h.getRooms();

		try
		{
			hc.createRoomGroup(groupRoomName, rooms);
		} catch (InvalidInputException e)
		{
			error = e.getMessage();
		}

		assertEquals("Room Group must have at least one room!", error);
		assertEquals(0, h.getRoomGroups().size());
	}

	@Test
	public void testAddRoomToGroupRoom()
	{
		HAS h = HAS.getInstance();
		assertEquals(0, h.getRooms().size());

		HASController hc = new HASController();

		String name1 = "RoomName";
		String name2 = "Room2";
		try
		{
			hc.createRoom(name1);
		} catch (InvalidInputException e)
		{
			fail();
		}

		try
		{
			hc.createRoom(name2);
		} catch (InvalidInputException e)
		{
			fail();
		}

		String groupRoomName = "Group 1";
		List<Room> rooms = new ArrayList<Room>();
		
		for(Room r: h.getRooms())
			rooms.add(r);
		
		rooms.remove(0);
		
		try
		{
			hc.createRoomGroup(groupRoomName, rooms);
		} catch (InvalidInputException e)
		{
			fail();
		}
		
		List<Room> newRooms = new ArrayList<Room>();
		newRooms.add(h.getRoom(0));
		
		try
		{
			hc.addRoomToRoomGroup(h.getRoomGroup(0), newRooms);
		} catch (InvalidInputException e)
		{
			fail();
		}

		assertEquals("Room2", h.getRoomGroup(0).getRoom(0).getName());
		assertEquals("RoomName", h.getRoomGroup(0).getRoom(1).getName());
	}

	@Test
	public void testAddRoomToGroupRoomNoGroup()
	{
		HAS h = HAS.getInstance();
		assertEquals(0, h.getRooms().size());

		HASController hc = new HASController();

		String name1 = "RoomName";
		String error = "";
		try
		{
			hc.createRoom(name1);
		} catch (InvalidInputException e)
		{
			fail();
		}
		List<Room> newRooms = new ArrayList<Room>();
		newRooms.add(h.getRoom(0));

		try
		{
			hc.addRoomToRoomGroup(null, newRooms);
		} catch (InvalidInputException e)
		{
			error = e.getMessage();
		}

		assertEquals("Must select a room group!", error);
	}

	@Test
	public void testAddRoomToGroupRoomNoRoom()
	{
		HAS h = HAS.getInstance();
		assertEquals(0, h.getRooms().size());

		HASController hc = new HASController();

		String name1 = "RoomName";
		String error = "";
		String groupRoomName = "Group 1";

		try
		{
			hc.createRoom(name1);
		} catch (InvalidInputException e)
		{
			fail();
		}
		
		List<Room> rooms = h.getRooms();

		try
		{
			hc.createRoomGroup(groupRoomName, rooms);
		} catch (InvalidInputException e)
		{
			fail();
		}

		try
		{
			hc.addRoomToRoomGroup(h.getRoomGroup(0), null);
		} catch (InvalidInputException e)
		{
			error = e.getMessage();
		}

		assertEquals("Must select at least one room to add to room group!", error);
		assertEquals(1, h.getRoomGroup(0).getRooms().size());
	}

	@Test
	public void testSetVolume()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		int volume = 30;

		try
		{
			hc.createRoom("kitchen");
		} catch (InvalidInputException e)
		{
			fail();
		}

		try
		{
			hc.setRoomVolumeLevel(h.getRoom(0), volume);
		} catch (InvalidInputException e)
		{
			fail();
		}

		HAS h2 = (HAS) PersistenceXStream.loadFromXMLwithXStream();
		
		assertEquals("kitchen", h2.getRoom(0).getName());
		assertEquals(30, h2.getRoom(0).getVolume());
	}
	
	@Test
	public void testSetVolumeZeroVolume()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();

		try
		{
			hc.createRoom("kitchen");
		} catch (InvalidInputException e)
		{
			fail();
		}

		try
		{
			hc.setRoomVolumeLevel(h.getRoom(0), 0);
		} catch (InvalidInputException e)
		{
			fail();
		}
		
		assertEquals(0, h.getRoom(0).getVolume());
		assertTrue(h.getRoom(0).getMute());
	}
	
	@Test
	public void testSetVolumeNoRoom()
	{
		HASController hc = new HASController();
		String error = "";

		try
		{
			hc.setRoomVolumeLevel(null, 0);
		} catch (InvalidInputException e)
		{
			error = e.getMessage();
		}
		
		assertEquals("Must select a room to set the volume in!", error);
	}
	
	@Test
	public void testSetMute()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		
		try
		{
			hc.createRoom("kitchen");
		} catch (InvalidInputException e)
		{
			fail();
		}

		try
		{
			hc.setMute(h.getRoom(0), true);
		} catch (InvalidInputException e)
		{
			fail();
		}

		assertEquals("kitchen", h.getRoom(0).getName());
		assertTrue(h.getRoom(0).getMute());
	}
	
	@Test
	public void testSetMuteNoRoom()
	{
		HASController hc = new HASController();
		String error = "";

		try
		{
			hc.setMute(null, true);
		} catch (InvalidInputException e)
		{
			error = e.getMessage();
		}

		assertEquals("Must select a room to mute!", error);
	}
	
	@Test
	public void testAddFeaturedArtist()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		
		Song song = h.getSong(0);
		Artist ftArt = new Artist("Alex");
		
		try
		{
			hc.addFeaturedArtist(song, ftArt);
		} 
		catch (InvalidInputException e)
		{
			fail();
		}
		
		assertTrue(song.hasFtArtists());
		assertEquals("Alex", song.getFtArtist(0).getName());
	}
	
	@Test
	public void testAddFeaturedArtistNoSong()
	{
		HASController hc = new HASController();
		String error = "";
		
		Song song = null;
		Artist ftArt = new Artist("Alex");
		
		try
		{
			hc.addFeaturedArtist(song, ftArt);
		} 
		catch (InvalidInputException e)
		{
			error = e.getMessage();
		}
	
		assertEquals("Must select a song to add a featured artist!", error);
	}
	
	@Test
	public void testAddFeaturedArtistNoArtist()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		String error = "";
		
		Song song = h.getSong(0);
		Artist ftArt = null;
		
		try
		{
			hc.addFeaturedArtist(song, ftArt);
		} 
		catch (InvalidInputException e)
		{
			error = e.getMessage();
		}
	
		assertEquals("Must select a featured artist!", error);
		assertFalse(song.hasFtArtists());
	}
	
	@Test
	public void testSortSongs()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		@SuppressWarnings("deprecation")
		Date d1 = new Date(116, 02, 8);

		Album a = new Album("Jack", "Bring me food", d1, new Artist("Jack the Reaper"));
		h.addAlbum(a);

		String[] names =
		{ "Wind Rises", "Dark Horses", "Leo's Oscar", "Life", "KIA", "IKEA", "Food", "Cake", "Porto", "Angel" };
		int[] position =
		{ 2, 3, 7, 8, 1, 4, 9, 10, 6, 5 };
		int i = 0;

		for (String name : names)
		{
			Song song =new Song(name, 123, position[i], a);
			a.addSong(song);
			i++;
		}

		hc.sortSongs(a);

		for (int j = 0; j < names.length - 1; j++)
		{
			assertEquals(a.getSong(j).getPosition(), j + 1);
		}
		
		HAS h2 = (HAS) PersistenceXStream.loadFromXMLwithXStream();
		
		for (int j = 0; j < names.length - 1; j++)
		{
			assertEquals(h2.getAlbum(1).getSong(j).getPosition(), j + 1);
		}
	}

	private void checkResultAlbum(HAS h, String name, String genre, String artName, Date date)
	{
		assertEquals(1, h.getAlbums().size());
		assertEquals(name, h.getAlbum(0).getName());
		assertEquals(genre, h.getAlbum(0).getGenre());
		assertEquals(artName, h.getAlbum(0).getMainArtist().getName());
		assertEquals(date, h.getAlbum(0).getReleaseDate());
	}
}
