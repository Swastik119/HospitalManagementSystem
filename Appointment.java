package model;

import java.time.LocalDateTime;

public class Appointment {
    private int id;
    private Patient patient;
    private Doctor doctor;
    private LocalDateTime appointmentDateTime;
    private String timeSlot;
    private String status; // SCHEDULED, COMPLETED, CANCELLED
    private String description;
    private double fee;
    
    public Appointment(int id, Patient patient, Doctor doctor, LocalDateTime appointmentDateTime, 
                      String timeSlot, String description) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentDateTime = appointmentDateTime;
        this.timeSlot = timeSlot;
        this.status = "SCHEDULED";
        this.description = description;
        this.fee = doctor.getConsultationFee();
    }
    
    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    
    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
    
    public LocalDateTime getAppointmentDateTime() { return appointmentDateTime; }
    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) { this.appointmentDateTime = appointmentDateTime; }
    
    public String getTimeSlot() { return timeSlot; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public double getFee() { return fee; }
    public void setFee(double fee) { this.fee = fee; }
    
    public void completeAppointment() {
        this.status = "COMPLETED";
        patient.addToBill(fee);
        System.out.println("Appointment completed. Fee of $" + fee + " added to patient's bill.");
    }
    
    public void cancelAppointment() {
        this.status = "CANCELLED";
        doctor.addAvailableSlot(timeSlot);
        System.out.println("Appointment cancelled. Slot " + timeSlot + " made available again.");
    }
    
    @Override
    public String toString() {
        return String.format("Appointment ID: %d\nPatient: %s\nDoctor: %s\nDate: %s\nTime: %s\nStatus: %s\nFee: $%.2f", 
                           id, patient.getName(), doctor.getName(), 
                           appointmentDateTime.toLocalDate(), timeSlot, status, fee);
    }
}
