package exceptions;

public class DoctorNotFoundException extends Exception {
    public DoctorNotFoundException(String message) {
        super(message);
    }
    
    public DoctorNotFoundException(int doctorId) {
        super("Doctor with ID " + doctorId + " not found.");
    }
}
