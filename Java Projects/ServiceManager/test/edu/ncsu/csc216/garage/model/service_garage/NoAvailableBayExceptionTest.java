package edu.ncsu.csc216.garage.model.service_garage;

import static org.junit.Assert.*;

import org.junit.Test;


/**
 * Tests the custom NoAvailableBayException
 * 
 * @author Will Parker
 * @author Christine Park
 *
 */
public class NoAvailableBayExceptionTest {

	/**
	 * Tests the null NoAvailableBayException constructor
	 */
	@Test
	public void testNoAvailableBayException() {
		NoAvailableBayException e = new NoAvailableBayException("No Available Bay");
		assertEquals("No Available Bay", e.getMessage());
	}

	/**
	 * Tests the parameterized NoAvailableBayException constructor
	 */
	@Test
	public void testNoAvailableBayExceptionString() {
		NoAvailableBayException e = new NoAvailableBayException("No Available Bay");
		assertEquals("No Available Bay", e.getMessage());
	}

}
