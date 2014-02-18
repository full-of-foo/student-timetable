package daos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import models.Course;
import models.Offering;
import utils.DatabaseConnection;

public class OfferingDao extends BaseDao {
	private Offering offering;

	public OfferingDao(String tableName) {
		super(tableName);
	}

	@Override
	public void createTable() {
		try {
			DatabaseConnection.getInstance().connect();
			Connection conn = DatabaseConnection.getInstance().getConnection();
			Statement stmt = conn.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS OFFERING" +
					"(ID INT  PRIMARY KEY     NOT NULL," +
					"COURSE_NAME          TEXT    references COURSE(NAME)," +
					"DAYSTIMES        TEXT    NOT NULL)";
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
		offering = (Offering) newObject;
		try {
			DatabaseConnection.getInstance().connect();
			Connection conn = DatabaseConnection.getInstance().getConnection();
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT MAX(ID) FROM offering;");
			result.next();
			int newId = 1 + result.getInt(1);
			offering = new Offering(newId, offering.getCourse(),  offering.getDaysTimes());
			statement.executeUpdate("INSERT INTO offering VALUES ('"+ newId + "','" + offering.getCourse().getName() + "','" + offering.getDaysTimes() + "');");
		} 
		catch (Exception e) {
			newObject = null;
			e.printStackTrace();
		}
		return offering;
	}

	@Override
	public Object find(Object findByObject) {
		int id = (Integer) findByObject;
		try {
			DatabaseConnection.getInstance().connect();
			Connection conn = DatabaseConnection.getInstance().getConnection();
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM offering WHERE ID =" + id + ";");
			if (result.next() == false)
				return null;
			String courseName = result.getString("COURSE_NAME");
			Course course = (Course) DAOFactory.getCourseDao().find(courseName);
			String dateTime = result.getString("DAYSTIMES");
			DatabaseConnection.getInstance().disconnect();
			offering = new Offering(id, course, dateTime);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return offering;
	}

	@Override
	public void update(Object newObject) {
		offering = (Offering) newObject;
		try {
			DatabaseConnection.getInstance().connect();
			Connection conn = DatabaseConnection.getInstance().getConnection();
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM Offering WHERE ID=" + offering.getId() + ";");
			statement.executeUpdate("INSERT INTO Offering VALUES('" + offering.getId() + "','" + offering.getCourse().getName() + "','" + offering.getDaysTimes() + "');");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
