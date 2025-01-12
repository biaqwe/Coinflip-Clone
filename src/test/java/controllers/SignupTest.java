package controllers;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;

class SignupTest {
    private Signup signup;
    
    @BeforeEach
    void setUp() {
        signup=new Signup();
    }

    @Test
    void usernameTaken() {
        String username="test";
        boolean isValid=signup.valid(username);
        assertTrue(isValid);
    }

    @Test
    void usernameNotTaken() {
        String username="neww";
        boolean isValid=signup.valid(username);
        assertFalse(isValid);
    }

    @Test
    void addUserValid() {
        String username="neww";
        String password="pass";
        boolean result=signup.addUser(username, password);
        assertTrue(result);
    }

    @Test
    void addUserAlreadyExists() {
        String username="test";
        String password="test";
        boolean result=signup.addUser(username, password);
        assertFalse(result);
    }

    @Test
    void addUserEmptyFields() {
        String username="";
        String password="";
        boolean result=signup.addUser(username, password);
        assertFalse(result);
    }
}
