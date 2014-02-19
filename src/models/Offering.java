package models;

/**
 * Offering --- POJO that represents an offering entity.
 * 
 * @author      Anthony Troy
 */
public class Offering {
	private int id;
	private Course course;
	private String daysTimes;

	/**
	 * Persistent Offering entity
	 */
	public Offering(int id, Course course, String daysTimes) {
		this.setId(id);;
		this.setCourse(course);
		this.setDaysTimes(daysTimes);
	}

	/**
	 * Non-persistent Offering entity
	 */
	public Offering(Course course, String daysTimes) {
		this.course = course;
		this.daysTimes = daysTimes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getDaysTimes() {
		return daysTimes;
	}

	public void setDaysTimes(String daysTimes) {
		this.daysTimes = daysTimes;
	}

	public String toString() {
		return "Offering " + getId() + ": " + getCourse() + " meeting " + getDaysTimes();
	}
}
