package daos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import models.Course;
import models.Offering;
import utils.DatabaseConnection;

public class OfferingDao {
	
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


	public static Offering create(Offering newOffering) throws Exception {
		Connection conn = null;
		try {
			DatabaseConnection.getInstance().connect();
			conn = DatabaseConnection.getInstance().getConnection();
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT MAX(ID) FROM offering;");
			result.next();
			int newId = 1 + result.getInt(1);
			newOffering = new Offering(newId, newOffering.getCourse(),  newOffering.getDaysTimes());
			statement.executeUpdate("INSERT INTO offering VALUES ('"+ newId + "','" + newOffering.getCourse().getName() + "','" + newOffering.getDaysTimes() + "');");
			return newOffering;
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
			Course course = CourseDao.find(courseName);
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

	public static void update(Offering newOffering) throws Exception {
		Connection conn = null;
		try {
			DatabaseConnection.getInstance().connect();
			conn = DatabaseConnection.getInstance().getConnection();
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM Offering WHERE ID=" + newOffering.getId() + ";");
			statement.executeUpdate("INSERT INTO Offering VALUES('" + newOffering.getId() + "','" + newOffering.getCourse().getName() + "','" + newOffering.getDaysTimes() + "');");
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
