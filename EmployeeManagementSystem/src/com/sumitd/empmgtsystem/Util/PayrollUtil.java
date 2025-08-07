package com.sumitd.empmgtsystem.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import com.sumitd.empmgtsystem.Model.Employee;

public class PayrollUtil {
	private static final String PAYROLL_FILE ="C:\\Users\\sumit\\eclipse-workspace2\\EmployeeManagementSystem \\resources\\PayrollData.csv";


    public static void savePayroll(List<Employee> employees) {
    	
//    	System.out.println(employees.toString());
//    	System.out.println(employees.size());
       
    	File file = new File(PAYROLL_FILE);
        boolean isNew = !file.exists();

        try (PrintWriter pw = new PrintWriter(new FileWriter(file, true))) {
            if (isNew) {
                pw.println("Date,EmpID,Name,Department,FinalSalary");
            }

            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            for (Employee e : employees) {
                pw.printf("%s,%d,%s,%s,%.2f\n",
                        date,
                        e.getEmp_id(),
                        e.getEmp_name(),
                        e.getEmp_department(),
                        e.calculateSalary());
            }
        } catch (IOException e) {
            System.out.println("Error writing payroll: " + e.getMessage());
        }
    }
    public static void viewPayrollHistory() {
        File file = new File(PAYROLL_FILE);
        if (!file.exists()) {
            System.out.println("No payroll history found.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean headerSkipped = false;
            while ((line = br.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue; // skip header
                }
                System.out.println(line);
            }
        }
        catch (IOException e) {
            System.out.println("Error reading payroll history: " + e.getMessage());
          }
    }

    public static void readPayrollHistory() {
        File file = new File(PAYROLL_FILE);
        if (!file.exists()) {
            System.out.println("No payroll history found.");
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // skip header
                    continue;
                }
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading payroll file: " + e.getMessage());
        }
    }
    
   
} 
