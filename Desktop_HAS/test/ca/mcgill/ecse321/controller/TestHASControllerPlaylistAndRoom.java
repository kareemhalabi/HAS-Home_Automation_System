package ca.mcgill.ecse321.controller;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.HAS.controller.HASController;
import ca.mcgill.ecse321.HAS.controller.InvalidInputException;
import ca.mcgill.ecse321.HAS.model.Album;
import ca.mcgill.ecse321.HAS.model.Artist;
import ca.mcgill.ecse321.HAS.model.HAS;
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
			hc.addSongtoAlbum(h.getAlbum(0), testSongName1, songDuration1, songPosition1);
		}

		catch (InvalidInputException e)
		{
			fail();
		}

		try
		{
			hc.addSongtoAlbum(h.getAlbum(0), testSongName2, songDuration2, songPosition2);
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
	public void testCreatePlaylist()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();

		assertEquals(0, h.getPlaylists().size());

		String playlistName = "Playlist1";

		try
		{
			hc.createPlaylist(playlistName, h.getAlbum(0).getSong(0));
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

		try
		{
			hc.createPlaylist(playlistName, h.getAlbum(0).getSong(0));
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

		try
		{
			hc.createPlaylist(playlistName, null);
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

		try
		{
			hc.createPlaylist(playlistName, h.getAlbum(0).getSong(0));
		} catch (InvalidInputException e)
		{
			fail();
		}

		try
		{
			hc.addSongtoPlaylist(h.getPlaylist(0), h.getAlbum(0).getSong(1));
		} catch (InvalidInputException e)
		{
			fail();
		}

		assertEquals(2, h.getPlaylist(0).getSongs().size());
		assertEquals("testName2", h.getPlaylist(0).getSong(1).getName());
		assertEquals(123, h.getPlaylist(0).getSong(1).getDuration());
		assertEquals(2, h.getPlaylist(0).getSong(1).getPosition());
	}

	@Test
	public void testAddSongToPlaylistNoPlaylist()

	{
		HAS h = HAS.getInstance();
		assertEquals(0, h.getPlaylists().size());

		HASController hc = new HASController();
		String error = "";

		try
		{
			hc.addSongtoPlaylist(null, h.getAlbum(0).getSong(1));
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

		try
		{
			hc.createPlaylist(playlistName, h.getAlbum(0).getSong(0));
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

		assertEquals("A song must be selected!", error);
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
	public void testCreateRoomGroup()
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

		try
		{
			hc.createRoomGroup(groupRoomName, h.getRoom(0));
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

		try
		{
			hc.createRoomGroup(groupRoomName, h.getRoom(0));
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

		try
		{
			hc.createRoomGroup(groupRoomName, null);
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

		try
		{
			hc.createRoomGroup(groupRoomName, h.getRoom(0));
		} catch (InvalidInputException e)
		{
			fail();
		}

		try
		{
			hc.addRoomToRoomGroup(h.getRoomGroup(0), h.getRoom(1));
		} catch (InvalidInputException e)
		{
			fail();
		}

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

		try
		{
			hc.addRoomToRoomGroup(null, h.getRoom(0));
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

		try
		{
			hc.createRoomGroup(groupRoomName, h.getRoom(0));
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

		assertEquals("Must select a room to add to room group!", error);
		assertEquals(1, h.getRoomGroup(0).getRooms().size());
	}

	@Test
	public void testSetVolume()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		int volume = 5;

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
		assertEquals(5, h.getRoom(0).getVolume());
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

	private void checkResultAlbum(HAS h, String name, String genre, String artName, Date date)
	{
		assertEquals(1, h.getAlbums().size());
		assertEquals(name, h.getAlbum(0).getName());
		assertEquals(genre, h.getAlbum(0).getGenre());
		assertEquals(artName, h.getAlbum(0).getMainArtist().getName());
		assertEquals(date, h.getAlbum(0).getReleaseDate());
	}
}
