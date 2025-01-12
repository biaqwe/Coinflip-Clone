package controllers;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;
/**
 * Clasa de test pentru functionalitatea de autentificare
 */
class LoginTest {
	/**
	 * Obiect de tip Login
	 */
    private Login login;
    
    /**
     * Initializarea obiectului Login inainte de fiecare test
     */
    @BeforeEach
    void setUp() {
        login=new Login();
    }
    
    /**
     * Testeaza scenariul in care numele de utilizator si parola sunt valide, verifica daca este returnat id ul corect pentru utilizator
     */
    @Test
    void allValid() {
        String username="test";
        String password="test";
        int userID=login.getUID(username, password);
        assertEquals(3, userID);
    }

    /**
     * Testeaza scenariul in care numele de utilizator nu e valid, verifica daca este returnat -1 pentru utilizator invalid
     */
    @Test
    void invalidUsername() {
        String username="t";
        String password="test";
        int userID=login.getUID(username, password);
        assertEquals(-1, userID);
    }

    /**
     * Testeaza scenariul in care parola nu e valida, verifica daca este returnat -1 pentru utilizator invalid
     */
    @Test
    void invalidPassword() {
        String username="test";
        String password="t";
        int userID=login.getUID(username, password);
        assertEquals(-1, userID);
    }

    /**
     * Testeaza scenariul in care numele de utilizator si parola nu sunt valide, verifica daca este returnat -1 pentru utilizator invalid
     */
    @Test
    void allInvalid() {
        String username="miau";
        String password="mau";
        int userID=login.getUID(username, password);
        assertEquals(-1, userID);
    }

    @Test
    void empty() {
        String username="";
        String password="";
        int userID=login.getUID(username, password);
        assertEquals(-1, userID);
    }
}
