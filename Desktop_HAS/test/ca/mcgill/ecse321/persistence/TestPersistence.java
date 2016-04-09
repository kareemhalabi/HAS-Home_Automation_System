package ca.mcgill.ecse321.persistence;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
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
		Room room3 = new Room("Bedroom", 5, false);
		
		RoomGroup roomGroup = new RoomGroup("Group1", room1);
		
		h.addAlbum(a1);
		h.addAlbum(a2);
		
		h.getAlbum(0).addSong("Sintara", 2000, 1);
		h.getAlbum(1).addSong("The Cave", 100, 2);
		h.getAlbum(1).addSong("Why?", 120, 3);
		
		h.addSong(h.getAlbum(0).getSong(0));
		h.addSong(h.getAlbum(1).getSong(0));
		h.addSong(h.getAlbum(1).getSong(1));
		
		h.addRoom(room1);
		h.addRoom(room2);
		h.addRoom(room3);
		
		List<Room> rooms = new ArrayList();
		rooms.add(room1);
		rooms.add(room2);
		rooms.add(room3);
		
		h.addRoomGroup(roomGroup);
		hc.addRoomToRoomGroup(roomGroup, rooms);
		
		hc.setRoomVolumeLevel(room1, 7);
		hc.setRoomMute(room2, true);
		
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
		HAS h2 = (HAS) PersistenceXStream.loadFromXMLwithXStream();
		if(h2 == null)
		{
			fail("Could not load file.");
		}
		
		Date d1 = new Date(2007,01,25);
		Date d2 = new Date(2009,10,9);
		
		//check for albums
		assertEquals(2, h2.getAlbums().size());
		
		//check album number 1
		assertEquals("Flume", h2.getAlbum(0).getName().toString());
		assertEquals("Indie", h2.getAlbum(0).getGenre());
		assertEquals("Flume", h2.getAlbum(0).getMainArtist().getName());
		assertEquals(d1, h2.getAlbum(0).getReleaseDate());
		
		//check songs in the album 1
		assertEquals("Sintara", h2.getAlbum(0).getSong(0).getName());
		assertEquals(2000, h2.getAlbum(0).getSong(0).getDuration());
		assertEquals(1, h2.getAlbum(0).getSong(0).getPosition());
		
		
		//check album number 2
		assertEquals("Sigh No More", h2.getAlbum(1).getName());
		assertEquals("Folk", h2.getAlbum(1).getGenre());
		assertEquals("Mumford and Sons", h2.getAlbum(1).getMainArtist().getName());
		assertEquals(d2, h2.getAlbum(1).getReleaseDate());
		
		//check songs in album 2
		assertEquals("The Cave", h2.getAlbum(1).getSong(0).getName());
		assertEquals(100, h2.getAlbum(1).getSong(0).getDuration());
		assertEquals(2, h2.getAlbum(1).getSong(0).getPosition());
		
		//check song 1 independently
		assertEquals("Sintara", h2.getSong(0).getName());
		assertEquals(2000, h2.getSong(0).getDuration());
		assertEquals(1, h2.getSong(0).getPosition());
		
		//check song 2 independently
		assertEquals("The Cave", h2.getSong(1).getName());
		assertEquals(100, h2.getSong(1).getDuration());
		assertEquals(2, h2.getSong(1).getPosition());
		
		assertEquals("Why?", h2.getSong(2).getName());
		assertEquals(120, h2.getSong(2).getDuration());
		assertEquals(3, h2.getSong(2).getPosition());
		
		assertEquals(3, h2.getRoomGroup(0).getRooms().size());
		assertEquals("Kitchen", h2.getRoomGroup(0).getRoom(0).getName());
		assertEquals(7, h2.getRoomGroup(0).getRoom(0).getVolume());
		assertEquals(7, h2.getRoom(0).getVolume());
		
		assertEquals("Living Room", h2.getRoom(1).getName());
		
		assertTrue(h2.getRoomGroup(0).getRoom(1).getMute());
		assertTrue(h2.getRoom(1).getMute());
		
		assertEquals("Playlist1", h2.getPlaylist(0).getName());
		assertEquals("Sintara", h2.getPlaylist(0).getSong(0).getName());
		assertEquals("The Cave", h2.getPlaylist(0).getSong(1).getName());
	}

}
