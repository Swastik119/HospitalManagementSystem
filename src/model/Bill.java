package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Bill {
    private int id;
    private Patient patient;
    private LocalDateTime billDate;
    private List<BillItem> items;
    private double totalAmount;
    private String status; // GENERATED, PAID, PARTIALLY_PAID
    
    public Bill(int id, Patient patient) {
        this.id = id;
        this.patient = patient;
        this.billDate = LocalDateTime.now();
        this.items = new ArrayList<>();
        this.totalAmount = 0.0;
        this.status = "GENERATED";
    }
    
    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    
    public LocalDateTime getBillDate() { return billDate; }
    public void setBillDate(LocalDateTime billDate) { this.billDate = billDate; }
    
    public List<BillItem> getItems() { return items; }
    
    public double getTotalAmount() { return totalAmount; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public void addItem(String description, double amount) {
        BillItem item = new BillItem(description, amount);
        items.add(item);
        totalAmount += amount;
        patient.addToBill(amount);
    }
    
    public void payBill(double amount) {
        if (amount >= totalAmount) {
            status = "PAID";
            patient.payBill(amount);
            System.out.println("Bill fully paid. Thank you!");
        } else if (amount > 0) {
            status = "PARTIALLY_PAID";
            patient.payBill(amount);
            System.out.println("Partial payment of $" + amount + " received. Remaining: $" + (totalAmount - amount));
        }
    }
    
    public void printBill() {
        System.out.println("=== HOSPITAL BILL ===");
        System.out.println("Bill ID: " + id);
        System.out.println("Patient: " + patient.getName());
        System.out.println("Date: " + billDate);
        System.out.println("Status: " + status);
        System.out.println("\nItems:");
        for (BillItem item : items) {
            System.out.println("  - " + item.getDescription() + ": $" + item.getAmount());
        }
        System.out.println("Total Amount: $" + totalAmount);
        System.out.println("=====================");
    }
    
    // Inner class for bill items
    public static class BillItem {
        private String description;
        private double amount;
        
        public BillItem(String description, double amount) {
            this.description = description;
            this.amount = amount;
        }
        
        public String getDescription() { return description; }
        public double getAmount() { return amount; }
    }
}
