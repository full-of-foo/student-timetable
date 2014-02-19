package daos;

import java.sql.Connection;
import java.sql.Statement;

import utils.DatabaseConnection;

/**
 * BaseDao --- abstract class that standardizes
 * the dropping of tables and sets out
 * abstract CRUD method definitions for all
 * DAOs to implement
 *   
 * @author       Anthony Troy
 */
public abstract class BaseDao {
	private String tableName;

	public BaseDao(String tableName){
		this.tableName = tableName;
	}

	abstract void createTable();

	public void dropTable() {
		try {
			DatabaseConnection.getInstance().connect();
			Connection conn = DatabaseConnection.getInstance().getConnection();
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DROP TABLE " + tableName + ";");
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
