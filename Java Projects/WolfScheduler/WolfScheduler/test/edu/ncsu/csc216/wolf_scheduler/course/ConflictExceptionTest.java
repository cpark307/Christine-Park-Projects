/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test class for ConflictException.
 * @author cpark
 *
 */
public class ConflictExceptionTest {

  /**
   * Test method for ConflictException constructor with String parameter.
   */
  @Test
  public void testConflictExceptionString() {
      ConflictException ce = new ConflictException("Custom exception message");
      assertEquals("Custom exception message", ce.getMessage());
  }

  /**
   * Test method for ConflictException constructor with no parameter.
   */
  @Test
  public void testConflictException() {
    ConflictException ce = new ConflictException();
    assertEquals("Schedule conflict.", ce.getMessage());
  }

}
