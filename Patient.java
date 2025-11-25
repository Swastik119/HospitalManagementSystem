package model;

import interfaces.IReportable;
import interfaces.IBillable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Patient extends Person implements IReportable, IBillable {
    private int age;
    private String gender;
    private String address;
    private String emergencyContact;
    private LocalDate registrationDate;
    private String bloodGroup;
    private List<String> allergies;
    private double totalBillAmount;
    
    public Patient(int id, String name, String contactNumber, String email, 
                  int age, String gender, String address, String emergencyContact, 
                  String bloodGroup) {
        super(id, name, contactNumber, email);
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.emergencyContact = emergencyContact;
        this.bloodGroup = bloodGroup;
        this.registrationDate = LocalDate.now();
        this.allergies = new ArrayList<>();
        this.totalBillAmount = 0.0;
    }
    
    // Getters and setters
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; }
    
    public LocalDate getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }
    
    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    
    public List<String> getAllergies() { return allergies; }
    public void addAllergy(String allergy) { this.allergies.add(allergy); }
    
    public double getTotalBillAmount() { return totalBillAmount; }
    public void setTotalBillAmount(double totalBillAmount) { this.totalBillAmount = totalBillAmount; }
    
    @Override
    public void displayInfo() {
        System.out.println("=== Patient Information ===");
        System.out.println("ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Age: " + age);
        System.out.println("Gender: " + gender);
        System.out.println("Blood Group: " + bloodGroup);
        System.out.println("Contact: " + getContactNumber());
        System.out.println("Email: " + getEmail());
        System.out.println("Emergency Contact: " + emergencyContact);
        System.out.println("Address: " + address);
        System.out.println("Registration Date: " + registrationDate);
        System.out.println("Allergies: " + String.join(", ", allergies));
        System.out.println("Total Bill Amount: $" + totalBillAmount);
    }
    
    @Override
    public String generateReport() {
        return String.format("Patient Medical Report\nID: %d\nName: %s\nAge: %d\nBlood Group: %s\nAllergies: %s\nRegistration Date: %s", 
                           getId(), getName(), age, bloodGroup, String.join(", ", allergies), registrationDate);
    }
    
    @Override
    public double calculateBill() {
        return totalBillAmount;
    }
    
    @Override
    public void addToBill(double amount) {
        this.totalBillAmount += amount;
        System.out.println("Added $" + amount + " to bill. Total: $" + totalBillAmount);
    }
    
    @Override
    public void payBill(double amount) {
        if (amount <= totalBillAmount) {
            totalBillAmount -= amount;
            System.out.println("Payment of $" + amount + " received. Remaining balance: $" + totalBillAmount);
        } else {
            System.out.println("Payment amount exceeds total bill.");
        }
    }
}
