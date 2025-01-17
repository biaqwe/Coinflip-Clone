package entities;

import java.time.LocalDate;
/**
 * Clasa care reprezinta o cheltuiala, extinde clasa "Transaction" si are ca atribut specific "essential"
 */
public class Expense extends Transaction{
	/**
	 * Indica daca cheltuiala e esentiala
	 */
	private boolean essential;
	/**
	 * Constructor implicit
	 */
	public Expense() {}
	/**
	 * Constructor explicit
	 * @param id ID ul tranzactiei
	 * @param n numele tranzactiei
	 * @param a suma tranzactiei
	 * @param c categoria tranzactiei
	 * @param p modul de plata al tranzactiei
	 * @param d data in care a fost efectuata tranzactia
	 * @param s indica daca tranzactia e de tip abonament
	 * @param e indica daca tranzactia e exclusa din raport
	 * @param es indica daca tranzactia e esentiala 
	 */
	public Expense(int id, String n, double a, String c, String p, LocalDate d, boolean s, boolean e, String cr, boolean es) {
		super(id, n, a, c, p, d, s, e, cr);
		essential=es;
	}
	/**
	 * Returneaza daca cheltuiala e esentiala
	 * @return "true" daca este esentiala, "false" altfel
	 */
	public boolean isEssential() {
		return essential;
	}
	/**
	 * Daca cheltuiala e esentiala devine neesentiala si invers
	 */
	public void flipEssential() {
		essential=!(essential);
	}
	/**
	 * Seteaza daca cheluiala e esentiala sau nu
	 * @param e daca tranzactia e esentiala sau nu
	 */
	public void setEssential(boolean e) {
		essential=e;
	}
}
