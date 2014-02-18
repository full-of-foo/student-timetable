package daos;

import java.sql.Connection;
import java.sql.Statement;

import utils.DatabaseConnection;

public abstract class BaseDao {
	String tableName;

	public BaseDao(String tableName){
		this.tableName = tableName;
	}

	abstract void createTable();

	public void dropTable() {
		try {
			DatabaseConnection.getInstance().connect();
			Connection conn = DatabaseConnection.getInstance().getConnection();
			Statement stmt = conn.createStatement();
			String sql = "DROP TABLE " + tableName + ";";
			stmt.executeUpdate(sql);
			stmt.close();
			DatabaseConnection.getInstance().disconnect(); 
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	abstract Object create(Object newObject);

	abstract Object find(Object findByObject);

	abstract void update(Object newObject);



}
