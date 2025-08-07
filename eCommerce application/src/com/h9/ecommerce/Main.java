package com.h9.ecommerce;

import com.h9.model.User;
import com.h9.service.*;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.stream.Collectors;

    public class Main {
        private final Scanner scanner = new Scanner(System.in);
        private final UserService userService = new UserService();
        private final ProductService productService = new ProductService();
        private final CartService cartService = new CartService();
        private final OrderService orderService = new OrderService();
        private final ReportService reportService = new ReportService();
        private User currentUser;

        public void start() {
            while (true) {
                if (currentUser == null) {
                    showMainMenu();
                } else if (currentUser.isAdmin()) {
                    showAdminMenu();
                } else {
                    showCustomerMenu();
                }
            }
        }

        private void showMainMenu() {
            System.out.println("\n=== eCommerce System ===\n1. Register\n2. Login\n3. Exit\nChoose an option: ");
            try {
                int option = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                switch (option) {
                    case 1: register(); break;
                    case 2: login(); break;
                    case 3: System.exit(0); break;
                    default: System.out.println("Invalid option!");
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
            }
        }

        private void register() {
            try {
                System.out.print("Enter name: ");
                String name = scanner.nextLine();
                System.out.print("Enter email: ");
                String email = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                userService.register(name, email, password);
                System.out.println("Registration successful!");
            } catch (SQLException | IllegalArgumentException e) {
                System.out.println("Registration failed: " + e.getMessage());
            }
        }

        private void login() {
            try {
                System.out.print("Enter email: ");
                String email = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                currentUser = userService.login(email, password);
                if (currentUser != null) {
                    System.out.println("Login successful! Welcome, " + currentUser.getName());
                   
                    if (currentUser.isAdmin()) {
                        showAdminMenu();
                    } else {
                        showCustomerMenu();
                    }
                } else {
                    System.out.println("Invalid credentials!");
                }
            } catch (SQLException | IllegalArgumentException e) {
                System.out.println("Login failed: " + e.getMessage());
            }
        }

        private void showCustomerMenu() {
            System.out.println("\n=== Customer Menu ===\n1. View Products\n2. Search Products by Name\n3. Add to Cart\n4. View Cart\n5. Place Order\n6. Logout\nChoose an option: ");
            try {
                int option = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                switch (option) {
                    case 1: viewProducts(); break;
                    case 2: searchProducts(); break;
                    case 3: addToCart(); break;
                    case 4: viewCart(); break;
                    case 5: placeOrder(); break;
                    case 6: currentUser = null; cartService.clearCart(); break;
                    default: System.out.println("Invalid option!");
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
            }
        }

        private void showAdminMenu() {
            System.out.println("\n=== Admin Menu ===\n1. View All Products\n2. Search Products by Name\n3. Add Product\n4. Edit Product\n5. Delete Product\n6. View Sales Report\n7. View All Orders\n8. Logout\nChoose an option: ");
            try {
                int option = scanner.nextInt();
                scanner.nextLine(); 
                switch (option) {
                    case 1: viewProducts(); break;
                    case 2: searchProducts(); break;
                    case 3: addProduct(); break;
                    case 4: editProduct(); break;
                    case 5: deleteProduct(); break;
                    case 6: viewSalesReport(); break;
                    case 7: viewAllOrders(); break;
                    case 8: currentUser = null; cartService.clearCart(); break;
                    default: System.out.println("Invalid option!");
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
            }
        }

        private void viewProducts() {
        	try {
                StringBuilder table = new StringBuilder("\n=== Products ===\n");
                table.append(String.format("%-5s | %-30s | %-10s | %-10s | %-50s%n", "ID", "Name", "Price", "Quantity", "Description"));
                table.append("-".repeat(110)).append("\n");
                
                String rows = productService.getAllProducts().stream()
                        .map(p -> String.format("%-5d | %-30s | ₹%-9.2f | %-10d | %-50s",
                                p.getId(), truncate(p.getName(), 30), p.getPrice(), p.getQuantity(), truncate(p.getDescription(), 50)))
                        .collect(Collectors.joining("\n"));
                
                table.append(rows.isEmpty() ? "No products available!" : rows);
                System.out.println(table);
            } catch (SQLException e) {
                System.out.println("Error viewing products: " + e.getMessage());
            }
        }

        private String truncate(String str, int maxLength) {
            return str.length() > maxLength ? str.substring(0, maxLength - 3) + "..." : str;
        
        }
//            try {
//                String products = productService.getAllProducts().stream()
//                        .map(p -> String.format("ID: %d, Name: %s, Price: $%.2f, Quantity: %d, Description: %s",
//                                p.getId(), p.getName(), p.getPrice(), p.getQuantity(), p.getDescription()))
//                        .collect(Collectors.joining("\n", "\n=== Products ===\n", ""));
//                System.out.println(products.isEmpty() ? "No products available!" : products);
//            } catch (SQLException e) {
//                System.out.println("Error viewing products: " + e.getMessage());
//            }
//        }

        private void searchProducts() {
            try {
                System.out.print("Enter product name to search: ");
                String name = scanner.nextLine();
                String result = productService.searchProductsByName(name);
                System.out.println(result.isEmpty() ? "No products found!" : result);
            } catch (SQLException e) {
                System.out.println("Error searching products: " + e.getMessage());
            }
        }

        private void addToCart() {
            try {
                viewProducts();
                System.out.print("Enter product ID: ");
                int productId = scanner.nextInt();
                System.out.print("Enter quantity: ");
                int quantity = scanner.nextInt();
                scanner.nextLine();
                cartService.addToCart(productId, quantity);
                System.out.println("Added to cart!");
            } catch (SQLException | IllegalArgumentException e) {
                System.out.println("Error adding to cart: " + e.getMessage());
                scanner.nextLine();
            }
        }

        private void viewCart() {
            try {
                System.out.println(cartService.getCartDetails());
            } catch (SQLException e) {
                System.out.println("Error viewing cart: " + e.getMessage());
            }
        }

        private void placeOrder() {
            try {
                int orderId = orderService.placeOrder(currentUser.getId(), cartService.getCart());
                cartService.clearCart();
                System.out.println("Order placed successfully! Order ID: " + orderId);
            } catch (SQLException | IllegalArgumentException e) {
                System.out.println("Error placing order: " + e.getMessage());
            }
        }

        private void addProduct() {
            try {
                System.out.print("Enter product name: ");
                String name = scanner.nextLine();
                System.out.print("Enter price: ");
                double price = scanner.nextDouble();
                System.out.print("Enter quantity: ");
                int quantity = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter description: ");
                String description = scanner.nextLine();
                productService.addProduct(name, price, quantity, description);
                System.out.println("Product added successfully!");
            } catch (SQLException | IllegalArgumentException e) {
                System.out.println("Error adding product: " + e.getMessage());
                scanner.nextLine();
            }
        }

        private void editProduct() {
            try {
                viewProducts();
                System.out.print("Enter product ID to edit: ");
                int productId = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter new name (or press Enter to keep unchanged): ");
                String name = scanner.nextLine();
                System.out.print("Enter new price (or 0 to keep unchanged): ");
                double price = scanner.nextDouble();
                System.out.print("Enter new quantity (or -1 to keep unchanged): ");
                int quantity = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter new description (or press Enter to keep unchanged): ");
                String description = scanner.nextLine();
                productService.updateProduct(productId, name, price, quantity, description);
                System.out.println("Product updated successfully!");
            } catch (SQLException | IllegalArgumentException e) {
                System.out.println("Error editing product: " + e.getMessage());
                scanner.nextLine();
            }
        }

        private void deleteProduct() {
            try {
                viewProducts();
                System.out.print("Enter product ID to delete: ");
                int productId = scanner.nextInt();
                scanner.nextLine();
                productService.deleteProduct(productId);
                System.out.println("Product deleted successfully!");
            } catch (SQLException | IllegalArgumentException e) {
                System.out.println("Error deleting product: " + e.getMessage());
                scanner.nextLine();
            }
        }

        private void viewSalesReport() {
            try {
                System.out.println(reportService.getFormattedSalesReport());
            } catch (SQLException e) {
                System.out.println("Error generating sales report: " + e.getMessage());
            }
        }

        private void viewAllOrders() {
            try {
                System.out.println(orderService.getAllOrdersDetails());
            } catch (SQLException e) {
                System.out.println("Error viewing orders: " + e.getMessage());
            }
        }

        public static void main(String[] args) {
            new Main().start();
        }
    }