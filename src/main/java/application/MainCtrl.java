package application;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
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
    private VBox box;
    @FXML
    private ScrollPane scroll;
    //creates transaction cards
    @FXML
    private Group createCard(Transaction transaction) {
        Group card=new Group();
        GridPane grid=new GridPane();
        grid.setStyle("-fx-background-color: white; -fx-background-radius: 20px; -fx-padding: 10px;");
        grid.getColumnConstraints().addAll(new ColumnConstraints(100), new ColumnConstraints(100), new ColumnConstraints(100));
        grid.getRowConstraints().addAll(new RowConstraints(30), new RowConstraints(30), new RowConstraints(30));

        Text name=new Text(transaction.getName());
        name.setFill(javafx.scene.paint.Color.valueOf("#6b6290"));
        name.setFont(new Font("HirukoPro-Book", 20));
        GridPane.setHalignment(name, javafx.geometry.HPos.LEFT);
        grid.add(name, 0, 0);

        if(transaction instanceof Income) {
	        Button btn=new Button("Income");
	        btn.setStyle("-fx-background-color: #aad8b9; -fx-background-radius: 10px; -fx-cursor: hand;");
	        btn.setTextFill(javafx.scene.paint.Color.WHITE);
	        btn.setFont(new Font("HirukoPro-Regular", 14));
	        GridPane.setHalignment(btn, javafx.geometry.HPos.RIGHT);
	        grid.add(btn, 2, 0);
        }
        else {
        	Button btn=new Button("Expense");
	        btn.setStyle("-fx-background-color: #d9aeac; -fx-background-radius: 10px; -fx-cursor: hand;");
	        btn.setTextFill(javafx.scene.paint.Color.WHITE);
	        btn.setFont(new Font("HirukoPro-Regular", 14));
	        GridPane.setHalignment(btn, javafx.geometry.HPos.RIGHT);
	        grid.add(btn, 2, 0);
        }

        DecimalFormat df=new DecimalFormat("#,###.00");
        String amountStr=df.format(transaction.getAmount());
        Text amount=new Text(amountStr);
        amount.setFill(javafx.scene.paint.Color.valueOf("#6b6290"));
        amount.setFont(new Font("HirukoPro-Book", 18));
        GridPane.setHalignment(amount, javafx.geometry.HPos.LEFT);
        grid.add(amount, 0, 1);

        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateStr=transaction.getDate().format(dtf);
        Text date=new Text(dateStr);
        date.setFill(javafx.scene.paint.Color.valueOf("#6b6290"));
        date.setFont(new Font("HirukoPro-Book", 17));
        GridPane.setHalignment(date, javafx.geometry.HPos.CENTER);
        grid.add(date, 2, 1);

        Button categ=new Button("Category");
        categ.setStyle("-fx-background-color: #d6c8ff; -fx-background-radius: 10px;");
        categ.setTextFill(javafx.scene.paint.Color.WHITE);
        categ.setFont(new Font("HirukoPro-Regular", 14));
        GridPane.setHalignment(categ, javafx.geometry.HPos.LEFT);
        grid.add(categ, 0, 2);
        
        if(transaction instanceof Income income) {
            Button src=new Button(income.getSource());
            src.setStyle("-fx-background-color: #ffc8a2; -fx-background-radius: 10px;");
            src.setTextFill(javafx.scene.paint.Color.WHITE);
            src.setFont(new Font("HirukoPro-Regular", 14));
            GridPane.setHalignment(src, javafx.geometry.HPos.CENTER);
            grid.add(src, 1, 2);
        }
        else if(transaction instanceof Expense expense) {
        	Button ess=new Button(expense.isEssential() ? "Essential":"Not Essential");
            ess.setStyle("-fx-background-color: #ffc8a2; -fx-background-radius: 10px;");
            ess.setTextFill(javafx.scene.paint.Color.WHITE);
            ess.setFont(new Font("HirukoPro-Regular", 14));
            GridPane.setHalignment(ess, javafx.geometry.HPos.CENTER);
            grid.add(ess, 1, 2);
        	
        }
        
        if(transaction.isExcluded()) {
        	Button excl=new Button("Excluded");
        	excl.setStyle("-fx-background-color: #aab6fe; -fx-background-radius: 10px;");
            excl.setTextFill(javafx.scene.paint.Color.WHITE);
            excl.setFont(new Font("HirukoPro-Regular", 14));
            GridPane.setHalignment(excl, javafx.geometry.HPos.RIGHT);
            grid.add(excl, 2, 2);
        }
        else {
        	Button excl=new Button("Included");
        	excl.setStyle("-fx-background-color: #aab6fe; -fx-background-radius: 10px;");
            excl.setTextFill(javafx.scene.paint.Color.WHITE);
            excl.setFont(new Font("HirukoPro-Regular", 14));
            GridPane.setHalignment(excl, javafx.geometry.HPos.RIGHT);
            grid.add(excl, 2, 2);
        }
        
        card.getChildren().add(grid);
        return card;
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
    	transactions.add(new Income(1, "Salary", +2000.0, "Work", "Bank Transfer", LocalDate.parse("2024-11-12"), true, false, "Work"));
    	transactions.add(new Expense(2, "Rent", -800.0, "Home", "Bank Transfer", LocalDate.parse("2024-11-01"), false, true, true));
        displayCards(transactions);
    }

    //load transactions on scene load
    @FXML
    public void initialize() {
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
