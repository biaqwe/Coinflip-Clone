package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import entities.Expense;
import entities.Income;
import entities.Transaction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.DatabaseConn;
import utils.Session;
/**
 * Clasa pentru pagina principala
 */
public class MainCtrl {
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
	 * Butonul pentru accesarea paginii cu principale
	 */
    @FXML
    private Button mainBtn;
    /**
     * Redirectioneaza utilizatorul pe pagina principala dupa apasarea butonului
     */
    @FXML
    private void mainPage() {
        try {
        	FXMLLoader loader=new FXMLLoader(getClass().getResource("/pages/Main.fxml"));
            Parent root=loader.load();
            String css = getClass().getResource("/resources/application.css").toExternalForm();
            root.getStylesheets().add(css);
            Stage stage=(Stage) mainBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**
	 * Butonul pentru accesarea paginii cu rapoarte
	 */
    @FXML
    private Button reportBtn;
    /**
     * Redirectioneaza utilizatorul pe pagina cu rapoarte dupa apasarea butonului
     */
    @FXML
    private void report() {
        try {
        	FXMLLoader loader=new FXMLLoader(getClass().getResource("/pages/Report.fxml"));
            Parent root=loader.load();
            String css = getClass().getResource("/resources/application.css").toExternalForm();
            root.getStylesheets().add(css);
            Stage stage=(Stage) reportBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**
	 * Butonul pentru accesarea paginii cu abonamente
	 */
    @FXML
    private Button subsBtn;
    /**
     * Redirectioneaza utilizatorul pe pagina cu abonamente dupa apasarea butonului
     */
    @FXML
    private void subs() {
        try {
        	FXMLLoader loader=new FXMLLoader(getClass().getResource("/pages/Subs.fxml"));
            Parent root=loader.load();
            String css = getClass().getResource("/resources/application.css").toExternalForm();
            root.getStylesheets().add(css);
            Stage stage=(Stage) subsBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Buton pentru deschiderea paginii de adaugare tranzactie
     */
    @FXML
    private Button addBtn;
    /**
     * Redirectioneaza utilizatorul pe pagina de adaugare tranzactie
     */
    @FXML
    private void addBtnClick() {
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/pages/Add.fxml"));
            Parent root=loader.load();
            String css = getClass().getResource("/resources/application.css").toExternalForm();
            root.getStylesheets().add(css);
            Stage stage=(Stage) addBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Container pentru organizarea elementelor
     */
    @FXML
    private VBox box;
    /**
     * ScrollPane care permite vizualizarea continutului care depaseste dimensiunile ferestrei
     */
    @FXML
    private ScrollPane scroll;
    /**
     * Creeaza un card stilizat pentru o tranzactie; cardul contine numele tranzactiei, tipul tranzactiei, suma, data, categoria, sursa (pentru venit), daca e esentiala (pentru cheltuiala), daca e exclusa din raport si butoanele de editare si stergere
     * @param transaction Obiectul "Income" sau "Expense" pentru care se va crea cardul
     * @return Un obiect de tip Group care contine cardul si butoanele de stergere si editare
     */
    @FXML
    private Group createCard(Transaction transaction) {
        Group card=new Group();
        GridPane grid=new GridPane();
        //design stuff
        grid.setStyle("-fx-background-color: white; -fx-background-radius: 20px; -fx-padding: 10px;");
        grid.getColumnConstraints().addAll(new ColumnConstraints(100), new ColumnConstraints(100), new ColumnConstraints(100));
        grid.getRowConstraints().addAll(new RowConstraints(30), new RowConstraints(30), new RowConstraints(30));

        Text name=new Text(transaction.getName());
        name.setFill(javafx.scene.paint.Color.valueOf("#6b6290"));
        name.setFont(new Font("HirukoPro-Book", 20));
        GridPane.setHalignment(name, javafx.geometry.HPos.LEFT);
        grid.add(name, 0, 0);

        if(transaction instanceof Income) { //if transaction is income add green button
	        Button btn=new Button("Income");
	        btn.setStyle("-fx-background-color: #aad8b9; -fx-background-radius: 10px; -fx-min-width: 80px; -fx-min-height: 30px; -fx-max-width: 80px; -fx-max-height: 30px;");
	        btn.setTextFill(javafx.scene.paint.Color.WHITE);
	        btn.setFont(new Font("HirukoPro-Regular", 14));
	        GridPane.setHalignment(btn, javafx.geometry.HPos.RIGHT);
	        btn.setOnAction(e->filterByType("income"));
	        grid.add(btn, 2, 0);
        }
        else {
        	Button btn=new Button("Expense");  //if transaction is income add red button
	        btn.setStyle("-fx-background-color: #d9aeac; -fx-background-radius: 10px; -fx-min-width: 80px; -fx-min-height: 30px; -fx-max-width: 80px; -fx-max-height: 30px;");
	        btn.setTextFill(javafx.scene.paint.Color.WHITE);
	        btn.setFont(new Font("HirukoPro-Regular", 14));
	        GridPane.setHalignment(btn, javafx.geometry.HPos.RIGHT);
	        btn.setOnAction(e->filterByType("expense"));
	        grid.add(btn, 2, 0);
        }
        //format amount to string
        DecimalFormat df=new DecimalFormat("#,###.00"); // , as thousand separator + always 2 decimals
        String currency=transaction.getCurrency()!=null ? transaction.getCurrency():"";
        String amountStr=df.format(transaction.getAmount())+" "+currency;
        Text amount=new Text(amountStr);
        amount.setFill(javafx.scene.paint.Color.valueOf("#6b6290"));
        amount.setFont(new Font("HirukoPro-Book", 18));
        GridPane.setHalignment(amount, javafx.geometry.HPos.LEFT);
        grid.add(amount, 0, 1);
        //format date to string
        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateStr=transaction.getDate().format(dtf);
        Text date=new Text(dateStr);
        date.setFill(javafx.scene.paint.Color.valueOf("#6b6290"));
        date.setFont(new Font("HirukoPro-Book", 17));
        GridPane.setHalignment(date, javafx.geometry.HPos.CENTER);
        grid.add(date, 2, 1);

        Button categ=new Button(transaction.getCategory());
        categ.setStyle("-fx-background-color: #d6c8ff; -fx-background-radius: 10px; -fx-min-width: 90px; -fx-min-height: 30px; -fx-max-width: 90px; -fx-max-height: 30px;");
        categ.setTextFill(javafx.scene.paint.Color.WHITE);
        categ.setFont(new Font("HirukoPro-Regular", 14));
        GridPane.setHalignment(categ, javafx.geometry.HPos.LEFT);
        grid.add(categ, 0, 2);
        categ.setOnAction(e->filterByCateg(transaction.getCategory()));
        
        if(transaction instanceof Income income) { //if transaction is income add source button
            Button src=new Button(income.getSource());
            src.setStyle("-fx-background-color: #ffc8a2; -fx-background-radius: 10px; -fx-min-width: 90px; -fx-min-height: 30px; -fx-max-width: 90px; -fx-max-height: 30px;");
            src.setTextFill(javafx.scene.paint.Color.WHITE);
            src.setFont(new Font("HirukoPro-Regular", 14));
            GridPane.setHalignment(src, javafx.geometry.HPos.CENTER);
            grid.add(src, 1, 2);
        }
        else if(transaction instanceof Expense expense) { //if transaction is expense add essential button
        	Button ess=new Button(expense.isEssential() ? "Essential":"Not Essential");
            ess.setStyle("-fx-background-color: #ffc8a2; -fx-background-radius: 10px; -fx-min-width: 90px; -fx-min-height: 30px; -fx-max-width: 90px; -fx-max-height: 30px;");
            ess.setTextFill(javafx.scene.paint.Color.WHITE);
            ess.setFont(new Font("HirukoPro-Regular", 14));
            GridPane.setHalignment(ess, javafx.geometry.HPos.CENTER);
            grid.add(ess, 1, 2);
        	
        }
        
        Button excl=new Button(transaction.isExcluded() ? "Excluded":"Included");
       	excl.setStyle("-fx-background-color: #aab6fe; -fx-background-radius: 10px; -fx-min-width: 90px; -fx-min-height: 30px; -fx-max-width: 90px; -fx-max-height: 30px;");
        excl.setTextFill(javafx.scene.paint.Color.WHITE);
        excl.setFont(new Font("HirukoPro-Regular", 14));
        GridPane.setHalignment(excl, javafx.geometry.HPos.RIGHT);
        grid.add(excl, 2, 2);
        
        VBox btns=new VBox(10);
        btns.setAlignment(Pos.CENTER_LEFT);
        btns.setStyle("-fx-padding: 2px;");
        
        Image editIcon=new Image(getClass().getResourceAsStream("/resources/edit.png"));
        ImageView editIV=new ImageView(editIcon);
        editIV.setFitWidth(17);
        editIV.setFitHeight(20);
        Button editBtn=new Button();
        editBtn.setGraphic(editIV);
        editBtn.setStyle("-fx-background-color: #c67ac8; -fx-text-fill: white; -fx-background-radius: 50%; -fx-min-width: 30px; -fx-min-height: 30px; -fx-cursor: hand;");
        editBtn.setOnAction(e->edit(transaction));
        btns.getChildren().add(editBtn);
        
        Image delIcon=new Image(getClass().getResourceAsStream("/resources/delete.png"));
        ImageView delIV=new ImageView(delIcon);
        delIV.setFitWidth(16);
        delIV.setFitHeight(20);
        Button delBtn=new Button();
        delBtn.setGraphic(delIV);
        delBtn.setStyle("-fx-background-color: #c67ac8; -fx-text-fill: white; -fx-background-radius: 50%; -fx-min-width: 30px; -fx-min-height: 30px; -fx-cursor: hand;");
        delBtn.setOnAction(e->delete(transaction));
        btns.getChildren().add(delBtn);
        
        HBox all=new HBox(1);
        all.getChildren().addAll(grid, btns);
        all.setAlignment(Pos.CENTER);
        
        card.getChildren().add(all);//add grid  pane to card
        return card;
    }
    /**
     * Redirectioneaza utilizatorul pe pagina de editare tranzactie
     * @param transaction tranzactia care va fi editata
     */
    public void edit(Transaction transaction) {
    	try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/pages/Edit.fxml"));
            Parent root=loader.load();
            String css = getClass().getResource("/resources/application.css").toExternalForm();
            EditCtrl editCtrl=loader.getController();
            editCtrl.setTransaction(transaction);
            root.getStylesheets().add(css);
            Stage stage=(Stage) addBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Sterge o tranzactie din baza de date si de pe pagina
     * @param transaction tranzactia care va fi stearsa
     */
    public void delete(Transaction transaction) {
    	System.out.println("Deleting transaction id: "+transaction.getID());
    	int id=transaction.getID();
    	String query="DELETE FROM transactions WHERE transactionID=?";
    	try(Connection connection=DatabaseConn.getConnection(); PreparedStatement stmt=connection.prepareStatement(query)){
    		stmt.setInt(1, id);
    		int rows=stmt.executeUpdate();
    		if(rows>0) {
    			System.out.println("Deleted");
    			box.getChildren().removeIf(node->{
    				Group card=(Group) node;
    				return card.getUserData()!=null && ((Transaction) card.getUserData()).getID()==id;
    			});
    			loadTransactions();
    		}
    		else {
    			System.out.println("Transaction not found");
    		}
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * Filtreaza tranzactiile de pe pagina dupa categorie
     * @param category categoria dupa care vor fi filtrate tranzactiile
     */
    private void filterByCateg(String category) {
    	List<Transaction> filtered=new ArrayList<>();
    	int userID=Session.getUID();
    	if(userID==-1) {
    		System.out.println("User not logged in");
    		return;
    	}
    	String query="SELECT * FROM transactions WHERE userID=? AND category=? ORDER BY transactionID DESC";
    	try(Connection connection=DatabaseConn.getConnection(); PreparedStatement stmt=connection.prepareStatement(query)){
    		stmt.setInt(1, userID);
    		stmt.setString(2, category);
    		ResultSet result=stmt.executeQuery();
    		while(result.next()) {
    			int transactionID=result.getInt("transactionID");
                String name=result.getString("name");
                double amount=result.getDouble("amount");
                String transactionCategory=result.getString("category");
                String paymentMethod=result.getString("paymentMethod");
                LocalDate date=result.getDate("date").toLocalDate();
                boolean excluded=result.getInt("excludedFromReport")==1;
                String transactionType=result.getString("transactionType");
                String source=result.getString("source");
                int essentialVal=result.getInt("essential");
                String currency=result.getString("currency");
                boolean essential=essentialVal==1;
    			
                if("income".equalsIgnoreCase(transactionType)) {
                	filtered.add(new Income(transactionID, name, amount, transactionCategory, paymentMethod, date, false, excluded, currency, source));
                }
                else if("expense".equalsIgnoreCase(transactionType)) {
                	filtered.add(new Expense(transactionID, name, amount, transactionCategory, paymentMethod, date, essential, excluded, currency, essential));
                }
    		}
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	displayCards(filtered);
    }
    
    /**
     * Filtreaza tranzactiile de pe pagina dupa tipul de tranzactie (venit/cheltuiala)
     * @param transactionType tipul dupa care vor fi filtrate tranzactiile
     */
    private void filterByType(String transactionType) {
    	List<Transaction> filtered=new ArrayList<>();
    	int userID=Session.getUID();
    	if(userID==-1) {
    		System.out.println("User not logged in");
    		return;
    	}
    	String query="SELECT * FROM transactions WHERE userID=? AND transactionType=? ORDER BY transactionID DESC";
    	try(Connection connection=DatabaseConn.getConnection(); PreparedStatement stmt=connection.prepareStatement(query)){
    		stmt.setInt(1, userID);
    		stmt.setString(2, transactionType);
    		ResultSet result=stmt.executeQuery();
    		while(result.next()) {
    			int transactionID=result.getInt("transactionID");
                String name=result.getString("name");
                double amount=result.getDouble("amount");
                String transactionCategory=result.getString("category");
                String paymentMethod=result.getString("paymentMethod");
                LocalDate date=result.getDate("date").toLocalDate();
                boolean excluded=result.getInt("excludedFromReport")==1;
                String source=result.getString("source");
                int essentialVal=result.getInt("essential");
                String currency=result.getString("currency");
                boolean essential=essentialVal==1;
    			
                if("income".equalsIgnoreCase(transactionType)) {
                	filtered.add(new Income(transactionID, name, amount, transactionCategory, paymentMethod, date, false, excluded, currency, source));
                }
                else if("expense".equalsIgnoreCase(transactionType)) {
                	filtered.add(new Expense(transactionID, name, amount, transactionCategory, paymentMethod, date, essential, excluded, currency, essential));
                }
    		}
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	displayCards(filtered);
    }
    /**
     * Field ul pentru cautare
     */
    @FXML
    private TextField searchF;
    /**
     * Butonul pentru cautare
     */
    @FXML
    private Button searchBtn;
    
    /**
     * Cauta tranzactiile dupa nume
     */
    @FXML
    private void search() {
    	String text=searchF.getText().trim();
    	if(text.isEmpty()) {
    		loadTransactions();
    		return;
    	}
    	
    	List<Transaction> filtered=new ArrayList<>();
    	int userID=Session.getUID();
    	if(userID==-1) {
    		System.out.println("User not logged in");
    		return;
    	}
    	String query="SELECT * FROM transactions WHERE userID=? AND LOWER(name) LIKE ? ORDER BY transactionID DESC";
    	try(Connection connection=DatabaseConn.getConnection(); PreparedStatement stmt=connection.prepareStatement(query)){
    		stmt.setInt(1, userID);
    		stmt.setString(2, "%"+text.toLowerCase()+"%");
    		ResultSet result=stmt.executeQuery();
    		while(result.next()) {
    			int transactionID=result.getInt("transactionID");
                String name=result.getString("name");
                double amount=result.getDouble("amount");
                String transactionCategory=result.getString("category");
                String paymentMethod=result.getString("paymentMethod");
                LocalDate date=result.getDate("date").toLocalDate();
                boolean excluded=result.getInt("excludedFromReport")==1;
                String transactionType=result.getString("transactionType");
                String source=result.getString("source");
                int essentialVal=result.getInt("essential");
                String currency=result.getString("currency");
                boolean essential=essentialVal==1;
    			
                if("income".equalsIgnoreCase(transactionType)) {
                	filtered.add(new Income(transactionID, name, amount, transactionCategory, paymentMethod, date, false, excluded, currency, source));
                }
                else if("expense".equalsIgnoreCase(transactionType)) {
                	filtered.add(new Expense(transactionID, name, amount, transactionCategory, paymentMethod, date, essential, excluded, currency, essential));
                }
    		}
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	displayCards(filtered);
    }
    
    /**
     * Afiseaza cardurile tranzactiilor; elimina tot ce exista in container, dupa creeaza si adauga cate un card pentru fiecare tranzactie din lista
     * @param transactions lista de tranzactii care va fi afisata sub forma de carduri
     */
    public void displayCards(List<Transaction> transactions){
        box.getChildren().clear();
        for(Transaction transaction:transactions){
            Group card=createCard(transaction);
            box.getChildren().add(card);
        }
        box.setSpacing(10);
    }
    /**
     * Obtine din baza de date tranzactiile asociate utilizatorului curent pe baza id ului din sesiune
     */
    public void loadTransactions() {
    	List<Transaction> transactions=new ArrayList<>();
    	//sample
    	//transactions.add(new Income(1, "Salary", +2000.0, "Work", "Bank Transfer", LocalDate.paresulte("2024-11-12"), true, false, "Work"));
    	//transactions.add(new Expense(2, "Rent", -800.0, "Home", "Bank Transfer", LocalDate.paresulte("2024-11-01"), false, true, true));
    	int userID=Session.getUID(); //gets uid from session
    	if(userID==-1) {
    		System.out.println("User not logged in");
    		return;
    	}
    	String query="SELECT * FROM transactions WHERE userID=? ORDER BY transactionID DESC";
    	try(Connection connection=DatabaseConn.getConnection(); PreparedStatement stmt=connection.prepareStatement(query)){
    		stmt.setInt(1, userID);
    		ResultSet result=stmt.executeQuery();
    		while(result.next()) {
    			//extract values from columns
    			int transactionID=result.getInt("transactionID");
                String name=result.getString("name");
                double amount=result.getDouble("amount");
                String category=result.getString("category");
                String paymentMethod=result.getString("paymentMethod");
                LocalDate date=result.getDate("date").toLocalDate();
                boolean excluded=result.getInt("excludedFromReport") == 1;
                String transactionType=result.getString("transactionType");
                String source=result.getString("source");
                int essentialVal=result.getInt("essential");
                boolean essential=essentialVal==1;
                String currency=result.getString("currency");
                if("income".equalsIgnoreCase(transactionType)) { //add income object
        			transactions.add(new Income(transactionID, name, amount, category, paymentMethod, date, false, excluded, currency, source));
        		}
                else if("expense".equalsIgnoreCase(transactionType)) {//add expeense object
                	Expense expense=new Expense(transactionID, name, amount, category, paymentMethod, date, essential, excluded, currency, essential);
                	transactions.add(expense);
                }
    		}
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
        displayCards(transactions);
    }
    /**
     * Initializeaza elementele interfetei
     */
    @FXML
    public void initialize() {
    	//more design stuff
        box.setAlignment(javafx.geometry.Pos.TOP_CENTER);
        box.setPadding(new javafx.geometry.Insets(100, 0, 0, 0));
        scroll.setStyle("-fx-padding: 20px;");
        scroll.setFitToWidth(true);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        box.setMinWidth(Region.USE_PREF_SIZE);
        box.setMaxWidth(Double.MAX_VALUE);
        box.setMaxHeight(Double.MAX_VALUE);
        loadTransactions();
    }
}
