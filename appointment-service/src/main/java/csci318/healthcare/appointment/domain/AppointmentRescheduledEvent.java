package csci318.healthcare.appointment.domain;

import java.time.LocalDateTime;

public class AppointmentRescheduledEvent {
    private final Long appointmentId;
    private final LocalDateTime oldDateTime;
    private final LocalDateTime newDateTime;
    private final LocalDateTime occurredAt;
    
    public AppointmentRescheduledEvent(Long appointmentId, LocalDateTime oldDateTime, LocalDateTime newDateTime) {
        this.appointmentId = appointmentId;
        this.oldDateTime = oldDateTime;
        this.newDateTime = newDateTime;
        this.occurredAt = LocalDateTime.now();
    }
    
    public Long getAppointmentId() { return appointmentId; }
    public LocalDateTime getOldDateTime() { return oldDateTime; }
    public LocalDateTime getNewDateTime() { return newDateTime; }
    public LocalDateTime getOccurredAt() { return occurredAt; }
}