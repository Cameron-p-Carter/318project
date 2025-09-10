package csci318.healthcare.appointment.domain;

import java.time.LocalDateTime;

public class AppointmentCancelledEvent {
    private final Long appointmentId;
    private final LocalDateTime originalDateTime;
    private final LocalDateTime occurredAt;
    
    public AppointmentCancelledEvent(Long appointmentId, LocalDateTime originalDateTime) {
        this.appointmentId = appointmentId;
        this.originalDateTime = originalDateTime;
        this.occurredAt = LocalDateTime.now();
    }
    
    public Long getAppointmentId() { return appointmentId; }
    public LocalDateTime getOriginalDateTime() { return originalDateTime; }
    public LocalDateTime getOccurredAt() { return occurredAt; }
}