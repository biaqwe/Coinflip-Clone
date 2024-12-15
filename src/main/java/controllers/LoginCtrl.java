package controllers;

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
import utils.Session;
/**
 * Clasa pentru pagina de autentificare
 */
public class LoginCtrl {
	/**
	 * Hyperlink pentru pagina de inregistrare
	 */
	@FXML
	private Hyperlink signupLink;
	/**
	 * Redirectioneaza utilizatorul pe pagina de inregistrare dupa apasarea hyperlink ului
	 */
	@FXML
	private void goToSignup() {
		try {
			FXMLLoader loader=new FXMLLoader(getClass().getResource("/pages/Signup.fxml"));
			Parent root=loader.load();
			Stage stage=(Stage) signupLink.getScene().getWindow();
			stage.setScene(new Scene(root));
            stage.show();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Campul pentru numele de utilizator
	 */
	@FXML
    private TextField ufield;
	/**
	 * Campul pentru parola
	 */
    @FXML
    private PasswordField pfield;
    /**
     * Verifica daca combinatia de username si parola e valida, redirectioneaza utilizatorul pe pagina principala si salveaza user id ul in sesiune daca da, altfel afiseaza un mesaj de eroare
     */
    @FXML
	public void loginBtnClick() {
		String username=ufield.getText();
		String password=pfield.getText();
		System.out.println("Attempting login with username: "+username+" and password: "+password);
		Login login=new Login();
		int userID=login.getUID(username, password);
		if(userID!=-1) {
			System.out.println("Login successful, userID: "+userID);
			Session.setUID(userID); //saves userid in session
			try {
				FXMLLoader loader=new FXMLLoader(getClass().getResource("/pages/Main.fxml"));
	            Parent root=loader.load();
	            String css = getClass().getResource("/resources/application.css").toExternalForm();
	            root.getStylesheets().add(css);
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
