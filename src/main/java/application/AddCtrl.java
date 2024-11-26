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

public class AddCtrl {
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
    private Button closeBtn;
    //handles click on close button
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
    @FXML
    private Label srcLbl;
    @FXML
    private Label essLbl;
    @FXML
    private TextField srcField;
    @FXML
    private CheckBox essCb;
    @FXML
    private ComboBox select;
    @FXML
    private GridPane grid;
    
    //if transaction type is income adds source label and source text field to the form
    private void addSrc() {
    	Label srcLbl=new Label("Source");
    	srcLbl.setStyle("-fx-text-fill: #6b6290; -fx-font-size: 20px; -fx-font-family: 'HirukoPro-Book';");
    	srcField=new TextField();
    	srcField.setPrefHeight(31.0);
        srcField.setPrefWidth(160.0);
        srcField.setStyle("-fx-background-radius: 30px; -fx-border-color: #aab6fe; -fx-border-radius: 30px; -fx-border-width: 2px;");
        srcField.setId("srcField");
        grid.add(srcLbl, 0, 6);
        grid.add(srcField, 1, 6);
        GridPane.setColumnSpan(srcField, GridPane.REMAINING);
    }
    
    //if transaction type is expense adds expense label and source expense checkbox to the form
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
    
    
}
