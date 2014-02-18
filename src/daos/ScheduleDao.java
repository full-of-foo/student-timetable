package daos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import models.Offering;
import models.Schedule;
import utils.DatabaseConnection;

public class ScheduleDao extends BaseDao {
	private Schedule schedule;

	public ScheduleDao(String tableName) {
		super(tableName);
	}

	@Override
	public void createTable() {
		try {
			DatabaseConnection.getInstance().connect();
			Connection c = DatabaseConnection.getInstance().getConnection();
			Statement stmt = c.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS SCHEDULE" +
					"(ID SERIAL PRIMARY KEY     NOT NULL," +
					"NAME     TEXT             NOT NULL," +
					"OFFERING_ID INT references OFFERING(ID))";
			stmt.executeUpdate(sql);
			stmt.close();
			DatabaseConnection.getInstance().disconnect();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteAll() {
		try {
			DatabaseConnection.getInstance().connect();
			Connection conn = DatabaseConnection.getInstance().getConnection();
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM schedule;");
			statement.close();
			DatabaseConnection.getInstance().disconnect(); 
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Collection<Schedule> all() {
		ArrayList<Schedule> result = new ArrayList<Schedule>();
		Connection conn = null;
		try {
			DatabaseConnection.getInstance().connect();
			conn = DatabaseConnection.getInstance().getConnection();
			Statement statement = conn.createStatement();
			ResultSet results = statement.executeQuery("SELECT DISTINCT NAME FROM schedule;");
			while (results.next())
				result.add((Schedule) this.find(results.getString("NAME")));
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Object create(Object newObject) {
		schedule = (Schedule) newObject;	
		try {
			DatabaseConnection.getInstance().connect();
			Connection conn = DatabaseConnection.getInstance().getConnection();
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM schedule WHERE name = '" + schedule.getName() + "';");
			statement.executeUpdate("INSERT INTO schedule (NAME) VALUES ('" + schedule.getName() +"')");
			statement.close();
			DatabaseConnection.getInstance().disconnect(); 
		} 
		catch (Exception e) {
			schedule = null;
			e.printStackTrace();
		}
		return schedule;
	}

	@Override
	public Object find(Object findByObject) {
		String name = (String) findByObject;
		try {
			DatabaseConnection.getInstance().connect();
			Connection conn = DatabaseConnection.getInstance().getConnection();
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM schedule WHERE Name= '" + name + "';");
			schedule = new Schedule(name);
			while (result.next()) {
				int offeringId = result.getInt("OFFERING_ID");
				Offering offering = (Offering) DAOFactory.getOfferingDao().find(offeringId);
				schedule.getScheduleList().addOffering(offering);
			}
			statement.close();
			DatabaseConnection.getInstance().disconnect(); 
		} 
		catch (Exception e) {
			schedule = null;
			e.printStackTrace();
		} 
		return schedule;
	}

	@Override
	public void update(Object newObject) {
		schedule = (Schedule) newObject;
		try {
			DatabaseConnection.getInstance().connect();
			Connection conn = DatabaseConnection.getInstance().getConnection();
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM schedule WHERE name = '" + schedule.getName() + "';");
			for (int i = 0; i < schedule.getScheduleList().size(); i++) {
				Offering offering = (Offering) schedule.getScheduleList().get(i);
				statement.executeUpdate("INSERT INTO schedule (NAME, OFFERING_ID) VALUES('" + schedule.getName() + "','" + offering.getId() + "');");
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
