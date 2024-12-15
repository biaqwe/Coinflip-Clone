package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import utils.DatabaseConn;
import utils.Session;
/**
 * Clasa pentru functionalitatea de adaugare tranzactie
 */
public class AddCtrl {
	/**
	 * Butonul pentru delogare
	 */
    @FXML
    private Button logoutBtn;
    /**
     * Redirectioneaza utilizatorul pe pagina de logare dupa apasarea butonului de loguot
     */
    @FXML
    private void logout() {
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/pages/Login.fxml"));
            Parent root=loader.load();
            Stage stage=(Stage) logoutBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Butonul pentru inchiderea paginii de adaugare
     */
    @FXML
    private Button closeBtn;
    /**
     * Redirectioneaza utilizatorul pe pagina principala daca e apasat butonul de inchidere a formularului de adaugare
     */
    @FXML
    private void close() {
        try {
        	FXMLLoader loader=new FXMLLoader(getClass().getResource("/pages/Main.fxml"));
            Parent root=loader.load();
            String css = getClass().getResource("/resources/application.css").toExternalForm();
            root.getStylesheets().add(css);
            Stage stage=(Stage) closeBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**
	 * Eticheta pentru campul "source", afisata daca tranzactia e de tip "venit"
	 */
    @FXML
    private Label srcLbl;
    /**
	 * Eticheta pentru checkbox ul "essential", afisata daca tranzactia e de tip "cheltuiala"
	 */
    @FXML
    private Label essLbl;
    /**
	 * Campul "source", afisat daca tranzactia e de tip "venit"
	 */
    @FXML
    private TextField srcField;
    /**
	 * Checkbox ul "essential", afisat daca tranzactia e de tip "cheltuiala"
	 */
    @FXML
    private CheckBox essCb;
    /**
     * Casuta pentru selectare a tipului de tranzactie
     */
    @FXML
	private ComboBox select;
    /**
     * Grid folosit pentru organizarea elementelor
     */
    @FXML
	private GridPane grid;
    
    /**
     * Adauga campul pentru sursa daca tranzactia e de tip "venit"
     */
    private void addSrc() {
    	Label srcLbl=new Label("Source");
    	srcLbl.setStyle("-fx-text-fill: #6b6290; -fx-font-size: 20px; -fx-font-family: 'HirukoPro-Book';");
    	srcField=new TextField();
    	srcField.setPrefHeight(31.0);
        srcField.setPrefWidth(160.0);
        srcField.setStyle("-fx-background-radius: 30px; -fx-border-color: #aab6fe; -fx-border-radius: 30px; -fx-border-width: 2px; -fx-text-fill: #6b6290; -fx-font-size: 20px; -fx-font-family: 'HirukoPro-Book';-fx-text-fill: #6b6290; -fx-font-size: 20px; -fx-font-family: 'HirukoPro-Book';");
        srcField.setId("srcField");
        grid.add(srcLbl, 0, 6);
        grid.add(srcField, 1, 6);
        GridPane.setColumnSpan(srcField, GridPane.REMAINING);
    }
    
    /**
     * Adauga checkbox ul pentru tranzactie esentiala daca tranzactia e de tip "cheltuiala"
     */
    private void addEss() {
    	Label essLbl=new Label("Essential?");
    	essLbl.setStyle("-fx-text-fill: #6b6290; -fx-font-size: 20px; -fx-font-family: 'HirukoPro-Book';");
    	essCb=new CheckBox();
    	essCb.getStyleClass().add("check-box");
        essCb.setStyle("-fx-cursor: hand;");
        essCb.setId("essCb");
        grid.add(essLbl, 0, 6);
        grid.add(essCb, 0, 6);
        GridPane.setHalignment(essCb, javafx.geometry.HPos.RIGHT);
    }
    /**
     * Campul pentru numele tranzactiei
     */
    @FXML
	private TextField nameField;
    /**
     * Campul pentru suma tranzactiei
     */
    @FXML
	private TextField amountField;
    /**
     * Campul pentru categoria tranzactiei
     */
    @FXML
	private TextField categField;
    /**
     * Campul pentru modul de plata al tranzactiei
     */
    @FXML
	private TextField paymentField;
    /**
     * Checkbox pentru tranzactii de tip abonament
     */
    @FXML
    private CheckBox subBox;
    /**
     * Checkbox pentru tranzactii excluse din raport
     */
    @FXML
    private CheckBox exclBox;
    /**
     * Adauga tipurile de tranzactii in casuta select si afiseaza formularul in functie de tipul de tranzactie selectat
     */
    @FXML
	private void initialize() {
        select.getItems().addAll("Income", "Expense");
        select.setOnAction(event-> {
        	String type=(String) select.getValue();
        	//clears row 6 to avoid overlapping
        	grid.getChildren().removeIf(node->GridPane.getRowIndex(node)!=null && GridPane.getRowIndex(node)==6);
        	if("Income".equals(type)) {
        		addSrc();
        	}
        	else if("Expense".equals(type)) {
        		addEss();
        	}
        });
    }
    /**
     * Buton pentru adaugarea tranzactiei in baza de date; la apasarea acestuia se executa functia "add"
     */
    @FXML
    private Button save;
    /**
     * Colecteaza datele din formular si adauga o noua tranzactie in baza de date; dupa adaugare redirectioneaza utilizatorul catre pagina principala
     */
    @FXML
    private void add() { 
    	int userID=Session.getUID();
    	String name=nameField.getText();
    	double amount=Double.parseDouble(amountField.getText());
    	String category=categField.getText();
    	String paymentMethod=paymentField.getText();
    	boolean subscription=subBox.isSelected();
    	boolean excludedFromReport=exclBox.isSelected();
    	String transactionType=(String) select.getValue();
    	String source=transactionType.equals("Income") ? srcField.getText():null;
    	boolean essential=transactionType.equals("Expense") ? essCb.isSelected():false;
    	//debug
    	System.out.println("UserID: " + userID);
        System.out.println("Name: " + name);
        System.out.println("Amount: " + amount);
        System.out.println("Category: " + category);
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("Transaction Type: " + transactionType);
        System.out.println("Source: " + source);
        System.out.println("Essential: " + essential);
    	String query="INSERT INTO transactions (userID, name, amount, category, paymentMethod, date, subscription, excludedFromReport, transactionType, source, essential) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try(Connection connection=DatabaseConn.getConnection(); PreparedStatement stmt=connection.prepareStatement(query)){
			stmt.setInt(1, userID);
            stmt.setString(2, name);
            stmt.setDouble(3, amount);
            stmt.setString(4, category);
            stmt.setString(5, paymentMethod);
            stmt.setDate(6, java.sql.Date.valueOf(LocalDate.now()));
            stmt.setBoolean(7, subscription);
            stmt.setBoolean(8, excludedFromReport);
            stmt.setString(9, transactionType);
            stmt.setString(10, source);
            stmt.setBoolean(11, essential);
            int rows=stmt.executeUpdate();
            if(rows>0)
            	System.out.println("Transaction added");
            else
            	System.out.println("Failed to add transaction");
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/pages/Main.fxml"));
            Parent root=loader.load();
            String css = getClass().getResource("/resources/application.css").toExternalForm();
            root.getStylesheets().add(css);
            Stage stage=(Stage) save.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
		}
		catch(SQLException e) {
			System.out.println("Error adding transaction "+e.getMessage());
			e.printStackTrace();
		}
		catch(Exception e) {
			System.out.println("Error "+e.getMessage());
			e.printStackTrace();
		}
    }
}
