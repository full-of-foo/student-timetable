package design_pattern_assignment;

import java.sql.*;

public class Course {
	private String name;
	private int credits;
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
	         String sql = "CREATE TABLE IF NOT EXISTS COURSE" +
	                      "(NAME TEXT PRIMARY KEY     NOT NULL," +
	                      " CREDITS        INT    NOT NULL)";
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
	         String sql = "DROP TABLE course;";
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

	public static Course create(String name, int credits) throws Exception {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, "design_patterns", "");
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM course WHERE NAME = '" + name + "';");
			statement.executeUpdate("INSERT INTO course VALUES ('" + name + "', '" + credits + "');");
			return new Course(name, credits);
		} 
		finally {
			try { 
				conn.close(); 
			} 
			catch (Exception ignored) {}
		}
	}

	public static Course find(String name) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, "design_patterns", "");
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM course WHERE NAME = '" + name + "';");
			if (!result.next()) return null;
			int credits = result.getInt("CREDITS");
			return new Course(name, credits);
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
	
	public void update() throws Exception {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, "design_patterns", "");
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM COURSE WHERE name = '" + name + "';");
			statement.executeUpdate("INSERT INTO course VALUES('" + name + "','" + credits + "');");
		} 
		finally {
			try { 
				conn.close(); 
			} 
			catch (Exception ignored) {}
		}
	}

	Course(String name, int credits) {
		this.name = name;
		this.credits = credits;
	}
	
	public int getCredits() {
		return credits;
	}

	public String getName() {
		return name;
	}
}
