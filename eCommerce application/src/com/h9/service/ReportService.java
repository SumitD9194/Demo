package com.h9.service;


import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

import com.h9.database.OrderDAO;

public class ReportService {
    private final OrderDAO orderDAO = new OrderDAO();

    public Map<String, Double> getSalesReport() throws SQLException {
        return orderDAO.getSalesReport();
    }

    public String getFormattedSalesReport() throws SQLException {
        return getSalesReport().entrySet().stream()
                .map(entry -> entry.getKey().equals("total_orders") ?
                        String.format("Total Orders: %d", entry.getValue().intValue()) :
                        String.format("Total Revenue: ₹%.2f", entry.getValue()))
                .collect(Collectors.joining("\n", "\n=== Sales Report ===\n", ""));
    }
}