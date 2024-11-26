package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainCtrl {
    @FXML
    private Button logoutBtn;
    
    //handles click on logout button
    @FXML
    private void logout() {
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root=loader.load();
            Stage stage=(Stage) logoutBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private Button addBtn;
    @FXML
    private void addBtnClick() {
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("Add.fxml"));
            Parent root=loader.load();
            String css = getClass().getResource("application.css").toExternalForm();
            root.getStylesheets().add(css);
            Stage stage=(Stage) addBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private VBox box;
    @FXML
    private ScrollPane scroll;
    //creates transaction cards
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
    
    public void edit(Transaction transaction) {
    	try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("Edit.fxml"));
            Parent root=loader.load();
            String css = getClass().getResource("application.css").toExternalForm();
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
    
    //displays transaction cards in vbox
    public void displayCards(List<Transaction> transactions){
        box.getChildren().clear();
        for(Transaction transaction:transactions){
            Group card=createCard(transaction);
            box.getChildren().add(card);
        }
        box.setSpacing(10);
    }

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
    	String query="SELECT * FROM transactions WHERE userID=?";
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
                boolean essential=result.getInt("essential") == 1;
                if("income".equalsIgnoreCase(transactionType)) { //add income object
        			transactions.add(new Income(transactionID, name, amount, category, paymentMethod, date, false, excluded, source));
        		}
                else if("expense".equalsIgnoreCase(transactionType)) {//add expeense object
                	transactions.add(new Expense(transactionID, name, amount, category, paymentMethod, date, essential, excluded, false));
                }
    		}
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
        displayCards(transactions);
    }

    //load transactions on scene load
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
