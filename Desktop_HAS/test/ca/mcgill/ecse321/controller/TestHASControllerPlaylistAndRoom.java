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
	String name = "Flume";
	String genre = "Indie";
	String artName = "Oscar";
	@SuppressWarnings("deprecation")
	Date d1 = new Date(107, 01, 25);
	Artist ar1 = new Artist(artName);
	String testSongName1 = "testName";
	String testSongName2 = "testName2";
	int songDuration1 = 213;
	int songDuration2 = 123;
	int songPosition1 = 1;
	int songPosition2 = 2;
	String roomName = "RoomName";

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
		HASController hc = new HASController();

		try
		{
			hc.createAlbum(roomName, genre, d1, ar1);
		} catch (InvalidInputException e)
		{
			fail();
		}

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

	/**
	 * Test the creation of a playlist with many songs
	 */
	@Test
	public void testCreatePlaylistManySongs()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		String playlistName = "Playlist1";
		List<Song> songs = h.getAlbum(0).getSongs();

		assertEquals(0, h.getPlaylists().size());

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
	}

	/**
	 * Test the creation of a playlist without name
	 */
	@Test
	public void testCreatePlaylistNoName()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		String error = "";
		String playlistName = "";
		List<Song> songs = h.getAlbum(0).getSongs();

		assertEquals(0, h.getPlaylists().size());

		try
		{
			hc.createPlaylist(playlistName, songs);
		} catch (InvalidInputException e)
		{
			error = e.getMessage();
		}
		assertEquals("Playlist must have a name!", error);
	}

	/**
	 * Test the creation of a playlist without initial set of songs
	 */
	@Test
	public void testCreatePlaylistNoSong()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		String error = "";
		String playlistName = "Name";
		List<Song> songs = new ArrayList<Song>();

		assertEquals(0, h.getPlaylists().size());

		try
		{
			hc.createPlaylist(playlistName, songs);
		} catch (InvalidInputException e)
		{
			error = e.getMessage();
		}

		assertEquals("Playlist must have at least one song!", error);
	}

	/**
	 * Test adding a song to a playlist
	 */
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

	/**
	 * Test adding a song to a playlist without a playlist
	 */
	@Test
	public void testAddSongToPlaylistNoPlaylist()

	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		String error = "";

		assertEquals(0, h.getPlaylists().size());

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

	/**
	 * Test adding a song to a playlist without songs
	 */
	@Test
	public void testAddSongToPlaylistNoSong()

	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		String error = "";
		String playlistName = "Playlist1";
		List<Song> noSongs = new ArrayList<Song>();
		List<Song> songs = h.getAlbum(0).getSongs();

		assertEquals(0, h.getPlaylists().size());

		try
		{
			hc.createPlaylist(playlistName, songs);
		} catch (InvalidInputException e)
		{
			fail();
		}

		try
		{
			hc.addSongtoPlaylist(h.getPlaylist(0), noSongs);
		} catch (InvalidInputException e)
		{
			error = e.getMessage();
		}

		assertEquals("Must select at least one song to add to playlist!",
				error);
	}

	/**
	 * Test the creation of a room
	 */
	@Test
	public void testCreateRoom()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();

		assertEquals(0, h.getRooms().size());

		try
		{
			hc.createRoom(roomName);
		} catch (InvalidInputException e)
		{
			fail();
		}

		assertEquals(1, h.getRooms().size());
		assertEquals("RoomName", h.getRoom(0).getName());
	}

	/**
	 * Test the creation of a room without name
	 */
	@Test
	public void testCreateRoomNoName()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		String name = "";
		String error = "";

		assertEquals(0, h.getRooms().size());

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

	/**
	 * Test the creation of a Room Group
	 */
	@Test
	public void testCreateRoomGroupMultipleRooms()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		String groupRoomName = "Group 1";
		List<Room> rooms = h.getRooms();

		assertEquals(0, h.getRooms().size());

		try
		{
			hc.createRoom(roomName);
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

	/**
	 * Test the creation of a Room Group without group name
	 */
	@Test
	public void testCreateRoomGroupNoName()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		String error = "";
		String groupRoomName = "";
		List<Room> rooms = h.getRooms();

		assertEquals(0, h.getRooms().size());

		try
		{
			hc.createRoom(roomName);
		} catch (InvalidInputException e)
		{
			fail();
		}

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

	/**
	 * Test the creation of a Room Group without initial room
	 */
	@Test
	public void testCreateRoomGroupNoInitialRoom()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		String error = "";
		String groupRoomName = "Group 1";
		List<Room> rooms = h.getRooms();

		assertEquals(0, h.getRooms().size());

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

	/**
	 * Test add room to room group
	 */
	@Test
	public void testAddRoomToGroupRoom()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		String name1 = "RoomName";
		String name2 = "Room2";
		String groupRoomName = "Group 1";
		List<Room> rooms = new ArrayList<Room>();
		List<Room> newRooms = new ArrayList<Room>();

		assertEquals(0, h.getRooms().size());

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

	/**
	 * Test add room to room group without a group
	 */
	@Test
	public void testAddRoomToGroupRoomNoGroup()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		String name1 = "RoomName";
		String error = "";
		List<Room> newRooms = new ArrayList<Room>();

		assertEquals(0, h.getRooms().size());

		try
		{
			hc.createRoom(name1);
		} catch (InvalidInputException e)
		{
			fail();
		}

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

	/**
	 * Test add room to room group without room
	 */
	@Test
	public void testAddRoomToGroupRoomNoRoom()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		String name1 = "RoomName";
		String error = "";
		String groupRoomName = "Group 1";

		assertEquals(0, h.getRooms().size());

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

	/**
	 * Test creation of multiple room groups
	 */
	@Test
	public void testMultipleRoomGroups()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		String name = "RoomName";
		String name2 = "RoomName2";
		String groupRoomName = "Group 1";
		String groupRoomName2 = "Group 2";

		List<Room> rooms = new ArrayList<Room>();

		assertEquals(0, h.getRooms().size());

		try
		{
			hc.createRoom(name);
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

		assertEquals("Group 1", h.getRoomGroup(0).getName());
		assertEquals("RoomName", h.getRoomGroup(0).getRoom(0).getName());

		assertEquals("Group 2", h.getRoomGroup(1).getName());
		assertEquals("RoomName", h.getRoomGroup(1).getRoom(0).getName());
		assertEquals("RoomName2", h.getRoomGroup(1).getRoom(1).getName());
	}

	/**
	 * Test setting volume within a single room
	 */
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

		assertEquals("kitchen", h.getRoom(0).getName());
		assertEquals(30, h.getRoom(0).getVolume());
	}

	/**
	 * Test setting volume within a single room to zero
	 */
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

	/**
	 * Test setting volume within a single room to zero
	 */
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

	/**
	 * Test setting volume within a room group
	 */
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

		assertEquals("Kitchen", h.getRoomGroup(0).getRoom(0).getName());
		assertEquals(30, h.getRoomGroup(0).getRoom(0).getVolume());
		assertEquals("Living Room", h.getRoomGroup(0).getRoom(1).getName());
		assertEquals(30, h.getRoomGroup(0).getRoom(1).getVolume());
		assertEquals(30, h.getRoomGroup(0).getVolume());
	}

	/**
	 * Test setting volume within a room group without a room group
	 */
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

	/**
	 * Test setting volume to zero within a room group
	 */
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

		assertEquals("Kitchen", h.getRoomGroup(0).getRoom(0).getName());
		assertEquals(0, h.getRoomGroup(0).getRoom(0).getVolume());
		assertTrue(h.getRoomGroup(0).getRoom(0).getMute());
		assertEquals("Living Room", h.getRoomGroup(0).getRoom(1).getName());
		assertEquals(0, h.getRoomGroup(0).getRoom(1).getVolume());
		assertTrue(h.getRoomGroup(0).getRoom(1).getMute());
	}

	/**
	 * Test setting mute within a room
	 */
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

	/**
	 * Test setting mute within a room without a room
	 */
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

	/**
	 * Test setting mute within a room group
	 */
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

	/**
	 * Test setting mute within a room group to false
	 */
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

	/**
	 * Test setting mute within a room group without a room group
	 */
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
}
