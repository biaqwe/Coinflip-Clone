package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Clasa pentru conexiunea la baza de date
 */
public class DatabaseConn {
	/**
	 * URL ul bazei de date, adresa serverului si numele bazei de date
	 */
	private static final String url="jdbc:mysql://localhost:3306/coinflip";
	/**
	 * Numele de utilizator pentru conectarea la baza de date
	 */
	private static final String user="root";
	/**
	 * Parola pentru conectarea la baza de date
	 */
	private static final String password="pass";
	
	/**
	 * Returneaza o conexiune la baza de date
	 * @return un obiect Connection - conexiunea la baza de date
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}
}
