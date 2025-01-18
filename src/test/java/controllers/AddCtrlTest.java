package controllers;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import utils.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import utils.DatabaseConn;

public class AddCtrlTest {

    @Test
    public void testSessionGetUID() {
        Session.setUID(3);
        assertEquals(3, Session.getUID());
    }

    @Test
    public void incomeToDb() {
        int userID=3;
        String name="test";
        double amount=50.0;
        String category="test";
        String paymentMethod="test";
        boolean subscription=false;
        boolean excludedFromReport=false;
        String transactionType="Income";
        String source="test";
        boolean essential=false;
        String currency="EUR";
        String query="INSERT INTO transactions (userID, name, amount, category, paymentMethod, date, subscription, excludedFromReport, transactionType, source, essential, currency) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try(Connection connection=DatabaseConn.getConnection(); PreparedStatement stmt=connection.prepareStatement(query)) {
            stmt.setInt(1, userID);
            stmt.setString(2, name);
            stmt.setDouble(3, amount);
            stmt.setString(4, category);
            stmt.setString(5, paymentMethod);
            stmt.setDate(6, java.sql.Date.valueOf(java.time.LocalDate.now()));
            stmt.setBoolean(7, subscription);
            stmt.setBoolean(8, excludedFromReport);
            stmt.setString(9, transactionType);
            stmt.setString(10, source);
            stmt.setBoolean(11, essential);
            stmt.setString(12, currency);
            int rows=stmt.executeUpdate();
            assertTrue(rows>0);
        }
        catch(SQLException e) {
            fail("Error adding transaction "+e.getMessage());
        }
    }
    
    @Test
    public void expenseToDb() {
        int userID=3;
        String name="test";
        double amount=50.0;
        amount=-Math.abs(amount);
        String category="test";
        String paymentMethod="test";
        boolean subscription=false;
        boolean excludedFromReport=false;
        String transactionType="Expense";
        String source=null;
        boolean essential=true;
        String currency="EUR";
        String query="INSERT INTO transactions (userID, name, amount, category, paymentMethod, date, subscription, excludedFromReport, transactionType, source, essential, currency) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try(Connection connection=DatabaseConn.getConnection(); PreparedStatement stmt=connection.prepareStatement(query)) {
            stmt.setInt(1, userID);
            stmt.setString(2, name);
            stmt.setDouble(3, amount);
            stmt.setString(4, category);
            stmt.setString(5, paymentMethod);
            stmt.setDate(6, java.sql.Date.valueOf(java.time.LocalDate.now()));
            stmt.setBoolean(7, subscription);
            stmt.setBoolean(8, excludedFromReport);
            stmt.setString(9, transactionType);
            stmt.setString(10, source);
            stmt.setBoolean(11, essential);
            stmt.setString(12, currency);
            int rows=stmt.executeUpdate();
            assertTrue(rows>0);
        }
        catch(SQLException e) {
            fail("Error adding transaction "+e.getMessage());
        }
    }

    @Test
    public void testTransactionValidation() {
        AddCtrl addCtrl=new AddCtrl();
        String validName="test";
        String validCategory="test";
        double validAmount=100;
        String validCurrency="EUR";
        assertDoesNotThrow(()-> {
            addCtrl.ok(validName, String.valueOf(validAmount), validCategory, "Income", "test", validCurrency);
        });
        
        String invalidName=null;
        assertThrows(NullPointerException.class, ()-> {
            addCtrl.ok(invalidName, String.valueOf(validAmount), validCategory, "Income", "test", validCurrency);
        });

        String invalidAmount="-10";
        assertThrows(IllegalArgumentException.class, ()-> {
            addCtrl.ok(validName, invalidAmount, validCategory, "Income", "test", validCurrency);
        });

        String invalidCategory=null;
        assertThrows(NullPointerException.class, ()-> {
            addCtrl.ok(validName, String.valueOf(validAmount), invalidCategory, "Income", "test", validCurrency);
        });

        assertThrows(NullPointerException.class, ()-> {
            addCtrl.ok(validName, String.valueOf(validAmount), validCategory, "Income", "", validCurrency);
        });
        
        String invalidCurrency=null;
        assertThrows(NullPointerException.class, ()-> {
        	addCtrl.ok(validName, String.valueOf(validAmount), validCategory, "Income", "test", invalidCurrency);
        });
        
        assertThrows(NullPointerException.class, ()-> {
        	addCtrl.ok(validName, String.valueOf(validAmount), validCategory, "Income", "test", "");
        });
    }
}
