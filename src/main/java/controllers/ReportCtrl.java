package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.Expense;
import entities.Income;
import entities.Transaction;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
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
public class ReportCtrl {
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
     * ComboBox care permite selectarea anului pentru raport
     */
    @FXML
    private ComboBox<Integer> ySelect;
    /**
     * ComboBox care permite selectarea lunii pentru raport
     */
    @FXML
    private ComboBox<String> mSelect;
    /**
     * Buton care permite generarea raportului
     */
    @FXML
    private Button gen;
    @FXML
    private void initialize() {
    	int cyear=LocalDate.now().getYear();
    	for(int i=cyear; i>=cyear-10; i--) {
    		ySelect.getItems().add(i);
    	}
    	String[] months= {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    	mSelect.getItems().addAll(months);
    }
    
    private List<Transaction> fetchTransactions(int y, int m) {
        List<Transaction> transactions=new ArrayList<>();
        String query = "SELECT * FROM transactions WHERE userID=? AND excludedFromReport=false";
        try(Connection conn=DatabaseConn.getConnection(); PreparedStatement stmt=conn.prepareStatement(query)) {
            stmt.setInt(1, Session.getUID());
            ResultSet rs=stmt.executeQuery();
            while(rs.next()) {
                LocalDate transactionDate=rs.getDate("date").toLocalDate();
                if(transactionDate.getYear()==y && transactionDate.getMonthValue()==m) {
                    String type=rs.getString("transactionType");
                    String currency=rs.getString("currency");
                    if("Income".equalsIgnoreCase(type)) {
                        Income income=new Income();
                        income.setID(rs.getInt("transactionID"));
                        income.setName(rs.getString("name"));
                        income.setAmount(rs.getDouble("amount"));
                        income.setCategory(rs.getString("category"));
                        income.setPayment(rs.getString("paymentMethod"));
                        income.setExcluded(rs.getBoolean("excludedFromReport"));
                        income.setSource(rs.getString("source"));
                        income.setCurrency(currency);
                        transactions.add(income);
                    }
                    else if("Expense".equalsIgnoreCase(type)) {
                        Expense expense=new Expense();
                        expense.setID(rs.getInt("transactionID"));
                        expense.setName(rs.getString("name"));
                        expense.setAmount(rs.getDouble("amount"));
                        expense.setCategory(rs.getString("category"));
                        expense.setPayment(rs.getString("paymentMethod"));
                        expense.setExcluded(rs.getBoolean("excludedFromReport"));
                        expense.setEssential(rs.getBoolean("essential"));
                        expense.setCurrency(currency);
                        transactions.add(expense);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return transactions;
    }

    @FXML
    private void generate() {
        Integer y=ySelect.getValue();
        String m=mSelect.getValue();
        if(y==null || m==null) {
            System.out.println("Year or month not selected");
            return;
        }
        System.out.println("Selected Year: "+y+" Selected Month: "+m);
        int mIndex=mSelect.getItems().indexOf(m)+1;
        List<Transaction> transactions=fetchTransactions(y, mIndex);
        System.out.println("Transactions count: "+transactions.size());
        box.getChildren().clear();
        box.setAlignment(Pos.CENTER);

        VBox reportBox=new VBox(10);
        reportBox.setStyle("-fx-padding: 20px; -fx-background-color: rgba(255, 255, 255, 0.8); -fx-background-radius: 20; -fx-pref-width: 350; -fx-pref-height: 110; -fx-text-fill: #6b6290;");
        reportBox.setAlignment(Pos.CENTER_LEFT);

        if(transactions.isEmpty()) {
            Text noDataTxt=new Text("No transactions found for\n"+m+" "+y);
            noDataTxt.setStyle("-fx-font-size: 20px; -fx-font-family: 'HirukoPro-Book'; -fx-text-fill: #6b6290;");
            noDataTxt.setFill(javafx.scene.paint.Color.valueOf("#6b6290"));
            reportBox.getChildren().add(noDataTxt);
        }
        else {
            Map<String, Double> exchange=Map.of(
                "USD", 1.08,
                "EUR", 1.0,
                "RON", 4.95,
                "GBP", 0.85,
                "JPY", 142.0,
                "INR", 88.0
            );
            double totalEarned=0;
            double totalSpent=0;
            int essExp=0;

            for(Transaction transaction : transactions) {
                String currency=transaction.getCurrency();
                if(currency==null || !exchange.containsKey(currency)) {
                    System.out.println("Skipping transaction with invalid currency: "+transaction.getName());
                    continue;
                }

                double rate=exchange.get(currency);
                double converted=transaction.getAmount()/rate;

                if(transaction instanceof Income) {
                    totalEarned+=converted;
                }
                else if(transaction instanceof Expense) {
                    totalSpent-=converted;
                    if(((Expense) transaction).isEssential()) {
                        essExp++;
                    }
                }
            }
            //savings
            double savings=totalEarned-totalSpent;

            //top categ
            Map<String, Double> categMap=new HashMap<>();
            for(Transaction transaction : transactions) {
                if(transaction instanceof Expense) {
                    String currency=transaction.getCurrency();
                    if(currency==null || !exchange.containsKey(currency)) {
                    	continue;
                    }
                    double rate=exchange.get(currency);
                    double convertedAmount=transaction.getAmount()/rate;
                    categMap.merge(transaction.getCategory(), convertedAmount, Double::sum);
                }
            }
            List<Map.Entry<String, Double>> categSort=new ArrayList<>(categMap.entrySet());
            categSort.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

            // piecharttt
            PieChart pieChart=new PieChart();
            if(totalEarned>0) {
                PieChart.Data incomeData=new PieChart.Data("Incomes", totalEarned);
                pieChart.getData().add(incomeData);
                Platform.runLater(()-> {
                    if(incomeData.getNode()!=null) {
                        incomeData.getNode().setStyle("-fx-pie-color: #aad8b9;");
                    }
                });
            }
            if(totalSpent!=0) {
                PieChart.Data expensesData=new PieChart.Data("Expenses", Math.abs(totalSpent));
                pieChart.getData().add(expensesData);
                Platform.runLater(()-> {
                    if(expensesData.getNode()!=null) {
                        expensesData.getNode().setStyle("-fx-pie-color: #d9aeac;");
                    }
                });
            }
            pieChart.setLegendVisible(true);
            pieChart.setTitle(m+" "+y+" Report");
            pieChart.setStyle("-fx-font-family: 'HirukoPro-Book'; -fx-font-size: 14px; -fx-text-fill: #6b6290;");
            Platform.runLater(()-> {
                if(pieChart.lookup(".chart-title")!=null) {
                    pieChart.lookup(".chart-title").setStyle("-fx-font-family: 'HirukoPro-Book'; -fx-font-size: 20px; -fx-text-fill: #6b6290;");
                }
            });

            Text totalEarnedTxt=new Text("Total Earned: "+new DecimalFormat("#,##0.00").format(totalEarned)+" EUR");
            totalEarnedTxt.setStyle("-fx-font-size: 20px; -fx-font-family: 'HirukoPro-Book'; -fx-text-fill: #6b6290;");
            totalEarnedTxt.setFill(javafx.scene.paint.Color.valueOf("#6b6290"));

            Text totalSpentTxt=new Text("Total Spent: "+new DecimalFormat("#,##0.00").format(totalSpent)+" EUR");
            totalSpentTxt.setStyle("-fx-font-size: 20px; -fx-font-family: 'HirukoPro-Book'; -fx-text-fill: #6b6290;");
            totalSpentTxt.setFill(javafx.scene.paint.Color.valueOf("#6b6290"));

            Text essExpTxt=new Text("Number of essential expenses: "+essExp);
            essExpTxt.setStyle("-fx-font-size: 20px; -fx-font-family: 'HirukoPro-Book'; -fx-text-fill: #6b6290;");
            essExpTxt.setFill(javafx.scene.paint.Color.valueOf("#6b6290"));

            Text savingsTxt=new Text("Savings: "+new DecimalFormat("#,##0.00").format(savings)+" EUR");
            savingsTxt.setStyle("-fx-font-size: 20px; -fx-font-family: 'HirukoPro-Book'; -fx-text-fill: #6b6290;");
            savingsTxt.setFill(javafx.scene.paint.Color.valueOf("#6b6290"));

            Text topCategTxt=new Text("Top Expense Category: "+(categSort.isEmpty()?"None":categSort.get(0).getKey()));
            topCategTxt.setStyle("-fx-font-size: 20px; -fx-font-family: 'HirukoPro-Book'; -fx-text-fill: #6b6290;");
            topCategTxt.setFill(javafx.scene.paint.Color.valueOf("#6b6290"));

            reportBox.getChildren().addAll(pieChart, totalEarnedTxt, totalSpentTxt, savingsTxt, essExpTxt, topCategTxt);
        }
        VBox.setMargin(reportBox, new Insets(50, 0, 0, 28));
        box.getChildren().add(reportBox);
    }


}
