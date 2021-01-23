package edu.ncsu.csc216.garage.model.service_garage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.garage.model.vehicle.BadVehicleInformationException;
import edu.ncsu.csc216.garage.model.vehicle.HybridElectricCar;
import edu.ncsu.csc216.garage.model.vehicle.RegularCar;
import edu.ncsu.csc216.garage.model.vehicle.Vehicle;

/**
 * Tests the Garage class
 * 
 * @author Will Parker
 * @author Christine Park
 *
 */
public class GarageTest {
	
	/** Instance of garage */
	Garage g;
	
    /**
     * Sets up a new garage for testing
     */
    @Before
    public void setUp() {
        g = new Garage();
    }

	/**
	 * Tests the Garage constructor
	 */
	@Test
	public void testGarage() {
		 assertEquals(8, g.numberOfEmptyBays());
	}

	/**
	 * Tests the addRepairBay method
	 */
	@Test
	public void testAddRepairBay() {
        g.addRepairBay();
        assertEquals(9, g.numberOfEmptyBays());
        
        g.addRepairBay();
        g.addRepairBay();
        g.addRepairBay();
        assertEquals(12, g.numberOfEmptyBays());
        
        g.addRepairBay();
        g.addRepairBay();
        g.addRepairBay();
        g.addRepairBay();
        g.addRepairBay();
        g.addRepairBay();
        assertEquals(18, g.numberOfEmptyBays());
        Vehicle vehicle;
        try {
            vehicle = new RegularCar("License", "Name", 0);
            try {
                g.getBayAt(0).occupy(vehicle);
            } catch (BayOccupiedException e) {
                fail("Should not have thrown an exception");
            } catch (BayCarMismatchException e) {
                fail("Should not have thrown an exception");
            }
        } catch (BadVehicleInformationException e) {
            fail("Should not have thrown an exception");
        }
        
        assertEquals(17, g.numberOfEmptyBays());
	}

	/**
	 * Tests the release method
	 * @throws BadVehicleInformationException if invalid vehicle information given 
	 * @throws BayCarMismatchException if attempt to slot vehicle into invalid bay is made
	 * @throws BayOccupiedException if attempt to slot vehicle into already occupied bay is made
	 */
	@Test
	public void testRelease() throws BadVehicleInformationException, BayOccupiedException, BayCarMismatchException {
		Vehicle hybrid = new HybridElectricCar("license", "name", 1);	
		assertFalse(g.getBayAt(0).isOccupied());
		g.getBayAt(0).occupy(hybrid);
		assertTrue(g.getBayAt(0).isOccupied());
		g.release(0);
		assertFalse(g.getBayAt(0).isOccupied());
		
		assertNull(g.release(1));
		
		
	}

}
