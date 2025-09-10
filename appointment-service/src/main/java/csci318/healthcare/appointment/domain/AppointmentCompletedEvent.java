package csci318.healthcare.appointment.domain;

import java.time.LocalDateTime;

public class AppointmentCompletedEvent {
    private final Long appointmentId;
    private final Long patientId;
    private final LocalDateTime occurredAt;
    
    public AppointmentCompletedEvent(Long appointmentId, Long patientId) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.occurredAt = LocalDateTime.now();
    }
    
    public Long getAppointmentId() { return appointmentId; }
    public Long getPatientId() { return patientId; }
    public LocalDateTime getOccurredAt() { return occurredAt; }
}