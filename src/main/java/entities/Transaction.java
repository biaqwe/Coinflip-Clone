package entities;

import java.time.LocalDate;
/**
 * Clasa care reprezinta o tranzactie
 */
public class Transaction {
	/**
	 * ID ul tranzactiei
	 */
	private int transactionID;
	/**
	 * Numele tranzactiei
	 */
	private String name;
	/**
	 * Suma tranzactiei
	 */
	private double amount;
	/**
	 * Categoria tranzactiei
	 */
	private String category;
	/**
	 * Metoda de plata
	 */
	private String paymentMethod;
	/**
	 * Data efectuarii tranzactiei
	 */
	private LocalDate date;
	/**
	 * Indica daca tranzactia e de tip abonament
	 */
	private boolean subscription;
	/**
	 * Indica daca tranzactia e exclusa din raport
	 */
	private boolean excludedFromReport;
	/**
	 * Constructor implicit
	 */
	public Transaction() {}
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
	 */
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
	/**
	 * Returneaza id ul tranzactiei
	 * @return id ul tranzactiei
	 */
	public int getID() {
		return transactionID;
	}
	/**
	 * Returneaza numele tranzactiei
	 * @return numele tranzactiei
	 */
	public String getName() {
		return name;
	}
	/**
	 * Returneaza suma tranzactiei
	 * @return suma tranzactiei
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * Returneaza categoria tranzactiei
	 * @return categoria tranzactiei
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * Returneaza modul de plata al tranzactiei
	 * @return modul de plata al tranzactiei
	 */
	public String getPayment() {
		return paymentMethod;
	}
	/**
	 * Returneaza data la care a fost efectuata tranzactiei
	 * @return data la care a fost efectuata tranzactiei
	 */
	public LocalDate getDate() {
		return date;
	}
	/**
	 * Returneaza daca tranzactia e de tip abonament
	 * @return "true" daca e de tip abonament, "false" altfel
	 */
	public boolean isSubscription() {
		return subscription;
	}
	/**
	 * Returneaza daca tranzactia e exclusa din raport
	 * @return "true" daca e exclusa din raport, "false" altfel
	 */
	public boolean isExcluded() {
		return excludedFromReport;
	}
	/**
	 * Seteaza id ul tranzactiei
	 * @param id id ul tranzactiei
	 */
	public void setID(int id) {
		transactionID=id;
	}
	/**
	 * Seteaza valoarea numelui
	 * @param n valoarea numelui
	 */
	public void setName(String n) {
		name=n;
	}
	/**
	 * Seteaza valoarea sumei
	 * @param a valoarea sumei
	 */
	public void setAmount(double a) {
		amount=a;
	}
	/**
	 * Seteaza valoarea categoriei
	 * @param c valoarea categoriei
	 */
	public void setCategory(String c) {
		category=c;
	}
	/**
	 * Seteaza modul de plata
	 * @param p modul de plata
	 */
	public void setPayment(String p) {
		paymentMethod=p;
	}
	/**
	 * Seteaza data
	 * @param d data
	 */
	public void setDate(LocalDate d) {
		date=d;
	}
	/**
	 * Daca tranzactia e de tip abonament devine nu de tip abonament si invers
	 */
	public void flipSubscription() {
		subscription=!(subscription);
	}
	/**
	 * Daca cheltuiala e exclusa din raport devine inclusa in raport si invers
	 */
	public void flipExcluded() {
		excludedFromReport=!(excludedFromReport);
	}
	/**
	 * Seteaza daca tranzactia va fi exclusa din raport
	 * @param e daca tranzactia este sau nu exclusa din raport
	 */
	public void setExcluded(boolean e) {
		excludedFromReport=e;
	}
	/**
	 * Metoda de afisare
	 */
	@Override
	public String toString() {
		return "Transaction [amount=" + amount + ", date=" + date + ", category=" + category + "]";
	}

}
