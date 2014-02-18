package design_pattern_assignment;
import java.util.*;
import java.sql.*;

public class Schedule {
	
	String name;
	int credits = 0;
	static final int minCredits = 12;
	static final int maxCredits = 18;
	boolean permission = false;
	
	ArrayList<Offering> schedule = new ArrayList<Offering>();
	
	static String url = "jdbc:postgresql://127.0.0.1:5432/design_patterns_test";
	static { 
		try { 
			Class.forName("org.postgresql.Driver"); 
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static void createTable() throws Exception {
		Connection c = null;
	    Statement stmt = null;
		try {
			c = DriverManager.getConnection(url, "design_patterns", "");
	        stmt = c.createStatement();
	         String sql = "CREATE TABLE IF NOT EXISTS SCHEDULE" +
	                      "(ID SERIAL PRIMARY KEY     NOT NULL," +
	                      "NAME     TEXT             NOT NULL," +
	                      "OFFERING_ID INT references OFFERING(ID))";
	         stmt.executeUpdate(sql);
	         stmt.close();
	         c.close();
		} 
		finally {
			try { 
				c.close(); 
			} 
			catch (Exception ignored) {}
		}
	}
	
	public static void dropTable() throws Exception {
		Connection c = null;
	    Statement stmt = null;
		try {
			c = DriverManager.getConnection(url, "design_patterns", "");
	        stmt = c.createStatement();
	         String sql = "DROP TABLE schedule;";
	         stmt.executeUpdate(sql);
	         stmt.close();
	         c.close();
		} 
		finally {
			try { 
				c.close(); 
			} 
			catch (Exception ignored) {}
		}
	}

	public static void deleteAll() throws Exception {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, "design_patterns", "");
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM schedule;");
		} 
		finally {
			try { 
				conn.close(); 
			} 
			catch (Exception ignored) {}
		}
	}
	
	public static Schedule create(String name) throws Exception {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, "design_patterns", "");
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM schedule WHERE name = '" + name + "';");
			statement.executeUpdate("INSERT INTO schedule (NAME) VALUES ('" + name +"')");
			return new Schedule(name);
		} 
		finally {
			try { 
				conn.close(); 
			} 
			catch (Exception ignored) {}
		}
	}
	
	public static Schedule find(String name) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, "design_patterns", "");
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM schedule WHERE Name= '" + name + "';");
			Schedule schedule = new Schedule(name);
			while (result.next()) {
				int offeringId = result.getInt("OFFERING_ID");
				Offering offering = Offering.find(offeringId);
				schedule.add(offering);
			}
			return schedule;
		} 
		catch (Exception ex) {
			return null;
		} 
		finally {
			try { 
				conn.close(); 
			} 
			catch (Exception ignored) {}
		}
	}

	public static Collection<Schedule> all() throws Exception {
		ArrayList<Schedule> result = new ArrayList<Schedule>();
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, "design_patterns", "");
			Statement statement = conn.createStatement();
			ResultSet results = statement.executeQuery("SELECT DISTINCT NAME FROM schedule;");
			while (results.next())
			result.add(Schedule.find(results.getString("NAME")));
		} 
		finally {
			try { 
				conn.close(); 
			} 
			catch (Exception ignored) {}
		}
		return result;
	}

	public void update() throws Exception {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, "design_patterns", "");
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM schedule WHERE name = '" + name + "';");
			for (int i = 0; i < schedule.size(); i++) {
				Offering offering = (Offering) schedule.get(i);
				statement.executeUpdate("INSERT INTO schedule (NAME, OFFERING_ID) VALUES('" + name + "','" + offering.getId() + "');");
			}
		} 
		finally {
			try { 
				conn.close(); 
			} 
			catch (Exception ignored) {}
		}
	}

	public Schedule(String name) {
		this.name = name;
	}

	public void add(Offering offering) {
		credits += offering.getCourse().getCredits();
		schedule.add(offering);
	}

	public void authorizeOverload(boolean authorized) {
		permission = authorized;
	}

	public List<String> analysis() {
		ArrayList<String> result = new ArrayList<String>();
		if (credits < minCredits)
			result.add("Too few credits");
		if (credits > maxCredits && !permission)
			result.add("Too many credits");
		checkDuplicateCourses(result);
		checkOverlap(result);
		return result;
	}

	public void checkDuplicateCourses(ArrayList<String> analysis) {
		HashSet<Course> courses = new HashSet<Course>();
		for (int i = 0; i < schedule.size(); i++) {
			Course course = ((Offering) schedule.get(i)).getCourse();
			if (courses.contains(course))
				analysis.add("Same course twice - " + course.getName());
			courses.add(course);
		}
	}

	public void checkOverlap(ArrayList<String> analysis) {
		HashSet<String> times = new HashSet<String>();
		for (Iterator<Offering> iterator = schedule.iterator(); iterator.hasNext();) {
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

	public String toString() {
		return "Schedule " + name + ": " + schedule;
	}
}
