package com.h9.database;
import java.sql.*;
import com.h9.model.User;

public class UserDAO {
    public void register(String name, String email, String password) throws SQLException {
        PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(
            "INSERT INTO users (name, email, password, is_admin) VALUES (?, ?, ?, false)");
        stmt.setString(1, name);
        stmt.setString(2, email);
        stmt.setString(3, password);
        stmt.executeUpdate();
    }

    public User login(String email, String password) throws SQLException {
        PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(
            "SELECT * FROM users WHERE email = ? AND password = ?");
        stmt.setString(1, email);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();
        return rs.next() ? new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"), 
                                   rs.getString("password"), rs.getBoolean("is_admin")) : null;
    }
}
