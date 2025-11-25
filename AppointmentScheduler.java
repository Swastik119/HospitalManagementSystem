package multithreading;

import model.Appointment;
import collections.HospitalRegistry;
import exceptions.AppointmentNotFoundException;
import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AppointmentScheduler {
    private final BlockingQueue<Appointment> appointmentQueue;
    private final HospitalRegistry<Appointment> appointmentRegistry;
    private final ScheduledExecutorService scheduler;
    private volatile boolean isRunning;
    
    public AppointmentScheduler(HospitalRegistry<Appointment> appointmentRegistry) {
        this.appointmentQueue = new LinkedBlockingQueue<>();
        this.appointmentRegistry = appointmentRegistry;
        this.scheduler = Executors.newScheduledThreadPool(2);
        this.isRunning = false;
    }
    
    public void startScheduler() {
        isRunning = true;
        
        // Process appointments every 5 seconds
        scheduler.scheduleAtFixedRate(this::processAppointments, 0, 5, TimeUnit.SECONDS);
        
        // Monitor queue size every 10 seconds
        scheduler.scheduleAtFixedRate(this::monitorQueue, 0, 10, TimeUnit.SECONDS);
        
        System.out.println("Appointment Scheduler started.");
    }
    
    public void stopScheduler() {
        isRunning = false;
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.println("Appointment Scheduler stopped.");
    }
    
    public void scheduleAppointment(Appointment appointment) {
        try {
            appointmentQueue.put(appointment);
            appointmentRegistry.add(appointment);
            System.out.println("Appointment scheduled and added to queue: " + appointment.getId());
        } catch (Exception e) {
            System.err.println("Error scheduling appointment: " + e.getMessage());
        }
    }
    
    private void processAppointments() {
        if (!isRunning) return;
        
        try {
            Appointment appointment = appointmentQueue.poll(1, TimeUnit.SECONDS);
            if (appointment != null) {
                processSingleAppointment(appointment);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Appointment processing interrupted.");
        }
    }
    
    private void processSingleAppointment(Appointment appointment) {
        System.out.println("Processing appointment: " + appointment.getId());
        
        // Simulate appointment processing
        try {
            Thread.sleep(1000); // Simulate processing time
            
            // Check if appointment is for today or in the past
            if (appointment.getAppointmentDateTime().isBefore(LocalDateTime.now())) {
                appointment.completeAppointment();
                System.out.println("Appointment " + appointment.getId() + " processed and completed.");
            } else {
                System.out.println("Appointment " + appointment.getId() + " is scheduled for future.");
            }
        } catch (Exception e) {
            System.err.println("Error processing appointment " + appointment.getId() + ": " + e.getMessage());
        }
    }
    
    private void monitorQueue() {
        if (!isRunning) return;
        
        int queueSize = appointmentQueue.size();
        System.out.println("Appointment Queue Monitoring - Current size: " + queueSize);
        
        if (queueSize > 10) {
            System.out.println("Warning: Appointment queue is getting large!");
        }
    }
    
    public void cancelAppointment(int appointmentId) throws AppointmentNotFoundException {
        try {
            Appointment appointment = appointmentRegistry.getById(appointmentId);
            if (appointmentQueue.remove(appointment)) {
                appointment.cancelAppointment();
                System.out.println("Appointment " + appointmentId + " cancelled and removed from queue.");
            } else {
                throw new AppointmentNotFoundException("Appointment not found in queue: " + appointmentId);
            }
        } catch (Exception e) {
            throw new AppointmentNotFoundException("Error cancelling appointment: " + e.getMessage());
        }
    }
    
    public int getQueueSize() {
        return appointmentQueue.size();
    }
}
