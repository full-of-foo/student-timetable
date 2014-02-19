package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static DatabaseConnection instance = new DatabaseConnection();
	private static String url = "jdbc:postgresql://127.0.0.1:5432/design_patterns_test";
	private Connection conn;

	private DatabaseConnection() {}

	public static DatabaseConnection getInstance() {
		return instance;
	}

	public Connection getConnection() {
		return conn;
	}

	public void connect()  {
		try {
			if (conn != null && !conn.isClosed()) return;			
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(url, "design_patterns", "");
		} 
		catch (SQLException | ClassNotFoundException e) {
			System.out.println("Can't connect");
		}
	}

	public void disconnect() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("Can't close connnection");
			}
		}
		conn = null;
	}

}
