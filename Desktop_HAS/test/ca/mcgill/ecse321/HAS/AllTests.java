package ca.mcgill.ecse321.HAS;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ca.mcgill.ecse321.controller.TestHASController;
import ca.mcgill.ecse321.controller.TestHASControllerPlaylistAndRoom;
import ca.mcgill.ecse321.persistence.TestPersistence;

@RunWith(Suite.class)
@SuiteClasses(
{ TestHASController.class, TestPersistence.class, TestHASControllerPlaylistAndRoom.class })

public class AllTests
{

}
