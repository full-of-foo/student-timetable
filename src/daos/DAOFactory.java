package daos;

public class DAOFactory {
	
	public static CourseDao getCourseDao() {
		return new CourseDao();
	}
	
	public static OfferingDao getOfferingDao() {
		return new OfferingDao();
	}
	
	public static ScheduleDao getScheduleDao() {
		return new ScheduleDao();
	}
}
