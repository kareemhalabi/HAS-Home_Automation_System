package ca.mcgill.ecse321.HAS.model;
/*PLEASE DO NOT EDIT THIS CODE*/

/*This code was generated using the UMPLE 1.24.0-2a0418e modeling language!*/

// line 2 "model.ump"
// line 11 "model.ump"
public abstract class Location
{

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Location Attributes
	private String name;
	private int volume;
	private boolean mute;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Location(String aName, int aVolume, boolean aMute)
	{
		name = aName;
		volume = aVolume;
		mute = aMute;
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public boolean setName(String aName)
	{
		boolean wasSet = false;
		name = aName;
		wasSet = true;
		return wasSet;
	}

	public boolean setVolume(int aVolume)
	{
		boolean wasSet = false;
		volume = aVolume;
		wasSet = true;
		return wasSet;
	}

	public boolean setMute(boolean aMute)
	{
		boolean wasSet = false;
		mute = aMute;
		wasSet = true;
		return wasSet;
	}

	public String getName()
	{
		return name;
	}

	public int getVolume()
	{
		return volume;
	}

	public boolean getMute()
	{
		return mute;
	}

	public void delete()
	{
	}

	public String toString()
	{
		String outputString = "";
		return super.toString() + "[" + "name" + ":" + getName() + ","
				+ "volume" + ":" + getVolume() + "," + "mute" + ":" + getMute()
				+ "]" + outputString;
	}
}
