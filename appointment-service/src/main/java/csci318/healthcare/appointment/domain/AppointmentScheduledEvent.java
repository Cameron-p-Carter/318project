package csci318.healthcare.appointment.domain;

import java.time.LocalDateTime;

public class AppointmentScheduledEvent {
    private final Long appointmentId;
    private final Long patientId;
    private final String patientName;
    private final String doctorName;
    private final LocalDateTime appointmentDateTime;
    private final LocalDateTime occurredAt;
    
    public AppointmentScheduledEvent(Long appointmentId, Long patientId, String patientName,
                                   String doctorName, LocalDateTime appointmentDateTime) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.appointmentDateTime = appointmentDateTime;
        this.occurredAt = LocalDateTime.now();
    }
    
    public Long getAppointmentId() { return appointmentId; }
    public Long getPatientId() { return patientId; }
    public String getPatientName() { return patientName; }
    public String getDoctorName() { return doctorName; }
    public LocalDateTime getAppointmentDateTime() { return appointmentDateTime; }
    public LocalDateTime getOccurredAt() { return occurredAt; }
}