/**
 * Provides the classes necessary to create input/output functionality
 * for WolfScheduler.
 */

package edu.ncsu.csc216.wolf_scheduler.io;

import edu.ncsu.csc216.wolf_scheduler.course.Course;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
//import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;



/**
 * Reads Course records from text files. Writes a set of CourseRecords to a
 * file.
 * 
 * @author Christine Park
 */
public class CourseRecordIO {


  /**
   * Reads course records from a file and generates a list of valid Courses. Any
   * invalid Courses are ignored. If the file to read cannot be found or the
   * permissions are incorrect a File NotFoundException is thrown.
   * 
   * @param fileName file to read Course records from
   * @return a list of valid Courses
   * @throws FileNotFoundException if the file cannot be found or read
   */
  public static ArrayList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
    Scanner fileReader = new Scanner(new FileInputStream(fileName));
    ArrayList<Course> courses = new ArrayList<Course>();
    while (fileReader.hasNextLine()) {
      try {
        Course course = readCourse(fileReader.nextLine());
        boolean duplicate = false;
        for (int i = 0; i < courses.size(); i++) {
          Course c = courses.get(i);
          if (course.getName().equals(c.getName()) && course.getSection().equals(c.getSection())) {
            // it's a duplicate
            duplicate = true;
          }
        }
        if (!duplicate) {
          courses.add(course);
        }
      } catch (IllegalArgumentException e) {
        // skip the line
      }
    }
    fileReader.close();
    return courses;
  }

  /**
   * Receives a string which is a line from the input file. Uses a "," as a
   * delimiter and creates a course object based on input from file.
   * 
   * @param line line from file
   * @return new Course object
   */
  public static Course readCourse(String line) {
    Scanner sc = new Scanner(line);
    sc.useDelimiter(",");
    String name = "";
    String title = "";
    String section = "";
    int credits = 0;
    String id = "";
    String meetingDays = "";
    int start = 0;
    int end = 0;

    try {
      name = sc.next();
      title = sc.next();
      section = sc.next();
      credits = sc.nextInt();
      id = sc.next();
      meetingDays = sc.next();

      if (meetingDays.equals("A")) {
        if (sc.hasNext()) {
          sc.close();
          throw new IllegalArgumentException("Invalid entry");
        } else {
          sc.close();
          Course timesArranged = new Course(name, title, section, credits, id, meetingDays);
          return timesArranged;
        }
      } else {
        start = sc.nextInt();
        end = sc.nextInt();
        sc.close();
        Course timesSet = new Course(name, title, section, credits, id, meetingDays, start, end);
        return timesSet;
      }
    } catch (NoSuchElementException e) {
      throw new IllegalArgumentException("Error creating Course");
    }
  }

}
