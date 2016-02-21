package ca.mcgill.ecse321.HAS.controller;

import java.sql.Date;

import ca.mcgill.ecse321.HAS.model.Album;
import ca.mcgill.ecse321.HAS.model.Artist;
import ca.mcgill.ecse321.HAS.model.HAS;
import ca.mcgill.ecse321.HAS.model.Song;
import ca.mcgill.ecse321.HAS.persistence.PersistenceXStream;

public class HASController
{
	public HASController()
	{
		
	}
	
	public void createArtist(String artName) throws InvalidInputException
	{
		if (artName == null || artName.trim().length() == 0)
			throw new InvalidInputException("Artist name cannot be empty! ");
		
		Artist art = new Artist(artName);
		HAS h = HAS.getInstance();
		h.addArtist(art);
		PersistenceXStream.saveToXMLwithXStream(h);
	}
	
	public void createAlbum(String name, String genre, Date releaseDate, Artist ar) throws InvalidInputException
	{
		//check that the input is valid for this
		String error = "";
		if(name == null || name.trim().length() ==0)
			error = error + "Album name cannot be empty! ";
		if(genre == null || genre.trim().length()==0)
			error = error + "Genre name cannot be empty! ";
		if(releaseDate == null)
			error = error + "Release date cannot be empty! ";
		if(ar == null )
			error = error + "Album must have an artist! ";
		if(error.length() > 0)
			throw new InvalidInputException(error);
		
		
		Album a = new Album(name, genre, releaseDate, ar);
		HAS h = HAS.getInstance();
		h.addAlbum(a);
		PersistenceXStream.saveToXMLwithXStream(h);
	}
	
	public void addSongtoAlbum(Album a, String aName, int aDuration, int aPosition) throws InvalidInputException
	{
		HAS h = HAS.getInstance();
		
		String error = "";
		
		//checks if the album exists
		if(a == null)
			error = error + "Song must belong to an album! ";
		else if(!h.getAlbums().contains(a))
			error = error + "Album does not exist! ";
		
		//checks the name
		if(aName == null || aName.trim().length() == 0)
			error = error + "Song must have a name! ";
		
		//if integer field is left without a number in there, what will it give?
		if(aDuration == 0) 
			error = error + "Song must have a duration! ";
		if(aPosition == 0)
			error = error + "Song must have a position! ";
		
		if(error.length() > 0)
			throw new InvalidInputException(error);
		
		Song newSong = new Song(aName, aDuration, aPosition, a);
		
		PersistenceXStream.saveToXMLwithXStream(h);
	}

}
