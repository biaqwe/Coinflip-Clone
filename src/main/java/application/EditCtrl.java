package application;

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
			FXMLLoader loader=new FXMLLoader(getClass().getResource("Login.fxml"));
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
			FXMLLoader loader=new FXMLLoader(getClass().getResource("Main.fxml"));
			Parent root=loader.load();
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
    	categField.setText(transaction.getCategory());
    	categField.setStyle("-fx-background-radius: 30px; -fx-border-color: #aab6fe; -fx-border-radius: 30px; -fx-border-width: 2px; -fx-text-fill: #6b6290; -fx-font-size: 20px; -fx-font-family: 'HirukoPro-Book';");
    	paymentField.setText(transaction.getPayment());
    	paymentField.setStyle("-fx-background-radius: 30px; -fx-border-color: #aab6fe; -fx-border-radius: 30px; -fx-border-width: 2px; -fx-text-fill: #6b6290; -fx-font-size: 20px; -fx-font-family: 'HirukoPro-Book';");
    	subBox.setSelected(transaction.isSubscription());
    	exclBox.setSelected(transaction.isExcluded());
    	select.setStyle("-fx-background-radius: 30px; -fx-border-color: #aab6fe; -fx-border-radius: 30px; -fx-border-width: 2px; -fx-text-fill: #6b6290; -fx-font-size: 20px; -fx-font-family: 'HirukoPro-Book'; -fx-background-colour: white; -fx-background-insets: 0; -fx-opaque: true; -fx-effect: null;");
    	if(transaction instanceof Income) {
    		select.setValue("Income");
    		addSrc();
    		srcField.setText(((Income) transaction).getSource());
    	}
    	else if(transaction instanceof Expense) {
    		select.setValue("Expense");
    		addEss();
    		essCb.setSelected(((Expense) transaction).isEssential());
    	}
    }
}
