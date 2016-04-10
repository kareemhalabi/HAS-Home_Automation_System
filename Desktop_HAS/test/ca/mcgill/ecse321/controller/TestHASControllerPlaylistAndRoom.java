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
		PersistenceXStream.setFilename("test" + File.separator + "ca"
				+ File.separator + "mcgill" + File.separator + "ecse321"
				+ File.separator + "controller" + File.separator + "data.xml");
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
			fail();
		}

		String testSongName1 = "testName";
		String testSongName2 = "testName2";
		int songDuration1 = 213;
		int songDuration2 = 123;
		int songPosition1 = 1;
		int songPosition2 = 2;

		try
		{
			hc.addSongtoAlbum(h.getAlbum(0), testSongName1, songDuration1,
					songPosition1, null);
		}

		catch (InvalidInputException e)
		{
			fail();
		}

		try
		{
			hc.addSongtoAlbum(h.getAlbum(0), testSongName2, songDuration2,
					songPosition2, null);
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
		assertEquals("testName", h.getPlaylist(0).getSong(0).getName());

		HAS h2 = (HAS) PersistenceXStream.loadFromXMLwithXStream();
		assertEquals(1, h2.getPlaylists().size());
		assertEquals("Playlist1", h2.getPlaylist(0).getName());
		assertEquals("testName", h2.getPlaylist(0).getSong(0).getName());
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
		for (Song s : h.getSongs())
			songs.add(s);

		songs.remove(0);

		try
		{
			hc.createPlaylist(playlistName, songs);
		} catch (InvalidInputException e)
		{
			fail();
		}

		List<Song> newSongs = new ArrayList<Song>();
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

		assertEquals("Must select at least one song to add to playlist!",
				error);
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

	// TODO MAY NOT NEED THIS BECAUSE THE MANY ROOMS TAKES THE SAME PATH
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
		assertEquals("RoomName", h.getRoomGroup(0).getRoom(1).getName());
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

		for (Room r : h.getRooms())
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

		assertEquals("RoomName", h.getRoomGroup(0).getRoom(0).getName());
		assertEquals("Room2", h.getRoomGroup(0).getRoom(1).getName());
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

		assertEquals("Must select at least one room to add to room group!",
				error);
		assertEquals(1, h.getRoomGroup(0).getRooms().size());
	}
	
	@Test
	public void testMultipleRoomGroups()
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
		
		String name2 = "RoomName2";
		try
		{
			hc.createRoom(name2);
		} catch (InvalidInputException e)
		{
			fail();
		}

		String groupRoomName = "Group 1";
		String groupRoomName2 = "Group 2";

		List<Room> rooms = new ArrayList<Room>();
		rooms.add(h.getRoom(0));
		try
		{
			hc.createRoomGroup(groupRoomName, rooms);
		} catch (InvalidInputException e)
		{
			fail();
		}
		rooms.add(h.getRoom(1));
		
		try
		{
			hc.createRoomGroup(groupRoomName2, rooms);
		} catch (InvalidInputException e)
		{
			fail();
		}
		
		h.delete();
		
		h = (HAS) PersistenceXStream.loadFromXMLwithXStream();
		assertEquals("Group 1", h.getRoomGroup(0).getName());
		assertEquals("RoomName", h.getRoomGroup(0).getRoom(0).getName());
		
		assertEquals("Group 2", h.getRoomGroup(1).getName());
		assertEquals("RoomName", h.getRoomGroup(1).getRoom(0).getName());
		assertEquals("RoomName2", h.getRoomGroup(1).getRoom(1).getName());
	}

	@Test
	public void testSetRoomVolume()
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
	public void testSetRoomVolumeZeroVolume()
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
	public void testSetRoomVolumeNoRoom()
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
	public void testSetRoomGroupVolume()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		int volume = 30;

		try
		{
			hc.createRoom("Kitchen");
			hc.createRoom("Living Room");
			hc.createRoomGroup("Room Group 1", h.getRooms());
		} catch (InvalidInputException e)
		{
			fail();
		}

		try
		{
			hc.setRoomGroupVolumeLevel(h.getRoomGroup(0), volume);
		} catch (InvalidInputException e)
		{
			fail();
		}

		HAS h2 = (HAS) PersistenceXStream.loadFromXMLwithXStream();

		assertEquals("Kitchen", h2.getRoomGroup(0).getRoom(0).getName());
		assertEquals(30, h2.getRoomGroup(0).getRoom(0).getVolume());
		assertEquals("Living Room", h2.getRoomGroup(0).getRoom(1).getName());
		assertEquals(30, h2.getRoomGroup(0).getRoom(1).getVolume());
		assertEquals(30, h2.getRoomGroup(0).getVolume());
	}

	@Test
	public void testSetRoomGroupVolumeNoRoomGroup()
	{
		HASController hc = new HASController();
		int volume = 30;
		String error = "";

		try
		{
			hc.setRoomGroupVolumeLevel(null, volume);
		} catch (InvalidInputException e)
		{
			error = e.getMessage();
		}

		assertEquals("Must select a room group to set the volume in!", error);
	}

	@Test
	public void testSetRoomGroupZeroVolume()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		int volume = 0;

		try
		{
			hc.createRoom("Kitchen");
			hc.createRoom("Living Room");
			hc.createRoomGroup("Room Group 1", h.getRooms());
		} catch (InvalidInputException e)
		{
			fail();
		}

		try
		{
			hc.setRoomGroupVolumeLevel(h.getRoomGroup(0), volume);
		} catch (InvalidInputException e)
		{
			fail();
		}

		HAS h2 = (HAS) PersistenceXStream.loadFromXMLwithXStream();

		assertEquals("Kitchen", h2.getRoomGroup(0).getRoom(0).getName());
		assertEquals(0, h2.getRoomGroup(0).getRoom(0).getVolume());
		assertTrue(h2.getRoomGroup(0).getRoom(0).getMute());
		assertEquals("Living Room", h2.getRoomGroup(0).getRoom(1).getName());
		assertEquals(0, h2.getRoomGroup(0).getRoom(1).getVolume());
		assertTrue(h2.getRoomGroup(0).getRoom(1).getMute());
	}

	@Test
	public void testSetRoomMute()
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
			hc.setRoomMute(h.getRoom(0), true);
		} catch (InvalidInputException e)
		{
			fail();
		}

		assertEquals("kitchen", h.getRoom(0).getName());
		assertTrue(h.getRoom(0).getMute());
	}

	@Test
	public void testSetRoomMuteNoRoom()
	{
		HASController hc = new HASController();
		String error = "";

		try
		{
			hc.setRoomMute(null, true);
		} catch (InvalidInputException e)
		{
			error = e.getMessage();
		}

		assertEquals("Must select a room to mute!", error);
	}

	@Test
	public void testSetRoomGroupMuteRoomGroup()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		boolean mute = true;

		try
		{
			hc.createRoom("kitchen");
			hc.createRoom("Living Room");
			hc.createRoomGroup("Room Group 1", h.getRooms());
		} catch (InvalidInputException e)
		{
			fail();
		}

		try
		{
			hc.setRoomGroupMute(h.getRoomGroup(0), mute);
		} catch (InvalidInputException e)
		{
			fail();
		}

		assertTrue(h.getRoomGroup(0).getMute());
		assertTrue(h.getRoomGroup(0).getRoom(0).getMute());
		assertTrue(h.getRoomGroup(0).getRoom(1).getMute());
	}

	@Test
	public void testSetRoomGroupMuteRoomGroupFalse()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		boolean mute = false;

		try
		{
			hc.createRoom("kitchen");
			hc.createRoom("Living Room");
			hc.createRoomGroup("Room Group 1", h.getRooms());
		} catch (InvalidInputException e)
		{
			fail();
		}

		try
		{
			hc.setRoomGroupMute(h.getRoomGroup(0), mute);
		} catch (InvalidInputException e)
		{
			fail();
		}
		assertFalse(h.getRoomGroup(0).getRoom(0).getMute());
		assertFalse(h.getRoomGroup(0).getRoom(1).getMute());
		assertFalse(h.getRoomGroup(0).getMute());
	}

	@Test
	public void testSetRoomGroupMuteNoRoomGroup()
	{
		HASController hc = new HASController();
		String error = "";

		try
		{
			hc.setRoomGroupMute(null, true);
		} catch (InvalidInputException e)
		{
			error = e.getMessage();
		}

		assertEquals("Must select a room group to mute!", error);
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
		} catch (InvalidInputException e)
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
		} catch (InvalidInputException e)
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
		} catch (InvalidInputException e)
		{
			error = e.getMessage();
		}

		assertEquals("Must select a featured artist!", error);
		assertFalse(song.hasFtArtists());
	}
}
