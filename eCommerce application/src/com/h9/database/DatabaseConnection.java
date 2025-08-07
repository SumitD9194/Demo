package com.h9.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DatabaseConnection {
    private static Connection conn;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/eCommerce?useSSL=false&serverTimezone=UTC";
                String username = "root"; 
                String password = "root"; 
                conn = DriverManager.getConnection(url, username, password);
            } catch (ClassNotFoundException e) {
                System.out.println("MySQL JDBC Driver not found: " + e.getMessage());
            } catch (SQLException e) {
                System.out.println("Database connection error: " + e.getMessage());
            }
        }
        return conn;
    }
// initializeDatabase();
   public static void initializeDatabase() {
        try {
        	Connection conn= getConnection();
        	createTables(conn);
            insertProduct(conn, "Mobile - Galaxy S23", 40000.00, 15, "Latest 5G smartphone with 128GB storage and AMOLED display");
            insertProduct(conn, "Smart Watch - FitPro", 1999.99, 30, "Fitness tracker with heart rate monitor and GPS");
            insertProduct(conn, "Furniture - Dining Table", 4995.99, 5, "Solid wood dining table for 6 people");
            
            insertProduct(conn, "Laptop - Dell Inspiron", 89969.99, 10, "15.6 inch laptop with 16GB RAM and 512GB SSD");
            insertProduct(conn, "Camera - Canon EOS", 11199.99, 8, "Digital SLR camera with 24MP sensor");
            insertProduct(conn, "Headphones - Noise Cancelling", 1490.99, 50, "Wireless over-ear noise-cancelling headphones");
            insertProduct(conn, "Monitor - 24 inch LED", 17900.99, 20, "Full HD LED monitor with HDMI support");
            insertProduct(conn, "Tablet - iPad 10.2", 33329.99, 18, "Apple iPad with 64GB storage and Retina display");
            insertProduct(conn, "Gaming Console - PlayBox", 18499.99, 12, "Next-gen gaming console with 1TB storage");
            insertProduct(conn, "Router - WiFi 6", 1229.99, 30, "Dual-band WiFi 6 router with gigabit ports");
            insertProduct(conn, "Keyboard - Mechanical", 189.99, 25, "RGB backlit mechanical keyboard with blue switches");
            insertProduct(conn, "Mouse - Wireless", 299.99, 60, "Ergonomic wireless mouse with adjustable DPI");
            insertProduct(conn, "Printer - Inkjet", 11549.99, 15, "All-in-one inkjet printer with wireless printing");
            insertProduct(conn, "Speaker - Bluetooth", 599.99, 45, "Portable Bluetooth speaker with 10hr battery");
            insertProduct(conn, "Power Bank - 20000mAh", 799.99, 70, "Fast charging power bank with dual USB output");
            insertProduct(conn, "Watch - Classic Analog", 1799.99, 20, "Stylish analog watch with leather strap");
            insertProduct(conn, "Shoe - Running Shoes", 1989.99, 35, "Lightweight running shoes with breathable mesh");
            insertProduct(conn, "Jacket - Winter Coat", 1529.99, 22, "Waterproof winter coat with insulation");
            insertProduct(conn, "Bag - Travel Backpack", 1669.99, 40, "Durable backpack with 30L capacity");
            System.out.println("Product Insert Successfuly ");
           
            insertAdminIfNotExists(conn, "Admin", "admin@store.com", "admin123", true);
        } catch (Exception e) {
            System.out.println("Database initialization error: " + e.getMessage());
        }
    }
    private static void insertAdminIfNotExists(Connection conn, String name, String email, String password, boolean isAdmin) throws SQLException {
        String checkSql = "SELECT COUNT(*) FROM users WHERE email = ?";
        String insertSql = "INSERT INTO users (name, email, password, is_admin) VALUES (?, ?, ?, ?)";

        try (
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            PreparedStatement insertStmt = conn.prepareStatement(insertSql)
        ) {
            checkStmt.setString(1, email);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();

            if (rs.getInt(1) == 0) {
                insertStmt.setString(1, name);
                insertStmt.setString(2, email);
                insertStmt.setString(3, password);
                insertStmt.setBoolean(4, isAdmin);
                insertStmt.executeUpdate();
                System.out.println("Admin user inserted.");
            } else {
                System.out.println("Admin user already exists.");
            }
        }
    }
private static void createTables(Connection conn) throws SQLException {
    Statement stmt = conn.createStatement();

    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS products (" +
            "id INT PRIMARY KEY AUTO_INCREMENT," +
            "name VARCHAR(255) UNIQUE," +  
            "price DOUBLE," +
            "quantity INT," +
            "description TEXT)");
}

private static void insertProduct(Connection conn, String name, double price, int quantity, String description) throws SQLException {
    String sql = "INSERT INTO products (name, price, quantity, description) VALUES (?, ?, ?, ?) " +
                 "ON DUPLICATE KEY UPDATE price = VALUES(price), quantity = VALUES(quantity), description = VALUES(description)";
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, name);
        pstmt.setDouble(2, price);
        pstmt.setInt(3, quantity);
        pstmt.setString(4, description);
        pstmt.executeUpdate();
    }
}
}

