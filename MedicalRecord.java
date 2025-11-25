package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MedicalRecord {
    private int id;
    private Patient patient;
    private Doctor doctor;
    private String diagnosis;
    private List<String> prescriptions;
    private String treatment;
    private LocalDate treatmentDate;
    private String notes;
    private double treatmentCost;
    
    public MedicalRecord(int id, Patient patient, Doctor doctor, String diagnosis, 
                        String treatment, double treatmentCost, String notes) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.treatmentCost = treatmentCost;
        this.notes = notes;
        this.treatmentDate = LocalDate.now();
        this.prescriptions = new ArrayList<>();
    }
    
    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    
    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
    
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    
    public List<String> getPrescriptions() { return prescriptions; }
    public void addPrescription(String prescription) { 
        this.prescriptions.add(prescription); 
    }
    
    public String getTreatment() { return treatment; }
    public void setTreatment(String treatment) { this.treatment = treatment; }
    
    public LocalDate getTreatmentDate() { return treatmentDate; }
    public void setTreatmentDate(LocalDate treatmentDate) { this.treatmentDate = treatmentDate; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public double getTreatmentCost() { return treatmentCost; }
    public void setTreatmentCost(double treatmentCost) { this.treatmentCost = treatmentCost; }
    
    public void addToPatientBill() {
        patient.addToBill(treatmentCost);
    }
    
    @Override
    public String toString() {
        return String.format("Medical Record ID: %d\nPatient: %s\nDoctor: %s\nDiagnosis: %s\nTreatment: %s\nDate: %s\nCost: $%.2f", 
                           id, patient.getName(), doctor.getName(), diagnosis, treatment, treatmentDate, treatmentCost);
    }
}
