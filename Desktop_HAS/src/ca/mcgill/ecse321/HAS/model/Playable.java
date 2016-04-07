/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.23.0-2950f84 modeling language!*/

package ca.mcgill.ecse321.HAS.model;

// line 95 "../../../../../../../../ump/160303721337/model.ump"
// line 180 "../../../../../../../../ump/160303721337/model.ump"
public abstract class Playable
{

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Playable Attributes
	private String name;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Playable(String aName)
	{
		name = aName;
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

	public String getName()
	{
		return name;
	}

	public void delete()
	{
	}

	public abstract void play();

	public String toString()
	{
		String outputString = "";
		return super.toString() + "[" + "name" + ":" + getName() + "]"
				+ outputString;
	}
}