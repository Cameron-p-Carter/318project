package csci318.healthcare.records.domain;

import java.time.LocalDateTime;

public class DiagnosisAddedEvent {
    private final Long recordId;
    private final Long patientId;
    private final String diagnosis;
    private final LocalDateTime occurredAt;

    public DiagnosisAddedEvent(Long recordId, Long patientId, String diagnosis) {
        this.recordId = recordId;
        this.patientId = patientId;
        this.diagnosis = diagnosis;
        this.occurredAt = LocalDateTime.now();
    }

    public Long getRecordId() { return recordId; }
    public Long getPatientId() { return patientId; }
    public String getDiagnosis() { return diagnosis; }
    public LocalDateTime getOccurredAt() { return occurredAt; }
}