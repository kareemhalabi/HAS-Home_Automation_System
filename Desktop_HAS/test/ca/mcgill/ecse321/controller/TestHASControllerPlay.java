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
import ca.mcgill.ecse321.HAS.model.Room;
import ca.mcgill.ecse321.HAS.model.Song;
import ca.mcgill.ecse321.HAS.persistence.PersistenceXStream;

public class TestHASControllerPlay
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
			hc.addSongtoAlbum(h.getAlbum(0), testSongName1, songDuration1, songPosition1, null);
		} catch (InvalidInputException e)
		{
			fail();
		}

		try
		{
			hc.addSongtoAlbum(h.getAlbum(0), testSongName2, songDuration2, songPosition2, null);
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
	//	TODO; EXPECT TO FAIL
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

		assertEquals("testName", room.getCurrentSong().getName());
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
	//TODO: EXPECT TO FAIL
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

		//iterated through both
		assertEquals("testName2", room.getCurrentSong().getName());
		assertEquals("Flume", room.getPlayable().getName());
		assertTrue(room.hasPlayable());
	}
}