package model;

import interfaces.IReportable;
import java.util.ArrayList;
import java.util.List;

public class Doctor extends Person implements IReportable {
    private String specialization;
    private Department department;
    private int experienceYears;
    private String qualification;
    private List<String> availableSlots;
    private double consultationFee;
    
    public Doctor(int id, String name, String contactNumber, String email, 
                  String specialization, Department department, int experienceYears, 
                  String qualification, double consultationFee) {
        super(id, name, contactNumber, email);
        this.specialization = specialization;
        this.department = department;
        this.experienceYears = experienceYears;
        this.qualification = qualification;
        this.consultationFee = consultationFee;
        this.availableSlots = new ArrayList<>();
        initializeDefaultSlots();
    }
    
    private void initializeDefaultSlots() {
        availableSlots.add("09:00-10:00");
        availableSlots.add("10:00-11:00");
        availableSlots.add("11:00-12:00");
        availableSlots.add("14:00-15:00");
        availableSlots.add("15:00-16:00");
        availableSlots.add("16:00-17:00");
    }
    
    // Getters and setters
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
    
    public int getExperienceYears() { return experienceYears; }
    public void setExperienceYears(int experienceYears) { this.experienceYears = experienceYears; }
    
    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }
    
    public List<String> getAvailableSlots() { return availableSlots; }
    public void addAvailableSlot(String slot) { this.availableSlots.add(slot); }
    public boolean removeAvailableSlot(String slot) { return availableSlots.remove(slot); }
    
    public double getConsultationFee() { return consultationFee; }
    public void setConsultationFee(double consultationFee) { this.consultationFee = consultationFee; }
    
    @Override
    public void displayInfo() {
        System.out.println("=== Doctor Information ===");
        System.out.println("ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Specialization: " + specialization);
        System.out.println("Department: " + department.getName());
        System.out.println("Experience: " + experienceYears + " years");
        System.out.println("Qualification: " + qualification);
        System.out.println("Consultation Fee: $" + consultationFee);
        System.out.println("Contact: " + getContactNumber());
        System.out.println("Email: " + getEmail());
        System.out.println("Available Slots: " + String.join(", ", availableSlots));
    }
    
    @Override
    public String generateReport() {
        return String.format("Doctor Performance Report\nID: %d\nName: %s\nSpecialization: %s\nDepartment: %s\nExperience: %d years\nConsultation Fee: $%.2f", 
                           getId(), getName(), specialization, department.getName(), experienceYears, consultationFee);
    }
    
    public void prescribeMedication(String medication, String dosage) {
        System.out.println("Dr. " + getName() + " prescribed: " + medication + " (" + dosage + ")");
    }
    
    public boolean isSlotAvailable(String slot) {
        return availableSlots.contains(slot);
    }
}
