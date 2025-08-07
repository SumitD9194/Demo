package com.sumitd.empmgtsystem.Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sumitd.empmgtsystem.Model.Developer;
import com.sumitd.empmgtsystem.Model.Employee;
import com.sumitd.empmgtsystem.Model.Manager;


public class FileUtil {
    public static List<Employee> readFromFile(String path) {
        List<Employee> list = new ArrayList<Employee>();
        File file = new File(path);
        if (!file.exists()) return list;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    String dept = parts[2].trim();
                    long salary = Long.parseLong(parts[3].trim());
                    if (dept.equalsIgnoreCase("Manager")) {
                        list.add(new Manager(id, name, dept, salary));
                    } else {
                        list.add(new Developer(id, name, dept, salary));
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return list;
    }

    public static void saveToFile(String path, List<Employee> list) {
        try {
            File file = new File(path);
            file.getParentFile().mkdirs();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (Employee emp : list) {
                String line = emp.getEmp_id() + "," + emp.getEmp_name() + "," +
                              emp.getEmp_department() + "," + emp.getEmp_basicSalary();
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}
