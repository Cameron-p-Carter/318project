package csci318.healthcare.medication.domain;

import jakarta.persistence.*;
import org.springframework.data.domain.AbstractAggregateRoot;
import java.time.LocalDate;

@Entity
@Table(name = "prescriptions")
public class Prescription extends AbstractAggregateRoot<Prescription> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patientId;
    private Long appointmentId;
    private String patientName;
    private String doctorName;

    @Column(nullable = false)
    private String medicationName;

    private String dosage;
    private String frequency;
    private Integer durationDays;

    private LocalDate prescribedDate;
    private LocalDate expiryDate;

    @Enumerated(EnumType.STRING)
    private PrescriptionStatus status;

    private Integer refillsRemaining;

    protected Prescription() {}

    public Prescription(Long patientId, Long appointmentId, String patientName,
                       String doctorName, String medicationName, String dosage,
                       String frequency, Integer durationDays, Integer refillsAllowed) {
        this.patientId = patientId;
        this.appointmentId = appointmentId;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.frequency = frequency;
        this.durationDays = durationDays;
        this.prescribedDate = LocalDate.now();
        this.expiryDate = LocalDate.now().plusDays(durationDays);
        this.status = PrescriptionStatus.ACTIVE;
        this.refillsRemaining = refillsAllowed;

        registerEvent(new PrescriptionCreatedEvent(this.id, patientId, medicationName, dosage));
    }

    public void refill() {
        if (refillsRemaining > 0 && status == PrescriptionStatus.ACTIVE) {
            refillsRemaining--;
            registerEvent(new PrescriptionRefilledEvent(this.id, this.patientId,
                        this.medicationName, refillsRemaining));
        }
    }

    public void cancel() {
        this.status = PrescriptionStatus.CANCELLED;
        registerEvent(new PrescriptionCancelledEvent(this.id, this.patientId, this.medicationName));
    }

    public void complete() {
        this.status = PrescriptionStatus.COMPLETED;
    }

    // Getters
    public Long getId() { return id; }
    public Long getPatientId() { return patientId; }
    public Long getAppointmentId() { return appointmentId; }
    public String getPatientName() { return patientName; }
    public String getDoctorName() { return doctorName; }
    public String getMedicationName() { return medicationName; }
    public String getDosage() { return dosage; }
    public String getFrequency() { return frequency; }
    public Integer getDurationDays() { return durationDays; }
    public LocalDate getPrescribedDate() { return prescribedDate; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public PrescriptionStatus getStatus() { return status; }
    public Integer getRefillsRemaining() { return refillsRemaining; }
}