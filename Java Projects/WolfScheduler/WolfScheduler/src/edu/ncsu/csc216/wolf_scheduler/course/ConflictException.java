/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Class for Conflict Exception.
 * @author cpark
 *
 */
public class ConflictException extends Exception {
  /** ID used for serialization */
  private static final long serialVersionUID = 1L;
  
  /**
   * Constructor for Conflict Exception with String parameter
   * @param message exception message
   */
  public ConflictException(String message) {
    super(message);
  }

  /**
   * Constructor for Conflict Exception with no parameters
   */
  public ConflictException() {
    this("Schedule conflict.");
  }
}
