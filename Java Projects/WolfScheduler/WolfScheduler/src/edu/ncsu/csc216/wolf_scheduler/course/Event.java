/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Activity superclass for Event and Course.
 * @author cpark
 *
 */
public class Event extends Activity {
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
  
  private int weeklyRepeat;
  private String eventDetails;

  /**
   *  Constructor for Event class
   * @param title title of Event
   * @param meetingDays meeting days of Event
   * @param startTime start time
   * @param endTime end time
   * @param weeklyRepeat number of times event repeats
   * @param eventDetails description of Event
   */
  public Event(String title, String meetingDays, int startTime, 
      int endTime, int weeklyRepeat, String eventDetails) {
    super(title, meetingDays, startTime, endTime);
    setWeeklyRepeat(weeklyRepeat);
    setEventDetails(eventDetails);
  }

  /** 
   * Returns the weekly repeat amount of event.
   * @return the weeklyRepeat
   */
  public int getWeeklyRepeat() {
    return weeklyRepeat;
  }

  /**
   * Sets the weekly repeat amount of event.
   * @param weeklyRepeat the weeklyRepeat to set
   */
  public void setWeeklyRepeat(int weeklyRepeat) {
    if (weeklyRepeat < 1 || weeklyRepeat > 4) {
      throw new IllegalArgumentException("Invalid weekly repeat");
    }
    this.weeklyRepeat = weeklyRepeat;
  }

  /**
   * Returns the event details.
   * @return the eventDetails
   */
  public String getEventDetails() {
    return eventDetails;
  }

  /**
   * Sets the event details.
   * @param eventDetails the eventDetails to set
   */
  public void setEventDetails(String eventDetails) {
    if (eventDetails == null) {
      throw new IllegalArgumentException("Invalid event details");
    }
    this.eventDetails = eventDetails;
  }

  
  /** 
   * Returns a string array of length 4. The first two values are empty strings
   * and the last 2 values should be the title and the meeting string.
   */
  @Override
  public String[] getShortDisplayArray() {
    String[] shortDisplayArray = new String[SHORT_ARR_LENGTH];
    shortDisplayArray[ZERO] = "";
    shortDisplayArray[ONE] = "";
    shortDisplayArray[TWO] = getTitle();
    shortDisplayArray[THREE] = getMeetingString();
    return shortDisplayArray;
  }

  /**
   * Returns a String array of length 7. The first 2 values should be empty strings.
   * The third value is the title followed by two values with empty strings. 
   * The last 2 are the meeting string and event details.
   */
  @Override
  public String[] getLongDisplayArray() {
    String[] longDisplayArray = new String[LONG_ARR_LENGTH];
    longDisplayArray[ZERO] = "";
    longDisplayArray[ONE] = "";
    longDisplayArray[TWO] = getTitle();
    longDisplayArray[THREE] = "";
    longDisplayArray[FOUR] = "";
    longDisplayArray[FIVE] = getMeetingString();
    longDisplayArray[SIX] = getEventDetails();
    return longDisplayArray;
  }

  /**
   * Overrides Acitivty's getMeetingString()
   */
  @Override
  public String getMeetingString() {
    return super.getMeetingString() + " (every " + getWeeklyRepeat() + " weeks)";
  }

  /**
   * Overrides Activity's toString()
   */
  @Override
  public String toString() {
    return getTitle() + "," + getMeetingDays() + "," + getStartTime() + "," + 
        getEndTime() + "," + getWeeklyRepeat() + "," + getEventDetails();
  }

  @Override
  public void setMeetingDays(String meetingDays) {
    if (meetingDays == null || meetingDays.equals("")) {
      throw new IllegalArgumentException();
    }
  
    for (int i = 0; i < meetingDays.length(); i++) {
      if (meetingDays == null || meetingDays.length() <= 0) {
        throw new IllegalArgumentException("Invalid meeting days");
      }
      if (meetingDays.charAt(i) != 'M' && meetingDays.charAt(i) != 'T' 
          && meetingDays.charAt(i) != 'W' && meetingDays.charAt(i) != 'H' 
          && meetingDays.charAt(i) != 'F' && meetingDays.charAt(i) != 'S' 
          && meetingDays.charAt(i) != 'U') {
        throw new IllegalArgumentException();
      }
    }
    super.setMeetingDays(meetingDays);
  }
  /** Checks whether or not activity is duplicate 
   * @param activity activity
   * @return true or false if activity is duplicate
   */
  public boolean isDuplicate(Activity activity) {
    if (activity instanceof Event) {
      Event event = (Event) activity;
        return event.getTitle().equals(this.getTitle());
      }
      return false;
    }
}
