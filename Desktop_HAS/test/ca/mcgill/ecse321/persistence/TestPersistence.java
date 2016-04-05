package ca.mcgill.ecse321.persistence;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.mcgill.ecse321.HAS.controller.HASController;
import ca.mcgill.ecse321.HAS.model.Album;
import ca.mcgill.ecse321.HAS.model.Artist;
import ca.mcgill.ecse321.HAS.model.HAS;
import ca.mcgill.ecse321.HAS.model.Room;
import ca.mcgill.ecse321.HAS.model.RoomGroup;
import ca.mcgill.ecse321.HAS.model.Song;
import ca.mcgill.ecse321.HAS.persistence.PersistenceXStream;

//TODO add tests for playlist
public class TestPersistence {

	@Before
	public void setUp() throws Exception {
		HAS h = HAS.getInstance();
		HASController hc = new HASController();
		
		Date d1 = new Date(2007,01,25);
		Date d2 = new Date(2009,10,9);
		
		Artist ar1 = new Artist("Flume");
		Artist ar2 = new Artist("Mumford and Sons");
		
		Album a1 = new Album("Flume","Indie",d1, ar1);
		Album a2 = new Album("Sigh No More","Folk",d2, ar2);
		
		Room room1 = new Room("Kitchen", 5, false);
		Room room2 = new Room("Living Room", 5, false);
		
		RoomGroup roomGroup = new RoomGroup("Group1", room1);
		
		h.addAlbum(a1);
		h.addAlbum(a2);
		
		h.getAlbum(0).addSong("Sintara", 2000, 1);
		h.getAlbum(1).addSong("The Cave", 100, 2);
		
		h.addSong(h.getAlbum(0).getSong(0));
		h.addSong(h.getAlbum(1).getSong(0));
		
		h.addRoom(room1);
		h.addRoom(room2);
		
		h.addRoomGroup(roomGroup);
		hc.addRoomToRoomGroup(roomGroup, room2);
		
		hc.setRoomVolumeLevel(room1, 7);
		hc.setMute(room2, true);
		
		List<Song> songs = h.getSongs();
		
		hc.createPlaylist("Playlist1", songs);
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
		PersistenceXStream.setFilename("test"+File.separator+"ca"+File.separator+"mcgill"+File.separator
				+"ecse321"+File.separator+"persistence"+File.separator+"data.xml");
		PersistenceXStream.setAlias("HAS", HAS.class);
		PersistenceXStream.setAlias("album", Album.class);
		PersistenceXStream.setAlias("artist", Artist.class);
		if(!PersistenceXStream.saveToXMLwithXStream(h))
		{
			fail("Not yet implemented");
		}
		
		//clear the model in memory
		h.delete();
		assertEquals(0, h.getAlbums().size());
		
		//load model
		h = (HAS) PersistenceXStream.loadFromXMLwithXStream();
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
		assertEquals("Flume", h.getAlbum(0).getMainArtist().getName());
		assertEquals(d1, h.getAlbum(0).getReleaseDate());
		
		//check songs in the album 1
		assertEquals("Sintara", h.getAlbum(0).getSong(0).getName());
		assertEquals(2000, h.getAlbum(0).getSong(0).getDuration());
		assertEquals(1, h.getAlbum(0).getSong(0).getPosition());
		
		
		//check album number 2
		assertEquals("Sigh No More", h.getAlbum(1).getName());
		assertEquals("Folk", h.getAlbum(1).getGenre());
		assertEquals("Mumford and Sons", h.getAlbum(1).getMainArtist().getName());
		assertEquals(d2, h.getAlbum(1).getReleaseDate());
		
		//check songs in album 2
		assertEquals("The Cave", h.getAlbum(1).getSong(0).getName());
		assertEquals(100, h.getAlbum(1).getSong(0).getDuration());
		assertEquals(2, h.getAlbum(1).getSong(0).getPosition());
		
		//check song 1 independently
		assertEquals("Sintara", h.getSong(0).getName());
		assertEquals(2000, h.getSong(0).getDuration());
		assertEquals(1, h.getSong(0).getPosition());
		
		//check song 2 independently
		assertEquals("The Cave", h.getSong(1).getName());
		assertEquals(100, h.getSong(1).getDuration());
		assertEquals(2, h.getSong(1).getPosition());
		
		assertEquals(2, h.getRoomGroup(0).getRooms().size());
		assertEquals("Kitchen", h.getRoomGroup(0).getRoom(0).getName());
		assertEquals(7, h.getRoomGroup(0).getRoom(0).getVolume());
		
		assertEquals("Kitchen", h.getRoom(0).getName());
		assertEquals(7, h.getRoom(0).getVolume());
		
		assertTrue(h.getRoomGroup(0).getRoom(1).getMute());
		assertTrue(h.getRoom(1).getMute());
		
	}

}
