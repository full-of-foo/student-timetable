package design_pattern_assignment;
import junit.framework.TestCase;

import java.util.List;
import java.util.Collection;

import daos.CourseDao;
import daos.DAOFactory;
import daos.OfferingDao;
import daos.ScheduleDao;
import models.Course;
import models.Offering;
import models.Schedule;

public class TestSchedule extends TestCase {
	private CourseDao courseDao = DAOFactory.getCourseDao();
	private OfferingDao offeringDao = DAOFactory.getOfferingDao();
	private ScheduleDao scheduleDao = DAOFactory.getScheduleDao();
	
	public TestSchedule(String name) {
		super(name);
	}
	
	private void createTables() throws Exception {
		courseDao.createTable();
		offeringDao.createTable();
		scheduleDao.createTable();
	}
	
	private void dropTables() throws Exception {
		scheduleDao.dropTable();
		offeringDao.dropTable();
		courseDao.dropTable();
	}


	public void testMinCredits() {
		Schedule schedule = new Schedule("name");
		Collection<String> analysis = schedule.analysis();
		assertEquals(1, analysis.size());
		assertTrue(analysis.contains("Too few credits"));
	}
	
	public void testJustEnoughCredits() {
		Course cs110 = new Course("CS110", 11);
		Offering mwf10 = new Offering(1, cs110, "M10,W10,F10");
		Schedule schedule = new Schedule("name");
		schedule.add(mwf10);
		List<String> analysis = schedule.analysis();
		assertEquals(1, analysis.size());
		assertTrue(analysis.contains("Too few credits"));
		schedule = new Schedule("name");
		Course cs101 = new Course("CS101", 12);
		Offering th11 = new Offering(1, cs101, "T11,H11");
		schedule.add(th11);
		analysis = schedule.analysis();
		assertEquals(0, analysis.size());
	}

	public void testMaxCredits() {
		Course cs110 = new Course("CS110", 20);
		Offering mwf10 = new Offering(1, cs110, "M10,W10,F10");
		Schedule schedule = new Schedule("name");
		schedule.add(mwf10);
		List<String> analysis = schedule.analysis();
		assertEquals(1, analysis.size());
		assertTrue(analysis.contains("Too many credits"));
		schedule.authorizeOverload(true);
		analysis = schedule.analysis();
		assertEquals(0, analysis.size());
	}

	public void testJustBelowMax() {
		Course cs110 = new Course("CS110", 19);
		Offering mwf10 = new Offering(1, cs110, "M10,W10,F10");
		Schedule schedule = new Schedule("name");
		schedule.add(mwf10);
		List<String> analysis = schedule.analysis();
		assertEquals(1, analysis.size());
		assertTrue(analysis.contains("Too many credits"));
		schedule = new Schedule("name");
		Course cs101 = new Course("CS101", 18);
		Offering th11 = new Offering(1, cs101, "T11,H11");
		schedule.add(th11);
		analysis = schedule.analysis();
		assertEquals(0, analysis.size());
	}

	public void testDupCourses() {
		Course cs110 = new Course("CS110", 6);
		Offering mwf10 = new Offering(1, cs110, "M10,W10,F10");
		Offering th11 = new Offering(1, cs110, "T11,H11");
		Schedule schedule = new Schedule("name");
		schedule.add(mwf10);
		schedule.add(th11);
		List<String> analysis = schedule.analysis();
		assertEquals(1, analysis.size());
		assertTrue(analysis.contains("Same course twice - CS110"));
	}
	
	public void testOverlap() {
		Schedule schedule = new Schedule("name");
		Course cs110 = new Course("CS110", 6);
		Offering mwf10 = new Offering(1, cs110, "M10,W10,F10");
		schedule.add(mwf10);
		Course cs101 = new Course("CS101", 6);
		Offering mixed = new Offering(1, cs101, "M10,W11,F11");
		schedule.add(mixed);
		List<String> analysis = schedule.analysis();
		assertEquals(1, analysis.size());
		assertTrue(analysis.contains("Course overlap - M10"));
		Course cs102 = new Course("CS102", 1);
		Offering mixed2 = new Offering(1, cs102, "M9,W10,F11");
		schedule.add(mixed2);
		analysis = schedule.analysis();
		assertEquals(3, analysis.size());
		assertTrue(analysis.contains("Course overlap - M10"));
		assertTrue(analysis.contains("Course overlap - W10"));
		assertTrue(analysis.contains("Course overlap - F11"));
	}

	public void testCourseCreate() throws Exception {
		courseDao.create(new Course("CS202", 1));
		Course c = courseDao.find("CS202");
		assertEquals("CS202", c.getName());
		Course c2 = courseDao.find("Nonexistent");
		assertNull(c2);
	}

	public void testOfferingCreate() throws Exception {
		Course c = courseDao.create(new Course("CS202", 2));
		Offering offering = offeringDao.create(new Offering(c, "M10"));
		assertNotNull(offering);
	}

	public void testPersistentSchedule() throws Exception {
		scheduleDao.create(new Schedule("Bob"));
		Schedule s = scheduleDao.find("bob");
		assertNotNull(s);
	}

	public void testScheduleUpdate() throws Exception {
		dropTables();
		createTables();
		Course cs101 = courseDao.create(new Course("CS101", 3));
		Offering off1 = offeringDao.create(new Offering(cs101, "M10"));
		offeringDao.update(off1);
		Offering off2 = offeringDao.create(new Offering(cs101, "T9"));
		offeringDao.update(off2);
		Schedule s = scheduleDao.create(new Schedule("Bob"));
		s.add(off1);
		s.add(off2);
		scheduleDao.update(s);
		Schedule s2 = scheduleDao.create(new Schedule("Alice"));
		s2.add(off1);
		scheduleDao.update(s2);
		Schedule s3 =  scheduleDao.find("Bob");
		assertEquals(2, s3.schedule.size());
		Schedule s4 =  scheduleDao.find("Alice");
		assertEquals(1, s4.schedule.size());
	}
}
