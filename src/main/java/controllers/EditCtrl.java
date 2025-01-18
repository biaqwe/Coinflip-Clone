package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import entities.Expense;
import entities.Income;
import entities.Transaction;
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
/**
 * Clasa pentru functionalitatea de editare tranzactie
 */
public class EditCtrl {
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
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Butonul pentru inchiderea paginii de editare
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
    private ComboBox<String> select;
    /**
     * Container folosit pentru organizarea elementelor
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
        srcField.setStyle("-fx-background-radius: 30px; -fx-border-color: #aab6fe; -fx-border-radius: 30px; -fx-border-width: 2px; -fx-text-fill: #6b6290; -fx-font-size: 20px; -fx-font-family: 'HirukoPro-Book';");
        srcField.setId("srcField");
        grid.add(srcLbl, 0, 6);
        grid.add(srcField, 1, 6);
        GridPane.setColumnSpan(srcField, GridPane.REMAINING);
    }
    
    /**
     * Adauga checkbox ul pentru tranzactie esentiala daca tranzactia e de tip "cheltuiala"
     */
    private void addEss() {
    	if(essCb!=null) {
    		grid.getChildren().remove(essCb);
    	}
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
        currencyBox.setStyle("-fx-background-radius: 30px;"+"-fx-border-color: none;"+"-fx-border-radius: 20px;"+"-fx-border-width: 0px;"+"-fx-background-color: white;"+"-fx-font-size: 12px;"+"-fx-text-fill: #6b6290;");
        currencyBox.getItems().addAll("USD", "EUR", "RON", "GBP", "JPY", "INR");
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
     * Combobox pentru moneda tranzactiei
     */
    @FXML
    private ComboBox<String> currencyBox;
    /**
     * Obiect de tip tranzactie
     */
    private Transaction transaction;
    /**
     * Seteaza datele tranzactiei pentru formularul de editare
     * @param transaction Obiectul Transaction care contine datele de editat
     */
    public void setTransaction(Transaction transaction) {
    	this.transaction=transaction;
    	nameField.setText(transaction.getName());
    	nameField.setStyle("-fx-background-radius: 30px; -fx-border-color: #aab6fe; -fx-border-radius: 30px; -fx-border-width: 2px; -fx-text-fill: #6b6290; -fx-font-size: 20px; -fx-font-family: 'HirukoPro-Book';");
    	amountField.setText(String.valueOf(transaction.getAmount()));
    	amountField.setStyle("-fx-background-radius: 30px; -fx-border-color: #aab6fe; -fx-border-radius: 30px; -fx-border-width: 2px; -fx-text-fill: #6b6290; -fx-font-size: 20px; -fx-font-family: 'HirukoPro-Book';");
    	currencyBox.setValue(transaction.getCurrency());
    	categField.setText(transaction.getCategory());
    	categField.setStyle("-fx-background-radius: 30px; -fx-border-color: #aab6fe; -fx-border-radius: 30px; -fx-border-width: 2px; -fx-text-fill: #6b6290; -fx-font-size: 20px; -fx-font-family: 'HirukoPro-Book';");
    	paymentField.setText(transaction.getPayment());
    	paymentField.setStyle("-fx-background-radius: 30px; -fx-border-color: #aab6fe; -fx-border-radius: 30px; -fx-border-width: 2px; -fx-text-fill: #6b6290; -fx-font-size: 20px; -fx-font-family: 'HirukoPro-Book';");
    	subBox.setSelected(transaction.isSubscription());
    	exclBox.setSelected(transaction.isExcluded());
    	select.setStyle("-fx-background-radius: 30px; -fx-border-color: #aab6fe; -fx-border-radius: 30px; -fx-border-width: 2px; -fx-text-fill: #6b6290; -fx-font-size: 20px; -fx-font-family: 'HirukoPro-Book'; -fx-background-colour: white; -fx-background-insets: 0; -fx-opaque: true; -fx-effect: null;");
    	if(transaction instanceof Income) {
    		select.setValue("Income");
    		//clears row 6 to avoid overlapping
        	grid.getChildren().removeIf(node->GridPane.getRowIndex(node)!=null && GridPane.getRowIndex(node)==6);
    		addSrc();
    		srcField.setText(((Income) transaction).getSource());
    	}
    	else if(transaction instanceof Expense) {
    		select.setValue("Expense");
    		//clears row 6 to avoid overlapping
        	grid.getChildren().removeIf(node->GridPane.getRowIndex(node)!=null && GridPane.getRowIndex(node)==6);
    		addEss();
    		boolean essVal=((Expense) transaction).isEssential();
    		essCb.setSelected(essVal);

    	}
    }
    /**
     * Functie pentru afisarea alertelor
     * @param title titlul alertei
     * @param message mesajul alertei
     */
    private void showAlert(String title, String message) {
    	javafx.scene.control.Alert alert=new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    /**
     * Afiseaza alerte daca datele introduse nu sunt valide
     * @param name numele tranzactiei
     * @param amountStr suma tranzactiei
     * @param category categoria tranzactiei
     * @param transactionType tipul tranzactiei
     * @param source sursa tranzactiei, obligatorie daca tranzactia e de tip cheltuiala
     * @param currency moneda tranzactiei
     * @return true daca toate datele sunt valide
     */
    public boolean valid(String name, String amountStr, String category, String transactionType, String source, String currency) {
    	try {
    		ok(name, amountStr, category, transactionType, source, currency);
    	}
    	catch(NullPointerException | IllegalArgumentException e) {
    		showAlert("Validation Error", e.getMessage());
            return false;
    	}
    	return true;
    }
    /**
     * Verifica daca datele introduse sunt valide
     * @param name numele tranzactiei
     * @param amountStr suma tranzactiei
     * @param category categoria tranzactiei
     * @param transactionType tipul tranzactiei
     * @param source sursa tranzactiei, obligatorie daca tranzactia e de tip cheltuiala
     * @param currency moneda tranzactiei
     * @throws NullPointerException daca numele, categoria, sursa sau moneda sunt null sau goale
     * @throws IllegalArgumentException daca suma nu e un numar valid pozitiv
     */
    public void ok(String name, String amountStr, String category, String transactionType, String source, String currency) {
    	if(name==null || name.trim().isEmpty()) {
    		throw new NullPointerException("Transaction name should not be null or empty");
    	}
    	double amount;
    	try {
    		amount=Double.parseDouble(amountStr);
    	}
    	catch (NumberFormatException e){
    		throw new IllegalArgumentException("Transaction amount not valid");
    	}
    	if(currency==null || currency.trim().isEmpty()) {
    		throw new NullPointerException("Transaction currency should not be null or empty");
    	}
    	if(category==null || category.trim().isEmpty()) {
    		throw new NullPointerException("Transaction category should not be null or empty");
    	}
    	if("Income".equals(transactionType) && (source==null || source.trim().isEmpty())) {
    		throw new NullPointerException("Transaction source should not be null or empty");
    	}
    	
    }
    /**
     * Colecteaza datele din formular si editeaza o tranzactie din baza de date; dupa editare redirectioneaza utilizatorul catre pagina principala
     */
    @FXML
    private void edit() {
        String name=nameField.getText();
        String amountStr=amountField.getText();
        String category=categField.getText();
        String payment=paymentField.getText();
        boolean isSubscription=subBox.isSelected();
        boolean isExcluded=exclBox.isSelected();
        String type=select.getValue();
        String source="Income".equals(type) ? srcField.getText():null;
        boolean isEssential="Expense".equals(type) && essCb.isSelected();
        String currency=currencyBox.getValue();

        if(!valid(name, amountStr, category, type, source, currency)) {
        	return;
        }

        double amount=Double.parseDouble(amountStr);
        
        if("Income".equals(type) && amount<0) {
            amount=Math.abs(amount);
        }
        else if("Expense".equals(type) && amount>0) {
            amount=-amount;
        }

        try(Connection conn=DatabaseConn.getConnection()) {
            String query="UPDATE transactions SET name=?, amount=?, category=?, paymentMethod=?, subscription=?, excludedFromReport=?, transactionType=?, currency=? WHERE transactionID=?";
            try(PreparedStatement stmt=conn.prepareStatement(query)) {
                stmt.setString(1, name);
                stmt.setDouble(2, amount);
                stmt.setString(3, category);
                stmt.setString(4, payment);
                stmt.setBoolean(5, isSubscription);
                stmt.setBoolean(6, isExcluded);
                stmt.setString(7, type);
                stmt.setString(8, currency);
                stmt.setInt(9, transaction.getID());
                stmt.executeUpdate();
            }

            if("Income".equals(type)) {
                query="UPDATE transactions SET source=?, essential=NULL WHERE transactionID=?";
                try(PreparedStatement stmt=conn.prepareStatement(query)) {
                    stmt.setString(1, source!=null ? source:"");
                    stmt.setInt(2, transaction.getID());
                    stmt.executeUpdate();
                }
            }
            else if("Expense".equals(type)) {
                query="UPDATE transactions SET essential=?, source=NULL WHERE transactionID=?";
                try(PreparedStatement stmt=conn.prepareStatement(query)) {
                    stmt.setBoolean(1, isEssential);
                    stmt.setInt(2, transaction.getID());
                    stmt.executeUpdate();
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        close();
    }

}
