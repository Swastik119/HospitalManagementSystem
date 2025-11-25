package model;

import java.time.LocalDate;

public class Staff extends Person {
    private String position;
    private double salary;
    private Department department;
    private LocalDate joinDate;
    
    public Staff(int id, String name, String contactNumber, String email, 
                 String position, double salary, Department department) {
        super(id, name, contactNumber, email);
        this.position = position;
        this.salary = salary;
        this.department = department;
        this.joinDate = LocalDate.now();
    }
    
    // Getters and setters
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    
    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
    
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
    
    public LocalDate getJoinDate() { return joinDate; }
    public void setJoinDate(LocalDate joinDate) { this.joinDate = joinDate; }
    
    @Override
    public void displayInfo() {
        System.out.println("=== Staff Information ===");
        System.out.println("ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Position: " + position);
        System.out.println("Department: " + department.getName());
        System.out.println("Salary: $" + salary);
        System.out.println("Join Date: " + joinDate);
        System.out.println("Contact: " + getContactNumber());
        System.out.println("Email: " + getEmail());
    }
}
