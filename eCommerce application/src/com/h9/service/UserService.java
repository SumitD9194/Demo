package com.h9.service;

import java.sql.SQLException;

import com.h9.database.UserDAO;
import com.h9.model.User;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    public void register(String name, String email, String password) throws SQLException {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("All fields are required");
        }
        userDAO.register(name, email, password);
    }

    public User login(String email, String password) throws SQLException {
        if (email.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Email and password are required");
        }
        return userDAO.login(email, password);
    }
}
