package edu.ncsu.csc216.garage.model.vehicle;

import static org.junit.Assert.*;

import org.junit.Test;


/**
 * Tests the custom BadVehicleInformationException
 * 
 * @author Will Parker
 * @author Christine Park
 *
 */
public class BadVehicleInformationExceptionTest {

	/**
	 * Tests the null BadVehicleInformationException constructor
	 */
	@Test
	public void testBadVehicleInformationException() {
		BadVehicleInformationException e = new BadVehicleInformationException("Invalid tier.");
		assertEquals("Invalid tier.", e.getMessage());
	}

	/**
	 * Tests the parameterized BadVehicleInformationException constructor
	 */
	@Test
	public void testBadVehicleInformationExceptionString() {
		BadVehicleInformationException e = new BadVehicleInformationException("Invalid tier.");
		assertEquals("Invalid tier.", e.getMessage());
	}
}
