package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Super class of Course and Event.
 * @author cpark
 *
 */
public abstract class Activity implements Conflict {

  @Override
  public void checkConflict(Activity pCA) throws ConflictException {
    String s1 = this.getMeetingDays();
    String s2 = pCA.getMeetingDays();
    int start1 = this.getStartTime();
    int end1 = this.getEndTime();
    int start2 = pCA.getStartTime();
    int end2 = pCA.getEndTime();
    
    if (s1.equals("A") || s2.equals("A")) {
      return;
    }
    for (int i = 0; i < s1.length(); i++){
      for (int j = 0; j < s2.length(); j++){
        if (s1.charAt(i) == s2.charAt(j)){
          if (start1 >= start2 && start1 <= end2){
            throw new ConflictException ();
          }
          if (start2 >= start1 && start2 <= end1){
            throw new ConflictException ();
          }
          if (start2 <= start1 && end2 >= end1){
            throw new ConflictException ();
          }
          if (start1 <= start2 && end2 <= end1){
            throw new ConflictException ();
          }
        }
      }
    }
    
  }

  /** Upper military time. */
  public static final int UPPER_TIME = 2400;
  /** Minutes in an hour. */
  public static final int UPPER_HOUR = 60;
  /** Factor to convert military time to standard time. */
  public static final int STAND_CONV_FACTOR = 100;
  /** Factor to convert PM military time hour to standard time hour. */
  public static final int STAND_HOUR_CONV = 12;
  /** Course's title. */
  private String title;
  /** Course's meeting days. */
  private String meetingDays;
  /** Course's starting time. */
  private int startTime;
  /** Course's ending time. */
  private int endTime;

  /** 
   * Constructor for Activity.
   * @param title title of Activity
   * @param meetingDays meeting days for Activity
   * @param startTime start time
   * @param endTime end time
   */
  public Activity(String title, String meetingDays, int startTime, int endTime) {
    super();
    setTitle(title);
    setMeetingDays(meetingDays);
    setActivityTime(startTime, endTime);
  }

  /**
   * Returns the Course's title.
   * 
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets the Course's title.
   * 
   * @param title the title to set
   */
  public void setTitle(String title) {
    if (title == null || title.equals("")) {
      throw new IllegalArgumentException();
    }
    this.title = title;
  }

  /**
   * Returns the Course's meeting days.
   * 
   * @return the meetingDays
   */
  public String getMeetingDays() {
    return meetingDays;
  }

  /**
   * Sets the Course's meeting days.
   * 
   * @param meetingDays the meetingDays to set
   */
  public void setMeetingDays(String meetingDays) {
    this.meetingDays = meetingDays;
  }

  /**
   * Returns the Course's starting time.
   * 
   * @return the startTime
   */
  public int getStartTime() {
    return startTime;
  }

  /**
   * Sets the Course's time.
   * 
   * @param startTime the startTime to set
   * @param endTime the endTIme to set
   */
  public void setActivityTime(int startTime, int endTime) {
    if (startTime < 0 || startTime >= UPPER_TIME || endTime < 0 || endTime >= UPPER_TIME) {
      throw new IllegalArgumentException();
    }
    if ((startTime % STAND_CONV_FACTOR) >= UPPER_HOUR 
        || (endTime % STAND_CONV_FACTOR) >= UPPER_HOUR) {
      throw new IllegalArgumentException();
    }
    if (endTime < startTime) {
      throw new IllegalArgumentException();
    }
    if (this.getMeetingDays().equals("A") && this.getStartTime() != 0 && this.getEndTime() != 0) {
      throw new IllegalArgumentException();
    }
    this.startTime = startTime;
    this.endTime = endTime;
  }

  /**
   * Returns a String format of the Meeting times/days of course.
   * 
   * @return the meeting time/days
   */
  public String getMeetingString() {
    String start = "";
    int standardStartHour = 0;
    String standardStartMin = "" + this.getStartTime() % STAND_CONV_FACTOR;
    if (standardStartMin.equals("0")) {
      standardStartMin = "00";
    }
  
    if (this.getStartTime() / STAND_CONV_FACTOR < STAND_HOUR_CONV) {
      start = "AM";
      if (this.getStartTime() / STAND_CONV_FACTOR == 0) {
        standardStartHour = STAND_HOUR_CONV;
      } else {
        standardStartHour = this.getStartTime() / STAND_CONV_FACTOR;
      }
    } else if (this.getStartTime() / STAND_CONV_FACTOR >= STAND_HOUR_CONV){
      start = "PM";
      if (this.getStartTime() / STAND_CONV_FACTOR != 12) {
        standardStartHour = this.getStartTime() / STAND_CONV_FACTOR - STAND_HOUR_CONV;
      } else {
        standardStartHour = 12;
      }
    }
  
    String end = "";
    int standardEndHour = 0;
    String standardEndMin = "" + this.getEndTime() % STAND_CONV_FACTOR;
    if (standardEndMin.equals("0")) {
      standardEndMin = "00";
    }
    if (this.getEndTime() / STAND_CONV_FACTOR < STAND_HOUR_CONV) {
      end = "AM";
      if (this.getEndTime() / STAND_CONV_FACTOR == 0) {
        standardEndHour = STAND_HOUR_CONV;
      } else {
        standardEndHour = this.getEndTime() / STAND_CONV_FACTOR;
      }
    } else if (this.getEndTime() / STAND_CONV_FACTOR >= STAND_HOUR_CONV) {
      end = "PM";
      if (this.getEndTime() / STAND_CONV_FACTOR != 12) {
        standardEndHour = this.getEndTime() / STAND_CONV_FACTOR - STAND_HOUR_CONV;
      } else {
        standardEndHour = 12;
      }
    }
  
    if (this.getMeetingDays().equals("A")) {
      return "Arranged";
    } else {
      return this.getMeetingDays() + " " + standardStartHour + ":" + standardStartMin + start + "-"
          + standardEndHour + ":" + standardEndMin + end;
    }
  }

  /**
   * Returns the Course's ending time.
   * 
   * @return the endTime
   */
  public int getEndTime() {
    return endTime;
  }

  /** Returns an array of length 4 containing course name,
   * section, title, and meeting days string
   * @return String array
   */
  public abstract String[] getShortDisplayArray();
  /** Returns an array of length 7 containing course name,
   * section, title, credits, instructorID, meeting days string, and empty string
   * @return String array
   */
  public abstract String[] getLongDisplayArray();
  
  /** Checks whether or not activity is duplicate 
   * @param activity activity
   * @return true or false if activity is duplicate
   */
  public abstract boolean isDuplicate(Activity activity);

  /**
   * Hash. code for Activity class
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + endTime;
    result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
    result = prime * result + startTime;
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    return result;
  }

  /**
   * Equals method for Activity class.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Activity other = (Activity) obj;
    if (endTime != other.endTime)
      return false;
    if (meetingDays == null) {
      if (other.meetingDays != null)
        return false;
    } else if (!meetingDays.equals(other.meetingDays))
      return false;
    if (startTime != other.startTime)
      return false;
    if (title == null) {
      if (other.title != null)
        return false;
    } else if (!title.equals(other.title))
      return false;
    return true;
  }

  
  
}