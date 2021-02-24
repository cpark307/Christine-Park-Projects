package edu.ncsu.csc216.wolf_scheduler.course;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the Event class.
 * @author Sarah Heckman
 */
public class EventTest {
	
	/** Event title */
	private static final String EVENT_TITLE = "Exercise";
	/** Event meeting days */
	private static final String EVENT_MEETING_DAYS = "MTWHF";
	/** Event start time */
	private static final int EVENT_START_TIME = 800;
	/** Event end time */
	private static final int EVENT_END_TIME = 900;
	/** Event repeat */
	private static final int EVENT_WEEKLY_REPEAT = 1;
	/** Event details */
	private static final String EVENT_DETAILS = "Cardio Time!";

	/**
	 * Test Event.Event().
	 */
	@Test
	public void testEvent() {
		//Test invalid title
		Event event = null;
		try {
			event = new Event(null, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_WEEKLY_REPEAT, EVENT_DETAILS);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(event);
		}
		
		try {
			event = new Event("", EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_WEEKLY_REPEAT, EVENT_DETAILS);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(event);
		}
		
		//Test invalid meetingDays
		try {
			event = new Event(EVENT_TITLE, null, EVENT_START_TIME, EVENT_END_TIME, EVENT_WEEKLY_REPEAT, EVENT_DETAILS);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(event);
		}
		
		try {
			event = new Event(EVENT_TITLE, "", EVENT_START_TIME, EVENT_END_TIME, EVENT_WEEKLY_REPEAT, EVENT_DETAILS);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(event);
		}
		
		//Test invalid start time
		try {
			event = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, 1897, EVENT_END_TIME, EVENT_WEEKLY_REPEAT, EVENT_DETAILS);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(event);
		}
		
		try {
			event = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, -1, EVENT_END_TIME, EVENT_WEEKLY_REPEAT, EVENT_DETAILS);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(event);
		}
		
		//Test invalid end time
		try {
			event = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, 997, EVENT_WEEKLY_REPEAT, EVENT_DETAILS);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(event);
		}
		
		try {
			event = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, 90, EVENT_WEEKLY_REPEAT, EVENT_DETAILS);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(event);
		}
		
		//Test invalid weekly repeat
		try {
			event = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, 0, EVENT_DETAILS);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(event);
		}
		
		try {
			event = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, 5, EVENT_DETAILS);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(event);
		}
		
		//Test invalid details
		try {
			event = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_WEEKLY_REPEAT, null);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(event);
		}
		
		//Test a valid event
		event = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_WEEKLY_REPEAT, EVENT_DETAILS);
		assertEquals(EVENT_TITLE, event.getTitle());
		assertEquals(EVENT_MEETING_DAYS, event.getMeetingDays());
		assertEquals(EVENT_START_TIME, event.getStartTime());
		assertEquals(EVENT_END_TIME, event.getEndTime());
		assertEquals(EVENT_WEEKLY_REPEAT, event.getWeeklyRepeat());
		assertEquals(EVENT_DETAILS, event.getEventDetails());
		
	}

	/**
	 * Test Event.setMeetingDays()
	 */
	@Test
	public void testSetMeetingDays() {
		Event event = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_WEEKLY_REPEAT, EVENT_DETAILS);
		try {
			event.setMeetingDays("A");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(EVENT_TITLE, event.getTitle());
			assertEquals(EVENT_MEETING_DAYS, event.getMeetingDays());
			assertEquals(EVENT_START_TIME, event.getStartTime());
			assertEquals(EVENT_END_TIME, event.getEndTime());
			assertEquals(EVENT_WEEKLY_REPEAT, event.getWeeklyRepeat());
			assertEquals(EVENT_DETAILS, event.getEventDetails());
		}
		
		try {
			event.setMeetingDays("MTZTH");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(EVENT_TITLE, event.getTitle());
			assertEquals(EVENT_MEETING_DAYS, event.getMeetingDays());
			assertEquals(EVENT_START_TIME, event.getStartTime());
			assertEquals(EVENT_END_TIME, event.getEndTime());
			assertEquals(EVENT_WEEKLY_REPEAT, event.getWeeklyRepeat());
			assertEquals(EVENT_DETAILS, event.getEventDetails());
		}
		
		event.setMeetingDays("MTTHS");
		assertEquals(EVENT_TITLE, event.getTitle());
		assertEquals("MTTHS", event.getMeetingDays());
		assertEquals(EVENT_START_TIME, event.getStartTime());
		assertEquals(EVENT_END_TIME, event.getEndTime());
		assertEquals(EVENT_WEEKLY_REPEAT, event.getWeeklyRepeat());
		assertEquals(EVENT_DETAILS, event.getEventDetails());
		
		event.setMeetingDays("UMTWF");
		assertEquals(EVENT_TITLE, event.getTitle());
		assertEquals("UMTWF", event.getMeetingDays());
		assertEquals(EVENT_START_TIME, event.getStartTime());
		assertEquals(EVENT_END_TIME, event.getEndTime());
		assertEquals(EVENT_WEEKLY_REPEAT, event.getWeeklyRepeat());
		assertEquals(EVENT_DETAILS, event.getEventDetails());
	}

	/**
	 * Test Event.getMeetingString().
	 */
	@Test
	public void testGetMeetingString() {
		Event event = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_WEEKLY_REPEAT, EVENT_DETAILS);
		assertEquals("MTWHF 8:00AM-9:00AM (every 1 weeks)", event.getMeetingString());
		
		event = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, 1200, 1300, EVENT_WEEKLY_REPEAT, EVENT_DETAILS);
		assertEquals("MTWHF 12:00PM-1:00PM (every 1 weeks)", event.getMeetingString());
		
		event = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, 1135, 1235, EVENT_WEEKLY_REPEAT, EVENT_DETAILS);
		assertEquals("MTWHF 11:35AM-12:35PM (every 1 weeks)", event.getMeetingString());
	}

	/**
	 * Test Event.getShortDisplayArray().
	 */
	@Test
	public void testGetShortDisplayArray() {
		Event event = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_WEEKLY_REPEAT, EVENT_DETAILS);
		assertEquals(EVENT_TITLE, event.getTitle());
		assertEquals(EVENT_MEETING_DAYS, event.getMeetingDays());
		assertEquals(EVENT_START_TIME, event.getStartTime());
		assertEquals(EVENT_END_TIME, event.getEndTime());
		assertEquals(EVENT_WEEKLY_REPEAT, event.getWeeklyRepeat());
		assertEquals(EVENT_DETAILS, event.getEventDetails());
		String [] actualShortDisplay = event.getShortDisplayArray();
		assertEquals("", actualShortDisplay[0]);
		assertEquals("", actualShortDisplay[1]);
		assertEquals(EVENT_TITLE, actualShortDisplay[2]);
		assertEquals("MTWHF 8:00AM-9:00AM (every 1 weeks)", actualShortDisplay[3]);
	}

	/**
	 * Test Event.getLongDisplayArray().
	 */
	@Test
	public void testGetLongDisplayArray() {
		Event event = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_WEEKLY_REPEAT, EVENT_DETAILS);
		assertEquals(EVENT_TITLE, event.getTitle());
		assertEquals(EVENT_MEETING_DAYS, event.getMeetingDays());
		assertEquals(EVENT_START_TIME, event.getStartTime());
		assertEquals(EVENT_END_TIME, event.getEndTime());
		assertEquals(EVENT_WEEKLY_REPEAT, event.getWeeklyRepeat());
		assertEquals(EVENT_DETAILS, event.getEventDetails());
		String [] actualLongDisplay = event.getLongDisplayArray();
		assertEquals("", actualLongDisplay[0]);
		assertEquals("", actualLongDisplay[1]);
		assertEquals(EVENT_TITLE, actualLongDisplay[2]);
		assertEquals("", actualLongDisplay[3]);
		assertEquals("", actualLongDisplay[4]);
		assertEquals("MTWHF 8:00AM-9:00AM (every 1 weeks)", actualLongDisplay[5]);
		assertEquals(EVENT_DETAILS, actualLongDisplay[6]);
	}

	/**
	 * Test Event.setWeeklyRepeat().
	 */
	@Test
	public void testSetWeeklyRepeat() {
		Event event = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_WEEKLY_REPEAT, EVENT_DETAILS);
		try {
			event.setWeeklyRepeat(0);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(EVENT_TITLE, event.getTitle());
			assertEquals(EVENT_MEETING_DAYS, event.getMeetingDays());
			assertEquals(EVENT_START_TIME, event.getStartTime());
			assertEquals(EVENT_END_TIME, event.getEndTime());
			assertEquals(EVENT_WEEKLY_REPEAT, event.getWeeklyRepeat());
			assertEquals(EVENT_DETAILS, event.getEventDetails());
		}
		
		try {
			event.setWeeklyRepeat(5);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(EVENT_TITLE, event.getTitle());
			assertEquals(EVENT_MEETING_DAYS, event.getMeetingDays());
			assertEquals(EVENT_START_TIME, event.getStartTime());
			assertEquals(EVENT_END_TIME, event.getEndTime());
			assertEquals(EVENT_WEEKLY_REPEAT, event.getWeeklyRepeat());
			assertEquals(EVENT_DETAILS, event.getEventDetails());
		}
		
		event.setWeeklyRepeat(2);
		assertEquals(EVENT_TITLE, event.getTitle());
		assertEquals(EVENT_MEETING_DAYS, event.getMeetingDays());
		assertEquals(EVENT_START_TIME, event.getStartTime());
		assertEquals(EVENT_END_TIME, event.getEndTime());
		assertEquals(2, event.getWeeklyRepeat());
		assertEquals(EVENT_DETAILS, event.getEventDetails());
	}

	/**
	 * Test.Event.setEventDetails().
	 */
	@Test
	public void testSetEventDetails() {
		Event event = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_WEEKLY_REPEAT, EVENT_DETAILS);
		try {
			event.setEventDetails(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(EVENT_TITLE, event.getTitle());
			assertEquals(EVENT_MEETING_DAYS, event.getMeetingDays());
			assertEquals(EVENT_START_TIME, event.getStartTime());
			assertEquals(EVENT_END_TIME, event.getEndTime());
			assertEquals(EVENT_WEEKLY_REPEAT, event.getWeeklyRepeat());
			assertEquals(EVENT_DETAILS, event.getEventDetails());
		}
		
		event.setEventDetails("Other details");
		assertEquals(EVENT_TITLE, event.getTitle());
		assertEquals(EVENT_MEETING_DAYS, event.getMeetingDays());
		assertEquals(EVENT_START_TIME, event.getStartTime());
		assertEquals(EVENT_END_TIME, event.getEndTime());
		assertEquals(EVENT_WEEKLY_REPEAT, event.getWeeklyRepeat());
		assertEquals("Other details", event.getEventDetails());
	}

	/**
	 * Test Event.setTitle().
	 */
	@Test
	public void testSetTitle() {
		Event event = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_WEEKLY_REPEAT, EVENT_DETAILS);
		assertEquals(EVENT_TITLE, event.getTitle());
		assertEquals(EVENT_MEETING_DAYS, event.getMeetingDays());
		assertEquals(EVENT_START_TIME, event.getStartTime());
		assertEquals(EVENT_END_TIME, event.getEndTime());
		assertEquals(EVENT_WEEKLY_REPEAT, event.getWeeklyRepeat());
		assertEquals(EVENT_DETAILS, event.getEventDetails());
		
		//Test that setting the title to null doesn't change the title (or anything else).
		try {
			event.setTitle(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(EVENT_TITLE, event.getTitle());
			assertEquals(EVENT_MEETING_DAYS, event.getMeetingDays());
			assertEquals(EVENT_START_TIME, event.getStartTime());
			assertEquals(EVENT_END_TIME, event.getEndTime());
			assertEquals(EVENT_WEEKLY_REPEAT, event.getWeeklyRepeat());
			assertEquals(EVENT_DETAILS, event.getEventDetails());
		}
		
		//Test that setting the title to "" doesn't change the title (or anything else).
		try {
			event.setTitle("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(EVENT_TITLE, event.getTitle());
			assertEquals(EVENT_MEETING_DAYS, event.getMeetingDays());
			assertEquals(EVENT_START_TIME, event.getStartTime());
			assertEquals(EVENT_END_TIME, event.getEndTime());
			assertEquals(EVENT_WEEKLY_REPEAT, event.getWeeklyRepeat());
			assertEquals(EVENT_DETAILS, event.getEventDetails());
		}
		
		//Valid set
		event.setTitle("A new title");
		assertEquals("A new title", event.getTitle());
		assertEquals(EVENT_MEETING_DAYS, event.getMeetingDays());
		assertEquals(EVENT_START_TIME, event.getStartTime());
		assertEquals(EVENT_END_TIME, event.getEndTime());
		assertEquals(EVENT_WEEKLY_REPEAT, event.getWeeklyRepeat());
		assertEquals(EVENT_DETAILS, event.getEventDetails());
	}

	/**
	 * Test Event.setCourseTime().
	 */
	@Test
	public void testSetCourseTime() {
		Event event = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_WEEKLY_REPEAT, EVENT_DETAILS);
		assertEquals(EVENT_TITLE, event.getTitle());
		assertEquals(EVENT_MEETING_DAYS, event.getMeetingDays());
		assertEquals(EVENT_START_TIME, event.getStartTime());
		assertEquals(EVENT_END_TIME, event.getEndTime());
		assertEquals(EVENT_WEEKLY_REPEAT, event.getWeeklyRepeat());
		assertEquals(EVENT_DETAILS, event.getEventDetails());
		
		//Test that setting the start time to 2400 doesn't change the start time (or anything else).
		try {
			event.setActivityTime(2400, 1445);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(EVENT_TITLE, event.getTitle());
			assertEquals(EVENT_MEETING_DAYS, event.getMeetingDays());
			assertEquals(EVENT_START_TIME, event.getStartTime());
			assertEquals(EVENT_END_TIME, event.getEndTime());
			assertEquals(EVENT_WEEKLY_REPEAT, event.getWeeklyRepeat());
			assertEquals(EVENT_DETAILS, event.getEventDetails());
		}
		
		//Test that setting the start time to 1360 doesn't change the start time (or anything else).
		try {
			event.setActivityTime(1360, 1445);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(EVENT_TITLE, event.getTitle());
			assertEquals(EVENT_MEETING_DAYS, event.getMeetingDays());
			assertEquals(EVENT_START_TIME, event.getStartTime());
			assertEquals(EVENT_END_TIME, event.getEndTime());
			assertEquals(EVENT_WEEKLY_REPEAT, event.getWeeklyRepeat());
			assertEquals(EVENT_DETAILS, event.getEventDetails());
		}
		
		//Test that setting the start time to -1 doesn't change the start time (or anything else).
		try {
			event.setActivityTime(-1, 1445);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(EVENT_TITLE, event.getTitle());
			assertEquals(EVENT_MEETING_DAYS, event.getMeetingDays());
			assertEquals(EVENT_START_TIME, event.getStartTime());
			assertEquals(EVENT_END_TIME, event.getEndTime());
			assertEquals(EVENT_WEEKLY_REPEAT, event.getWeeklyRepeat());
			assertEquals(EVENT_DETAILS, event.getEventDetails());
		}
		
		//Test that setting the start time to 2400 doesn't change the start time (or anything else).
		try {
			event.setActivityTime(1330, 2400);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(EVENT_TITLE, event.getTitle());
			assertEquals(EVENT_MEETING_DAYS, event.getMeetingDays());
			assertEquals(EVENT_START_TIME, event.getStartTime());
			assertEquals(EVENT_END_TIME, event.getEndTime());
			assertEquals(EVENT_WEEKLY_REPEAT, event.getWeeklyRepeat());
			assertEquals(EVENT_DETAILS, event.getEventDetails());
		}
		
		//Test that setting the start time to 1360 doesn't change the start time (or anything else).
		try {
			event.setActivityTime(1330, 1360);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(EVENT_TITLE, event.getTitle());
			assertEquals(EVENT_MEETING_DAYS, event.getMeetingDays());
			assertEquals(EVENT_START_TIME, event.getStartTime());
			assertEquals(EVENT_END_TIME, event.getEndTime());
			assertEquals(EVENT_WEEKLY_REPEAT, event.getWeeklyRepeat());
			assertEquals(EVENT_DETAILS, event.getEventDetails());
		}
		
		//Test that setting the start time to -1 doesn't change the start time (or anything else).
		try {
			event.setActivityTime(1330, -1);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(EVENT_TITLE, event.getTitle());
			assertEquals(EVENT_MEETING_DAYS, event.getMeetingDays());
			assertEquals(EVENT_START_TIME, event.getStartTime());
			assertEquals(EVENT_END_TIME, event.getEndTime());
			assertEquals(EVENT_WEEKLY_REPEAT, event.getWeeklyRepeat());
			assertEquals(EVENT_DETAILS, event.getEventDetails());
		}
		
		//Test that having the start time after the end time doesn't change the values.
		try {
			event.setActivityTime(1445, 1330);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(EVENT_TITLE, event.getTitle());
			assertEquals(EVENT_MEETING_DAYS, event.getMeetingDays());
			assertEquals(EVENT_START_TIME, event.getStartTime());
			assertEquals(EVENT_END_TIME, event.getEndTime());
			assertEquals(EVENT_WEEKLY_REPEAT, event.getWeeklyRepeat());
			assertEquals(EVENT_DETAILS, event.getEventDetails());
		}
		
		//Valid set of start time
		event.setActivityTime(1350, 1445);
		assertEquals(EVENT_TITLE, event.getTitle());
		assertEquals(EVENT_MEETING_DAYS, event.getMeetingDays());
		assertEquals(1350, event.getStartTime());
		assertEquals(1445, event.getEndTime());
		assertEquals(EVENT_WEEKLY_REPEAT, event.getWeeklyRepeat());
		assertEquals(EVENT_DETAILS, event.getEventDetails());
		
	}

	/**
	 * Test Event.toString().
	 */
	@Test
	public void testToString() {
		Event event = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_WEEKLY_REPEAT, EVENT_DETAILS);
		assertEquals(EVENT_TITLE + "," + EVENT_MEETING_DAYS + "," + EVENT_START_TIME + "," + EVENT_END_TIME + "," + EVENT_WEEKLY_REPEAT + "," + EVENT_DETAILS, event.toString());
	}

	/**
	 * Test Event.equals().
	 */
	@Test
	public void testEqualsObject() {
		Activity e1 = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_WEEKLY_REPEAT, EVENT_DETAILS);
		Activity e2 = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_WEEKLY_REPEAT, EVENT_DETAILS);
		Activity e3 = new Event("Title", EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_WEEKLY_REPEAT, EVENT_DETAILS);
		Activity e4 = new Event(EVENT_TITLE, "MWF", EVENT_START_TIME, EVENT_END_TIME, EVENT_WEEKLY_REPEAT, EVENT_DETAILS);
		Activity e5 = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, 830, EVENT_END_TIME, EVENT_WEEKLY_REPEAT, EVENT_DETAILS);
		Activity e6 = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, 930, EVENT_WEEKLY_REPEAT, EVENT_DETAILS);
		Activity e7 = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, 3, EVENT_DETAILS);
		Activity e8 = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_WEEKLY_REPEAT, "Details");
		
		//Test for equality in both directions
		assertTrue(e1.equals(e2));
		assertTrue(e2.equals(e1));
		
		//Test for each of the fields
		assertFalse(e1.equals(e3));
		assertFalse(e1.equals(e4));
		assertFalse(e1.equals(e5));
		assertFalse(e1.equals(e6));
		assertTrue(e1.equals(e7)); //field not considered in equals
		assertTrue(e1.equals(e8)); //field not considered in equals
	}

	/**
	 * Test Event.hashCode().
	 */
	@Test
	public void testHashCode() {
		Activity e1 = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_WEEKLY_REPEAT, EVENT_DETAILS);
		Activity e2 = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_WEEKLY_REPEAT, EVENT_DETAILS);
		Activity e3 = new Event("Title", EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_WEEKLY_REPEAT, EVENT_DETAILS);
		Activity e4 = new Event(EVENT_TITLE, "MWF", EVENT_START_TIME, EVENT_END_TIME, EVENT_WEEKLY_REPEAT, EVENT_DETAILS);
		Activity e5 = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, 830, EVENT_END_TIME, EVENT_WEEKLY_REPEAT, EVENT_DETAILS);
		Activity e6 = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, 930, EVENT_WEEKLY_REPEAT, EVENT_DETAILS);
		Activity e7 = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, 3, EVENT_DETAILS);
		Activity e8 = new Event(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_WEEKLY_REPEAT, "Details");
		
		//Test for the same hash code for the same values
		assertEquals(e1.hashCode(), e2.hashCode());
		
		//Test for each of the fields
		assertNotEquals(e1.hashCode(), e3.hashCode());
		assertNotEquals(e1.hashCode(), e4.hashCode());
		assertNotEquals(e1.hashCode(), e5.hashCode());
		assertNotEquals(e1.hashCode(), e6.hashCode());
		assertEquals(e1.hashCode(), e7.hashCode()); //field not considered in hashCode
		assertEquals(e1.hashCode(), e8.hashCode()); //field not considered in hashCode
		
	}

}
