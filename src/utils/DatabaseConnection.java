package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static DatabaseConnection instance = new DatabaseConnection();
	private static String url = "jdbc:postgresql://127.0.0.1:5432/design_patterns_test";
	private Connection con;

	private DatabaseConnection() {}

	public static DatabaseConnection getInstance() {
		return instance;
	}

	public Connection getConnection() {
		return con;
	}

	public void connect()  {
		try {
			if (con != null && !con.isClosed()) return;			
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(url, "design_patterns", "");
		} 
		catch (SQLException | ClassNotFoundException e) {
			System.out.println("Can't connect");
		}
	}

	public void disconnect() {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				System.out.println("Can't close connection");
			}
		}
		con = null;
	}


}
