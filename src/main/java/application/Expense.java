package application;

import java.time.LocalDate;

public class Expense extends Transaction{
	private boolean essential;
	
	public Expense() {}
	public Expense(int id, String n, double a, String c, String p, LocalDate d, boolean s, boolean e, boolean es) {
		super(id, n, a, c, p, d, s, e);
		essential=es;
	}
	public boolean isEssential() {
		return essential;
	}
	public void flipEssential() {
		essential=!(essential);
	}
}
