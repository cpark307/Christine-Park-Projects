package edu.ncsu.csc216.garage.model.vehicle;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.garage.model.service_garage.Garage;
import edu.ncsu.csc216.garage.model.service_garage.NoAvailableBayException;

/**
 * Tests the RegularCar class
 * 
 * @author Will Parker
 * @author Christine Park
 *
 */
public class RegularCarTest {

	/**
	 * Tests the pickServiceBay method
	 */
	@Test
	public void testPickServiceBay() {
		Garage garage = new Garage();
		assertEquals(8, garage.getSize());
		Vehicle regular1 = null;
		Vehicle regular2 = null;
		Vehicle regular3 = null;
		Vehicle regular4 = null;
		Vehicle regular5 = null;
		Vehicle regular6 = null;
		try {
	        regular1 = new RegularCar("Tag1", "Name1", 0);
	        regular2 = new RegularCar("Tag2", "Name2", 1);
	        regular3 = new RegularCar("Tag3", "Name3", 2);
	        regular4 = new RegularCar("Tag4", "Name4", 3);
	        regular5 = new RegularCar("Tag5", "Name5", 0);
	        regular6 = new RegularCar("Tag6", "Name6", 1);  
	    } catch (BadVehicleInformationException e) {
	    	fail("Should not get an exception.");
	    }
		
		// Fill up regular bays
		try {
			regular1.pickServiceBay(garage);
			regular2.pickServiceBay(garage);
			regular3.pickServiceBay(garage);
			regular4.pickServiceBay(garage);
			regular5.pickServiceBay(garage);
		} catch (NoAvailableBayException e) {
			fail("Should not get an exception");
		}
		
		// Can't add regular cars to HEV bays
		try {
			regular6.pickServiceBay(garage);
			fail();
		} catch (NoAvailableBayException f) {
			assertEquals("No Available Bay", f.getLocalizedMessage());
		}
	}
}
