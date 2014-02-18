package daos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import models.Course;
import utils.DatabaseConnection;

public class CourseDao extends BaseDao {
	private Course course;

	public CourseDao(String tableName) {
		super(tableName);
	}

	public void createTable() {
		try {
			DatabaseConnection.getInstance().connect();
			Connection conn = DatabaseConnection.getInstance().getConnection();
			Statement stmt = conn.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS COURSE" +
					"(NAME TEXT PRIMARY KEY     NOT NULL," +
					" CREDITS        INT    NOT NULL)";
			stmt.executeUpdate(sql);
			stmt.close();
			DatabaseConnection.getInstance().disconnect(); 
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object create(Object newObject) {
		course = (Course) newObject;
		try {
			DatabaseConnection.getInstance().connect();
			Connection conn = DatabaseConnection.getInstance().getConnection();
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM course WHERE NAME = '" + course.getName() + "';");
			statement.executeUpdate("INSERT INTO course VALUES ('" + course.getName() + "', '" + course.getCredits() + "');");
		} 
		catch (Exception e) {
			course = null;
			e.printStackTrace();
		}
		return course;
	}

	@Override
	public Object find(Object findByObject) {
		Course course = null;
		String name = (String) findByObject;
		try {
			DatabaseConnection.getInstance().connect();
			Connection conn = DatabaseConnection.getInstance().getConnection();
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM course WHERE NAME = '" + name + "';");
			if (!result.next()) return null;
			int credits = result.getInt("CREDITS");
			DatabaseConnection.getInstance().disconnect();
			course = new Course(name, credits);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return course;
	}

	@Override
	void update(Object newObject) {
		course = (Course) newObject;
		try {
			DatabaseConnection.getInstance().connect();
			Connection conn = DatabaseConnection.getInstance().getConnection();
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM COURSE WHERE name = '" + course.getName() + "';");
			statement.executeUpdate("INSERT INTO course VALUES('" + course.getName() + "','" + course.getCredits() + "');");
			statement.close();
		} 

		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
