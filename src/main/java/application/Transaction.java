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
	
	@Override
	public String toString() {
		return "Transaction [amount=" + amount + ", date=" + date + ", category=" + category + "]";
	}

}
