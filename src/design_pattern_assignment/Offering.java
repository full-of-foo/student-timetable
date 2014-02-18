package design_pattern_assignment;
import java.sql.*;

public class Offering {
	private int id;
	private Course course;
	private String daysTimes;
	static String url = "jdbc:postgresql://127.0.0.1:5432/design_patterns_test";
	static { 
		try { 
			Class.forName("org.postgresql.Driver"); 
			}
		catch (Exception ignored) {} 
	}
	
	public static void createTable() throws Exception {
		Connection c = null;
	    Statement stmt = null;
		try {
			c = DriverManager.getConnection(url, "design_patterns", "");
	        stmt = c.createStatement();
	         String sql = "CREATE TABLE IF NOT EXISTS OFFERING" +
	                      "(ID INT  PRIMARY KEY     NOT NULL," +
	                      "COURSE_NAME          TEXT    references COURSE(NAME)," +
	                      "DAYSTIMES        TEXT    NOT NULL)";
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
	         String sql = "DROP TABLE offering;";
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

	
	public static Offering create(Course course, String daysTimesCsv) throws Exception {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, "design_patterns", "");
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT MAX(ID) FROM offering;");
			result.next();
			int newId = 1 + result.getInt(1);
			statement.executeUpdate("INSERT INTO offering VALUES ('"+ newId + "','" + course.getName() + "','" + daysTimesCsv + "');");
			return new Offering(newId, course, daysTimesCsv);
		} 
		finally {
			try { 
				conn.close(); 
				} 
			catch (Exception ignored) {}
		}
	}

	public static Offering find(int id) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, "design_patterns", "");
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM offering WHERE ID =" + id + ";");
			if (result.next() == false)
				return null;
			String courseName = result.getString("COURSE_NAME");
			Course course = Course.find(courseName);
			String dateTime = result.getString("DAYSTIMES");
			conn.close();
			return new Offering(id, course, dateTime);
		} 
		catch (Exception ex) {
			try { 
				conn.close(); 
			} catch (Exception ignored) {}
			return null;
		}
	}
	
	public void update() throws Exception {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, "design_patterns", "");
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM Offering WHERE ID=" + id + ";");
			statement.executeUpdate("INSERT INTO Offering VALUES('" + id + "','" + course.getName() + "','" + daysTimes + "');");
		} 
		finally {
			try { 
				conn.close(); 
			} 
			catch (Exception ignored) {}
		}
	}

	public Offering(int id, Course course, String daysTimesCsv) {
		this.id = id;
		this.course = course;
		this.daysTimes = daysTimesCsv;
	}

	public int getId() {
		return id;
	}

	public Course getCourse() {
		return course;
	}

	public String getDaysTimes() {
		return daysTimes;
	}

	public String toString() {
		return "Offering " + getId() + ": " + getCourse() + " meeting " + getDaysTimes();
	}
}
