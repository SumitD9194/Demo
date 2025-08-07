package com.sumitd.empmgtsystem.Main;

import java.util.Scanner;

import com.sumitd.empmgtsystem.Authentication.AuthService;
import com.sumitd.empmgtsystem.Service.EmployeeService;
import com.sumitd.empmgtsystem.Util.AutoBackupTask;
import com.sumitd.empmgtsystem.Util.PayrollUtil;



public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AuthService authService = new AuthService();
        

        if (!authService.authenticate(sc)) {
            System.out.println("Invalid credentials. Exiting...");
            return;
        }

        EmployeeService employeeService = new EmployeeService();
        System.out.println("\nLog-in Succeessfully.");
        
        employeeService.loadFromFile(); 
        PayrollUtil.savePayroll(employeeService.getEmployeeList());
        
//        int recordCount = employeeService.getEmployeeList().size();
//        System.out.println("\nEmployee data loaded from file (" + recordCount + " records Found ).");

        System.out.print("Do you want to view existing employee data? (Y/N): ");
        if (sc.next().equalsIgnoreCase("Y")) {
            System.out.println("\n--- Employee Data (.txt) ---");
            employeeService.viewAllEmployees();
        }
        sc.nextLine(); 

        
        System.out.println("\n --- Payroll Data (.csv) ---");
        PayrollUtil.readPayrollHistory(); 

        new AutoBackupTask(employeeService).start();

        int choice;
        do {
            System.out.println("\n--- Employee Management System ---");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Search Employee by ID");
            System.out.println("4. Search Employee by Department");
            System.out.println("5. Update Employee");
            System.out.println("6. Delete Employee");
            System.out.println("7. Exit");
            System.out.print("Choose option: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    employeeService.addEmployee(sc);
                    break;
                case 2:
                    employeeService.viewAllEmployees();
                    break;
                case 3:
                    employeeService.searchById(sc);
                    break;
                case 4:
                    employeeService.searchByDepartment(sc);
                    break;
                case 5:
                    employeeService.updateEmployee(sc);
                    break;
                case 6:
                    employeeService.deleteEmployee(sc);
                    break;
                case 7:
                    System.out.println("Saving data before exit...");
                    employeeService.saveToFile();
                    System.out.println("Exiting system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        } while (choice != 7);
    }
}
