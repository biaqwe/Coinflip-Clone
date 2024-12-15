package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utils.DatabaseConn;
/**
 * Clasa pentru procesul de autentificare
 */
public class Login {
	/**
	 * Obtine id ul utilizatorului
	 * @param username numele de utilizator
	 * @param password parola utilizatorului
	 * @return id ul utilizatorului daca username ul si parola sunt valide, altfel -1
	 */
	public int getUID(String username, String password) {
		int userID=-1;
		String query="SELECT user_id FROM users WHERE username=? AND password=?";
		try(Connection connection=DatabaseConn.getConnection(); PreparedStatement stmt=connection.prepareStatement(query)){
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet result=stmt.executeQuery();
			if(result.next()) {
				userID=result.getInt("user_id");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return userID;
	}

}
