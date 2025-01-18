package controllers;

import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import utils.DatabaseConn;
import utils.Session;
import static org.junit.jupiter.api.Assertions.*;

public class SubsTest {

    @Test
    void testRenew() throws Exception {
        Session.setUID(3);
        LocalDate today=LocalDate.now();
        LocalDate lastMonth=today.minusMonths(1);
        String insertQuery="INSERT INTO transactions (userID, name, amount, category, paymentMethod, date, subscription, transactionType, source, currency) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn=DatabaseConn.getConnection(); PreparedStatement stmt=conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, 3);
            stmt.setString(2, "Test Subscription");
            stmt.setDouble(3, 10.99);
            stmt.setString(4, "Test Category");
            stmt.setString(5, "Credit Card");
            stmt.setDate(6, java.sql.Date.valueOf(lastMonth));
            stmt.setBoolean(7, true);
            stmt.setString(8, "expense");
            stmt.setString(9, "Test Source");
            stmt.setString(10, "USD");
            stmt.executeUpdate();
        }

        SubsCtrl subsCtrl=new SubsCtrl();
        subsCtrl.renew();
        
        String verifyQuery="SELECT * FROM transactions WHERE userID=? AND name=? AND date=?";
        try (Connection conn=DatabaseConn.getConnection(); PreparedStatement stmt=conn.prepareStatement(verifyQuery)) {
            stmt.setInt(1, 3);
            stmt.setString(2, "Test Subscription");
            stmt.setDate(3, java.sql.Date.valueOf(today));
            ResultSet rs=stmt.executeQuery();
            assertTrue(rs.next(), "Subscription should be renewed for today");
            assertEquals(3, rs.getInt("userID"), "User ID should match");
            assertEquals("Test Subscription", rs.getString("name"), "Name should match");
            assertEquals(10.99, rs.getDouble("amount"), 0.01, "Amount should match");
            assertEquals("Test Category", rs.getString("category"), "Category should match");
            assertEquals("Credit Card", rs.getString("paymentMethod"), "Payment method should match");
            assertEquals("expense", rs.getString("transactionType"), "Transaction type should match");
            assertEquals("USD", rs.getString("currency"), "Currency should match");
        }
    }
}
