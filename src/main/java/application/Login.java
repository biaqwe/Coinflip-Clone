package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
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
