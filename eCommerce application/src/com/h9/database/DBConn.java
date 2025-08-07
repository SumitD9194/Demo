package com.h9.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConn {

    private static final String DB_URL =  "jdbc:mysql://localhost:3306/eCommerce?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "root";
   

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            createTables(conn);
            
            // Insert or update products
            insertProduct(conn, "Mobile - Galaxy S23", 799.99, 15, "Latest 5G smartphone with 128GB storage and AMOLED display");
            insertProduct(conn, "Smart Watch - FitPro", 199.99, 30, "Fitness tracker with heart rate monitor and GPS");
            insertProduct(conn, "Furniture - Dining Table", 499.99, 5, "Solid wood dining table for 6 people");
            
            insertProduct(conn, "Laptop - Dell Inspiron", 899.99, 10, "15.6 inch laptop with 16GB RAM and 512GB SSD");
            insertProduct(conn, "Camera - Canon EOS", 1199.99, 8, "Digital SLR camera with 24MP sensor");
            insertProduct(conn, "Headphones - Noise Cancelling", 149.99, 50, "Wireless over-ear noise-cancelling headphones");
            insertProduct(conn, "Monitor - 24 inch LED", 179.99, 20, "Full HD LED monitor with HDMI support");
            insertProduct(conn, "Tablet - iPad 10.2", 329.99, 18, "Apple iPad with 64GB storage and Retina display");
            insertProduct(conn, "Gaming Console - PlayBox", 499.99, 12, "Next-gen gaming console with 1TB storage");
            insertProduct(conn, "Router - WiFi 6", 129.99, 30, "Dual-band WiFi 6 router with gigabit ports");
            insertProduct(conn, "Keyboard - Mechanical", 89.99, 25, "RGB backlit mechanical keyboard with blue switches");
            insertProduct(conn, "Mouse - Wireless", 29.99, 60, "Ergonomic wireless mouse with adjustable DPI");
            insertProduct(conn, "Printer - Inkjet", 149.99, 15, "All-in-one inkjet printer with wireless printing");
            insertProduct(conn, "Speaker - Bluetooth", 59.99, 45, "Portable Bluetooth speaker with 10hr battery");
            insertProduct(conn, "Power Bank - 20000mAh", 39.99, 70, "Fast charging power bank with dual USB output");
            insertProduct(conn, "Watch - Classic Analog", 79.99, 20, "Stylish analog watch with leather strap");
            insertProduct(conn, "Shoe - Running Shoes", 89.99, 35, "Lightweight running shoes with breathable mesh");
            insertProduct(conn, "Jacket - Winter Coat", 129.99, 22, "Waterproof winter coat with insulation");
            insertProduct(conn, "Bag - Travel Backpack", 69.99, 40, "Durable backpack with 30L capacity");
            
            System.out.println("Product Insert Successfuly ");

        } catch (SQLException e) {
            e.printStackTrace();
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