/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.23.0-2950f84 modeling language!*/

package ca.mcgill.ecse321.HAS.model;

// line 28 "../../../../../../../../ump/160303721337/model.ump"
// line 118 "../../../../../../../../ump/160303721337/model.ump"
// line 158 "../../../../../../../../ump/160303721337/model.ump"
public class Room implements Comparable<Room>
{

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Room Attributes
	private String name;
	private int volume;
	private boolean mute;

	// Room Associations
	private Playable playable;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Room(String aName, int aVolume, boolean aMute)
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

	public Playable getPlayable()
	{
		return playable;
	}

	public boolean hasPlayable()
	{
		boolean has = playable != null;
		return has;
	}

	public boolean setPlayable(Playable aNewPlayable)
	{
		boolean wasSet = false;
		playable = aNewPlayable;
		wasSet = true;
		return wasSet;
	}

	public void delete()
	{
		playable = null;
	}

	public String toString()
	{
		String outputString = "";
		return super.toString() + "[" + "name" + ":" + getName() + "," + "volume" + ":" + getVolume() + "," + "mute"
				+ ":" + getMute() + "]" + System.getProperties().getProperty("line.separator") + "  " + "playable = "
				+ (getPlayable() != null ? Integer.toHexString(System.identityHashCode(getPlayable())) : "null")
				+ outputString;
	}

	@Override
	public int compareTo(Room o)
	{
		assert (o != null);
		return name.compareTo(o.getName());
	}
}