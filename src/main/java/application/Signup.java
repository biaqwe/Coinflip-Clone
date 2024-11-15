package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Signup {
	//checks if username already exists in db
	public boolean valid(String username) {
		String query="SELECt * FROM users WHERE username=?";
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
