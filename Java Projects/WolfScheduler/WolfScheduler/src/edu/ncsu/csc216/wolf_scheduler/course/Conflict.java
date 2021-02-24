/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Interface to handle conflicts in Activity hierarchy.
 * @author cpark
 *
 */
public interface Conflict {
  
  /**
   * Method throws a custom checked exception.
   * 
   * @param possibleConflictingActivity conflicting activity
   * @throws ConflictException thrown if program encounters any conflicts
   */
  void checkConflict(Activity possibleConflictingActivity) throws ConflictException;

}
