package com.h9.database;

import java.sql.*;
import java.util.*;
import com.h9.model.Order;

public class OrderDAO {
    public int placeOrder(int userId, Map<Integer, Integer> items, double total) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        conn.setAutoCommit(false);
        try {
            PreparedStatement orderStmt = conn.prepareStatement(
                "INSERT INTO orders (user_id, total, order_date) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
            orderStmt.setInt(1, userId);
            orderStmt.setDouble(2, total);
            orderStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            orderStmt.executeUpdate();

            ResultSet rs = orderStmt.getGeneratedKeys();
            int orderId = rs.next() ? rs.getInt(1) : -1;

            PreparedStatement itemStmt = conn.prepareStatement(
                "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)");
            PreparedStatement updateStockStmt = conn.prepareStatement(
                "UPDATE products SET quantity = quantity - ? WHERE id = ?");

            items.entrySet().stream().forEach(entry -> {
                try {
                    PreparedStatement checkStock = conn.prepareStatement(
                        "SELECT quantity FROM products WHERE id = ?");
                    checkStock.setInt(1, entry.getKey());
                    ResultSet stockRs = checkStock.executeQuery();
                    if (!stockRs.next() || stockRs.getInt("quantity") < entry.getValue()) {
                        throw new SQLException("Insufficient stock for product ID: " + entry.getKey());
                    }

                    itemStmt.setInt(1, orderId);
                    itemStmt.setInt(2, entry.getKey());
                    itemStmt.setInt(3, entry.getValue());
                    itemStmt.executeUpdate();

                    updateStockStmt.setInt(1, entry.getValue());
                    updateStockStmt.setInt(2, entry.getKey());
                    updateStockStmt.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

            conn.commit();
            return orderId;
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM orders");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Map<Integer, Integer> items = new HashMap<>();
                try (PreparedStatement itemStmt = DatabaseConnection.getConnection().prepareStatement(
                    "SELECT product_id, quantity FROM order_items WHERE order_id = ?")) {
                    itemStmt.setInt(1, rs.getInt("order_id"));
                    ResultSet itemRs = itemStmt.executeQuery();
                    while (itemRs.next()) {
                        items.put(itemRs.getInt("product_id"), itemRs.getInt("quantity"));
                    }
                }
                orders.add(new Order(rs.getInt("order_id"), rs.getInt("user_id"), 
                                    items, rs.getDouble("total"), rs.getTimestamp("order_date")));
            }
        }
        return orders;
    }

    public Map<String, Double> getSalesReport() throws SQLException {
        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as total_orders, SUM(total) as total_revenue FROM orders")) {
            return rs.next() ? Map.of("total_orders", (double) rs.getInt("total_orders"), 
                                     "total_revenue", rs.getDouble("total_revenue")) : Map.of();
        }
    }
}