/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test class for Activity
 * @author cpark
 *
 */
public class ActivityTest {

  /**
   * Test method for {@link edu.ncsu.csc216.wolf_scheduler.course.Activity#checkConflict(edu.ncsu.csc216.wolf_scheduler.course.Activity)}.
   */
  @Test
  public void testCheckConflict() {
      Activity a1 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", "MW", 1330, 1445);
      Activity a2 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", "TH", 1330, 1445);
      try {
          a1.checkConflict(a2);
          assertEquals("Incorrect meeting string for this Activity.", "MW 1:30PM-2:45PM", a1.getMeetingString());
          assertEquals("Incorrect meeting string for possibleConflictingActivity.", "TH 1:30PM-2:45PM", a2.getMeetingString());
      } catch (ConflictException e) {
          fail("A ConflictException was thrown when two Activities at the same time on completely distinct days were compared.");
      }
      
      
    //Update a1 with the same meeting days and a start time that overlaps the end time of a2
      a1.setMeetingDays("TH");
      a1.setActivityTime(1445, 1530);
      try {
          a1.checkConflict(a2);
          fail(); //ConflictException should have been thrown, but was not.
      } catch (ConflictException e) {
          //Check that the internal state didn't change during method call.
          assertEquals("TH 2:45PM-3:30PM", a1.getMeetingString());
          assertEquals("TH 1:30PM-2:45PM", a2.getMeetingString());
      }
  }
}
