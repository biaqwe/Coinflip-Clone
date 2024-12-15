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
/**
 * Clasa pentru pagina de inregistrare
 */
public class SignupCtrl {
	/**
	 * Hyperlink pentru pagina de autentificare
	 */
	@FXML
	private Hyperlink loginLink;
	/**
	 * Redirectioneaza utilizatorul pe pagina de autentificare dupa apasarea hyperlink ului
	 */
	@FXML
	private void goToLogin() {
		try {
			FXMLLoader loader=new FXMLLoader(getClass().getResource("/pages/Login.fxml"));
			Parent root=loader.load();
			Stage stage=(Stage) loginLink.getScene().getWindow();
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
     * Verifica daca username ul exista deja in baza de date si valideaza datele introduse, redirectioneaza utilizatorul pe pagina de autentificare daca da, altfel afiseaza mesaje de eroare
     */
	@FXML
	public void signupBtnClick() {
		String username=ufield.getText();
		String password=pfield.getText();
		System.out.println("Attempting signup with username: "+username+" and password: "+password);//debug
		if(username.isEmpty() || password.isEmpty()) {
			Alert alert=new Alert(AlertType.ERROR);
			alert.setTitle("Signup Error");
            alert.setHeaderText("All fields are required.");
            alert.showAndWait();
            return;
		}
		Signup signup=new Signup();
		if(signup.valid(username)) {
			Alert alert=new Alert(AlertType.ERROR);
			alert.setTitle("Signup Error");
            alert.setHeaderText("Username is taken.");
            alert.showAndWait();
		}
		else {
			if(signup.addUser(username, password)) {
				try {
					FXMLLoader loader=new FXMLLoader(getClass().getResource("/pages/Login.fxml"));
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
				alert.setTitle("Signup Error");
	            alert.setHeaderText("Signup failed, please try again.");
	            alert.showAndWait();
			}
		}
	}

}
