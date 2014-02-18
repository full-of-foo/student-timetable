package design_pattern_assignment;
import daos.CourseDao;
import daos.OfferingDao;
import daos.ScheduleDao;
import models.Course;
import models.Offering;
import models.Schedule;
import junit.framework.TestCase;

public class TestReport extends TestCase {

	public TestReport(String name) { 
		super(name); 
	}
	
	private void createTables() throws Exception {
		CourseDao.createTable();
		OfferingDao.createTable();
		ScheduleDao.createTable();
	}
	
	private void dropTables() throws Exception {
		ScheduleDao.dropTable();
		OfferingDao.dropTable();
		CourseDao.dropTable();
	}

	
	public void testEmptyReport() throws Exception {
		dropTables();
		createTables();
		ScheduleDao.deleteAll();
		Report report = new Report();
		StringBuffer buffer = new StringBuffer();
		report.write(buffer);
		assertEquals("Number of scheduled offerings: 0\n", buffer.toString());
	}
	
	public void testReport() throws Exception {
		dropTables();
		createTables();
		ScheduleDao.deleteAll();
		Course cs101 = CourseDao.create(new Course("CS101", 3));
		Offering off1 = OfferingDao.create(new Offering(cs101, "M10"));
		OfferingDao.update(off1);
		Offering off2 = OfferingDao.create(new Offering(cs101, "T9"));
		OfferingDao.update(off2);
		Schedule s = ScheduleDao.create(new Schedule("Bob"));
		s.add(off1);
		s.add(off2);
		ScheduleDao.update(s);
		Schedule s2 = ScheduleDao.create(new Schedule("Alice"));
		s2.add(off1);
		ScheduleDao.update(s2);
		Report report = new Report();
		StringBuffer buffer = new StringBuffer();
		report.write(buffer);
		String result = buffer.toString();
		String valid1 = "CS101 M10\n\tAlice\n\tBob\n" + "CS101 T9\n\tBob\n" + "Number of scheduled offerings: 2\n";
		String valid2 = "CS101 T9\n\tBob\n" + "CS101 M10\n\tBob\n\tAlice\n" + "Number of scheduled offerings: 2\n";
		assertTrue(result.equals(valid1) || result.equals(valid2));
	}

}
