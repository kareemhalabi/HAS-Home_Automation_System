package ca.mcgill.ecse321.persistence;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.mcgill.ecse321.HAS.model.Album;
import ca.mcgill.ecse321.HAS.model.Artist;
import ca.mcgill.ecse321.HAS.model.HAS;
import ca.mcgill.ecse321.HAS.persistence.persistenceXStream;

public class TestPersistence {

	@Before
	public void setUp() throws Exception {
		HAS h = HAS.getInstance();
		
		//create Dates of album releases
		Date d1 = new Date(2007,01,25);
		Date d2 = new Date(2009,10,9);
		
		//create artist
		Artist ar1 = new Artist("Flume");
		Artist ar2 = new Artist("Mumford and Sons");
		
		
		//create albums
		Album a1 = new Album("Flume","Indie",d1, ar1);
		Album a2 = new Album("Sigh No More","Folk",d2, ar2);
		
		//add albums to h
		h.addAlbum(a1);
		h.addAlbum(a2);
		
		//add songs to the album
		h.getAlbum(0).addSong("Sintara", 2000, 1);
		h.getAlbum(1).addSong("The Cave", 100, 2);
		
	}

	@After
	public void tearDown() throws Exception {
		//clear everything
		HAS h = HAS.getInstance();
		h.delete();
	}

	@Test
	public void test() {
		HAS h = HAS.getInstance();
		persistenceXStream.setFilename("test"+File.separator+"ca"+File.separator+"mcgill"+File.separator
				+"ecse321"+File.separator+"persistence"+File.separator+"data.xml");
		persistenceXStream.setAlias("HAS", HAS.class);
		persistenceXStream.setAlias("album", Album.class);
		persistenceXStream.setAlias("artist", Artist.class);
		if(!persistenceXStream.saveToXMLwithXStream(h))
		{
			fail("Not yet implemented");
		}
		
		//clear the model in memory
		h.delete();
		assertEquals(0, h.getAlbums().size());
		
		//load model
		h = (HAS) persistenceXStream.loadFromXMLwithXStream();
		if(h == null)
		{
			fail("Could not load file.");
		}
		
		Date d1 = new Date(2007,01,25);
		Date d2 = new Date(2009,10,9);
		
		//check for albums
		assertEquals(2, h.getAlbums().size());
		
		//check album number 1
		assertEquals("Flume", h.getAlbum(0).getName().toString());
		assertEquals("Indie", h.getAlbum(0).getGenre());
		assertEquals("Flume", h.getAlbum(0).getArtist().getName());
		assertEquals(d1, h.getAlbum(0).getReleaseDate());
		
		//check songs in the album 1
		assertEquals("Sintara", h.getAlbum(0).getSong(0).getName());
		assertEquals(2000, h.getAlbum(0).getSong(0).getDuration());
		assertEquals(1, h.getAlbum(0).getSong(0).getPosition());
		
		
		//check album number 2
		assertEquals("Sigh No More", h.getAlbum(1).getName());
		assertEquals("Folk", h.getAlbum(1).getGenre());
		assertEquals("Mumford and Sons", h.getAlbum(1).getArtist().getName());
		assertEquals(d2, h.getAlbum(1).getReleaseDate());
		
		//check songs in album 2
		assertEquals("The Cave", h.getAlbum(1).getSong(0).getName());
		assertEquals(100, h.getAlbum(1).getSong(0).getDuration());
		assertEquals(2, h.getAlbum(1).getSong(0).getPosition());
		
	}

}
