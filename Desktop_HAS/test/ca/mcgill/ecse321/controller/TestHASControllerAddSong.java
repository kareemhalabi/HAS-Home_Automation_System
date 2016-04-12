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
import ca.mcgill.ecse321.HAS.model.Song;
import ca.mcgill.ecse321.HAS.persistence.PersistenceXStream;

public class TestHASControllerAddSong
{
	String name = "Flume";
	String genre = "Indie";
	String artName = "Oscar";
	@SuppressWarnings("deprecation")
	Date d1 = new Date(116, 02, 8);
	String testSongName1 = "testName";
	int songDuration1 = 213;
	int songPosition1 = 1;
	List<Artist> featured = new ArrayList<Artist>();
	Artist ft1 = new Artist("Adele");
	Artist ar1 = new Artist(artName);

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
		HASController hc = new HASController();
		try
		{
			hc.createAlbum(name, genre, d1, ar1);
		} catch (InvalidInputException e)
		{
			fail();
		}

		featured.add(ft1);
	}

	@After
	public void tearDown() throws Exception
	{
		HAS h = HAS.getInstance();
		h.delete();
	}

	/**
	 * Tests the creation of a song
	 */
	@Test
	public void testAddSong()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();

		try
		{
			hc.addSongtoAlbum(h.getAlbum(0), testSongName1, songDuration1,
					songPosition1, featured);
		} catch (InvalidInputException e)
		{
			fail();
		}
	}

	/**
	 * Tests the creation of a song without name
	 */
	@Test
	public void testAddSongNoName()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		String error = "";
		String testSongName2 = "";

		try
		{
			hc.addSongtoAlbum(h.getAlbum(0), testSongName2, songDuration1,
					songPosition1, featured);
		}

		catch (InvalidInputException e)
		{
			error = e.getMessage();
		}
		assertEquals("Song must have a name! ", error);
	}

	/**
	 * Tests the creation of a song without an album
	 */
	@Test
	public void testAddSongNoAlbum()
	{
		HASController hc = new HASController();
		String error = "";

		try
		{
			hc.addSongtoAlbum(null, testSongName1, songDuration1, songPosition1,
					featured);
		}

		catch (InvalidInputException e)
		{
			error = e.getMessage();
		}
		assertEquals("Song must belong to an album! ", error);
	}

	/**
	 * Tests the creation of a song without a duration
	 */
	@Test
	public void testAddSongNoDuration()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		String error = "";
		int songDuration2 = 0;

		try
		{
			hc.createAlbum(name, genre, d1, ar1);
		} catch (InvalidInputException e)
		{
			fail();
		}

		try
		{
			hc.addSongtoAlbum(h.getAlbum(0), testSongName1, songDuration2,
					songPosition1, featured);
		}

		catch (InvalidInputException e)
		{
			error = e.getMessage();
		}
		assertEquals("Song must have a duration! ", error);
	}

	/**
	 * Tests the creation of a song without a position
	 */
	@Test
	public void testAddSongNoPosition()

	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		String error = "";
		int songPosition2 = 0;

		try
		{
			hc.addSongtoAlbum(h.getAlbum(0), testSongName1, songDuration1,
					songPosition2, featured);
		}

		catch (InvalidInputException e)
		{
			error = e.getMessage();
		}

		assertEquals("Song must have a position! ", error);
	}

	/**
	 * Tests the creation of a song without featured artists
	 */
	@Test
	public void addSongNoFeatured()

	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		featured.removeAll(featured);

		try
		{
			hc.addSongtoAlbum(h.getAlbum(0), testSongName1, songDuration1,
					songPosition1, featured);
		}

		catch (InvalidInputException e)
		{
			fail();
		}

		assertEquals(0, h.getAlbum(0).getSong(0).getFtArtists().size());
	}

	/**
	 * Tests the creation of a song with many featured artists
	 */
	@Test
	public void addSongManyFeatured()

	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		Artist ft2 = new Artist("Black Jack");
		Artist ft3 = new Artist("Keiko");
		featured.add(ft2);
		featured.add(ft3);

		try
		{
			hc.addSongtoAlbum(h.getAlbum(0), testSongName1, songDuration1,
					songPosition1, featured);
		}

		catch (InvalidInputException e)
		{
			fail();
		}

		assertEquals(3, h.getAlbum(0).getSong(0).getFtArtists().size());
		assertEquals("Adele",
				h.getAlbum(0).getSong(0).getFtArtist(0).getName());
		assertEquals("Black Jack",
				h.getAlbum(0).getSong(0).getFtArtist(1).getName());
		assertEquals("Keiko",
				h.getAlbum(0).getSong(0).getFtArtist(2).getName());
	}

	/**
	 * Tests the creation of two songs with same position
	 */
	@Test
	public void testAddSongSamePosition()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		String errors = "";

		try
		{
			hc.addSongtoAlbum(h.getAlbum(0), testSongName1, songDuration1,
					songPosition1, featured);
		} catch (InvalidInputException e)
		{
			fail();
		}

		try
		{
			hc.addSongtoAlbum(h.getAlbum(0), "Please fail", songDuration1,
					songPosition1, null);
		} catch (InvalidInputException e)
		{
			errors = e.getMessage();
		}

		assertEquals(
				"A song already occupies this position, please choose another position!",
				errors);
	}

	/**
	 * Tests the addition of a featured artist association to a song
	 */
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

	/**
	 * Tests the addition of a featured artist association to a song without a
	 * song
	 */
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

	/**
	 * Tests the addition of a featured artist association to a song without an
	 * artist
	 */
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
