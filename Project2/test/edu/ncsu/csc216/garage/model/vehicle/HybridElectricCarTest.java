package edu.ncsu.csc216.garage.model.vehicle;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.garage.model.service_garage.Garage;
import edu.ncsu.csc216.garage.model.service_garage.NoAvailableBayException;

/**
 * Test the HybridElectricCar class
 * 
 * @author Will Parker
 * @author Christine Park
 *
 */
public class HybridElectricCarTest {

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
		Vehicle hev1 = null;
		Vehicle hev2 = null;
		Vehicle hev3 = null;
		Vehicle hev4 = null;
		try {
	        regular1 = new RegularCar("Tag1", "Name1", 0);
	        regular2 = new RegularCar("Tag2", "Name2", 1);
	        regular3 = new RegularCar("Tag3", "Name3", 2);
	        regular4 = new RegularCar("Tag4", "Name4", 3);
	        regular5 = new RegularCar("Tag5", "Name5", 0);
	        regular6 = new RegularCar("Tag6", "Name6", 1);
	        hev1 = new HybridElectricCar("Tag7", "Name7", 0);
	        hev2 = new HybridElectricCar("Tag8", "Name8", 1);
	        hev3 = new HybridElectricCar("Tag9", "Name9", 2);
	        hev4 = new HybridElectricCar("Tag10", "Name10", 3);
	    } catch (BadVehicleInformationException e) {
	    	fail("Should not get an exception.");
	    }
		// HEVs can go into regular bays
		try {
			regular1.pickServiceBay(garage);
			regular2.pickServiceBay(garage);
			regular3.pickServiceBay(garage);
			regular4.pickServiceBay(garage);
			regular5.pickServiceBay(garage);
		} catch (NoAvailableBayException e) {
			fail("Should not get an exception");
		}
		// Regulars cannot go into HEV bays
		try {
			regular6.pickServiceBay(garage);
			fail();
		} catch (NoAvailableBayException f) {
			assertEquals("No Available Bay", f.getLocalizedMessage());
		}
		// Fill all of the HEV bays with HEVs
		try {
			hev1.pickServiceBay(garage);
			hev2.pickServiceBay(garage);
			hev3.pickServiceBay(garage);
		} catch (NoAvailableBayException e) {
			fail("Should not get an exception");
		}
		// Can't add if garage is full
		try {
			hev4.pickServiceBay(garage);
			fail();
		} catch (NoAvailableBayException f) {
			assertEquals("No Available Bay", f.getLocalizedMessage());
		}		
	}
}
