package ca.mcgill.ecse321.controller;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
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
import ca.mcgill.ecse321.HAS.model.RoomGroup;
import ca.mcgill.ecse321.HAS.model.Song;
import ca.mcgill.ecse321.HAS.persistence.PersistenceXStream;

public class TestSortingAndPlay
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
			// check that no error has occurred in the creation of the album
			fail();
		}

		HAS h2 = (HAS) PersistenceXStream.loadFromXMLwithXStream();

		// new song 1 on album 1
		String testSongName1 = "testName";
		String testSongName2 = "testName2";
		int songDuration1 = 5;
		int songDuration2 = 123;
		int songPosition1 = 1;
		int songPosition2 = 2;

		// add song to an album
		try
		{
			hc.addSongtoAlbum(h.getAlbum(0), testSongName1, songDuration1,
					songPosition1, null);
		} catch (InvalidInputException e)
		{
			fail();
		}

		try
		{
			hc.addSongtoAlbum(h.getAlbum(0), testSongName2, songDuration2,
					songPosition2, null);
		} catch (InvalidInputException e)
		{
			fail();
		}

		String roomName = "RoomName";
		try
		{
			hc.createRoom(roomName);
		} catch (InvalidInputException e)
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
	public void testSortSongs()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		@SuppressWarnings("deprecation")
		Date d1 = new Date(116, 02, 8);

		Album a = new Album("Jack", "Bring me food", d1,
				new Artist("Jack the Reaper"));
		h.addAlbum(a);

		String[] names =
		{ "Wind Rises", "Dark Horses", "Leo's Oscar", "Life", "KIA", "IKEA",
				"Food", "Cake", "Porto", "Angel" };
		int[] position =
		{ 2, 3, 7, 8, 1, 4, 9, 10, 6, 5 };
		int i = 0;

		for (String name : names)
		{
			Song song = new Song(name, 123, position[i], a);
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

	@Test
	public void testSortAlbums()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();

		h.removeAlbum(h.getAlbum(0));

		String[] names =
		{ "Bob", "Jeb", "Oscar", "Vlad", "Aidan", "Kristina", "Aurélie",
				"Andrew", "Wang", "Gabe" };

		@SuppressWarnings("deprecation")
		Date d1 = new Date(116, 02, 8);

		Artist Bob = new Artist("Bob the Artist");
		h.addArtist(Bob);

		for (String name : names)
		{
			try
			{
				hc.createAlbum(name, "Sort Yourself", d1, Bob);
			} catch (InvalidInputException e)
			{
				fail();
			}
		}

		hc.sortAlbums();
		List<Album> sortedAlbums = h.getAlbums();

		Arrays.sort(names);
		for (int i = 0; i < names.length; i++)
		{
			assertTrue(names[i].equals(h.getAlbum(i).getName()));
		}

		Artist bob = h.getArtist(0);
		List<Album> albums = bob.getAlbums();

		for (Album a : sortedAlbums)
		{
			assertTrue(albums.contains(a));
		}
	}

	@Test
	public void testSortAlbumsNoAlbums()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		h.removeAlbum(h.getAlbum(0));

		assertEquals(0, h.getAlbums().size());
		hc.sortAlbums();
	}

	@Test
	public void testSortArtists()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();

		String[] names =
		{ "Bob", "Jeb", "Oscar", "Vlad", "Aidan", "Kristina", "Aurélie",
				"Andrew", "Wang", "Gabe" };
		for (String name : names)
			h.addArtist(new Artist(name));
		hc.sortArtists();

		Arrays.sort(names);
		for (int i = 0; i < names.length; i++)
		{
			assertTrue(names[i].equals(h.getArtist(i).getName()));
		}
	}

	@Test
	public void testSortArtistNoArtists()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();

		assertEquals(0, h.getArtists().size());

		hc.sortArtists();
	}

	@Test
	public void testPlaySongSingleRoom()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		Song s = h.getSong(0);
		Room room = h.getRoom(0);

		try
		{
			hc.playSingleRoom(s, room);
		} catch (InvalidInputException e)
		{
			fail();
		}

		assertEquals("testName", room.getPlayable().getName());
		assertTrue(room.hasPlayable());
	}

	@Test
	public void testPlayNoSongSingleRoom()
	{
		String error = "";
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		Room room = h.getRoom(0);

		try
		{
			hc.playSingleRoom(null, room);
		} catch (InvalidInputException e)
		{
			error = e.getMessage();
		}

		assertEquals("A playable must be selected! ", error);
		assertFalse(room.hasPlayable());
	}

	@Test
	public void testPlaySongNoSingleRoom()
	{
		String error = "";
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		Room room = null;
		Song s = h.getSong(0);

		try
		{
			hc.playSingleRoom(s, room);
		} catch (InvalidInputException e)
		{
			error = e.getMessage();
		}

		assertEquals("A room must be selected! ", error);
	}

	@Test
	// TODO: EXPECT TO FAIL
	public void testPlayAlbumSingleRoom()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		Album a = h.getAlbum(0);
		Room room = h.getRoom(0);

		try
		{
			hc.playSingleRoom(a, room);
		} catch (InvalidInputException e)
		{
			fail();
		}

		// iterated through both
		assertEquals("Flume", room.getPlayable().getName());
		assertTrue(room.hasPlayable());
	}

	@Test
	public void testPlaySongRoomGroup()
	{
		String error = "";
		HAS h = HAS.getInstance();
		HASController hc = new HASController();

		List<Room> rooms = new ArrayList<Room>();
		rooms.add(h.getRoom(0));

		try
		{
			hc.createRoomGroup("RoomGroup1", rooms);
		} catch (InvalidInputException e)
		{
			fail();
		}
		
		RoomGroup rg = h.getRoomGroup(0);
		try
		{
			hc.playRoomGroup(h.getSong(0), rg);
		} catch (InvalidInputException e)
		{
			fail();
		}

		assertEquals("testName", rg.getPlayable().getName());
		assertTrue(rg.hasPlayable());
	}
	
	@Test
	public void testPlayNoSongRoomGroup()
	{
		String error = "";
		HAS h = HAS.getInstance();
		HASController hc = new HASController();

		List<Room> rooms = new ArrayList<Room>();
		rooms.add(h.getRoom(0));

		try
		{
			hc.createRoomGroup("RoomGroup1", rooms);
		} catch (InvalidInputException e)
		{
			fail();
		}
		
		RoomGroup rg = h.getRoomGroup(0);
		try
		{
			hc.playRoomGroup(null, rg);
		} catch (InvalidInputException e)
		{
			error = e.getMessage();
		}

		assertEquals("A playable must be selected! ", error);
		assertFalse(rg.hasPlayable());
	}
	
	@Test
	public void testPlaySongNoRoomGroup()
	{
		String error = "";
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		try
		{
			hc.playRoomGroup(h.getSong(0), null);
		} catch (InvalidInputException e)
		{
			error = e.getMessage();
		}

		assertEquals("A room group must be selected! ", error);
	}
}
