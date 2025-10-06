package csci318.healthcare.medication.domain;

import java.time.LocalDateTime;

public class PrescriptionCreatedEvent {
    private final Long prescriptionId;
    private final Long patientId;
    private final String medicationName;
    private final String dosage;
    private final LocalDateTime occurredAt;

    public PrescriptionCreatedEvent(Long prescriptionId, Long patientId,
                                   String medicationName, String dosage) {
        this.prescriptionId = prescriptionId;
        this.patientId = patientId;
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.occurredAt = LocalDateTime.now();
    }

    public Long getPrescriptionId() { return prescriptionId; }
    public Long getPatientId() { return patientId; }
    public String getMedicationName() { return medicationName; }
    public String getDosage() { return dosage; }
    public LocalDateTime getOccurredAt() { return occurredAt; }
}