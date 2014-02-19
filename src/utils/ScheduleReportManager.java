package utils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import models.Offering;
import models.Schedule;
import daos.DAOFactory;

/**
 * ScheduleReportManager --- manager utility
 * class responsible for generating schedule
 * report data in a Hashtable
 *   
 * @author       Anthony Troy
 */
public class ScheduleReportManager {

	public static Hashtable<Integer, ArrayList<String>> getPopulatedOfferingToName() {
		Hashtable<Integer, ArrayList<String>> offeringToName = new Hashtable<Integer, ArrayList<String>>();
		return getPopulatedReportMap(offeringToName);
	}

	//helper method
	private static Hashtable<Integer, ArrayList<String>> getPopulatedReportMap(Hashtable<Integer, ArrayList<String>> offeringToName) {
		Collection<Schedule> schedules = DAOFactory.getScheduleDao().all();
		for (Iterator<Schedule> eachSchedule = schedules.iterator(); eachSchedule.hasNext();) {
			Schedule schedule = (Schedule) eachSchedule.next();
			for (Iterator<Offering> each = schedule.getScheduleList().iterator(); each.hasNext(); ) {
				Offering offering = (Offering) each.next();
				populateMapFor(offeringToName, schedule, offering);
			}
		}
		return offeringToName;
	}
	
	//helper method
	private static void populateMapFor(Hashtable<Integer, ArrayList<String>> map, Schedule schedule, Offering offering) {
		ArrayList<String> list = (ArrayList<String>) map.get(new Integer(offering.getId()));
		if (list == null) {
			list = new ArrayList<String>();
			map.put(new Integer(offering.getId()), list);
		}
		list.add(schedule.getName());
	}
}
