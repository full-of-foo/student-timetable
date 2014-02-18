package daos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import models.Course;
import utils.DatabaseConnection;

public class CourseDao {
	
	public void createTable() throws Exception {
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

	public void dropTable() throws Exception {
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

	public Course create(Course newCourse) throws Exception {
		Connection conn = null;
		try {
			DatabaseConnection.getInstance().connect();
			conn = DatabaseConnection.getInstance().getConnection();
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM course WHERE NAME = '" + newCourse.getName() + "';");
			statement.executeUpdate("INSERT INTO course VALUES ('" + newCourse.getName() + "', '" + newCourse.getCredits() + "');");
			statement.close();
			return newCourse;
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

	public Course find(String name) {
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

	public void update(Course newCourse) throws Exception {
		Connection conn = null;
		try {
			DatabaseConnection.getInstance().connect();
			conn = DatabaseConnection.getInstance().getConnection();
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM COURSE WHERE name = '" + newCourse.getName() + "';");
			statement.executeUpdate("INSERT INTO course VALUES('" + newCourse.getName() + "','" + newCourse.getCredits() + "');");
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

}
