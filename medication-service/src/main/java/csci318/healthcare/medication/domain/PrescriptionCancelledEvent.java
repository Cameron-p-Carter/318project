package csci318.healthcare.medication.domain;

import java.time.LocalDateTime;

public class PrescriptionCancelledEvent {
    private final Long prescriptionId;
    private final Long patientId;
    private final String medicationName;
    private final LocalDateTime occurredAt;

    public PrescriptionCancelledEvent(Long prescriptionId, Long patientId, String medicationName) {
        this.prescriptionId = prescriptionId;
        this.patientId = patientId;
        this.medicationName = medicationName;
        this.occurredAt = LocalDateTime.now();
    }

    public Long getPrescriptionId() { return prescriptionId; }
    public Long getPatientId() { return patientId; }
    public String getMedicationName() { return medicationName; }
    public LocalDateTime getOccurredAt() { return occurredAt; }
}