package com.sumitd.empmgtsystem.Model;

public abstract class Employee {
    private int emp_id;
    private String emp_name;
    private String emp_department;
    private long emp_basicSalary;

    private double allowance;
    private double deduction;

    public Employee() {}

    public Employee(int id, String name, String dept, long salary) {
        this.emp_id = id;
        this.emp_name = name;
        this.emp_department = dept;
        this.emp_basicSalary = salary;
    }

    public abstract double calculateSalary();

    public int getEmp_id() { return emp_id; }
    public void setEmp_id(int emp_id) { this.emp_id = emp_id; }

    public String getEmp_name() { return emp_name; }
    public void setEmp_name(String emp_name) { this.emp_name = emp_name; }

    public String getEmp_department() { return emp_department; }
    public void setEmp_department(String emp_department) { this.emp_department = emp_department; }

    public long getEmp_basicSalary() { return emp_basicSalary; }
    public void setEmp_basicSalary(long emp_basicSalary) { this.emp_basicSalary = emp_basicSalary; }

    public double getAllowance() { return allowance; }
    public void setAllowance(double allowance) { this.allowance = allowance; }

    public double getDeduction() { return deduction; }
    public void setDeduction(double deduction) { this.deduction = deduction; }

    @Override
    public String toString() {
        return "ID: " + emp_id + ", Name: " + emp_name + ", Dept: " + emp_department +
                ", Salary: " + emp_basicSalary + ", Net Salary: " + calculateSalary();
    }
}
