package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.DatabaseConn;
import utils.Session;
/**
 * Clasa pentru pagina cu abonamente
 */
public class SubsCtrl {
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
        VBox container=new VBox(10);
        container.setAlignment(Pos.TOP_CENTER);
        
        if(transaction.isSubscription()) {
        	HBox renewalWrapper=new HBox(10);
        	renewalWrapper.setAlignment(Pos.CENTER);
        	Line left=new Line(0, 0, 10, 0);
        	left.setStroke(javafx.scene.paint.Color.valueOf("#6b6290"));
        	left.setStrokeWidth(2);
        	Line right=new Line(0, 0, 10, 0);
        	right.setStroke(javafx.scene.paint.Color.valueOf("#6b6290"));
        	right.setStrokeWidth(2);
        	
        	HBox renewalBox=new HBox(10);
        	renewalBox.setAlignment(Pos.CENTER_LEFT);

        	LocalDate next=transaction.getDate().plusMonths(1);
        	Text renewalText=new Text("Next renewal on "+next.format(DateTimeFormatter.ofPattern("dd "+"MMM")));
        	renewalText.setFill(javafx.scene.paint.Color.valueOf("#4a4a4a"));
        	renewalText.setFont(new Font("HirukoPro-Book", 16));
        	renewalBox.getChildren().add(renewalText);

        	Button cancelSub=new Button("Cancel");
        	cancelSub.setStyle("-fx-background-color: #c67ac8; -fx-background-radius: 20px; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand; -fx-font-family: HirukoPro-Book");
        	cancelSub.setOnAction(e->cancelSubscription(transaction));
        	renewalBox.getChildren().add(cancelSub);

        	renewalWrapper.getChildren().addAll(left, renewalBox, right);

        	container.getChildren().add(renewalWrapper);
        }
        
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
	        grid.add(btn, 2, 0);
        }
        else {
        	Button btn=new Button("Expense");  //if transaction is income add red button
	        btn.setStyle("-fx-background-color: #d9aeac; -fx-background-radius: 10px; -fx-min-width: 80px; -fx-min-height: 30px; -fx-max-width: 80px; -fx-max-height: 30px;");
	        btn.setTextFill(javafx.scene.paint.Color.WHITE);
	        btn.setFont(new Font("HirukoPro-Regular", 14));
	        GridPane.setHalignment(btn, javafx.geometry.HPos.RIGHT);
	        grid.add(btn, 2, 0);
        }
        //format amount to string
        DecimalFormat df=new DecimalFormat("#,###.00"); // , as thousand separator + always 2 decimals
        String amountStr=df.format(transaction.getAmount());
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
        
        container.getChildren().add(grid);
        card.getChildren().add(container);
        return card;
    }
    
    /**
     * Anuleaza un abonament: schimba atributul "subscription" al tranzactiei in 0
     * @param transaction tranzactia pentru care va fi anulat abonamentul
     */
    private void cancelSubscription(Transaction transaction) {
    	String query="UPDATE transactions SET subscription=0 WHERE transactionID=?";
    	try(Connection conn=DatabaseConn.getConnection(); PreparedStatement stmt=conn.prepareStatement(query)){
    		stmt.setInt(1, transaction.getID());
    		int rows=stmt.executeUpdate();
    		if(rows>0) {
    			System.out.println("Subscription cancelled: "+transaction.getName());
    			loadTransactions();
    		}
    		else {
    			System.out.println("Failed to cancel transaction: "+transaction.getName());
    		}
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
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
    	//only loads the last subscription of that name added to avoid doubles
    	String query="SELECT * FROM transactions t1 WHERE userID=? AND subscription=1 AND transactionID=(SELECT MAX(transactionID) FROM transactions t2 WHERE t1.name=t2.name AND t1.userID=t2.userID) ORDER BY transactionID DESC";
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
                boolean isSubscription=result.getInt("subscription")==1;
                String currency=result.getString("currency");
                if(result.getInt("subscription")==1) {
                	if("income".equalsIgnoreCase(transactionType)) { //add income object
            			transactions.add(new Income(transactionID, name, amount, category, paymentMethod, date, isSubscription, excluded, currency, source));
            		}
                    else if("expense".equalsIgnoreCase(transactionType)) {//add expeense object
                    	Expense expense=new Expense(transactionID, name, amount, category, paymentMethod, date, isSubscription, excluded, currency, essential);
                    	transactions.add(expense);
                    }
                }
                else {
                	System.out.println("Skipping non-subscription transaction: "+name);
                }
    		}
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
        displayCards(transactions);
    }
    
    /**
     * Reinnoieste un abonament in aceeasi zi in fiecare luna; verifica daca abonamentul a fost deja reinnoit in acea zi inainte sa il adauge din nou
     */
    public void renew() {
    	int userID=Session.getUID();
    	if(userID==-1) {
    		System.out.println("User not logged in");
    		return;
    	}
    	String query="SELECT * FROM transactions WHERE userID=? AND subscription=1";
    	LocalDate today=LocalDate.now();
    	try(Connection conn=DatabaseConn.getConnection(); PreparedStatement stmt=conn.prepareStatement(query)){
    		stmt.setInt(1, userID);
    		ResultSet result=stmt.executeQuery();
    		while(result.next()) {
    			int transactionID=result.getInt("transactionID");
                String name=result.getString("name");
                double amount=result.getDouble("amount");
                String category=result.getString("category");
                String paymentMethod=result.getString("paymentMethod");
                LocalDate date=result.getDate("date").toLocalDate();
                String transactionType=result.getString("transactionType");
                String source=result.getString("source");
                String currency=result.getString("currency");
                
                if(date.getDayOfMonth()==today.getDayOfMonth()) {
                	String checkQ="SELECT COUNT(*) FROM transactions WHERE userID=? AND name=? AND date=?";
                	try(PreparedStatement checkS=conn.prepareStatement(checkQ)){
                		checkS.setInt(1, userID);
                		checkS.setString(2, name);
                		checkS.setDate(3, java.sql.Date.valueOf(today));
                		ResultSet checkR=checkS.executeQuery();
                		if(checkR.next() && checkR.getInt(1)>0) {
                			System.out.println("Subscription already renewed today: "+name);
                			continue;
                		}
                	}
                	catch (Exception e) {
						e.printStackTrace();
					}
                	String insertQ="INSERT INTO transactions (userID, name, amount, category, paymentMethod, date, subscription, transactionType, source, currency) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                	try(PreparedStatement insertS=conn.prepareStatement(insertQ)){
                		insertS.setInt(1, userID);
                        insertS.setString(2, name);
                        insertS.setDouble(3, amount);
                        insertS.setString(4, category);
                        insertS.setString(5, paymentMethod);
                        insertS.setDate(6, java.sql.Date.valueOf(today));
                        insertS.setBoolean(7, true);
                        insertS.setString(8, transactionType);
                        insertS.setString(9, source);
                        insertS.setString(10, currency);
                        
                        int rows=insertS.executeUpdate();
                        if(rows>0) {
                        	System.out.println("Renewed subscription: "+name);
                        }
                	}
                	catch(Exception e) {
						e.printStackTrace();
					}
                }
    		}
    	}
    	catch(Exception e1) {
			e1.printStackTrace();
		}
    }

    /**
     * Initializeaza elementele interfetei
     */
    @FXML
    public void initialize() {
    	//more design stuff
        box.setAlignment(javafx.geometry.Pos.TOP_CENTER);
        box.setPadding(new javafx.geometry.Insets(57, 0, 0, 0));
        scroll.setStyle("-fx-padding: 20px;");
        scroll.setFitToWidth(true);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        box.setMinWidth(Region.USE_PREF_SIZE);
        box.setMaxWidth(Double.MAX_VALUE);
        box.setMaxHeight(Double.MAX_VALUE);
        loadTransactions();
    }
}
