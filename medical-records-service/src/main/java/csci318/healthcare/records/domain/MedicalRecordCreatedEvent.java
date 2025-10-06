package csci318.healthcare.records.domain;

import java.time.LocalDateTime;

public class MedicalRecordCreatedEvent {
    private final Long recordId;
    private final Long patientId;
    private final String patientName;
    private final LocalDateTime occurredAt;

    public MedicalRecordCreatedEvent(Long recordId, Long patientId, String patientName) {
        this.recordId = recordId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.occurredAt = LocalDateTime.now();
    }

    public Long getRecordId() { return recordId; }
    public Long getPatientId() { return patientId; }
    public String getPatientName() { return patientName; }
    public LocalDateTime getOccurredAt() { return occurredAt; }
}