package models;

import java.sql.*;

import utils.DatabaseConnection;

public class Course {
	private String name;
	private int credits;

	public static void createTable() throws Exception {
		Connection conn = null;
		Statement stmt = null;
		try {
			DatabaseConnection.getInstance().connect();
			conn = DatabaseConnection.getInstance().getConnection();
			stmt = conn.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS COURSE" +
					"(NAME TEXT PRIMARY KEY     NOT NULL," +
					" CREDITS        INT    NOT NULL)";
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
			String sql = "DROP TABLE course;";
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

	public static Course create(String name, int credits) throws Exception {
		Connection conn = null;
		try {
			DatabaseConnection.getInstance().connect();
			conn = DatabaseConnection.getInstance().getConnection();
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM course WHERE NAME = '" + name + "';");
			statement.executeUpdate("INSERT INTO course VALUES ('" + name + "', '" + credits + "');");
			statement.close();
			return new Course(name, credits);
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

	public static Course find(String name) {
		Connection conn = null;
		Course course = null;
		try {
			DatabaseConnection.getInstance().connect();
			conn = DatabaseConnection.getInstance().getConnection();
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM course WHERE NAME = '" + name + "';");
			if (!result.next()) return null;
			int credits = result.getInt("CREDITS");
			DatabaseConnection.getInstance().disconnect();
			course = new Course(name, credits);
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		} 
		finally {
			try { 
				DatabaseConnection.getInstance().disconnect();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return course;
	}

	public void update() throws Exception {
		Connection conn = null;
		try {
			DatabaseConnection.getInstance().connect();
			conn = DatabaseConnection.getInstance().getConnection();
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM COURSE WHERE name = '" + name + "';");
			statement.executeUpdate("INSERT INTO course VALUES('" + name + "','" + credits + "');");
			statement.close();
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

	public Course(String name, int credits) {
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
