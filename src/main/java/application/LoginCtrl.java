package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginCtrl {
	//handles click on signup hyperlink
	@FXML
	private Hyperlink signupLink;
	@FXML
	private void goToSignup() {
		try {
			FXMLLoader loader=new FXMLLoader(getClass().getResource("Signup.fxml"));
			Parent root=loader.load();
			Stage stage=(Stage) signupLink.getScene().getWindow();
			stage.setScene(new Scene(root));
            stage.show();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//handles click on login button
	@FXML
    private TextField ufield;
    @FXML
    private PasswordField pfield;
    @FXML
	public void loginBtnClick() {
		String username=ufield.getText();
		String password=pfield.getText();
		System.out.println("Attempting login with username: "+username+" and password: "+password);
		Login login=new Login();
		if(login.valid(username, password)) {
			System.out.println("Login successful");
			try {
				FXMLLoader loader=new FXMLLoader(getClass().getResource("Main.fxml"));
				Parent root=loader.load();
				Stage stage=(Stage) ufield.getScene().getWindow();
				stage.setScene(new Scene(root));
	            stage.show();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		else {
			Alert alert=new Alert(AlertType.ERROR);
			alert.setTitle("Login Error");
            alert.setHeaderText("Invalid email or password.");
            alert.showAndWait();
		}
	}

}
