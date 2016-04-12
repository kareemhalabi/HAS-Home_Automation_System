package ca.mcgill.ecse321.controller;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.HAS.controller.HASController;
import ca.mcgill.ecse321.HAS.controller.InvalidInputException;
import ca.mcgill.ecse321.HAS.model.Album;
import ca.mcgill.ecse321.HAS.model.Artist;
import ca.mcgill.ecse321.HAS.model.HAS;
import ca.mcgill.ecse321.HAS.persistence.PersistenceXStream;

public class TestHASController
{
	String name = "Flume";
	String genre = "Indie";
	String artName = "Oscar";

	@SuppressWarnings("deprecation")
	Date d1 = new Date(116, 02, 8);

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

	@After
	public void tearDown() throws Exception
	{
		HAS h = HAS.getInstance();
		h.delete();
	}

	/**
	 * Tests creation of an artist
	 */
	@Test
	public void testCreateArtist()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();

		assertEquals(0, h.getArtists().size());

		try
		{
			hc.createArtist("Bob");
		} catch (InvalidInputException e)
		{
			fail();
		}

		assertEquals(1, h.getArtists().size());
		assertEquals("Bob", h.getArtist(0).getName());
	}

	/**
	 * Tests creation of an artist without a name
	 */
	@Test
	public void testCreateArtistNoName()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		String error = "";
		String artName = "";

		assertEquals(0, h.getAlbums().size());
		assertEquals(0, h.getArtists().size());

		try
		{
			hc.createArtist(artName);
		} catch (InvalidInputException e)
		{
			error = e.getMessage();
		}

		assertEquals("Artist name cannot be empty! ", error);
		assertEquals(0, h.getArtists().size());
	}

	/**
	 * Tests creation of album
	 */
	@Test
	public void testCreateAlbum()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		Artist ar1 = new Artist(artName);

		assertEquals(0, h.getAlbums().size());

		try
		{
			hc.createAlbum(name, genre, d1, ar1);
		} catch (InvalidInputException e)
		{
			fail();
		}

		// Persistence is simultaneously tested to check that createAlbum has
		// indeed written the correct data into the xml file.
		HAS h2 = (HAS) PersistenceXStream.loadFromXMLwithXStream();
		checkResultAlbum(h2, name, genre, artName, d1);
		assertEquals(1, h2.getAlbums().size());
	}

	/**
	 * Tests creation of an album without a name
	 */
	@Test
	public void testCreateAlbumNoName()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		String error = "";
		String name1 = "";
		Artist ar1 = new Artist(artName);

		assertEquals(0, h.getAlbums().size());

		try
		{
			hc.createAlbum(name1, genre, d1, ar1);
		} catch (InvalidInputException e)
		{
			error = e.getMessage();
		}

		assertEquals("Album name cannot be empty! ", error);
		assertEquals(0, h.getAlbums().size());
	}

	/**
	 * Tests creation of an album without a genre
	 */
	@Test
	public void testCreatAlbumNoGenre()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		String error = "";
		String genre1 = "";
		Artist ar1 = new Artist(artName);

		assertEquals(0, h.getAlbums().size());

		try
		{
			hc.createAlbum(name, genre1, d1, ar1);
		} catch (InvalidInputException e)
		{
			error = e.getMessage();
		}

		assertEquals("Genre name cannot be empty! ", error);
		assertEquals(0, h.getAlbums().size());
	}

	/**
	 * Tests creation of an album without release date
	 */
	@Test
	public void testCreateAlbumNoReleaseDate()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		String error = "";
		Date d2 = null;
		Artist ar1 = new Artist(artName);

		assertEquals(0, h.getAlbums().size());

		try
		{
			hc.createAlbum(name, genre, d2, ar1);
		} catch (InvalidInputException e)
		{
			error = e.getMessage();
		}

		assertEquals("Release date cannot be empty! ", error);
		assertEquals(0, h.getAlbums().size());
	}

	/**
	 * Tests creation of an album with a future release date
	 */
	@Test
	public void testCreateAlbumFutureReleaseDate()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		String error = "";
		@SuppressWarnings("deprecation")
		Date d3 = new Date(2007, 1, 25);
		Artist ar1 = new Artist(artName);

		assertEquals(0, h.getAlbums().size());

		try
		{
			hc.createAlbum(name, genre, d3, ar1);
		} catch (InvalidInputException e)
		{
			error = e.getMessage();
		}

		assertEquals("Release date cannot be in the future! ", error);
		assertEquals(0, h.getAlbums().size());
	}

	/**
	 * Tests the creation of an album with a present release date
	 */
	@Test
	public void testCreateAlbumPresentReleaseDate()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		// date is set relative to January 1900
		@SuppressWarnings("deprecation")
		Date d4 = new Date(116, 3, 11);
		Artist ar1 = new Artist(artName);

		assertEquals(0, h.getAlbums().size());

		try
		{
			hc.createAlbum(name, genre, d4, ar1);
		} catch (InvalidInputException e)
		{
			fail();
		}

		assertEquals(1, h.getAlbums().size());
	}

	/**
	 * Tests creation of an album without an artist
	 */
	@Test
	public void testCreateAlbumNoArtist()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		String error = "";
		Artist ar1 = null;

		assertEquals(0, h.getAlbums().size());

		try
		{
			hc.createAlbum(name, genre, d1, ar1);
		} catch (InvalidInputException e)
		{
			error = e.getMessage();
		}

		assertEquals("Album must have an artist! ", error);
		assertEquals(0, h.getAlbums().size());
	}

	public static void checkResultAlbum(	HAS h, String name, String genre,
									String artName, Date date)
	{
		assertEquals(1, h.getAlbums().size());
		assertEquals(name, h.getAlbum(0).getName());
		assertEquals(genre, h.getAlbum(0).getGenre());
		assertEquals(artName, h.getAlbum(0).getMainArtist().getName());
		assertEquals(date, h.getAlbum(0).getReleaseDate());
	}

}
