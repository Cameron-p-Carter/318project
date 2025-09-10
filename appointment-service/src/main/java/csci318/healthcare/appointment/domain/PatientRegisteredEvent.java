package csci318.healthcare.appointment.domain;

import java.time.LocalDateTime;

public class PatientRegisteredEvent {
    private final Long patientId;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final LocalDateTime occurredAt;
    
    public PatientRegisteredEvent(Long patientId, String firstName, String lastName, String email) {
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.occurredAt = LocalDateTime.now();
    }
    
    public Long getPatientId() { return patientId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public LocalDateTime getOccurredAt() { return occurredAt; }
}