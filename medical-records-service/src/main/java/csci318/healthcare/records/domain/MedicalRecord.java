package csci318.healthcare.records.domain;

import jakarta.persistence.*;
import org.springframework.data.domain.AbstractAggregateRoot;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "medical_records")
public class MedicalRecord extends AbstractAggregateRoot<MedicalRecord> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patientId;
    private String patientName;

    @ElementCollection
    @CollectionTable(name = "diagnoses", joinColumns = @JoinColumn(name = "record_id"))
    private List<String> diagnoses = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "treatments", joinColumns = @JoinColumn(name = "record_id"))
    private List<String> treatments = new ArrayList<>();

    @Column(length = 2000)
    private String notes;

    private LocalDateTime createdDate;
    private LocalDateTime lastUpdated;

    protected MedicalRecord() {}

    public MedicalRecord(Long patientId, String patientName) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.createdDate = LocalDateTime.now();
        this.lastUpdated = LocalDateTime.now();

        registerEvent(new MedicalRecordCreatedEvent(this.id, patientId, patientName));
    }

    public void addDiagnosis(String diagnosis) {
        this.diagnoses.add(diagnosis);
        this.lastUpdated = LocalDateTime.now();
        registerEvent(new DiagnosisAddedEvent(this.id, this.patientId, diagnosis));
    }

    public void addTreatment(String treatment) {
        this.treatments.add(treatment);
        this.lastUpdated = LocalDateTime.now();
        registerEvent(new TreatmentAddedEvent(this.id, this.patientId, treatment));
    }

    public void updateNotes(String additionalNotes) {
        if (this.notes == null) {
            this.notes = additionalNotes;
        } else {
            this.notes += "\n" + additionalNotes;
        }
        this.lastUpdated = LocalDateTime.now();
    }

    // Getters
    public Long getId() { return id; }
    public Long getPatientId() { return patientId; }
    public String getPatientName() { return patientName; }
    public List<String> getDiagnoses() { return diagnoses; }
    public List<String> getTreatments() { return treatments; }
    public String getNotes() { return notes; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public LocalDateTime getLastUpdated() { return lastUpdated; }
}