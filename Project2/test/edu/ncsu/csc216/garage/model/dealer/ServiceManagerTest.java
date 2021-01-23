package edu.ncsu.csc216.garage.model.dealer;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;


import edu.ncsu.csc216.garage.model.vehicle.BadVehicleInformationException;
import edu.ncsu.csc216.garage.model.vehicle.HybridElectricCar;
import edu.ncsu.csc216.garage.model.vehicle.RegularCar;
import edu.ncsu.csc216.garage.model.vehicle.Vehicle;

/**
 * Tests the ServiceManager class
 * 
 * @author Will Parker
 * @author Christine Park
 *
 */
public class ServiceManagerTest {
	
	/** Instance of ServiceManager */
    ServiceManager sm;
    
    /** Variable for instance of regular car */
    Vehicle r;
    
    /** Variable for instance of hybrid electric car */
    Vehicle h;
    
    /** Valid file path location for generating list of cars */
    String validFile = "test-files/cars.txt";
    
    /**
     * Standard setUp method to create car objects and a new instance of ServiceManager
     * 
     * @throws BadVehicleInformationException if bad vehicle info given when creating vehicles
     */
    @Before
    public void setUp() throws BadVehicleInformationException {
        r = new RegularCar("license1", "Park, Christine", 0);
        h = new HybridElectricCar("license", "Parker, William", 3);
        sm = new ServiceManager();
    }
	
	/**
	 * Tests the null ServiceManager constructor
	 */
	@Test
	public void testServiceManager() {
		  assertTrue(sm.printServiceBays().contains("108: EMPTY"));
		  assertTrue(sm.printServiceBays().contains("106: EMPTY"));
		  assertTrue(sm.printServiceBays().contains("105: EMPTY"));
		  assertTrue(sm.printServiceBays().contains("103: EMPTY"));
		  assertTrue(sm.printServiceBays().contains("102: EMPTY"));
		  assertTrue(sm.printServiceBays().contains("E01: EMPTY"));
		  assertTrue(sm.printServiceBays().contains("E04: EMPTY"));
		  assertTrue(sm.printServiceBays().contains("E07: EMPTY"));
	}

	/**
	 * Tests the parameterized ServiceManager constructor when given a list of cars with which to generate a waiting list.
	 * Also tests the printServiceBays method
	 * 
	 * @throws FileNotFoundException if unable to scan file for waiting list of cars
	 */
	@Test
	public void testServiceManagerScanner() throws FileNotFoundException {
		Scanner s = new Scanner(new File(validFile));
		ServiceManager sm1 = new ServiceManager(s);
		assertTrue(sm1.printServiceBays().contains("108: EMPTY"));
		assertTrue(sm1.printServiceBays().contains("106: EMPTY"));
		assertTrue(sm1.printServiceBays().contains("105: EMPTY"));
		assertTrue(sm1.printServiceBays().contains("103: EMPTY"));
		assertTrue(sm1.printServiceBays().contains("102: EMPTY"));
		assertTrue(sm1.printServiceBays().contains("E01: EMPTY"));
		assertTrue(sm1.printServiceBays().contains("E04: EMPTY"));
		assertTrue(sm1.printServiceBays().contains("E07: EMPTY"));	
		assertEquals(sm1.printWaitList("Towler"), "");
		assertTrue(sm1.printWaitList("Richards").contains("R Platinum  SC-RT098  Richards, Fiona"));	
	}

	/**
	 * Tests the putOnWaitingList method with parameters representing car type, the car's tag, the owner's name, and the service tier
	 */
	@Test
	public void testPutOnWaitingListStringStringStringInt() {
		try {
			sm.putOnWaitingList("E", "Tag3", "Name3", 3);
			sm.putOnWaitingList("R", "Tag4", "Name4", 0);
		} catch (Exception e) {
			fail("Bad vehicle info given");
		}
		assertEquals("E Platinum  Tag3      Name3", sm.getWaitingItem(null, 0).toString());
        assertEquals("R None      Tag4      Name4", sm.getWaitingItem(null, 1).toString());
	}
		

	/**
	 * Tests the putOnWaitingList method and getWaitingItem methods with a Tiered parameter
	 */
	@Test
	public void testPutOnWaitingListTiered() {
        sm.putOnWaitingList(r);
        assertEquals(r, sm.getWaitingItem("", 0));
        sm.putOnWaitingList(h);
        assertEquals(h, sm.getWaitingItem("", 0));
        assertEquals(r, sm.getWaitingItem("", 1));
	}

	/**
	 * Tests the remove method
	 */
	@Test
	public void testRemove() {
		sm.putOnWaitingList(r);
        sm.putOnWaitingList(h);
        assertEquals(r, sm.remove("", 1));
        assertEquals(h, sm.remove("", 0));
	}

	/**
	 * Tests the fillServiceBays method
	 */
	@Test
	public void testFillServiceBays() {
		Scanner s = null;
		ServiceManager sm2 = new ServiceManager(s);
		sm2.fillServiceBays();
		assertTrue(sm2.printServiceBays().contains("108: EMPTY"));
		try {
			s = new Scanner(new File(validFile));
		} catch (FileNotFoundException e) {
			fail("Error reading file");
		}
		ServiceManager sm1 = new ServiceManager(s);
		sm1.fillServiceBays();
		assertTrue(sm1.printServiceBays().contains("108: HI-01345 Rhyne, Lauren"));
		assertTrue(sm1.printServiceBays().contains("106: NC-DUR1  Nicholson, Henry"));
		assertTrue(sm1.printServiceBays().contains("105: SC-RT098 Richards, Fiona"));
		assertTrue(sm1.printServiceBays().contains("103: VIRG0122 Jones, Francis"));
		assertTrue(sm1.printServiceBays().contains("102: IL20987  Masters, Burt"));
		assertTrue(sm1.printServiceBays().contains("E01: FL-M3456 McKeel, Kenneth"));
		assertTrue(sm1.printServiceBays().contains("E04: 0987-NC  Nelson, Richard"));
		assertTrue(sm1.printServiceBays().contains("E07: NC-5678  Emerson, Jane"));
		sm1.releaseFromService(0);
		sm1.releaseFromService(7);
		assertTrue(sm1.printServiceBays().contains("108: EMPTY"));
		assertTrue(sm1.printServiceBays().contains("E07: EMPTY"));		
	}

	/**
	 * Tests the addNewBay method
	 */
	@Test
	public void testAddNewBay() {
		Scanner s = null;
		try {
			s = new Scanner(new File(validFile));
		} catch (FileNotFoundException e) {
			fail("Error reading file");
		}
		ServiceManager sm1 = new ServiceManager(s);
		sm1.fillServiceBays();
		assertTrue(sm1.printServiceBays().contains("108: HI-01345 Rhyne, Lauren"));
		assertTrue(sm1.printServiceBays().contains("106: NC-DUR1  Nicholson, Henry"));
		assertTrue(sm1.printServiceBays().contains("105: SC-RT098 Richards, Fiona"));
		assertTrue(sm1.printServiceBays().contains("103: VIRG0122 Jones, Francis"));
		assertTrue(sm1.printServiceBays().contains("102: IL20987  Masters, Burt"));
		assertTrue(sm1.printServiceBays().contains("E01: FL-M3456 McKeel, Kenneth"));
		assertTrue(sm1.printServiceBays().contains("E04: 0987-NC  Nelson, Richard"));
		assertTrue(sm1.printServiceBays().contains("E07: NC-5678  Emerson, Jane"));
		for (int i = 0; i < 5; i++) {
			sm1.addNewBay();
		}
		assertTrue(sm1.printServiceBays().contains("112: EMPTY"));
		assertTrue(sm1.printServiceBays().contains("111: EMPTY"));
		assertTrue(sm1.printServiceBays().contains("109: EMPTY"));
		assertTrue(sm1.printServiceBays().contains("E10: EMPTY"));
		assertTrue(sm1.printServiceBays().contains("E13: EMPTY"));			
	}

}
