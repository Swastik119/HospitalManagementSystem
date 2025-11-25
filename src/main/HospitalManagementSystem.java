package main;

import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

public class HospitalManagementSystem {
    private PatientRegistry patientRegistry;
    private DoctorRegistry doctorRegistry;
    private MedicalRecordRegistry medicalRecordRegistry;
    private int medicalRecordIdCounter = 1;

    // Constructor
    public HospitalManagementSystem() {
        this.patientRegistry = new PatientRegistry();
        this.doctorRegistry = new DoctorRegistry();
        this.medicalRecordRegistry = new MedicalRecordRegistry();
    }

    /**
     * Method to add a medical record for a patient
     * @param patientId The ID of the patient
     * @param doctorId The ID of the doctor
     */
    public void addMedicalRecord(int patientId, int doctorId) {
        try {
            // Retrieve patient and doctor from their respective registries
            Patient patient = patientRegistry.getById(patientId);
            Doctor doctor = doctorRegistry.getById(doctorId);

            // Create new medical record
            MedicalRecord record = new MedicalRecord(medicalRecordIdCounter++, patient, doctor);
            
            // Add to patient's bill
            record.addToPatientBill();
            
            // Add record to medical record registry
            medicalRecordRegistry.add(record);

            System.out.println("Medical record added successfully.");
            System.out.println("Record ID: " + record.getRecordId());
            System.out.println("Patient: " + patient.getName());
            System.out.println("Doctor: " + doctor.getName());
            
        } catch (PatientNotFoundException e) {
            System.err.println("Error adding medical record: Patient not found - " + e.getMessage());
        } catch (DoctorNotFoundException e) {
            System.err.println("Error adding medical record: Doctor not found - " + e.getMessage());
        } catch (DuplicateEntryException e) {
            System.err.println("Error adding medical record: Duplicate entry - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error adding medical record: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Other methods for the hospital management system...
    
    public void displayMedicalRecords() {
        medicalRecordRegistry.displayAllRecords();
    }
    
    public PatientRegistry getPatientRegistry() {
        return patientRegistry;
    }
    
    public DoctorRegistry getDoctorRegistry() {
        return doctorRegistry;
    }
    
    public MedicalRecordRegistry getMedicalRecordRegistry() {
        return medicalRecordRegistry;
    }

    // Main method to test the system
    public static void main(String[] args) {
        HospitalManagementSystem hospital = new HospitalManagementSystem();
        
        // Add some sample data
        hospital.getPatientRegistry().addPatient(new Patient(1, "John Doe"));
        hospital.getPatientRegistry().addPatient(new Patient(2, "Jane Smith"));
        hospital.getDoctorRegistry().addDoctor(new Doctor(1, "Dr. Smith"));
        hospital.getDoctorRegistry().addDoctor(new Doctor(2, "Dr. Johnson"));
        
        // Test adding medical records
        System.out.println("=== Adding Medical Records ===");
        hospital.addMedicalRecord(1, 1);  // Should succeed
        hospital.addMedicalRecord(2, 2);  // Should succeed
        hospital.addMedicalRecord(99, 1); // Should fail - patient not found
        hospital.addMedicalRecord(1, 99); // Should fail - doctor not found
        
        // Display all records
        System.out.println("\n=== All Medical Records ===");
        hospital.displayMedicalRecords();
    }
}

// Patient class
class Patient {
    private int id;
    private String name;
    
    public Patient(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public int getId() { return id; }
    public String getName() { return name; }
}

// Doctor class
class Doctor {
    private int id;
    private String name;
    
    public Doctor(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public int getId() { return id; }
    public String getName() { return name; }
}

// MedicalRecord class
class MedicalRecord {
    private int recordId;
    private Patient patient;
    private Doctor doctor;
    private Date dateCreated;
    
    public MedicalRecord(int recordId, Patient patient, Doctor doctor) {
        this.recordId = recordId;
        this.patient = patient;
        this.doctor = doctor;
        this.dateCreated = new Date();
    }
    
    public void addToPatientBill() {
        // Implementation for adding to patient's bill
        System.out.println("Added to patient bill: Record " + recordId);
    }
    
    public int getRecordId() { return recordId; }
    public Patient getPatient() { return patient; }
    public Doctor getDoctor() { return doctor; }
    public Date getDateCreated() { return dateCreated; }
}

// PatientRegistry class
class PatientRegistry {
    private Map<Integer, Patient> patients = new HashMap<>();
    
    public Patient getById(int patientId) throws PatientNotFoundException {
        Patient patient = patients.get(patientId);
        if (patient == null) {
            throw new PatientNotFoundException("Patient with ID " + patientId + " not found");
        }
        return patient;
    }
    
    public void addPatient(Patient patient) {
        patients.put(patient.getId(), patient);
    }
}

// DoctorRegistry class
class DoctorRegistry {
    private Map<Integer, Doctor> doctors = new HashMap<>();
    
    public Doctor getById(int doctorId) throws DoctorNotFoundException {
        Doctor doctor = doctors.get(doctorId);
        if (doctor == null) {
            throw new DoctorNotFoundException("Doctor with ID " + doctorId + " not found");
        }
        return doctor;
    }
    
    public void addDoctor(Doctor doctor) {
        doctors.put(doctor.getId(), doctor);
    }
}

// MedicalRecordRegistry class
class MedicalRecordRegistry {
    private Map<Integer, MedicalRecord> records = new HashMap<>();
    
    public void add(MedicalRecord record) throws DuplicateEntryException {
        if (records.containsKey(record.getRecordId())) {
            throw new DuplicateEntryException("Medical record with ID " + record.getRecordId() + " already exists");
        }
        records.put(record.getRecordId(), record);
    }
    
    public void displayAllRecords() {
        if (records.isEmpty()) {
            System.out.println("No medical records found.");
            return;
        }
        
        for (MedicalRecord record : records.values()) {
            System.out.println("Record ID: " + record.getRecordId() + 
                             ", Patient: " + record.getPatient().getName() +
                             ", Doctor: " + record.getDoctor().getName() +
                             ", Date: " + record.getDateCreated());
        }
    }
}

// Exception classes
class PatientNotFoundException extends Exception {
    public PatientNotFoundException(String message) {
        super(message);
    }
}

class DoctorNotFoundException extends Exception {
    public DoctorNotFoundException(String message) {
        super(message);
    }
}

class DuplicateEntryException extends Exception {
    public DuplicateEntryException(String message) {
        super(message);
    }
}
       
