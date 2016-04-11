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

	String testSongName1 = "testName";
	int songDuration1 = 213;
	int songPosition1 = 1;

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

	// Both createAlbum and addSong are tested within in order to avoid
	// 1) having to re-copy the same createAlbum before proceeding to AddSong
	// 2) test all connections between artists, albums and songs
	// Intermediary checks are present in order to be able to locate a test
	// failure should a specific step of the process fail
	@Test
	public void testCreateAlbumAndAddSong()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		assertEquals(0, h.getAlbums().size());

		Artist ar1 = new Artist(artName);

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

		// an arbitrary featured artist is entered into the HAS system in order
		// to fully check the functionality of addSongtoAlbum().
		List<Artist> featured = new ArrayList<Artist>();
		Artist ft1 = new Artist("Adele");
		featured.add(ft1);

		try
		{
			hc.addSongtoAlbum(h.getAlbum(0), testSongName1, songDuration1,
					songPosition1, featured);
		} catch (InvalidInputException e)
		{
			fail();
		}

		//Check the persistence once again to ensure proper functionality
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

		String name1 = "";

		Artist ar1 = new Artist(artName);

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

	@Test
	public void testCreatAlbumNoGenre()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		String error = "";
		assertEquals(0, h.getAlbums().size());

		String genre1 = "";
		Artist ar1 = new Artist(artName);

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

	@Test
	public void testCreateAlbumNoReleaseDate()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		String error = "";
		assertEquals(0, h.getAlbums().size());

		Date d2 = null;
		Artist ar1 = new Artist(artName);

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

	@Test
	public void testCreateAlbumFutureReleaseDate()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		String error = "";
		assertEquals(0, h.getAlbums().size());

		@SuppressWarnings("deprecation")
		Date d3 = new Date(2007, 1, 25);

		Artist ar1 = new Artist(artName);

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

	@Test
	public void testCreateAlbumPresentReleaseDate()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		assertEquals(0, h.getAlbums().size());

		@SuppressWarnings("deprecation")
		Date d4 = new Date(116, 3, 9);

		Artist ar1 = new Artist(artName);

		try
		{
			hc.createAlbum(name, genre, d4, ar1);
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
	public void testCreateArtistNoName()
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
		String error = "";

		assertEquals(0, h.getAlbums().size());

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

		List<Artist> featured = new ArrayList<Artist>();
		Artist ft1 = new Artist("Adele");
		featured.add(ft1);

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

	@Test
	public void testAddSongNoAlbum()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		String error = "";
		assertEquals(0, h.getAlbums().size());

		List<Artist> featured = new ArrayList<Artist>();
		Artist ft1 = new Artist("Adele");
		featured.add(ft1);

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

	@Test
	public void testAddSongNoDuration()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		String error = "";
		assertEquals(0, h.getAlbums().size());

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

		int songDuration2 = 0;

		List<Artist> featured = new ArrayList<Artist>();
		Artist ft1 = new Artist("Adele");
		featured.add(ft1);

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

	@Test
	public void testAddSongNoPosition()

	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		String error = "";
		assertEquals(0, h.getAlbums().size());

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

		int songPosition2 = 0;

		List<Artist> featured = new ArrayList<Artist>();
		Artist ft1 = new Artist("Adele");
		featured.add(ft1);

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

	@Test
	public void addSongNoFeatured()

	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		assertEquals(0, h.getAlbums().size());

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

		List<Artist> featured = new ArrayList<Artist>();

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

	@Test
	public void addSongManyFeatured()

	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		assertEquals(0, h.getAlbums().size());

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

		List<Artist> featured = new ArrayList<Artist>();
		Artist ft1 = new Artist("Adele");
		Artist ft2 = new Artist("Black Jack");
		Artist ft3 = new Artist("Keiko");
		featured.add(ft1);
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

	@Test
	public void testAddSongSamePosition()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();

		assertEquals(0, h.getAlbums().size());

		Artist ar1 = new Artist(artName);

		try
		{
			hc.createAlbum(name, genre, d1, ar1);
		} catch (InvalidInputException e)
		{
			fail();
		}

		assertEquals(1, h.getAlbums().size());

		List<Artist> featured = new ArrayList<Artist>();

		Artist ft1 = new Artist("Adele");
		featured.add(ft1);

		try
		{
			hc.addSongtoAlbum(h.getAlbum(0), testSongName1, songDuration1,
					songPosition1, featured);
		} catch (InvalidInputException e)
		{
			fail();
		}

		String errors = "";

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
	
	@Test
	public void testCreateAlbumAndAddThreeSongs()
	{
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		assertEquals(0, h.getAlbums().size());

		Artist ar1 = new Artist(artName);

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

		// an arbitrary featured artist is entered into the HAS system in order
		// to fully check the functionality of addSongtoAlbum().
		List<Artist> featured = new ArrayList<Artist>();
		Artist ft1 = new Artist("Adele");
		featured.add(ft1);

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
			hc.addSongtoAlbum(h.getAlbum(0), "Food", 123,
					3, featured);
		} catch (InvalidInputException e)
		{
			fail();
		}
		
		try
		{
			hc.addSongtoAlbum(h.getAlbum(0), "Lap", 123,
					4, featured);
		} catch (InvalidInputException e)
		{
			fail();
		}
		
		try
		{
			hc.addSongtoAlbum(h.getAlbum(0), "Dragons", 123,
					5, featured);
		} catch (InvalidInputException e)
		{
			fail();
		}

		//Check the persistence once again to ensure proper functionality
		HAS h3 = (HAS) PersistenceXStream.loadFromXMLwithXStream();
		checkResultSong(h3, testSongName1, songDuration1, songPosition1);
	}

	private void checkResultSong(	HAS h, String testSongName1, int songDuration1,
									int songPosition1)
	{
		assertEquals(testSongName1, h.getAlbum(0).getSong(0).getName());
		assertEquals(songDuration1, h.getAlbum(0).getSong(0).getDuration());
		assertEquals(songPosition1, h.getAlbum(0).getSong(0).getPosition());

		assertEquals(testSongName1, h.getSong(0).getName());
		assertEquals(songDuration1, h.getSong(0).getDuration());
		assertEquals(songPosition1, h.getSong(0).getPosition());
	}

	private void checkResultAlbum(	HAS h, String name, String genre,
									String artName, Date date)
	{
		assertEquals(1, h.getAlbums().size());
		assertEquals(name, h.getAlbum(0).getName());
		assertEquals(genre, h.getAlbum(0).getGenre());
		assertEquals(artName, h.getAlbum(0).getMainArtist().getName());
		assertEquals(date, h.getAlbum(0).getReleaseDate());
	}

}
