package ca.mcgill.ecse321.controller;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import ca.mcgill.ecse321.HAS.model.Playlist;
import ca.mcgill.ecse321.HAS.model.Song;
import ca.mcgill.ecse321.HAS.persistence.PersistenceXStream;

//TODO: check if it is needed to create a @Before
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
		HASController hc = new HASController();
		assertEquals(0, h.getAlbums().size());

		// album attributes
		String name = "Flume";
		String genre = "Indie";
		String artName = "Oscar";

		@SuppressWarnings("deprecation")
		Date d1 = new Date(116, 02, 8);

		Artist ar1 = new Artist(artName);

		try
		{
			hc.createAlbum(name, genre, d1, ar1);
		} catch (InvalidInputException e)
		{
			fail();
		}

		HAS h2 = (HAS) PersistenceXStream.loadFromXMLwithXStream();
		checkResultAlbum(h2, name, genre, artName, d1);

		String testSongName1 = "testName";
		int songDuration1 = 213;
		int songPosition1 = 1;
		assertEquals(1, h.getAlbums().size());

		List<Artist> featured = new ArrayList<Artist>();

		Artist ft1 = new Artist("Adele");
		featured.add(ft1);

		try
		{
			hc.addSongtoAlbum(h.getAlbum(0), testSongName1, songDuration1, songPosition1, featured);
		} catch (InvalidInputException e)
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
		HASController hc = new HASController();
		String error = "";
		assertEquals(0, h.getAlbums().size());

		String name = null;
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
			error = e.getMessage();
		}

		assertEquals("Album name cannot be empty! ", error);
		assertEquals(0, h.getAlbums().size());
	}

	@Test
	public void testCreatAlbumNoGenre()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		String error = "";
		assertEquals(0, h.getAlbums().size());

		String name = "Flume";
		String genre = "";
		String artName = "Oscar";
		@SuppressWarnings("deprecation")
		Date d1 = new Date(107, 01, 25);

		Artist ar1 = new Artist(artName);

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
		HASController hc = new HASController();
		String error = "";
		assertEquals(0, h.getAlbums().size());

		String name = "Flume";
		String genre = "Indie";
		String artName = "Oscar";
		Date d1 = null;

		Artist ar1 = new Artist(artName);

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
		HASController hc = new HASController();
		String error = "";
		assertEquals(0, h.getAlbums().size());

		String name = "Flume";
		String genre = "Indie";
		String artName = "Oscar";
		@SuppressWarnings("deprecation")
		Date d1 = new Date(2007, 1, 25);

		Artist ar1 = new Artist(artName);

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
	public void testCreateAlbumPresentReleaseDate()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		assertEquals(0, h.getAlbums().size());

		String name = "Flume";
		String genre = "Indie";
		String artName = "Oscar";
		@SuppressWarnings("deprecation")
		Date d1 = new Date(116, 2, 8);

		Artist ar1 = new Artist(artName);

		try
		{
			hc.createAlbum(name, genre, d1, ar1);
		} catch (InvalidInputException e)
		{
			fail();
		}

		assertEquals(1, h.getAlbums().size());
	}

	@Test
	public void testCreateAlbumNoArtist()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		String error = "";
		assertEquals(0, h.getAlbums().size());

		String name = "Flume";
		String genre = "Indie";

		@SuppressWarnings("deprecation")
		Date d1 = new Date(107, 01, 25);

		Artist ar1 = null;

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

	@Test
	public void testCreateArtistNull()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		String error = "";
		assertEquals(0, h.getAlbums().size());
		assertEquals(0, h.getArtists().size());

		String artName = "";

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
		HAS h = HAS.getInstance();
		HASController hc = new HASController();

		assertEquals(0, h.getAlbums().size());

		String name = "Flume";
		String genre = "Indie";
		String artName = "Oscar";
		String error = "";

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

		HAS h2 = (HAS) PersistenceXStream.loadFromXMLwithXStream();
		checkResultAlbum(h2, name, genre, artName, d1);

		String testSongName1 = "   ";
		int songDuration1 = 213;
		int songPosition1 = 1;

		List<Artist> featured = new ArrayList<Artist>();

		Artist ft1 = new Artist("Adele");
		featured.add(ft1);

		try
		{
			hc.addSongtoAlbum(h.getAlbum(0), testSongName1, songDuration1, songPosition1, featured);
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
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		assertEquals(0, h.getAlbums().size());

		String name = "Flume";
		String genre = "Indie";
		String artName = "Oscar";

		@SuppressWarnings("deprecation")
		Date d1 = new Date(107, 01, 25);

		String error = "";

		Artist ar1 = new Artist(artName);

		try
		{
			hc.createAlbum(name, genre, d1, ar1);
		} catch (InvalidInputException e)
		{
			fail();
		}

		HAS h2 = (HAS) PersistenceXStream.loadFromXMLwithXStream();
		checkResultAlbum(h2, name, genre, artName, d1);

		String testSongName1 = "testName";
		int songDuration1 = 213;
		int songPosition1 = 1;

		List<Artist> featured = new ArrayList<Artist>();

		Artist ft1 = new Artist("Adele");
		featured.add(ft1);

		try
		{
			hc.addSongtoAlbum(null, testSongName1, songDuration1, songPosition1, featured);
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
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		String error = "";
		assertEquals(0, h.getAlbums().size());

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

		HAS h2 = (HAS) PersistenceXStream.loadFromXMLwithXStream();
		checkResultAlbum(h2, name, genre, artName, d1);

		String testSongName1 = "testName";
		int songDuration1 = 0;
		int songPosition1 = 1;

		List<Artist> featured = new ArrayList<Artist>();

		Artist ft1 = new Artist("Adele");
		featured.add(ft1);

		try
		{
			hc.addSongtoAlbum(h.getAlbum(0), testSongName1, songDuration1, songPosition1, featured);
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
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		String error = "";
		assertEquals(0, h.getAlbums().size());

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

		HAS h2 = (HAS) PersistenceXStream.loadFromXMLwithXStream();
		checkResultAlbum(h2, name, genre, artName, d1);

		String testSongName1 = "testName";
		int songDuration1 = 213;
		int songPosition1 = 0;

		List<Artist> featured = new ArrayList<Artist>();

		Artist ft1 = new Artist("Adele");
		featured.add(ft1);

		try
		{
			hc.addSongtoAlbum(h.getAlbum(0), testSongName1, songDuration1, songPosition1, featured);
		}

		catch (InvalidInputException e)
		{
			error = e.getMessage();
		}
		assertEquals("Song must have a position! ", error);
	}

	@Test
	public void testSortArtists()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();

		String[] names =
		{ "Bob", "Jeb", "Oscar", "Vlad", "Aidan", "Kristina", "Aurélie", "Andrew", "Wang", "Gabe" };
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
	public void testSortAlbums()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();

		String[] names =
		{ "Bob", "Jeb", "Oscar", "Vlad", "Aidan", "Kristina", "Aurélie", "Andrew", "Wang", "Gabe" };

		@SuppressWarnings("deprecation")
		Date d1 = new Date(116, 02, 8);

		for (String name : names)
			h.addAlbum(new Album(name, "Sort Yourself", d1, new Artist("Bob the Artist")));
		hc.sortAlbums();

		Arrays.sort(names);
		for (int i = 0; i < names.length; i++)
		{
			assertTrue(names[i].equals(h.getAlbum(i).getName()));
		}
	}


	private void checkResultSong(HAS h, String testSongName1, int songDuration1, int songPosition1)
	{
		assertEquals(testSongName1, h.getAlbum(0).getSong(0).getName());
		assertEquals(songDuration1, h.getAlbum(0).getSong(0).getDuration());
		assertEquals(songPosition1, h.getAlbum(0).getSong(0).getPosition());

		assertEquals(testSongName1, h.getSong(0).getName());
		assertEquals(songDuration1, h.getSong(0).getDuration());
		assertEquals(songPosition1, h.getSong(0).getPosition());
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
