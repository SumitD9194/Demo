package com.h9.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.h9.database.OrderDAO;
import com.h9.model.Order;
import com.h9.model.Product;

public class OrderService {
    private final OrderDAO orderDAO = new OrderDAO();
    private final ProductService productService = new ProductService();

    public int placeOrder(int userId, Map<Integer, Integer> items) throws SQLException {
        if (items.isEmpty()) throw new IllegalArgumentException("Cart is empty");
        double total = new CartService().calculateTotal();
        return orderDAO.placeOrder(userId, items, total);
    }

    public List<Order> getAllOrders() throws SQLException {
        return orderDAO.getAllOrders();
    }

    public String getAllOrdersDetails() throws SQLException {
        List<Order> orders = getAllOrders();
        if (orders.isEmpty()) return "No orders found!";

        StringBuilder result = new StringBuilder("\n=== All Orders ===\n");

        for (Order order : orders) {
            StringBuilder itemsDetails = new StringBuilder();
            for (Map.Entry<Integer, Integer> item : order.getProductQuantityMap().entrySet()) {
                try {
                    Product product = productService.getProductById(item.getKey());
                    if (product != null) {
                        itemsDetails.append(String.format("- %s: %d x $%.2f\n",
                                product.getName(), item.getValue(), product.getPrice()));
                    }
                } catch (SQLException e) {
                    itemsDetails.append("Error retrieving product: ").append(e.getMessage()).append("\n");
                }
            }

            result.append(String.format("Order ID: %d, User ID: %d, Total: $%.2f, Date: %s%nItems:%n%s%n",
                    order.getOrderId(), order.getUserId(), order.getTotalAmount(),
                    order.getTimestamp().toString(), itemsDetails.toString()));
        }

        return result.toString();
    }

}