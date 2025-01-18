package controllers;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entities.Expense;
import entities.Income;
import entities.Transaction;

class ReportTest extends ReportCtrl {
    protected List<Transaction> fetchTransactions(int year, int month) {
        List<Transaction> transactions=new ArrayList<>();
        transactions.add(new Income(1, "Salary", 2000.0, "Work", "Bank Transfer", LocalDate.of(2025, 1, 15), false, false, "EUR", "Company A"));
        transactions.add(new Expense(2, "Groceries", -150.0, "Food", "Credit Card", LocalDate.of(2025, 1, 12), false, false, "USD", true));
        transactions.add(new Expense(3, "Rent", -800.0, "Housing", "Bank Transfer", LocalDate.of(2025, 1, 1), false, false, "RON", true));
        return transactions;
    }

    @Test
    void testReport() {
        List<Transaction> transactions=fetchTransactions(2025, 1);
        double exchangeRateEUR=1.0;
        double exchangeRateUSD=1.08;
        double exchangeRateRON=4.95;
        double totalEarned=0;
        double totalSpent=0;
        int essentialExpenses=0;
        String topCategory="None";
        double maxExpense=Double.MIN_VALUE;

        for(Transaction transaction:transactions) {
            double amountInEUR=transaction.getAmount();
            if("USD".equals(transaction.getCurrency())) {
                amountInEUR/=exchangeRateUSD;
            }
            else if("RON".equals(transaction.getCurrency())) {
                amountInEUR/=exchangeRateRON;
            }

            if(transaction instanceof Income) {
                totalEarned+=amountInEUR;
                System.out.println("Income "+transaction.getName()+" Amount in EUR: "+amountInEUR);
            } 
            else if(transaction instanceof Expense) {
                totalSpent+=amountInEUR;
                Expense expense=(Expense) transaction;
                if(expense.isEssential()) {
                    essentialExpenses++;
                }
                double absAmount=Math.abs(amountInEUR);
                if(absAmount>maxExpense) {
                    maxExpense=absAmount;
                    topCategory=expense.getCategory();
                }
                System.out.println("Expense "+transaction.getName()+" Amount in EUR: "+amountInEUR);
            }
        }

        totalSpent=-totalSpent;
        double savings=totalEarned-totalSpent;

        System.out.println("Total Earned "+totalEarned);
        System.out.println("Total Spent "+totalSpent);
        System.out.println("Savings "+savings);
        System.out.println("Number of Essential Expenses: "+essentialExpenses);
        System.out.println("Top Expense Category: "+topCategory);

        assertEquals(2000.0, totalEarned, 0.01);
        assertEquals(300.50, totalSpent, 0.01);
        assertEquals(1699.49, savings, 0.01);
        assertEquals(2, essentialExpenses);
        assertEquals("Housing", topCategory);
    }
}
