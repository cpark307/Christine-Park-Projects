/**
 * Provides classes for the logic for WolfScheduler.
 */

package edu.ncsu.csc216.wolf_scheduler.scheduler;

import edu.ncsu.csc216.wolf_scheduler.course.Activity;
import edu.ncsu.csc216.wolf_scheduler.course.ConflictException;
import edu.ncsu.csc216.wolf_scheduler.course.Course;
import edu.ncsu.csc216.wolf_scheduler.course.Event;
import edu.ncsu.csc216.wolf_scheduler.io.ActivityRecordIO;
import edu.ncsu.csc216.wolf_scheduler.io.CourseRecordIO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * WolfScheduler class.
 * @author Christine Park
 *
 */
public class WolfScheduler {
  
  /** index of 0 */
  public static final int ZERO = 0;
  /** index of 1 */
  public static final int ONE = 1;
  /** index of 2 */
  public static final int TWO = 2;
  /** index of 3 */
  public static final int THREE = 3;
  /** index of 4 */
  public static final int FOUR = 4;
  /** index of 5 */
  public static final int FIVE = 5;
  /** index of 6 */
  public static final int SIX = 6;
  /** index of 7 */
  public static final int SEVEN = 7;
  
  private ArrayList<Course> catalog;
  private ArrayList<Activity> schedule;
  private String title;

  /**
 * Constructs WolfScheduler object.
 * @param fileName name of file
 */
  public WolfScheduler(String fileName) {
    try {
      catalog = CourseRecordIO.readCourseRecords(fileName);
      setTitle("My Schedule");
      schedule = new ArrayList<Activity>();
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Cannot find file.");
    }
  }

  /**
   * Returns course from catalog.
   * @param course course name of interest
   * @param section course section of interest
   * @return course from catalog (if it exists)
   */
  public Course getCourseFromCatalog(String course, String section) {
    for (Course c: catalog) {
      if (c.getName().equals(course) && c.getSection().contentEquals(section)) {
        return c;
      }
    }
    return null;
  }

  /**
   * Adds course to schedule.
   * @param name name of course
   * @param section section of course
   * @return T/F if course added to schedule
   */
  @SuppressWarnings("unused")
  public boolean addCourse(String name, String section) {
    Course course = getCourseFromCatalog(name, section);
    if (course != null) {
      for (Activity a: schedule) {
        
        try {
          course.checkConflict(a);
        } catch(ConflictException e) {
          throw new IllegalArgumentException("The course cannot be added due to a conflict.");
        }
        
        if (course.isDuplicate(a)) {
          throw new IllegalArgumentException("You are already enrolled in " + name);
        }
      }
        schedule.add(course);
        return true;
    }
    if (course != null && schedule.size() == 0) {
      schedule.add(course);
      return true;
    }

    return false;
  }
  
  /**
   * Adds event to schedule.
   * @param title title of Event
   * @param meetingDays meeting days for event
   * @param startTime event's start time
   * @param endTime event's end time
   * @param weeklyRepeat weekly repeat amount of event
   * @param eventDetails details of event
   */
  public void addEvent(String title, String meetingDays, int startTime, int endTime,
      int weeklyRepeat, String eventDetails) {
    Event event = new Event(title, meetingDays, startTime, endTime, weeklyRepeat, eventDetails);
    for (int i = 0; i < schedule.size(); i++) {
      Activity a = schedule.get(i);
      if (event.isDuplicate(a)) {
        throw new IllegalArgumentException(
            "You have already created an event called " + a.getTitle());
      }
    
    }
    for (int i = 0; i < schedule.size(); i++) {
      Activity a = schedule.get(i);
        try {
          event.checkConflict(a);
        } catch (ConflictException e) {
          throw new IllegalArgumentException("The event cannot be added due to a conflict.");
        }
      }
      
    schedule.add(event);
  }



  /**
   * Removes activity from schedule.
   * @param idx index in catalog
   * 
   * @return T/F if class has been removed
   */
  public boolean removeActivity(int idx) {
   if (schedule.size() == 0) {
        return false;
    }
    if (idx < 0 || idx >= schedule.size()) {
      return false;
    }
     schedule.remove(schedule.get(idx));
    return true;
  }

  /**
   *  Resets schedule.
   */
  public void resetSchedule() {
    schedule = new ArrayList<Activity>();
  }

  /**
   * Gets courses from catalog.
   * @return 2D String array of catalog
   */
  public String[][] getCourseCatalog() {
    String[][] textCatalog = new String[catalog.size()][4];
    for (int i = 0; i < catalog.size(); i++) {
      textCatalog[i][0] = catalog.get(i).getName();
      textCatalog[i][1] = catalog.get(i).getSection();
      textCatalog[i][2] = catalog.get(i).getTitle();
      textCatalog[i][3] = catalog.get(i).getMeetingString();
    }
    return textCatalog;
  }

  /**
   * Get courses in schedule.
   * @return 2D String array of schedule
   */
  public String[][] getScheduledActivities() {
    String[][] textSchedule = new String[this.schedule.size()][FOUR];
    for (int i = 0; i < schedule.size(); i++) {
      textSchedule[i][ZERO] = schedule.get(i).getShortDisplayArray()[ZERO];
      textSchedule[i][ONE] = schedule.get(i).getShortDisplayArray()[ONE];
      textSchedule[i][TWO] = schedule.get(i).getShortDisplayArray()[TWO];
      textSchedule[i][THREE] = schedule.get(i).getShortDisplayArray()[THREE];
    }
    return textSchedule;
  }

  /**
   * Get courses in schedule (full detail).
   * @return 2D String array of schedule
   */
  public String[][] getFullScheduledActivities() {
    String[][] textSchedule = new String[schedule.size()][SEVEN];
    for (int i = 0; i < schedule.size(); i++) {
      textSchedule[i][ZERO] = schedule.get(i).getLongDisplayArray()[ZERO];
      textSchedule[i][ONE] = schedule.get(i).getLongDisplayArray()[ONE];
      textSchedule[i][TWO] = schedule.get(i).getLongDisplayArray()[TWO];
      textSchedule[i][THREE] = schedule.get(i).getLongDisplayArray()[THREE];
      textSchedule[i][FOUR] = schedule.get(i).getLongDisplayArray()[FOUR];
      textSchedule[i][FIVE] = schedule.get(i).getLongDisplayArray()[FIVE];
      textSchedule[i][SIX] = schedule.get(i).getLongDisplayArray()[SIX];
    }
    return textSchedule;
  }

  /**
   * Get title.
   * @return title
   */
  public String getTitle() {
    return this.title;
  }

  /** 
   * Set title.
   * @param title title of course
   */
  public void setTitle(String title) {
    if (title == null) {
      throw new IllegalArgumentException();
    }
    this.title = title;
  }

  /** 
   * Exports schedule.
   * @param fileName name of file
   */
  public void exportSchedule(String fileName) {
    try {
      ActivityRecordIO.writeActivityRecords(fileName, schedule);
    } catch (IOException e) {
      throw new IllegalArgumentException("The file cannot be saved");
    }
  }

}
