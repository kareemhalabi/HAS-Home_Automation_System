/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.23.0-2950f84 modeling language!*/

package ca.mcgill.ecse321.HAS.model;

import java.util.*;

// line 36 "../../../../../../../../ump/160303721337/model.ump"
// line 173 "../../../../../../../../ump/160303721337/model.ump"
public class RoomGroup extends Location implements Comparable<RoomGroup>
{

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// RoomGroup Attributes

	// RoomGroup Associations
	private List<Room> rooms;
	private Playable playable;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public RoomGroup(String aName, int aVolume, boolean aMute, Room... allRooms)
	{
		super(aName, aVolume, aMute);
		rooms = new ArrayList<Room>();
		boolean didAddRooms = setRooms(allRooms);
		if (!didAddRooms)
		{
			throw new RuntimeException(
					"Unable to create RoomGroup, must have at least 1 rooms");
		}
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public Room getRoom(int index)
	{
		Room aRoom = rooms.get(index);
		return aRoom;
	}

	public List<Room> getRooms()
	{
		List<Room> newRooms = Collections.unmodifiableList(rooms);
		return newRooms;
	}

	public int numberOfRooms()
	{
		int number = rooms.size();
		return number;
	}

	public boolean hasRooms()
	{
		boolean has = rooms.size() > 0;
		return has;
	}

	public int indexOfRoom(Room aRoom)
	{
		int index = rooms.indexOf(aRoom);
		return index;
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

	public static int minimumNumberOfRooms()
	{
		return 1;
	}

	public boolean addRoom(Room aRoom)
	{
		boolean wasAdded = false;
		if (rooms.contains(aRoom))
		{
			return false;
		}
		rooms.add(aRoom);
		wasAdded = true;
		return wasAdded;
	}

	public boolean removeRoom(Room aRoom)
	{
		boolean wasRemoved = false;
		if (!rooms.contains(aRoom))
		{
			return wasRemoved;
		}

		if (numberOfRooms() <= minimumNumberOfRooms())
		{
			return wasRemoved;
		}

		rooms.remove(aRoom);
		wasRemoved = true;
		return wasRemoved;
	}

	public boolean setRooms(Room... newRooms)
	{
		boolean wasSet = false;
		ArrayList<Room> verifiedRooms = new ArrayList<Room>();
		for (Room aRoom : newRooms)
		{
			if (verifiedRooms.contains(aRoom))
			{
				continue;
			}
			verifiedRooms.add(aRoom);
		}

		if (verifiedRooms.size() != newRooms.length
				|| verifiedRooms.size() < minimumNumberOfRooms())
		{
			return wasSet;
		}

		rooms.clear();
		rooms.addAll(verifiedRooms);
		wasSet = true;
		return wasSet;
	}

	public boolean addRoomAt(Room aRoom, int index)
	{
		boolean wasAdded = false;
		if (addRoom(aRoom))
		{
			if (index < 0)
			{
				index = 0;
			}
			if (index > numberOfRooms())
			{
				index = numberOfRooms() - 1;
			}
			rooms.remove(aRoom);
			rooms.add(index, aRoom);
			wasAdded = true;
		}
		return wasAdded;
	}

	public boolean addOrMoveRoomAt(Room aRoom, int index)
	{
		boolean wasAdded = false;
		if (rooms.contains(aRoom))
		{
			if (index < 0)
			{
				index = 0;
			}
			if (index > numberOfRooms())
			{
				index = numberOfRooms() - 1;
			}
			rooms.remove(aRoom);
			rooms.add(index, aRoom);
			wasAdded = true;
		} else
		{
			wasAdded = addRoomAt(aRoom, index);
		}
		return wasAdded;
	}

	public boolean setPlayable(Playable aNewPlayable)
	{
		boolean wasSet = false;

		for (Room room : rooms)
		{
			room.setPlayable(aNewPlayable);
		}
		playable = aNewPlayable;
		wasSet = true;
		return wasSet;
	}

	public void delete()
	{
		rooms.clear();
		playable = null;
	}

	public String toString()
	{
		String outputString = "";
		return super.toString() + "[" + "name" + ":" + getName() + "]"
				+ System.getProperties().getProperty("line.separator") + "  "
				+ "playable = "
				+ (getPlayable() != null
						? Integer.toHexString(
								System.identityHashCode(getPlayable()))
						: "null")
				+ outputString;
	}

	@Override
	public int compareTo(RoomGroup o)
	{
		assert (o != null);
		return this.getName().compareTo(o.getName());
	}
}