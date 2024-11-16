package application;

import java.time.LocalDate;

public class Income extends Transaction{
	private String source;
	
	public Income() {}
	public Income(int id, String n, double a, String c, String p, LocalDate d, boolean s, boolean e, String src) {
		super(id, n, a, c, p, d, s, e);
		source=src;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String src) {
		source=src;
	}
}
