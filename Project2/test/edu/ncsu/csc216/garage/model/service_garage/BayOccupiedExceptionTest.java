package edu.ncsu.csc216.garage.model.service_garage;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the custom BayOccupiedException
 * 
 * @author Will Parker
 * @author Christine Park
 *
 */
public class BayOccupiedExceptionTest {

	/**
	 * Tests the null BayOccupiedException constructor
	 */
	@Test
	public void testBayOccupiedException() {
		BayOccupiedException e = new BayOccupiedException("BayOccupiedException");
		assertEquals("BayOccupiedException", e.getMessage());
	}

	/**
	 * Tests the parameterized BayOccupiedException constructor
	 */
	@Test
	public void testBayOccupiedExceptionString() {
		BayOccupiedException e = new BayOccupiedException("BayOccupiedException");
		assertEquals("BayOccupiedException", e.getMessage());
	}

}
