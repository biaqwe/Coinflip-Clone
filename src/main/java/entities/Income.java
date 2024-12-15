package entities;

import java.time.LocalDate;
/**
 * Clasa care reprezinta un venit, extinde clasa "Transaction" si are ca atribut specific "source"
 */

public class Income extends Transaction{
	/**
	 * Reprezinta sursa venitului
	 */
	private String source;
	/**
	 * Constructor implicit
	 */
	public Income() {}
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
	 * @param src sursa tranzactiei
	 */
	public Income(int id, String n, double a, String c, String p, LocalDate d, boolean s, boolean e, String src) {
		super(id, n, a, c, p, d, s, e);
		source=src;
	}
	/**
	 * Returneaza sursa venitului
	 * @return sursa venitului
	 */
	public String getSource() {
		return source;
	}
	/**
	 * Seteaza valoarea sursei
	 * @param src valoarea sursei
	 */
	public void setSource(String src) {
		source=src;
	}
}
