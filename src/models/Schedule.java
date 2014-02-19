package models;
import utils.ScheduleArrayList;

public class Schedule {	
	private String name;
	private int credits;
	private ScheduleArrayList scheduleList;
	private boolean permission = false;
	public static final int minCredits = 12;
	public static final int maxCredits = 18;
	
	public Schedule(String name) {
		this.setName(name);
		this.setCredits(0);
		this.setSchedule(new ScheduleArrayList(this));
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

	public ScheduleArrayList getScheduleList() {
		return scheduleList;
	}

	public void setSchedule(ScheduleArrayList scheduleList) {
		this.scheduleList = scheduleList;
	}

	public boolean isPermission() {
		return permission;
	}

	public void setPermission(boolean permission) {
		this.permission = permission;
	}

	public String toString() {
		return "Schedule " + getName() + ": " + scheduleList;
	}
}
