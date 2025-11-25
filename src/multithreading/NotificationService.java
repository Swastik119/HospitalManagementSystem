package multithreading;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NotificationService {
    private final ScheduledExecutorService notificationService;
    
    public NotificationService() {
        this.notificationService = Executors.newScheduledThreadPool(1);
    }
    
    public void startNotificationService() {
        // Send periodic notifications every 30 seconds
        notificationService.scheduleAtFixedRate(this::sendScheduledNotifications, 0, 30, TimeUnit.SECONDS);
        System.out.println("Notification Service started.");
    }
    
    public void stopNotificationService() {
        notificationService.shutdown();
        try {
            if (!notificationService.awaitTermination(5, TimeUnit.SECONDS)) {
                notificationService.shutdownNow();
            }
        } catch (InterruptedException e) {
            notificationService.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.println("Notification Service stopped.");
    }
    
    private void sendScheduledNotifications() {
        System.out.println("=== SYSTEM NOTIFICATION ===");
        System.out.println("Regular health check: All systems operational.");
        System.out.println("Next maintenance window: Sunday 2:00 AM - 4:00 AM");
        System.out.println("===========================");
    }
    
    public void sendAppointmentReminder(String patientName, String doctorName, String dateTime) {
        notificationService.submit(() -> {
            try {
                Thread.sleep(1000); // Simulate notification delay
                System.out.println("=== APPOINTMENT REMINDER ===");
                System.out.println("Dear " + patientName + ",");
                System.out.println("You have an appointment with Dr. " + doctorName);
                System.out.println("Scheduled for: " + dateTime);
                System.out.println("Please arrive 15 minutes early.");
                System.out.println("============================");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
    
    public void sendBillNotification(String patientName, double amount) {
        notificationService.submit(() -> {
            try {
                Thread.sleep(500); // Simulate notification delay
                System.out.println("=== BILL NOTIFICATION ===");
                System.out.println("Dear " + patientName + ",");
                System.out.println("Your outstanding bill amount is: $" + amount);
                System.out.println("Please make payment at your earliest convenience.");
                System.out.println("==========================");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
}
