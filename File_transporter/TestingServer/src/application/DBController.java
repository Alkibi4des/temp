package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBController {

	private static DBController dbController = new DBController();
	private Connection conn = null;
	String jdbc_driver = "com.mysql.cj.jdbc.Driver";
	String jdbc_url = "jdbc:mysql://localhost:3306/testingdb?serverTimezone=UTC";
	String user = "root";
	String password = "test1234";
	
	private DBController() {
		try {
			Class.forName(jdbc_driver);
			conn = DriverManager.getConnection(jdbc_url, user, password);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static DBController getInstance() {
		return dbController;
	}
	
	public Connection getConnection() {
		return conn;
	}
	
	

}
