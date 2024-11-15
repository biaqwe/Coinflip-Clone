package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConn {
	private static final String url="jdbc:mysql://localhost:3306/coinflip";
	private static final String user="root";
	private static final String password="pass";
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}
}
