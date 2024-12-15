package controllers;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;

class LoginTest {
    private Login login;
    
    @BeforeEach
    void setUp() {
        login=new Login();
    }
    
    @Test
    void allValid() {
        String username="test";
        String password="test";
        int userID=login.getUID(username, password);
        assertEquals(3, userID);
    }

    @Test
    void invalidUsername() {
        String username="t";
        String password="test";
        int userID=login.getUID(username, password);
        assertEquals(-1, userID);
    }

    @Test
    void invalidPassword() {
        String username="test";
        String password="t";
        int userID=login.getUID(username, password);
        assertEquals(-1, userID);
    }

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
