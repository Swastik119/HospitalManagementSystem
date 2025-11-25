package exceptions;

public class AppointmentNotFoundException extends Exception {
    public AppointmentNotFoundException(String message) {
        super(message);
    }
    
    public AppointmentNotFoundException(int appointmentId) {
        super("Appointment with ID " + appointmentId + " not found.");
    }
}
