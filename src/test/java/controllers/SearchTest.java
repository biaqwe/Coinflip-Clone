package controllers;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entities.Expense;
import entities.Income;
import entities.Transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchTest {
    @Test
    void testSearch() {
        List<Transaction> transactions=new ArrayList<>();
        transactions.add(new Income(1, "Salary", 2000.0, "Work", "Bank Transfer", LocalDate.of(2025, 1, 15), false, false, "EUR", "Company A"));
        transactions.add(new Expense(2, "Groceries", -150.0, "Food", "Credit Card", LocalDate.of(2025, 1, 12), false, false, "USD", true));
        transactions.add(new Expense(3, "Rent", -800.0, "Housing", "Bank Transfer", LocalDate.of(2025, 1, 1), false, false, "RON", true));

        String searchText="Groceries";
        List<Transaction> filtered=searchTransactions(transactions, searchText);

        assertEquals(1, filtered.size());
        assertEquals("Groceries", filtered.get(0).getName());
    }

    @Test
    void testNoMatch() {
        List<Transaction> transactions=new ArrayList<>();
        transactions.add(new Income(1, "Salary", 2000.0, "Work", "Bank Transfer", LocalDate.of(2025, 1, 15), false, false, "EUR", "Company A"));
        transactions.add(new Expense(2, "Groceries", -150.0, "Food", "Credit Card", LocalDate.of(2025, 1, 12), false, false, "USD", true));
        transactions.add(new Expense(3, "Rent", -800.0, "Housing", "Bank Transfer", LocalDate.of(2025, 1, 1), false, false, "RON", true));

        String searchText="blabla";
        List<Transaction> filtered=searchTransactions(transactions, searchText);

        assertEquals(0, filtered.size());
    }

    private List<Transaction> searchTransactions(List<Transaction> transactions, String searchText) {
        List<Transaction> filtered=new ArrayList<>();
        for(Transaction transaction:transactions) {
            if(transaction.getName().toLowerCase().contains(searchText.toLowerCase())) {
                filtered.add(transaction);
            }
        }
        return filtered;
    }
}
