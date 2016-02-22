package ca.mcgill.ecse321.controller;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Date;

import org.junit.After;
import org.junit.AfterClass;
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

public class TestHASController
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

	@After
	public void tearDown() throws Exception
	{
		HAS h = HAS.getInstance();
		h.delete();
	}

	@Test
	public void testCreateAlbumAndAddSong()
	{
		HAS h = HAS.getInstance();
		assertEquals(0, h.getAlbums().size());

		// album attributes
		String name = "Flume";
		String genre = "Indie";
		String artName = "Oscar";
		Date d1 = new Date(107, 01, 25);

		// create the artist
		Artist ar1 = new Artist(artName);

		// create controller and create the album
		HASController hc = new HASController();
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
		int songDuration1 = 213;
		int songPosition1 = 1;

		// add song to an album
		try
		{
			assertEquals(1, h.getAlbums().size());
			hc.addSongtoAlbum(h.getAlbum(0), testSongName1, songDuration1, songPosition1);
		}

		catch (InvalidInputException e)
		{
			fail();
		}

		HAS h3 = (HAS) PersistenceXStream.loadFromXMLwithXStream();
		checkResultSong(h3, testSongName1, songDuration1, songPosition1);
	}

	@Test
	public void testCreateAlbumNoName()
	{
		HAS h = HAS.getInstance();
		assertEquals(0, h.getAlbums().size());

		// album attributes
		String name = null;
		String genre = "Indie";
		String artName = "Oscar";
		Date d1 = new Date(107, 01, 25);

		String error = "";

		// create the artist
		Artist ar1 = new Artist(artName);

		// create controller and create the album
		HASController hc = new HASController();
		try
		{
			hc.createAlbum(name, genre, d1, ar1);
		} catch (InvalidInputException e)
		{
			error = e.getMessage();
		}

		assertEquals("Album name cannot be empty! ", error);
		assertEquals(0, h.getAlbums().size());

	}

	@Test
	public void testCreatAlbumNoGenre()
	{
		HAS h = HAS.getInstance();
		assertEquals(0, h.getAlbums().size());

		// album attributes
		String name = "Flume";
		String genre = "";
		String artName = "Oscar";
		Date d1 = new Date(107, 01,25);

		String error = "";

		// create the artist
		Artist ar1 = new Artist(artName);

		// create controller and create the album
		HASController hc = new HASController();
		try
		{
			hc.createAlbum(name, genre, d1, ar1);
		} catch (InvalidInputException e)
		{
			error = e.getMessage();
		}

		assertEquals("Genre name cannot be empty! ", error);
		assertEquals(0, h.getAlbums().size());

	}

	@Test
	public void testCreateAlbumNoReleaseDate()
	{
		HAS h = HAS.getInstance();
		assertEquals(0, h.getAlbums().size());

		// album attributes
		String name = "Flume";
		String genre = "Indie";
		String artName = "Oscar";
		Date d1 = null;

		String error = "";

		// create the artist
		Artist ar1 = new Artist(artName);

		// create controller and create the album
		HASController hc = new HASController();
		try
		{
			hc.createAlbum(name, genre, d1, ar1);
		} catch (InvalidInputException e)
		{
			error = e.getMessage();
		}

		assertEquals("Release date cannot be empty! ", error);
		assertEquals(0, h.getAlbums().size());
	}
	
	@Test
	public void testCreateAlbumFutureReleaseDate()
	{
		HAS h = HAS.getInstance();
		assertEquals(0, h.getAlbums().size());

		// album attributes
		String name = "Flume";
		String genre = "Indie";
		String artName = "Oscar";
		Date d1 = new Date(2007, 1, 25);

		String error = "";

		// create the artist
		Artist ar1 = new Artist(artName);

		// create controller and create the album
		HASController hc = new HASController();
		try
		{
			hc.createAlbum(name, genre, d1, ar1);
		} catch (InvalidInputException e)
		{
			error = e.getMessage();
		}

		assertEquals("Release date cannot be in the future! ", error);
		assertEquals(0, h.getAlbums().size());
	}

	@Test
	public void testCreateAlbumNoArtist()
	{
		HAS h = HAS.getInstance();
		assertEquals(0, h.getAlbums().size());

		// album attributes
		String name = "Flume";
		String genre = "Indie";
		String artName = null;
		
		//For some weird reason my computer adds 1900 years to the date below
		Date d1 = new Date(107, 01, 25);

		String error = "";

		// create the artist
		Artist ar1 = null;

		// create controller and create the album
		HASController hc = new HASController();
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

	@Test
	public void testCreateArtistNull()
	{
		HAS h = HAS.getInstance();
		assertEquals(0, h.getAlbums().size());
		assertEquals(0, h.getArtists().size());

		String artName = "";
		String error = "";

		HASController hc = new HASController();
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

	@Test
	public void testAddSongNoName()
	{
		// make sure the has has been reset
		HAS h = HAS.getInstance();
		assertEquals(0, h.getAlbums().size());

		// album attributes of the album
		String name = "Flume";
		String genre = "Indie";
		String artName = "Oscar";
		//For some wierd reason, my calendar has decided to add 1900 years to my year... 
		Date d1 = new Date(107, 01, 25);

		String error = "";

		// create the artist
		Artist ar1 = new Artist(artName);

		// create controller and create the album
		HASController hc = new HASController();

		try
		{
			hc.createAlbum(name, genre, d1, ar1);
		} 
		catch (InvalidInputException e)
		{
			// check that no error has occurred in the creation of the album
			fail();
		}

		HAS h2 = (HAS) PersistenceXStream.loadFromXMLwithXStream();

		// check file contents
		checkResultAlbum(h2, name, genre, artName, d1);

		// new song 1 on album 1
		String testSongName1 = "   ";
		int songDuration1 = 213;
		int songPosition1 = 1;

		// add song to an album
		try
		{
			hc.addSongtoAlbum(h.getAlbum(0), testSongName1, songDuration1, songPosition1);
		}

		catch (InvalidInputException e)
		{
			error = e.getMessage();
		}

		assertEquals("Song must have a name! ", error);

	}

	@Test
	public void testAddSongNoAlbum()
	{
		// make sure the has has been reset
		HAS h = HAS.getInstance();
		assertEquals(0, h.getAlbums().size());

		// album attributes of the album
		String name = "Flume";
		String genre = "Indie";
		String artName = "Oscar";
		//For some wierd reason my calendar has decided to add 1900 years to my year...
		Date d1 = new Date(107, 01, 25);

		String error = "";

		// create the artist
		Artist ar1 = new Artist(artName);

		// create controller and create the album
		HASController hc = new HASController();

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
		int songDuration1 = 213;
		int songPosition1 = 1;

		// add song to an album
		try
		{
			hc.addSongtoAlbum(null, testSongName1, songDuration1, songPosition1);
		}

		catch (InvalidInputException e)
		{
			error = e.getMessage();
		}

		assertEquals("Song must belong to an album! ", error);

	}

	@Test
	public void testAddSongNoDuration()
	{
		// make sure the has has been reset
		HAS h = HAS.getInstance();
		assertEquals(0, h.getAlbums().size());

		// album attributes of the album
		String name = "Flume";
		String genre = "Indie";
		String artName = "Oscar";
		Date d1 = new Date(107, 01, 25);

		String error = "";

		// create the artist
		Artist ar1 = new Artist(artName);

		// create controller and create the album
		HASController hc = new HASController();

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
		int songDuration1 = 0;
		int songPosition1 = 1;

		// add song to an album
		try
		{
			hc.addSongtoAlbum(h.getAlbum(0), testSongName1, songDuration1, songPosition1);
		}

		catch (InvalidInputException e)
		{
			error = e.getMessage();
		}

		assertEquals("Song must have a duration! ", error);
	}

	@Test
	public void testAddSongNoPosition()
	{
		// make sure the has has been reset
		HAS h = HAS.getInstance();
		assertEquals(0, h.getAlbums().size());

		// album attributes of the album
		String name = "Flume";
		String genre = "Indie";
		String artName = "Oscar";
		Date d1 = new Date(107, 01, 25);

		String error = "";

		// create the artist
		Artist ar1 = new Artist(artName);

		// create controller and create the album
		HASController hc = new HASController();

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
		int songDuration1 = 213;
		int songPosition1 = 0;

		// add song to an album
		try
		{
			hc.addSongtoAlbum(h.getAlbum(0), testSongName1, songDuration1, songPosition1);
		}

		catch (InvalidInputException e)
		{
			error = e.getMessage();
		}

		assertEquals("Song must have a position! ", error);

	}

	private void checkResultSong(HAS h, String testSongName1, int songDuration1, int songPosition1)
	{
		//association with album
		assertEquals(testSongName1, h.getAlbum(0).getSong(0).getName());
		assertEquals(songDuration1, h.getAlbum(0).getSong(0).getDuration());
		assertEquals(songPosition1, h.getAlbum(0).getSong(0).getPosition());
		
		//association with HAS
		assertEquals(testSongName1, h.getSong(0).getName());
		assertEquals(songDuration1, h.getSong(0).getDuration());
		assertEquals(songPosition1, h.getSong(0).getPosition());
		
	}

	private void checkResultAlbum(HAS h, String name, String genre, String artName, Date date)
	{
		assertEquals(1, h.getAlbums().size());
		assertEquals(name, h.getAlbum(0).getName());
		assertEquals(genre, h.getAlbum(0).getGenre());
		assertEquals(artName, h.getAlbum(0).getArtist().getName());
		assertEquals(date, h.getAlbum(0).getReleaseDate());
	}

}
