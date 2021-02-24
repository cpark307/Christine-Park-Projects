package edu.ncsu.csc216.garage.model.service_garage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.garage.model.vehicle.BadVehicleInformationException;
import edu.ncsu.csc216.garage.model.vehicle.HybridElectricCar;
import edu.ncsu.csc216.garage.model.vehicle.Vehicle;

/**
 * Tests the HybridElectricBay class
 * 
 * @author Will Parker
 * @author Christine Park
 *
 */
public class HybridElectricBayTest {
	  HybridElectricBay h1;
	   HybridElectricBay h2;
	
    /**
     * Standard Setup method 
     */
    @Before 
    public void setUp() {
        h1 = new HybridElectricBay();
    }

	/**
	 * Tests the occupy method
	 * 
	 * @throws BadVehicleInformationException if invalid vehicle information given 
	 * @throws BayCarMismatchException if attempt to slot vehicle into invalid bay is made
	 * @throws BayOccupiedException if attempt to slot vehicle into already occupied bay is made
	 */
	@Test
	public void testOccupy() throws BadVehicleInformationException, BayOccupiedException, BayCarMismatchException {
		Vehicle hybrid = new HybridElectricCar("license", "name", 1);
		Vehicle hybrid2 = new HybridElectricCar("license2", "name2", 1);
		assertFalse(h1.isOccupied());
		h1.occupy(hybrid);
		assertTrue(h1.isOccupied());
		try {
			h1.occupy(hybrid2);
			fail();
		} catch (BayOccupiedException e) {
			assertEquals("BayOccupiedException", e.getLocalizedMessage());
		}
	}

	/**
	 * Tests the HybridElectricBay constructor
	 */
	@Test
	public void testHybridElectricBay() {
	     ServiceBay.startBayNumberingAt101();
	     h2 = new HybridElectricBay();
	     assertEquals("E01", h2.getBayID());
	     assertFalse(h2.isOccupied());
	}

}
