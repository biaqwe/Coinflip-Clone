package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 * Clasa pentru procesul de inregistrare
 */
public class Signup {
	/**
	 * Verifica daca numele de utilizator exista in baza de date
	 * @param username numele de utilizator
	 * @return "true" daca deja exista, "false" altfel
	 */
	public boolean valid(String username) {
		String query="SELECT * FROM users WHERE username=?";
		try(Connection connection=DatabaseConn.getConnection(); PreparedStatement stmt=connection.prepareStatement(query)){
			stmt.setString(1, username);
			ResultSet result=stmt.executeQuery();
			return result.next();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * Adauga un utilizator nou in baza de date
	 * @param username numele de utilizator
	 * @param password parola
	 * @return "true" daca utilizatorul a fost adaugat cu succes, "false" altfel
	 */
	public boolean addUser(String username, String password) {
		String query="INSERT INTO users (username, password) VALUES (?, ?)";
		try(Connection connection=DatabaseConn.getConnection(); PreparedStatement stmt=connection.prepareStatement(query)){
			stmt.setString(1, username);
			stmt.setString(2, password);
			int row=stmt.executeUpdate();
			if(row!=0)
				return true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
