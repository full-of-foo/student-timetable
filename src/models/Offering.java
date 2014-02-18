package models;
import java.sql.*;

import utils.DatabaseConnection;

public class Offering {
	private int id;
	private Course course;
	private String daysTimes;

	public static void createTable() throws Exception {
		Connection conn = null;
		Statement stmt = null;
		try {
			DatabaseConnection.getInstance().connect();
			conn = DatabaseConnection.getInstance().getConnection();
			stmt = conn.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS OFFERING" +
					"(ID INT  PRIMARY KEY     NOT NULL," +
					"COURSE_NAME          TEXT    references COURSE(NAME)," +
					"DAYSTIMES        TEXT    NOT NULL)";
			stmt.executeUpdate(sql);
			stmt.close();
			DatabaseConnection.getInstance().disconnect(); 
		} 
		finally {
			try { 
				DatabaseConnection.getInstance().disconnect();  
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void dropTable() throws Exception {
		Connection conn = null;
		Statement stmt = null;
		try {
			DatabaseConnection.getInstance().connect();
			conn = DatabaseConnection.getInstance().getConnection();
			stmt = conn.createStatement();
			String sql = "DROP TABLE offering;";
			stmt.executeUpdate(sql);
			stmt.close();
			DatabaseConnection.getInstance().disconnect(); 
		} 
		finally {
			try { 
				DatabaseConnection.getInstance().disconnect();  
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	public static Offering create(Course course, String daysTimesCsv) throws Exception {
		Connection conn = null;
		try {
			DatabaseConnection.getInstance().connect();
			conn = DatabaseConnection.getInstance().getConnection();
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT MAX(ID) FROM offering;");
			result.next();
			int newId = 1 + result.getInt(1);
			statement.executeUpdate("INSERT INTO offering VALUES ('"+ newId + "','" + course.getName() + "','" + daysTimesCsv + "');");
			return new Offering(newId, course, daysTimesCsv);
		} 
		finally {
			try { 
				DatabaseConnection.getInstance().disconnect(); 
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static Offering find(int id) {
		Connection conn = null;
		Offering offering = null;
		try {
			DatabaseConnection.getInstance().connect();
			conn = DatabaseConnection.getInstance().getConnection();
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM offering WHERE ID =" + id + ";");
			if (result.next() == false)
				return null;
			String courseName = result.getString("COURSE_NAME");
			Course course = Course.find(courseName);
			String dateTime = result.getString("DAYSTIMES");
			DatabaseConnection.getInstance().disconnect();
			offering = new Offering(id, course, dateTime);
		} 
		catch (Exception ex) {
			try { 
				DatabaseConnection.getInstance().disconnect(); 
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return offering;
	}

	public void update() throws Exception {
		Connection conn = null;
		try {
			DatabaseConnection.getInstance().connect();
			conn = DatabaseConnection.getInstance().getConnection();
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM Offering WHERE ID=" + id + ";");
			statement.executeUpdate("INSERT INTO Offering VALUES('" + id + "','" + course.getName() + "','" + daysTimes + "');");
		} 
		finally {
			try { 
				DatabaseConnection.getInstance().disconnect(); 
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
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
