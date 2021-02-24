package edu.ncsu.csc216.garage.model.service_garage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.garage.model.vehicle.BadVehicleInformationException;
import edu.ncsu.csc216.garage.model.vehicle.HybridElectricCar;
import edu.ncsu.csc216.garage.model.vehicle.RegularCar;
import edu.ncsu.csc216.garage.model.vehicle.Vehicle;

/**
 * Tests the ServiceBay class
 * 
 * @author Will Parker
 * @author Christine Park
 *
 */
public class ServiceBayTest {
	
	ServiceBay sb1;
	ServiceBay sb2;
	Garage g;
	Vehicle h;

    /**
     * Sets up variables for use in testing
     */
    @Before
    public void setUp() {
        g = new Garage();
        sb1 = new ServiceBay();  
    }

	/**
	 * Tests the ServiceBay constructor with a String parameter
	 */
	@Test
	public void testServiceBayString() {
        ServiceBay.startBayNumberingAt101();
        sb2 = new ServiceBay("E");
        assertEquals("E01", sb2.getBayID());
	}

	/**
	 * Test the getBayID method
	 */
	@Test
	public void testGetBayID() {
		 assertEquals("108", g.getBayAt(0).getBayID());
	     assertEquals("106", g.getBayAt(1).getBayID());
	     assertEquals("105", g.getBayAt(2).getBayID());
	     assertEquals("103", g.getBayAt(3).getBayID());
	     assertEquals("102", g.getBayAt(4).getBayID());
	     assertEquals("E01", g.getBayAt(5).getBayID());
	     assertEquals("E04", g.getBayAt(6).getBayID());
	     assertEquals("E07", g.getBayAt(7).getBayID());
	}

	/**
	 * Tests the isOccupied method
	 */
	@Test
	public void testIsOccupied() {
        assertFalse(g.getBayAt(0).isOccupied());
        assertFalse(g.getBayAt(1).isOccupied());
        assertFalse(g.getBayAt(2).isOccupied());
        assertFalse(g.getBayAt(3).isOccupied());
        assertFalse(g.getBayAt(4).isOccupied());
        assertFalse(g.getBayAt(5).isOccupied());
        assertFalse(g.getBayAt(6).isOccupied());
        assertFalse(g.getBayAt(7).isOccupied());
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
		Vehicle hybrid1 = new HybridElectricCar("license1", "name1", 2);
		Vehicle hybrid2 = new HybridElectricCar("license2", "name2", 3);
		Vehicle hybrid3 = new HybridElectricCar("license3", "name3", 3);
		Vehicle hybrid4 = new HybridElectricCar("license4", "name4", 3);
		Vehicle reg = new RegularCar("license5", "name5", 0);
		Vehicle hybrid5 = new HybridElectricCar("license6", "name6", 0);
		
		assertFalse(g.getBayAt(0).isOccupied());
		g.getBayAt(0).occupy(hybrid);
		assertTrue(g.getBayAt(0).isOccupied());
		assertEquals(g.getBayAt(0).toString(), "108: license  name");
		
		assertFalse(g.getBayAt(1).isOccupied());
		g.getBayAt(1).occupy(hybrid1);
		assertTrue(g.getBayAt(1).isOccupied());
		assertEquals(g.getBayAt(1).toString(), "106: license1 name1");
		
		assertFalse(g.getBayAt(2).isOccupied());
		g.getBayAt(2).occupy(hybrid2);
		assertTrue(g.getBayAt(2).isOccupied());
		assertEquals(g.getBayAt(2).toString(), "105: license2 name2");

		assertFalse(g.getBayAt(3).isOccupied());
		g.getBayAt(3).occupy(hybrid3);
		assertTrue(g.getBayAt(3).isOccupied());
		assertEquals(g.getBayAt(3).toString(), "103: license3 name3");
		
		assertFalse(g.getBayAt(4).isOccupied());
		g.getBayAt(4).occupy(hybrid4);
		assertTrue(g.getBayAt(4).isOccupied());
		assertEquals(g.getBayAt(4).toString(), "102: license4 name4");
		
		assertFalse(g.getBayAt(5).isOccupied());
		try {
			g.getBayAt(5).occupy(reg);
			fail();
		} catch (BayCarMismatchException e) {
			assertFalse(g.getBayAt(5).isOccupied());
		}
		
		assertTrue(g.getBayAt(2).isOccupied());
		try {
			g.getBayAt(2).occupy(reg);
			fail();
		} catch (BayOccupiedException e) {
			assertEquals(g.getBayAt(2).toString(), "105: license2 name2");
		}
		
		g.getBayAt(4).release();
		g.getBayAt(4).occupy(reg);
		assertEquals(g.getBayAt(4).toString(), "102: license5 name5");
		
		assertEquals(g.getBayAt(6).toString(), "E04: EMPTY");
		assertEquals(g.getBayAt(6).release(), null);
		assertEquals(g.getBayAt(7).toString(), "E07: EMPTY");
		
		g.getBayAt(7).occupy(hybrid5);
		assertEquals(g.getBayAt(7).toString(), "E07: license6 name6");
		
		
		g.addRepairBay();
		g.addRepairBay();
		g.addRepairBay();
		
		assertEquals(g.getBayAt(0).toString(), "112: EMPTY");
		assertEquals(g.getBayAt(1).toString(), "110: EMPTY");
		assertEquals(g.getBayAt(2).toString(), "108: license  name");
		assertEquals(g.getBayAt(3).toString(), "106: license1 name1");
		assertEquals(g.getBayAt(4).toString(), "105: license2 name2");
		assertEquals(g.getBayAt(5).toString(), "103: license3 name3");
		assertEquals(g.getBayAt(7).toString(), "E01: EMPTY");
		assertEquals(g.getBayAt(8).toString(), "E04: EMPTY");
		assertEquals(g.getBayAt(9).toString(), "E07: license6 name6");
		assertEquals(g.getBayAt(10).toString(), "E11: EMPTY");
	}

}
