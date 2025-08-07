package com.sumitd.empmgtsystem.Model;



public class Manager extends Employee {
    public Manager(int id, String name, String dept, long salary) {
        super(id, name, dept, salary);
        setAllowance(0.20 * salary);
        setDeduction(0.05 * salary);
    }

    public double calculateSalary() {
        return getEmp_basicSalary() + getAllowance() - getDeduction();
    }
}
