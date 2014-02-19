package utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import models.Course;
import models.Offering;
import models.Schedule;

@SuppressWarnings("serial")
public class ScheduleArrayList extends ArrayList<Offering> {
	private Schedule schedule;

	public ScheduleArrayList(Schedule schedule) {
		this.setSchedule(schedule);
	}
	
	public void addOffering(Offering offering){
		this.schedule.setCredits(this.schedule.getCredits() + offering.getCourse().getCredits()); 
		this.add(offering);
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	
	public List<String> analysis() {
		ArrayList<String> result = new ArrayList<String>();
		if (this.schedule.getCredits() < this.schedule.minCredits)
			result.add("Too few credits");
		if (this.schedule.getCredits() > this.schedule.maxCredits && !this.schedule.isPermission())
			result.add("Too many credits");
		this.checkDuplicateCourses(result);
		this.checkOverlap(result);
		return result;
	}
	
    // does nothing?	
	public void checkDuplicateCourses(ArrayList<String> analysis) {
		HashSet<Course> courses = new HashSet<Course>();
		for (int i = 0; i < this.size(); i++) {
			Course course = ((Offering) this.get(i)).getCourse();
			if (courses.contains(course))
				analysis.add("Same course twice - " + course.getName());
			courses.add(course);
		}
	}
	
	// does nothing?	
	public void checkOverlap(ArrayList<String> analysis) {
		HashSet<String> times = new HashSet<String>();
		for (Iterator<Offering> iterator = this.iterator(); iterator.hasNext();) {
			Offering offering = (Offering) iterator.next();
			String daysTimes = offering.getDaysTimes();
			StringTokenizer tokens = new StringTokenizer(daysTimes, ",");
			while (tokens.hasMoreTokens()) {
				String dayTime = tokens.nextToken();
				if (times.contains(dayTime))
					analysis.add("Course overlap - " + dayTime);
				times.add(dayTime);
			}
		}
	}

	

}
