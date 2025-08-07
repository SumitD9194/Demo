package com.sumitd.empmgtsystem.Service;

import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;

import com.sumitd.empmgtsystem.Model.Developer;
import com.sumitd.empmgtsystem.Model.Employee;
import com.sumitd.empmgtsystem.Model.Manager;
import com.sumitd.empmgtsystem.Util.FileUtil;
import com.sumitd.empmgtsystem.Util.PayrollUtil;



public class EmployeeService {
    private List<Employee> employeeList = new ArrayList<Employee>();
    private final String FILE_PATH = "C:\\Users\\sumit\\eclipse-workspace2\\EmployeeManagementSystem \\resources\\EmployeeManagementSystem.txt";

    public void loadFromFile() {
        employeeList = FileUtil.readFromFile(FILE_PATH);
    }
    
    public void saveToFile() {
     FileUtil.saveToFile(FILE_PATH, employeeList);
     PayrollUtil.savePayroll(employeeList);
    }
 
   

    public void addEmployee(Scanner sc) {
        System.out.println("\n--- Add New Employee ---");
        sc.nextLine();

        int id;
        while (true) {
            try {
                System.out.print("Enter Employee ID: ");
                id = Integer.parseInt(sc.nextLine().trim());

                if (findById(id) != null) {
                    System.out.println(" ID already exists. Try another.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println(" Invalid input! ID must be a number. Try again.");
            }
        }

        String name;
        while (true) {
            System.out.print("Enter Employee Name: ");
            name = sc.nextLine().trim();
            if (!name.isEmpty()) {
                break;
            } else {
                System.out.println(" Name cannot be empty. Please try again.");
            }
        }

        String dept;
        while (true) {
            System.out.print("Enter Department (Manager/Developer): ");
            dept = sc.nextLine().trim();
            if (dept.equalsIgnoreCase("Manager") || dept.equalsIgnoreCase("Developer")) {
                break;
            } else {
                System.out.println(" Invalid department. Please enter 'Manager' or 'Developer'.");
            }
        }

        long salary;
        while (true) {
            try {
                System.out.print("Enter Employee Basic Salary: ");
                salary = Long.parseLong(sc.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println(" Invalid input! Salary must be a number. Try again.");
            }
        }

        
        Employee emp = dept.equalsIgnoreCase("Manager")
                ? new Manager(id, name, dept, salary)
                : new Developer(id, name, dept, salary);

        employeeList.add(emp);
        saveToFile();
        System.out.println(" Employee added successfully.");

       
    }

    
   
//    public void viewAllEmployees() {
//        System.out.println("\n--- Employee List ---");
//        for (Employee e : employeeList) {
//            System.out.println(e);
//        }
//    }
    
    public void viewAllEmployees() {
       // System.out.println("\n--- Employee Data (.txt) ---");

        if (employeeList.isEmpty()) {
            System.out.println("No employee records found.");
        } else {
            System.out.println("\n--- Employee List ---");
            for (Employee e : employeeList) {
                System.out.println(e);
            }
        }

        System.out.println("\n--- Payroll History (.csv) ---");
        PayrollUtil.readPayrollHistory();
    }


    public void searchById(Scanner sc) {
        System.out.print("Enter Employee ID: ");
        int id = sc.nextInt();
        Employee e = findById(id);
        System.out.println(e != null ? e : "Employee not found.");
    }

    public void searchByDepartment(Scanner sc) {
        System.out.print("Enter Department: ");
        sc.nextLine();
        String dept = sc.nextLine();
        boolean found = false;
        for (Employee e : employeeList) {
            if (e.getEmp_department().equalsIgnoreCase(dept)) {
                System.out.println(e);
                found = true;
            }
        }
        if (!found) System.out.println("No employees in department.");
    }

    public void updateEmployee(Scanner sc) {
        System.out.print("Enter Employee ID to update: ");
        int id = sc.nextInt();
        Employee e = findById(id);
        if (e != null) {
            sc.nextLine();
            System.out.print("New Name: ");
            e.setEmp_name(sc.nextLine());
            System.out.print("New Department: ");
            e.setEmp_department(sc.nextLine());
            System.out.print("New Basic Salary: ");
            e.setEmp_basicSalary(sc.nextLong());
            System.out.println("Employee updated.");
        } else {
            System.out.println("Employee not found.");
        }
        saveToFile();
    }

    public void deleteEmployee(Scanner sc) {
        System.out.print("Enter Employee ID to delete: ");
        int id = sc.nextInt();
        Employee e = findById(id);
        if (e != null) {
            employeeList.remove(e);
           
            System.out.println("Employee removed.");
        } else {
            System.out.println("Employee not found.");
        }
        saveToFile();
    }

    public Employee findById(int id) {
        for (Employee e : employeeList) {
            if (e.getEmp_id() == id) return e;
        }
        return null;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }
}
