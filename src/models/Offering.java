package models;

public class Offering {
	private int id;
	private Course course;
	private String daysTimes;

	public Offering(int id, Course course, String daysTimesCsv) {
		this.id = id;
		this.course = course;
		this.daysTimes = daysTimesCsv;
	}
	
	public Offering(Course course, String daysTimesCsv) {
		this.course = course;
		this.daysTimes = daysTimesCsv;
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
