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
import ca.mcgill.ecse321.HAS.model.Playlist;
import ca.mcgill.ecse321.HAS.model.Room;
import ca.mcgill.ecse321.HAS.model.RoomGroup;
import ca.mcgill.ecse321.HAS.model.Song;
import ca.mcgill.ecse321.HAS.persistence.PersistenceXStream;

public class TestSorting
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

		hc.createArtist(artName);

		try
		{
			hc.createAlbum(name, genre, d1, h.getArtist(0));
		} catch (InvalidInputException e)
		{
			fail();
		}

		String testSongName1 = "testName";
		String testSongName2 = "testName2";
		int songDuration1 = 5;
		int songDuration2 = 123;
		int songPosition1 = 1;
		int songPosition2 = 2;

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

		String[] allNames =
		{ "Bob", "Jeb", "Oscar", "Vlad", "Aidan", "Kristina", "Aurélie",
				"Andrew", "Wang", "Gabe", "Flume" };
		Arrays.sort(allNames);
		for (int i = 0; i < allNames.length; i++)
		{
			assertTrue(allNames[i].equals(h.getAlbum(i).getName()));
		}

		assertEquals("testName", h.getAlbum(4).getSong(0).getName());
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

		h.delete();
		HAS h2 = HAS.getInstance();

		String[] names =
		{ "Bob", "Jeb", "Oscar", "Vlad", "Aidan", "Kristina", "Aurélie",
				"Andrew", "Wang", "Gabe" };
		for (String name : names)
			h.addArtist(new Artist(name));
		hc.sortArtists();

		Arrays.sort(names);
		for (int i = 0; i < names.length; i++)
		{
			assertTrue(names[i].equals(h2.getArtist(i).getName()));
		}
	}

	@Test
	public void testSortArtistNoArtists()
	{
		HAS h = HAS.getInstance();
		h.delete();

		HAS h2 = HAS.getInstance();
		HASController hc = new HASController();

		assertEquals(0, h2.getArtists().size());

		hc.sortArtists();
	}

	@Test
	public void testSortPlaylists()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();

		Song song = h.getSong(0);

		String[] names =
		{ "Workout", "Study", "Sleep", "Awake", "Alarm", "Kendo", "Competition",
				"Cooking", "Dishes", "Chill" };
		for (String name : names)
			h.addPlaylist(new Playlist(name, song));
		hc.sortPlaylists();

		Arrays.sort(names);
		for (int i = 0; i < names.length; i++)
		{
			assertTrue(names[i].equals(h.getPlaylist(i).getName()));
		}
	}

	@Test
	public void testSortPlaylistsNoPlaylists()
	{
		HAS h = HAS.getInstance();
		h.delete();

		HASController hc = new HASController();

		assertEquals(0, h.getPlaylists().size());
		hc.sortPlaylists();
	}

	@Test
	public void testSortRooms()
	{
		HAS h = HAS.getInstance();
		h.delete();

		HAS h2 = HAS.getInstance();
		HASController hc = new HASController();
		String[] roomNames =
		{ "Bob's Room", "Jeb's Room", "Oscar's Room", "Vlad's Den",
				"Aidan's Garage", "Living Room", "Kitchen", };

		for (String name : roomNames)
		{
			try
			{
				hc.createRoom(name);
			} catch (InvalidInputException e)
			{
				fail();
			}
		}

		hc.sortRooms();
		Arrays.sort(roomNames);
		for (int i = 0; i < roomNames.length; i++)
		{
			assertTrue(roomNames[i].equals(h2.getRoom(i).getName()));
		}

	}

	@Test
	public void testSortRoomsNoRooms()
	{
		HAS h = HAS.getInstance();
		h.delete();

		HASController hc = new HASController();
		assertEquals(0, h.getRooms().size());
		hc.sortRooms();
	}

	@Test
	public void testSortRoomGroups()
	{
		HAS h = HAS.getInstance();
		h.delete();

		HAS h2 = HAS.getInstance();
		HASController hc = new HASController();
		String[] roomNames =
		{ "Bob's Room", "Jeb's Room", "Oscar's Room", "Vlad's Den",
				"Aidan's Garage", "Living Room", "Kitchen", };

		for (String name : roomNames)
		{
			try
			{
				hc.createRoom(name);
			} catch (InvalidInputException e)
			{
				fail();
			}
		}

		String[] rgNames =
		{ "Bob's Set", "Rg1", "Guests", "Family", "Bedrooms", "Living Rooms",
				"Kitchens" };

		for (String rgName : rgNames)
		{
			try
			{
				hc.createRoomGroup(rgName, h.getRooms());
			} catch (InvalidInputException e)
			{
				fail();
			}
		}

		Arrays.sort(rgNames);
		hc.sortRoomGroups();

		for (int i = 0; i < rgNames.length; i++)
		{
			assertTrue(rgNames[i].equals(h2.getRoomGroup(i).getName()));
		}

	}

	@Test
	public void testSortRoomGroupsNoRGs()
	{
		HAS h = HAS.getInstance();
		h.delete();

		assertEquals(0, h.getRoomGroups().size());
		HASController hc = new HASController();

		hc.sortRoomGroups();
	}

}
