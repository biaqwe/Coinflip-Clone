package controllers;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import utils.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import utils.DatabaseConn;

public class EditCtrlTest {

    @Test
    public void testSessionGetUID() {
        Session.setUID(3);
        assertEquals(3, Session.getUID());
    }

    @Test
    public void incomeToDb() {
        int transactionID=98;
        String name="test edit";
        double amount=150.0;
        String category="test edit";
        String paymentMethod="test edit";
        boolean subscription=true;
        boolean excludedFromReport=true;
        String transactionType="Income";
        String source="test edit";
        boolean essential=false;
        String currency="USD";
        String query="UPDATE transactions SET name=?, amount=?, category=?, paymentMethod=?, subscription=?, excludedFromReport=?, transactionType=?, source=?, essential=?, currency=? WHERE transactionID=?";
        try(Connection connection=DatabaseConn.getConnection(); PreparedStatement stmt=connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setDouble(2, amount);
            stmt.setString(3, category);
            stmt.setString(4, paymentMethod);
            stmt.setBoolean(5, subscription);
            stmt.setBoolean(6, excludedFromReport);
            stmt.setString(7, transactionType);
            stmt.setString(8, source);
            stmt.setBoolean(9, essential);
            stmt.setString(10, currency);
            stmt.setInt(11, transactionID);
            int rows=stmt.executeUpdate();
            assertTrue(rows>0);
        }
        catch(SQLException e) {
            fail("Error adding transaction "+e.getMessage());
        }
    }
    
    @Test
    public void expenseToDb() {
    	int transactionID=97;
        String name="test edit";
        double amount=150.0;
        amount=-Math.abs(amount);
        String category="test edit";
        String paymentMethod="test edit";
        boolean subscription=true;
        boolean excludedFromReport=true;
        String transactionType="Expense";
        String source=null;
        boolean essential=true;
        String currency="USD";
        String query="UPDATE transactions SET name=?, amount=?, category=?, paymentMethod=?, subscription=?, excludedFromReport=?, transactionType=?, source=?, essential=?, currency=? WHERE transactionID=?";
        try(Connection connection=DatabaseConn.getConnection(); PreparedStatement stmt=connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setDouble(2, amount);
            stmt.setString(3, category);
            stmt.setString(4, paymentMethod);
            stmt.setBoolean(5, subscription);
            stmt.setBoolean(6, excludedFromReport);
            stmt.setString(7, transactionType);
            stmt.setString(8, source);
            stmt.setBoolean(9, essential);
            stmt.setString(10, currency);
            stmt.setInt(11, transactionID);
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

        String invalidAmount="a";
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
