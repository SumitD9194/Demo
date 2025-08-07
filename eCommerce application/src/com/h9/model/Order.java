package com.h9.model;
import java.sql.Timestamp;
import java.util.Map;

public class Order {
    private int orderId;
    private int userId;
    private Map<Integer, Integer> productQuantityMap; // productId -> quantity
    private double totalAmount;
    private Timestamp timestamp;

    
    public Order(int orderId, int userId, Map<Integer, Integer> productQuantityMap, double totalAmount, Timestamp timestamp) {
        this.orderId = orderId;
        this.userId = userId;
        this.productQuantityMap = productQuantityMap;
        this.totalAmount = totalAmount;
        this.timestamp = timestamp;
    }

   
    public int getOrderId() {
        return orderId;
    }

    public int getUserId() {
        return userId;
    }

    public Map<Integer, Integer> getProductQuantityMap() {
        return productQuantityMap;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setProductQuantityMap(Map<Integer, Integer> productQuantityMap) {
        this.productQuantityMap = productQuantityMap;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    
    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", productQuantityMap=" + productQuantityMap +
                ", totalAmount=" + totalAmount +
                ", timestamp=" + timestamp +
                '}';
    }
}

