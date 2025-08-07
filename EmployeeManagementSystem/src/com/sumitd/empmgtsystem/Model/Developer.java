package com.sumitd.empmgtsystem.Model;



public class Developer extends Employee {
    public Developer(int id, String name, String dept, long salary) {
        super(id, name, dept, salary);
        setAllowance(0.10 * salary);
        setDeduction(0.03 * salary);
    }

    public double calculateSalary() {
        return getEmp_basicSalary() + getAllowance() - getDeduction();
    }
}