package com.h9.service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import com.h9.database.ProductDAO;
import com.h9.model.Product;

public class ProductService {
	
    private ProductDAO productDAO = new ProductDAO();

    public List<Product> getAllProducts() throws SQLException {
        return productDAO.getAllProducts();
    }

    public void addProduct(String name, double price, int quantity, String description) throws SQLException {
        if (name.isEmpty() || price <= 0 || quantity < 0 || description.isEmpty()) {
            throw new IllegalArgumentException("All product fields are required");
        }
        productDAO.addProduct(name, price, quantity, description);
    }

    public void updateProduct(int id, String name, double price, int quantity, String description) throws SQLException {
        productDAO.updateProduct(id, name, price, quantity, description);
    }

    public void deleteProduct(int id) throws SQLException {
        productDAO.deleteProduct(id);
    }

    public Product getProductById(int id) throws SQLException {
        return productDAO.getProductById(id);
    }

    public String searchProductsByName(String name) throws SQLException {
        return getAllProducts().stream()
                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                .map(p -> String.format("ID: %d, Name: %s, Price: ₹%.2f, Quantity: %d, Description: %s",
                        p.getId(), p.getName(), p.getPrice(), p.getQuantity(), p.getDescription()))
                .collect(Collectors.joining("\n", "\n=== Search Results ===\n", ""));
    }
}
