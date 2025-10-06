package csci318.healthcare.medication.infrastructure;

import csci318.healthcare.medication.domain.Prescription;
import csci318.healthcare.medication.infrastructure.repository.PrescriptionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PrescriptionRepository prescriptionRepository;

    public DataInitializer(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    @Override
    public void run(String... args) {
        // Sample prescriptions
        Prescription rx1 = new Prescription(
            1L, 1L, "John Doe", "Dr. Smith",
            "Amoxicillin", "500mg", "3 times daily", 10, 2
        );

        Prescription rx2 = new Prescription(
            2L, 2L, "Jane Smith", "Dr. Johnson",
            "Ibuprofen", "200mg", "As needed", 30, 3
        );

        Prescription rx3 = new Prescription(
            1L, 3L, "John Doe", "Dr. Williams",
            "Lisinopril", "10mg", "Once daily", 90, 5
        );

        prescriptionRepository.save(rx1);
        prescriptionRepository.save(rx2);
        prescriptionRepository.save(rx3);

        System.out.println("=== Medication Service Data Initialized ===");
        System.out.println("Prescriptions loaded: " + prescriptionRepository.count());
    }
}