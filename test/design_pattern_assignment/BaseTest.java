package design_pattern_assignment;

import models.Course;
import models.Offering;
import models.Schedule;
import daos.CourseDao;
import daos.DAOFactory;
import daos.OfferingDao;
import daos.ScheduleDao;
import junit.framework.TestCase;

/**
 * BaseTest --- test superclass
 * to share variables global to 
 * tests and before/after
 * test hooks
 * 
 * @author       Anthony Troy
 */
public class BaseTest extends TestCase {
	protected CourseDao courseDao = DAOFactory.getCourseDao();
	protected OfferingDao offeringDao = DAOFactory.getOfferingDao();
	protected ScheduleDao scheduleDao = DAOFactory.getScheduleDao();
	
	protected void beforeCase(boolean isSeeded) {
		beforeCase();
		if(isSeeded) seedData();
	}
	
	protected void beforeCase() {
		dropTables();
		createTables();
	}
	
	private void createTables() {
		courseDao.createTable();
		offeringDao.createTable();
		scheduleDao.createTable();
	}
	
	private void dropTables() {
		scheduleDao.dropTable();
		offeringDao.dropTable();
		courseDao.dropTable();
	}
	
	private void seedData() {
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
	}
	
	public void testDummy(){
		//dummy fall through test to be a valid TestClass child
	}
	
	
}
