package csci318.healthcare.records.infrastructure;

import csci318.healthcare.records.domain.MedicalRecord;
import csci318.healthcare.records.infrastructure.repository.MedicalRecordRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final MedicalRecordRepository recordRepository;

    public DataInitializer(MedicalRecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @Override
    public void run(String... args) {
        // Create sample medical records
        MedicalRecord record1 = new MedicalRecord(1L, "John Doe");
        record1.addDiagnosis("Hypertension");
        record1.addDiagnosis("Type 2 Diabetes");
        record1.addTreatment("Lisinopril 10mg daily");
        record1.addTreatment("Metformin 500mg twice daily");
        record1.updateNotes("Patient shows good compliance with medication regimen");

        MedicalRecord record2 = new MedicalRecord(2L, "Jane Smith");
        record2.addDiagnosis("Seasonal Allergies");
        record2.addTreatment("Cetirizine 10mg as needed");
        record2.updateNotes("Allergy symptoms controlled with antihistamine");

        MedicalRecord record3 = new MedicalRecord(3L, "Bob Johnson");
        record3.addDiagnosis("Acute Bronchitis");
        record3.addTreatment("Amoxicillin 500mg three times daily for 10 days");
        record3.updateNotes("Follow-up scheduled in 2 weeks");

        recordRepository.save(record1);
        recordRepository.save(record2);
        recordRepository.save(record3);

        System.out.println("=== Medical Records Service Data Initialized ===");
        System.out.println("Medical records loaded: " + recordRepository.count());
    }
}