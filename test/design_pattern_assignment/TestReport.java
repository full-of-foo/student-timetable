package design_pattern_assignment;
import daos.CourseDao;
import daos.DAOFactory;
import daos.OfferingDao;
import daos.ScheduleDao;
import models.Course;
import models.Offering;
import models.Schedule;
import junit.framework.TestCase;

public class TestReport extends TestCase {
	private CourseDao courseDao = DAOFactory.getCourseDao();
	private OfferingDao offeringDao = DAOFactory.getOfferingDao();
	private ScheduleDao scheduleDao = DAOFactory.getScheduleDao();

	public TestReport(String name) { 
		super(name); 
	}
	
	public void createTables() throws Exception {
		courseDao.createTable();
		offeringDao.createTable();
		scheduleDao.createTable();
	}
	
	public void dropTables() throws Exception {
		scheduleDao.dropTable();
		offeringDao.dropTable();
		courseDao.dropTable();
	}

	
	public void testEmptyReport() throws Exception {
		dropTables();
		createTables();
		scheduleDao.deleteAll();
		Report report = new Report();
		StringBuffer buffer = new StringBuffer();
		report.write(buffer);
		assertEquals("Number of scheduled offerings: 0\n", buffer.toString());
	}
	
	public void testReport() throws Exception {
		dropTables();
		createTables();
		scheduleDao.deleteAll();
		Course cs101 = (Course) courseDao.create(new Course("CS101", 3));
		Offering off1 = (Offering) offeringDao.create(new Offering(cs101, "M10"));
		offeringDao.update(off1);
		Offering off2 = (Offering) offeringDao.create(new Offering(cs101, "T9"));
		offeringDao.update(off2);
		Schedule s = (Schedule) scheduleDao.create(new Schedule("Bob"));
		s.add(off1);
		s.add(off2);
		scheduleDao.update(s);
		Schedule s2 = (Schedule) scheduleDao.create(new Schedule("Alice"));
		s2.add(off1);
		scheduleDao.update(s2);
		Report report = new Report();
		StringBuffer buffer = new StringBuffer();
		report.write(buffer);
		String result = buffer.toString();
		String valid1 = "CS101 M10\n\tAlice\n\tBob\n" + "CS101 T9\n\tBob\n" + "Number of scheduled offerings: 2\n";
		String valid2 = "CS101 T9\n\tBob\n" + "CS101 M10\n\tBob\n\tAlice\n" + "Number of scheduled offerings: 2\n";
		assertTrue(result.equals(valid1) || result.equals(valid2));
	}

}
