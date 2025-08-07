package com.sumitd.empmgtsystem.Authentication;

import java.util.Scanner;

public class AuthService {
    private final String USERNAME ="admin"; //"SDCORP9";
    private final String PASSWORD ="admin";// "Hola@9";

    public boolean authenticate(Scanner sc) {
        while (true) {
            System.out.print("Enter Username: ");
            String user = sc.nextLine();

            System.out.print("Enter Password: ");
            String pass = sc.nextLine();

            if (USERNAME.equals(user) && PASSWORD.equals(pass)) {
                return true;
            } else {
                System.out.println("Invalid credentials!");
                System.out.print("Try again? (Y/N): ");
                String retry = sc.nextLine();
                if (!retry.equalsIgnoreCase("Y")) {
                    return false; 
                }
            }
        }
    }
}
