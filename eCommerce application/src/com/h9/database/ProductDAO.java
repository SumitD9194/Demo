package com.h9.database;
import com.h9.model.Product;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ProductDAO {
public List<Product> getAllProducts() throws SQLException {
	DatabaseConnection.initializeDatabase();
try (Statement stmt = DatabaseConnection.getConnection().createStatement();
		
   ResultSet rs = stmt.executeQuery("SELECT * FROM products")) {
  return Stream.of(rs).flatMap(r -> {
      List<Product> products = new ArrayList<>();
      try {
          while (r.next()) {
              products.add(new Product(r.getInt("id"), r.getString("name"), 
                                      r.getDouble("price"), r.getInt("quantity"), 
                                      r.getString("description")));
          }
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
      return products.stream();
  }).collect(Collectors.toList());
}
}
public void addProduct(String name, double price, int quantity, String description) throws SQLException {
try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(
  "INSERT INTO products (name, price, quantity, description) VALUES (?, ?, ?, ?)")) {
  stmt.setString(1, name);
  stmt.setDouble(2, price);
  stmt.setInt(3, quantity);
  stmt.setString(4, description);
  stmt.executeUpdate();
}
}

public void updateProduct(int id, String name, double price, int quantity, String description) throws SQLException {
StringBuilder sql = new StringBuilder("UPDATE products SET ");
List<Object> params = new ArrayList<>();
if (!name.isEmpty()) { sql.append("name = ?, "); params.add(name); }
if (price > 0) { sql.append("price = ?, "); params.add(price); }
if (quantity >= 0) { sql.append("quantity = ?, "); params.add(quantity); }
if (!description.isEmpty()) { sql.append("description = ?, "); params.add(description); }
sql.setLength(sql.length() - 2);
sql.append(" WHERE id = ?");
params.add(id);

try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql.toString())) {
  for (int i = 0; i < params.size(); i++) {
      stmt.setObject(i + 1, params.get(i));
  }
  stmt.executeUpdate();
}
}

public void deleteProduct(int id) throws SQLException {
try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(
  "DELETE FROM products WHERE id = ?")) {
  stmt.setInt(1, id);
  stmt.executeUpdate();
}
}

public Product getProductById(int id) throws SQLException {
try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(
  "SELECT * FROM products WHERE id = ?")) {
  stmt.setInt(1, id);
  ResultSet rs = stmt.executeQuery();
  return rs.next() ? new Product(rs.getInt("id"), rs.getString("name"), 
                               rs.getDouble("price"), rs.getInt("quantity"), 
                               rs.getString("description")) : null;
}
}
}