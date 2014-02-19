package design_pattern_assignment;
import java.util.Collection;
import java.util.List;

import models.Course;
import models.Offering;
import models.Schedule;

public class TestSchedule extends BaseTest {

	public void testMinCredits() {
		Schedule schedule = new Schedule("name");
		Collection<String> analysis = schedule.getScheduleList().analysis();
		
		assertEquals(1, analysis.size());
		assertTrue(analysis.contains("Too few credits"));
	}
	
	public void testJustEnoughCredits() {
		Course cs110 = new Course("CS110", 11);
		Offering mwf10 = new Offering(1, cs110, "M10,W10,F10");
		Schedule schedule = new Schedule("name");
		schedule.getScheduleList().addOffering(mwf10);
		List<String> analysis = schedule.getScheduleList().analysis();
		
		assertEquals(1, analysis.size());
		assertTrue(analysis.contains("Too few credits"));
		
		schedule = new Schedule("name");
		Course cs101 = new Course("CS101", 12);
		Offering th11 = new Offering(1, cs101, "T11,H11");
		schedule.getScheduleList().addOffering(th11);
		analysis = schedule.getScheduleList().analysis();
		
		assertEquals(0, analysis.size());
	}

	public void testMaxCredits() {
		Course cs110 = new Course("CS110", 20);
		Offering mwf10 = new Offering(1, cs110, "M10,W10,F10");
		Schedule schedule = new Schedule("name");
		schedule.getScheduleList().addOffering(mwf10);
		List<String> analysis = schedule.getScheduleList().analysis();
		
		assertEquals(1, analysis.size());
		assertTrue(analysis.contains("Too many credits"));
		
		schedule.setPermission(true);
		analysis = schedule.getScheduleList().analysis();
		
		assertEquals(0, analysis.size());
	}

	public void testJustBelowMax() {
		Course cs110 = new Course("CS110", 19);
		Offering mwf10 = new Offering(1, cs110, "M10,W10,F10");
		Schedule schedule = new Schedule("name");
		schedule.getScheduleList().addOffering(mwf10);
		List<String> analysis = schedule.getScheduleList().analysis();
		
		assertEquals(1, analysis.size());
		assertTrue(analysis.contains("Too many credits"));
		
		schedule = new Schedule("name");
		Course cs101 = new Course("CS101", 18);
		Offering th11 = new Offering(1, cs101, "T11,H11");
		schedule.getScheduleList().addOffering(th11);
		analysis = schedule.getScheduleList().analysis();
		
		assertEquals(0, analysis.size());
	}

	public void testDupCourses() {
		Course cs110 = new Course("CS110", 6);
		Offering mwf10 = new Offering(1, cs110, "M10,W10,F10");
		Offering th11 = new Offering(1, cs110, "T11,H11");
		Schedule schedule = new Schedule("name");
		schedule.getScheduleList().addOffering(mwf10);
		schedule.getScheduleList().addOffering(th11);
		List<String> analysis = schedule.getScheduleList().analysis();
		
		assertEquals(1, analysis.size());
		assertTrue(analysis.contains("Same course twice - CS110"));
	}
	
	public void testOverlap() {
		Schedule schedule = new Schedule("name");
		Course cs110 = new Course("CS110", 6);
		Offering mwf10 = new Offering(1, cs110, "M10,W10,F10");
		schedule.getScheduleList().addOffering(mwf10);
		Course cs101 = new Course("CS101", 6);
		Offering mixed = new Offering(1, cs101, "M10,W11,F11");
		schedule.getScheduleList().addOffering(mixed);
		List<String> analysis = schedule.getScheduleList().analysis();
		
		assertEquals(1, analysis.size());
		assertTrue(analysis.contains("Course overlap - M10"));
		
		Course cs102 = new Course("CS102", 1);
		Offering mixed2 = new Offering(1, cs102, "M9,W10,F11");
		schedule.getScheduleList().addOffering(mixed2);
		analysis = schedule.getScheduleList().analysis();
		
		assertEquals(3, analysis.size());
		assertTrue(analysis.contains("Course overlap - M10"));
		assertTrue(analysis.contains("Course overlap - W10"));
		assertTrue(analysis.contains("Course overlap - F11"));
	}

	public void testCourseCreate() {
		courseDao.create(new Course("CS202", 1));
		Course c = (Course) courseDao.find("CS202");
		assertEquals("CS202", c.getName());
		
		Course c2 = (Course) courseDao.find("Nonexistent");
		assertNull(c2);
	}

	public void testOfferingCreate() {
		Course c = (Course) courseDao.create(new Course("CS202", 2));
		Offering offering = (Offering) offeringDao.create(new Offering(c, "M10"));
		
		assertNotNull(offering);
	}

	public void testPersistentSchedule() {
		scheduleDao.create(new Schedule("Bob"));
		Schedule s = (Schedule) scheduleDao.find("bob");
		
		assertNotNull(s);
	}

	public void testScheduleUpdate() {
		beforeCase();

		Course cs101 = (Course) courseDao.create(new Course("CS101", 3));
		Offering off1 = (Offering) offeringDao.create(new Offering(cs101, "M10"));
		offeringDao.update(off1);
		Offering off2 = (Offering) offeringDao.create(new Offering(cs101, "T9"));
		offeringDao.update(off2);
		Schedule s = (Schedule) scheduleDao.create(new Schedule("Bob"));
		s.getScheduleList().addOffering(off1);
		s.getScheduleList().addOffering(off2);
		scheduleDao.update(s);
		Schedule s2 = (Schedule) scheduleDao.create(new Schedule("Alice"));
		s2.getScheduleList().addOffering(off1);
		scheduleDao.update(s2);
		Schedule s3 =  (Schedule) scheduleDao.find("Bob");
		assertEquals(2, s3.getScheduleList().size());
		
		Schedule s4 =  (Schedule) scheduleDao.find("Alice");
		assertEquals(1, s4.getScheduleList().size());
	}
}
