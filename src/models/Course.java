package models;

public class Course {
	private String name;
	private int credits;

	public Course(String name, int credits) {
		this.setName(name);
		this.setCredits(credits);
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getCredits() {
		return credits;
	}
	
	public void setCredits(int credits) {
		this.credits = credits;
	}

}
