package csci318.healthcare.medication.domain;

import java.time.LocalDateTime;

public class PrescriptionRefilledEvent {
    private final Long prescriptionId;
    private final Long patientId;
    private final String medicationName;
    private final Integer refillsRemaining;
    private final LocalDateTime occurredAt;

    public PrescriptionRefilledEvent(Long prescriptionId, Long patientId,
                                    String medicationName, Integer refillsRemaining) {
        this.prescriptionId = prescriptionId;
        this.patientId = patientId;
        this.medicationName = medicationName;
        this.refillsRemaining = refillsRemaining;
        this.occurredAt = LocalDateTime.now();
    }

    public Long getPrescriptionId() { return prescriptionId; }
    public Long getPatientId() { return patientId; }
    public String getMedicationName() { return medicationName; }
    public Integer getRefillsRemaining() { return refillsRemaining; }
    public LocalDateTime getOccurredAt() { return occurredAt; }
}