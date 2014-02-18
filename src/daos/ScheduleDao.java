package daos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import models.Offering;
import models.Schedule;
import utils.DatabaseConnection;

public class ScheduleDao {
	
	public static void createTable() throws Exception {
		Connection c = null;
		Statement stmt = null;
		try {
			DatabaseConnection.getInstance().connect();
			c = DatabaseConnection.getInstance().getConnection();
			stmt = c.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS SCHEDULE" +
					"(ID SERIAL PRIMARY KEY     NOT NULL," +
					"NAME     TEXT             NOT NULL," +
					"OFFERING_ID INT references OFFERING(ID))";
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
			String sql = "DROP TABLE schedule;";
			stmt.executeUpdate(sql);
			stmt.close();
			DatabaseConnection.getInstance().disconnect(); 
		} 
		finally {
			try { 
				DatabaseConnection.getInstance().disconnect(); 
			} 
			catch (Exception ignored) {}
		}
	}

	public static void deleteAll() throws Exception {
		Connection conn = null;
		try {
			DatabaseConnection.getInstance().connect();
			conn = DatabaseConnection.getInstance().getConnection();
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM schedule;");
			statement.close();
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

	public static Schedule create(Schedule newSchedule) throws Exception {
		Connection conn = null;
		try {
			DatabaseConnection.getInstance().connect();
			conn = DatabaseConnection.getInstance().getConnection();
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM schedule WHERE name = '" + newSchedule.getName() + "';");
			statement.executeUpdate("INSERT INTO schedule (NAME) VALUES ('" + newSchedule.getName() +"')");
			statement.close();
			DatabaseConnection.getInstance().disconnect(); 
			return newSchedule;
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

	public static Schedule find(String name) {
		Connection conn = null;
		Schedule schedule = null;
		try {
			DatabaseConnection.getInstance().connect();
			conn = DatabaseConnection.getInstance().getConnection();
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM schedule WHERE Name= '" + name + "';");
			schedule = new Schedule(name);
			while (result.next()) {
				int offeringId = result.getInt("OFFERING_ID");
				Offering offering = OfferingDao.find(offeringId);
				schedule.add(offering);
			}
			statement.close();
			DatabaseConnection.getInstance().disconnect(); 
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
		finally {
			try { 
				DatabaseConnection.getInstance().disconnect(); 
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return schedule;
	}

	public static Collection<Schedule> all() throws Exception {
		ArrayList<Schedule> result = new ArrayList<Schedule>();
		Connection conn = null;
		try {
			DatabaseConnection.getInstance().connect();
			conn = DatabaseConnection.getInstance().getConnection();
			Statement statement = conn.createStatement();
			ResultSet results = statement.executeQuery("SELECT DISTINCT NAME FROM schedule;");
			while (results.next())
				result.add(ScheduleDao.find(results.getString("NAME")));
		} 
		finally {
			try { 
				conn.close(); 
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static void update(Schedule newSchedule) throws Exception {
		Connection conn = null;
		try {
			DatabaseConnection.getInstance().connect();
			conn = DatabaseConnection.getInstance().getConnection();
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM schedule WHERE name = '" + newSchedule.getName() + "';");
			for (int i = 0; i < newSchedule.schedule.size(); i++) {
				Offering offering = (Offering) newSchedule.schedule.get(i);
				statement.executeUpdate("INSERT INTO schedule (NAME, OFFERING_ID) VALUES('" + newSchedule.getName() + "','" + offering.getId() + "');");
			}
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
