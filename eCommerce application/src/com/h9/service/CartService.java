package com.h9.service;



import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.h9.model.Product;

public class CartService {
    private Map<Integer, Integer> cart = new HashMap<>();
    private ProductService productService = new ProductService();

    public void addToCart(int productId, int quantity) throws SQLException {
        Product product = productService.getProductById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }
        if (quantity <= 0 || quantity > product.getQuantity()) {
            throw new IllegalArgumentException("Invalid quantity or insufficient stock");
        }
        cart.compute(productId, (k, v) -> v == null ? quantity : v + quantity);
    }

    public Map<Integer, Integer> getCart() {
        return cart;
    }

    public double calculateTotal() throws SQLException {
        return cart.entrySet().stream()
                .mapToDouble(e -> {
					try {
						return productService.getProductById(e.getKey()).getPrice() * e.getValue();
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					return 0;
				})
                .sum();
    }

    public void clearCart() {
        cart.clear();
    }

    public String getCartDetails() throws SQLException {
        return cart.isEmpty() ? "Cart is empty!" :
               cart.entrySet().stream()
                       .map(e -> {
                           Product p = null;
						try {
							p = productService.getProductById(e.getKey());
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                           return p != null ? String.format("ID: %d, Name: %s, Quantity: %d, Price: ₹%.2f, Total: ₹%.2f, Description: %s",
                                   p.getId(), p.getName(), e.getValue(), p.getPrice(), p.getPrice() * e.getValue(), p.getDescription()) : "";
                       })
                       .filter(s -> !s.isEmpty())
                       .collect(Collectors.joining("\n", "\n=== Your Cart ===\n", String.format("\nCart Total: ₹%.2f", calculateTotal())));
    }
}

