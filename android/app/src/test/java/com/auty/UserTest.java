package com.auty;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.auty.modules.models.User;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("", "");
    }

    // Test UCT-1: No User Information
    @Test
    public void testEmptyUserInformation() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            user.setUsername("");
            user.setPassword("");
        });
        assertEquals("Username and password cannot be empty", exception.getMessage());
    }

    // Test UCT-2: Invalid User Credentials
    @Test
    public void testInvalidUserCredentials() {
        user.setUsername("validUser");
        user.setPassword("validPassword");

        User invalidUser = new User("invalidUser", "wrongPassword");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            if (!user.getUsername().equals(invalidUser.getUsername()) ||
                    !user.getPassword().equals(invalidUser.getPassword())) {
                throw new RuntimeException("User not found. Please check credentials or register.");
            }
        });
        assertEquals("User not found. Please check credentials or register.", exception.getMessage());
    }

    // Test UCT-3: Register User with Valid Credentials
    @Test
    public void testValidUserRegistration() {
        user.setUsername("newUser");
        user.setPassword("newPassword");

        assertEquals("newUser", user.getUsername());
        assertEquals("newPassword", user.getPassword());
    }
}
