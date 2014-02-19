package design_pattern_assignment;

import junit.framework.TestCase;
import models.Course;
import models.Offering;
import models.Report;
import models.Schedule;
import views.ReportView;
import controllers.ReportController;
import daos.CourseDao;
import daos.DAOFactory;
import daos.OfferingDao;
import daos.ScheduleDao;

public class TestMvc extends TestCase {
	private CourseDao courseDao = DAOFactory.getCourseDao();
	private OfferingDao offeringDao = DAOFactory.getOfferingDao();
	private ScheduleDao scheduleDao = DAOFactory.getScheduleDao();

	public TestMvc(String name) { 
		super(name); 
	}
	
	public void createTables() {
		courseDao.createTable();
		offeringDao.createTable();
		scheduleDao.createTable();
	}
	
	public void dropTables() {
		scheduleDao.dropTable();
		offeringDao.dropTable();
		courseDao.dropTable();
	}
	
	public void testMvcFullReport() {
		dropTables();
		createTables();
		
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
		
		
		ReportView view = new ReportView();
		Report report = new Report(new StringBuffer());
		new ReportController(view, report);
		
		assertEquals("",view.getReportLabel().getText());
		
		view.getGenerateReportBtn().doClick();

		String text = view.getReportLabel().getText();
		String valid = "CS101 T9\n\tBob\n" + "CS101 M10\n\tBob\n\tAlice\n" + "Number of scheduled offerings: 2\n";
		assertTrue(text.equals(valid));
	}
	
	public void testMvcEmptyReport() {
		dropTables();
		createTables();
		
		ReportView view = new ReportView();
		Report report = new Report(new StringBuffer());
		new ReportController(view, report);
		
		assertEquals("",view.getReportLabel().getText());
		
		view.getGenerateReportBtn().doClick();
		String text = view.getReportLabel().getText();
		String valid = "Number of scheduled offerings: 0\n";
		assertTrue(text.equals(valid) || text.equals(valid));
	}
	
	
	
}
