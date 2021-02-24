package edu.ncsu.csc216.garage.model.service_garage;

import static org.junit.Assert.*;

import org.junit.Test;


/**
 * Tests the custom BayCarMismatchException
 * 
 * @author Will Parker
 * @author Christine Park
 *
 */
public class BayCarMismatchExceptionTest {

	/**
	 * Tests the null BayCarMismatchException constructor
	 */
	@Test
	public void testBayCarMismatchException() {
		BayCarMismatchException e = new BayCarMismatchException("BayCarMismatchException");
		assertEquals("BayCarMismatchException", e.getMessage());
	}

	/**
	 * Tests the parameterized BayCarMismatchException
	 */
	@Test
	public void testBayCarMismatchExceptionString() {
		BayCarMismatchException e = new BayCarMismatchException("BayCarMismatchException");
		assertEquals("BayCarMismatchException", e.getMessage());
	}

}
