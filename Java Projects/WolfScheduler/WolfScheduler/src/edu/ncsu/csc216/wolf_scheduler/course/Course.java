/**
 * Provides the classes necessary to create a Course in the
 * WolfScheduler Project.
 */

package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Represents a course in WolfScheduler.
 * 
 * @author Christine Park
 */
public class Course extends Activity {

  /** Length of section. */
  public static final int SECTION_LENGTH = 3;
  /** Maximum name length. */
  public static final int MAX_NAME_LENGTH = 6;
  /** Minimum name length. */
  public static final int MIN_NAME_LENGTH = 4;
  /** Maximum credits. */
  public static final int MAX_CREDITS = 5;
  /** Minimum credits. */
  public static final int MIN_CREDITS = 1;
  /** short array length */
  public static final int SHORT_ARR_LENGTH = 4;
  /** long array length */
  public static final int LONG_ARR_LENGTH = 7;
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
  
  
  /** Course's name. */
  private String name;
  /** Course's section. */
  private String section;
  /** Course's credit hours. */
  private int credits;
  /** Course's instructor. */
  private String instructorId;
  /**
   * Returns the Course's name.
   * 
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the Course's name. If the name is null, has a length less than 4 or
   * greater than 6, an IllegalArgumentException is thrown.
   * 
   * @param name the name to set
   * @throws IllegalArgumentException if name is null or length is less than 4 or
   *                                  greater than 6
   */
  private void setName(String name) {
    if (name == null || name.equals("")) {
      throw new IllegalArgumentException();
    }
    if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
      throw new IllegalArgumentException();
    }
    this.name = name;
  }

  /**
   * Returns the Course's section.
   * 
   * @return the section
   */
  public String getSection() {
    return section;
  }

  /**
   * Sets the Course's section.
   * 
   * @param section the section to set
   */
  public void setSection(String section) {
    if (section == null) {
      throw new IllegalArgumentException();
    }
    if (section.length() != SECTION_LENGTH) {
      throw new IllegalArgumentException();
    }
    for (int i = 0; i < section.length(); i++) {
      if (!Character.isDigit(section.charAt(i))) {
        throw new IllegalArgumentException();
      }
    }

    this.section = section;
  }

  /**
   * Returns the Course's credit hours.{
   * 
   * @return the credits
   */
  public int getCredits() {
    return credits;
  }

  /**
   * Sets the Course's credit hours.
   * 
   * @param credits the credits to set
   */
  public void setCredits(int credits) {
    if (credits < MIN_CREDITS || credits > MAX_CREDITS) {
      throw new IllegalArgumentException();
    }
    this.credits = credits;
  }

  /**
   * Returns the Course's instructor ID.
   * 
   * @return the instructorId
   */
  public String getInstructorId() {
    return instructorId;
  }

  /**
   * Sets the Course's instructor ID.
   * 
   * @param instructorId the instructorId to set
   */
  public void setInstructorId(String instructorId) {
    if (instructorId == null || instructorId.equals("")) {
      throw new IllegalArgumentException();
    }
    this.instructorId = instructorId;
  }

  /**
   * Constructs a Course object with values for all fields.
   * 
   * @param name         name of Course
   * @param title        title of Course
   * @param section      section of Course
   * @param credits      hours for Course
   * @param instructorId instructor's unity id
   * @param meetingDays  meeting days for Course as series of chars
   * @param startTime    start time for Course
   * @param endTime      end time for Course
   */
  public Course(String name, String title, String section, int credits, 
      String instructorId, String meetingDays,
      int startTime, int endTime) {
    super(title, meetingDays, startTime, endTime);
    setName(name);
    setSection(section);
    setCredits(credits);
    setInstructorId(instructorId);

  }

  /**
   * Creates a Course with the given name, title, section, credits, instructorId,
   * and meetingDays for courses that are arranged.
   * 
   * @param name name of Course
   * @param title title of Course
   * @param section section of Course
   * @param credits credits of Course
   * @param instructorId instructor ID for Course
   * @param meetingDays meeting days for Course
   */
  public Course(String name, String title, String section, int credits, 
      String instructorId, String meetingDays) {
    this(name, title, section, credits, instructorId, meetingDays, 0, 0);
  }

 
  /** 
   * Hash code for Course class.
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + credits;
    result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((section == null) ? 0 : section.hashCode());
    return result;
  }

  /** 
   * Equals method for Course class.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    Course other = (Course) obj;
    if (credits != other.credits)
      return false;
    if (instructorId == null) {
      if (other.instructorId != null)
        return false;
    } else if (!instructorId.equals(other.instructorId))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (section == null) {
      if (other.section != null)
        return false;
    } else if (!section.equals(other.section))
      return false;
    return true;
  }

  /**
   * Returns a comma separated value String of all Course fields.
   * 
   * @return String representation of Course
   */
  @Override
  public String toString() {
    String output; 
    if ("A".equals(getMeetingDays())) {
      output = "" + name + "," + getTitle() + "," + section + "," + credits + "," 
          + instructorId + ","  + getMeetingDays();
    } else {
      output = "" + name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," 
        + getMeetingDays() + "," + getStartTime() + "," + getEndTime();
    }
    return output;
  }

  /** 
   * Returns an array of length 4 containing course name, section,
   * title, and meeting days string.
   */
  @Override
  public String[] getShortDisplayArray() {
    String[] shortDisplayArray = new String[SHORT_ARR_LENGTH];
    shortDisplayArray[ZERO] = getName();
    shortDisplayArray[ONE] = getSection();
    shortDisplayArray[TWO] = getTitle();
    shortDisplayArray[THREE] = getMeetingString();
    return shortDisplayArray;
  }

  /**
   * Returns an array of length 7 containing course name, section,
   * title, credits, instructorId, meeting days string, empty string.
   */
  @Override
  public String[] getLongDisplayArray() {
    String[] longDisplayArray = new String[LONG_ARR_LENGTH];
    longDisplayArray[ZERO] = getName();
    longDisplayArray[ONE] = getSection();
    longDisplayArray[TWO] = getTitle();
    longDisplayArray[THREE] = "" + getCredits();
    longDisplayArray[FOUR] = getInstructorId();
    longDisplayArray[FIVE] = getMeetingString();
    longDisplayArray[SIX] = "";
    return longDisplayArray;
  }

  /**
   * Override's Activity's setMeetingDays();
   * @param meetingDays meeting day string
   */
  @Override
  public void setMeetingDays(String meetingDays) {
    if (meetingDays == null || meetingDays.equals("")) {
      throw new IllegalArgumentException();
    }
  
    for (int i = 0; i < meetingDays.length(); i++) {
      if (meetingDays.charAt(i) != 'M' && meetingDays.charAt(i) != 'T' 
          && meetingDays.charAt(i) != 'W' && meetingDays.charAt(i) != 'H' 
          && meetingDays.charAt(i) != 'F' && meetingDays.charAt(i) != 'A') {
        throw new IllegalArgumentException();
      }
    }
    if (meetingDays.contains("A") && meetingDays.length() > 1) {
      throw new IllegalArgumentException();
    }
    super.setMeetingDays(meetingDays);
  }
  
  /** Checks whether or not activity is duplicate 
   * @param activity activity
   * @return true or false if activity is duplicate
   */
  public boolean isDuplicate(Activity activity) {
    if (activity instanceof Course) {
      Course c = (Course) activity;
      if (c.getName().equals(this.getName())) {
        return true;
      }
      return false;
    }
    return false;
    }
}
  
  

