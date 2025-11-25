package exceptions;

public class PatientNotFoundException extends Exception {
    public PatientNotFoundException(String message) {
        super(message);
    }
    
    public PatientNotFoundException(int patientId) {
        super("Patient with ID " + patientId + " not found.");
    }
}
