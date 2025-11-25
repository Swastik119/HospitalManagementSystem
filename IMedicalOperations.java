package interfaces;

import model.Appointment;
import model.MedicalRecord;

public interface IMedicalOperations {
    void scheduleAppointment(Appointment appointment);
    void completeAppointment(int appointmentId) throws exceptions.AppointmentNotFoundException;
    void cancelAppointment(int appointmentId) throws exceptions.AppointmentNotFoundException;
    void addMedicalRecord(MedicalRecord record);
}
