package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
	public boolean valid(String username, String password) {
		boolean ok=false;
		String query="SELECT * FROM users WHERE username=? AND password=?";
		try(Connection connection=DatabaseConn.getConnection(); PreparedStatement stmt=connection.prepareStatement(query)){
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet result=stmt.executeQuery();
			if(result.next()) {
				ok=true;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return ok;
	}

}
