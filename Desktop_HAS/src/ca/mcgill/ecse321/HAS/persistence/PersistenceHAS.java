package ca.mcgill.ecse321.HAS.persistence;

import java.util.Iterator;

import ca.mcgill.ecse321.HAS.model.Album;
import ca.mcgill.ecse321.HAS.model.Artist;
import ca.mcgill.ecse321.HAS.model.HAS;
import ca.mcgill.ecse321.HAS.model.Playlist;
import ca.mcgill.ecse321.HAS.model.Room;
import ca.mcgill.ecse321.HAS.model.Song;

public class PersistenceHAS {

	public static String fileName = "HAS.xml";
	
	public static void setFileName(String newFileName) {
		fileName = newFileName;
	}
	
	private static void initializeXStream() {
		PersistenceXStream.setFilename(fileName);
		PersistenceXStream.setAlias("album", Album.class);
		PersistenceXStream.setAlias("artist", Artist.class);
		PersistenceXStream.setAlias("HAS", HAS.class);
		PersistenceXStream.setAlias("playlist", Playlist.class);
		PersistenceXStream.setAlias("room", Room.class);
		PersistenceXStream.setAlias("song", Song.class);
	}
	
	public static void loadHASModel() {
		HAS h = HAS.getInstance();
		PersistenceHAS.initializeXStream();
		HAS h2 = (HAS) PersistenceXStream.loadFromXMLwithXStream();
		if(h2 != null) {
			Iterator<Album> alIt = h2.getAlbums().iterator();
			while(alIt.hasNext())
				h.addAlbum(alIt.next());
			
			Iterator<Artist> arIt = h2.getArtists().iterator();
			while(arIt.hasNext())
				h.addArtist(arIt.next());
			
			Iterator<Playlist> pIt = h2.getPlaylists().iterator();
			while(pIt.hasNext())
				h.addPlaylist(pIt.next());
			
			Iterator<Room> rIt = h2.getRooms().iterator();
			while(rIt.hasNext())
				h.addRoom(rIt.next());
			
			Iterator<Song> sIt = h2.getSongs().iterator();
			while(sIt.hasNext())
				h.addSong(sIt.next());
			
			/*for(Song s : h2.getSongs())
				h.addSong(s);*/
		}
	}
	
}
