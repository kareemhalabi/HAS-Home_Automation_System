package ca.mcgill.ecse321.HAS.persistence;

import java.util.Iterator;

import ca.mcgill.ecse321.HAS.model.Album;
import ca.mcgill.ecse321.HAS.model.Artist;
import ca.mcgill.ecse321.HAS.model.HAS;
import ca.mcgill.ecse321.HAS.model.Playlist;
import ca.mcgill.ecse321.HAS.model.Room;
import ca.mcgill.ecse321.HAS.model.RoomGroup;
import ca.mcgill.ecse321.HAS.model.Song;

public class PersistenceHAS
{

	public static String fileName = "HAS.xml";

	public static void setFileName(String newFileName)
	{
		fileName = newFileName;
	}

	private static void initializeXStream()
	{
		PersistenceXStream.setFilename(fileName);
		PersistenceXStream.setAlias("album", Album.class);
		PersistenceXStream.setAlias("artist", Artist.class);
		PersistenceXStream.setAlias("HAS", HAS.class);
		PersistenceXStream.setAlias("playlist", Playlist.class);
		PersistenceXStream.setAlias("room", Room.class);
		PersistenceXStream.setAlias("roomgroup", RoomGroup.class);
		PersistenceXStream.setAlias("song", Song.class);
	}

	public static void loadHASModel()
	{
		HAS h = HAS.getInstance();
		PersistenceHAS.initializeXStream();
		HAS h2 = (HAS) PersistenceXStream.loadFromXMLwithXStream();
		if (h2 != null && h.getAlbums().size() == 0
				&& h.getArtists().size() == 0 && h.getSongs().size() == 0
				&& h.getRooms().size() == 0 && h.getRoomGroups().size() == 0
				&& h.getPlaylists().size() == 0)
		{
			for (Album a : h2.getAlbums())
				h.addAlbum(a);

			for (Artist ar : h2.getArtists())
				h.addArtist(ar);

			for (Playlist p : h2.getPlaylists())
				h.addPlaylist(p);

			for (Room r : h2.getRooms())
				h.addRoom(r);

			for (RoomGroup rg : h2.getRoomGroups())
				h.addRoomGroup(rg);

			for (Song s : h2.getSongs())
				h.addSong(s);
		}
	}

}
