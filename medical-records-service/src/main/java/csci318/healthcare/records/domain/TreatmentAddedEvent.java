package csci318.healthcare.records.domain;

import java.time.LocalDateTime;

public class TreatmentAddedEvent {
    private final Long recordId;
    private final Long patientId;
    private final String treatment;
    private final LocalDateTime occurredAt;

    public TreatmentAddedEvent(Long recordId, Long patientId, String treatment) {
        this.recordId = recordId;
        this.patientId = patientId;
        this.treatment = treatment;
        this.occurredAt = LocalDateTime.now();
    }

    public Long getRecordId() { return recordId; }
    public Long getPatientId() { return patientId; }
    public String getTreatment() { return treatment; }
    public LocalDateTime getOccurredAt() { return occurredAt; }
}