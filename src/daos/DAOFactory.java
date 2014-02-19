package daos;

/**
 * DAOFactory --- Basic factory that 
 * returns DAO subclass instances
 *   
 * @author       Anthony Troy
 */
public class DAOFactory {
	
	public static CourseDao getCourseDao() {
		return new CourseDao("course");
	}
	
	public static OfferingDao getOfferingDao() {
		return new OfferingDao("offering");
	}
	
	public static ScheduleDao getScheduleDao() {
		return new ScheduleDao("schedule");
	}
}
