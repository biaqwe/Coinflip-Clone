package application;
/**
 * Clasa pentru sesiunea utilizatorului
 */
public class Session { 
	/**
	 * id ul utilizatorului curent
	 */
	private static int userID;
	/**
	 * Returneaza id ul utilizatorului curent
	 * @return id ul utilizatorului curent
	 */
	public static int getUID() {
		return userID;
	}
	/**
	 * Seteaza id ul utilizatorului curent
	 * @param uid id ul nou
	 */
	public static void setUID(int uid) {
		userID=uid;
	}

}
