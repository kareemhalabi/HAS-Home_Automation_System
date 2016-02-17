package ca.mcgill.ecse321.persistence;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.mcgill.ecse321.HAS.model.Album;
import ca.mcgill.ecse321.HAS.model.HAS;

public class TestPersistence {

	@Before
	public void setUp() throws Exception {
		HAS h = HAS.getInstance();
		
		//create Dates of album releases
		Date d1 = new Date(2007,01,25);
		Date d2 = new Date(2009,10,9);
		
		
		//create albums
		Album a1 = new Album("Flume","Indie",d1);
		Album a2 = new Album("Sigh No More","Folk",d2);
		
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
