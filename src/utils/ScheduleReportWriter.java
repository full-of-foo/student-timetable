package utils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import models.Offering;
import daos.DAOFactory;

/**
 * ScheduleReportWritter --- writer utility
 * class responsible for writing schedule
 * report data to buffers
 *   
 * @author       Anthony Troy
 */
public class ScheduleReportWriter {
	
	public static StringBuffer writeReport(StringBuffer buffer) {
		Hashtable<Integer, ArrayList<String>> offeringToName = ScheduleReportManager.getPopulatedOfferingToName();
		Enumeration<Integer> enumeration = (Enumeration<Integer>) offeringToName.keys();
		
		while (enumeration.hasMoreElements()) {
			Integer offeringId = (Integer)enumeration.nextElement();
			ArrayList<String> list = (ArrayList<String>)offeringToName.get(offeringId);
			ScheduleReportWriter.writeOffering(buffer, list, (Offering) DAOFactory.getOfferingDao().find(offeringId.intValue()));
		}
		
		buffer.append("Number of scheduled offerings: ");
		buffer.append(offeringToName.size());
		buffer.append("\n");
		return buffer;
	}
	
    //helper method
	private static void writeOffering(StringBuffer buffer, ArrayList<String> list, Offering offering) {
		buffer.append(offering.getCourse().getName() + " " + offering.getDaysTimes() + "\n");
		for (Iterator<String> iterator = list.iterator(); iterator.hasNext();) {
			String s = (String) iterator.next();
			buffer.append("\t" + s + "\n");
		}
	}


}
