package application;

import java.time.LocalDate;

public class Transaction {
	private int transactionID;
	private String name;
	private double amount;
	private String category;
	private String paymentMethod;
	private LocalDate date;
	private boolean subscription;
	private boolean excludedFromReport;
	
	public Transaction() {}
	public Transaction(int id, String n, double a, String c, String p, LocalDate d, boolean s, boolean e) {
		transactionID=id;
		name=n;
		amount=a;
		category=c;
		paymentMethod=p;
		date=d;
		subscription=s;
		excludedFromReport=e;
	}
	public int getID() {
		return transactionID;
	}
	public String getName() {
		return name;
	}
	public double getAmount() {
		return amount;
	}
	public String getCategory() {
		return category;
	}
	public String getPayment() {
		return paymentMethod;
	}
	public LocalDate getDate() {
		return date;
	}
	public boolean isSubscription() {
		return subscription;
	}
	public boolean isExcluded() {
		return excludedFromReport;
	}
	public void setName(String n) {
		name=n;
	}
	public void setAmount(double a) {
		amount=a;
	}
	public void setCategory(String c) {
		category=c;
	}
	public void setPayment(String p) {
		paymentMethod=p;
	}
	public void setDate(LocalDate d) {
		date=d;
	}
	public void flipSubscription() {
		subscription=!(subscription);
	}
	public void flipExcluded() {
		excludedFromReport=!(excludedFromReport);
	}
	
	@Override
	public String toString() {
		return "Transaction [amount=" + amount + ", date=" + date + ", category=" + category + "]";
	}

}
