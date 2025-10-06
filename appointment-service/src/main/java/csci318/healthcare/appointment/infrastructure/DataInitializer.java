package csci318.healthcare.appointment.infrastructure;

import csci318.healthcare.appointment.domain.*;
import csci318.healthcare.appointment.infrastructure.repository.AppointmentRepository;
import csci318.healthcare.appointment.infrastructure.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    public DataInitializer(PatientRepository patientRepository,
                          AppointmentRepository appointmentRepository) {
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public void run(String... args) {
        // Create sample patients
        Patient patient1 = new Patient(
            "John",
            "Doe",
            "john.doe@example.com",
            "555-0123",
            LocalDate.of(1990, 5, 15)
        );

        Patient patient2 = new Patient(
            "Jane",
            "Smith",
            "jane.smith@example.com",
            "555-0456",
            LocalDate.of(1985, 8, 22)
        );

        Patient patient3 = new Patient(
            "Bob",
            "Johnson",
            "bob.johnson@example.com",
            "555-0789",
            LocalDate.of(2000, 3, 10)
        );

        patientRepository.save(patient1);
        patientRepository.save(patient2);
        patientRepository.save(patient3);

        // Create sample appointments
        Appointment appt1 = new Appointment(
            patient1,
            "Dr. Smith",
            LocalDateTime.now().plusDays(1).withHour(10).withMinute(0),
            AppointmentType.CONSULTATION,
            "Room 101"
        );

        Appointment appt2 = new Appointment(
            patient2,
            "Dr. Johnson",
            LocalDateTime.now().plusDays(2).withHour(14).withMinute(30),
            AppointmentType.FOLLOW_UP,
            "Room 202"
        );

        Appointment appt3 = new Appointment(
            patient1,
            "Dr. Williams",
            LocalDateTime.now().plusDays(5).withHour(9).withMinute(0),
            AppointmentType.ROUTINE_CHECKUP,
            "Room 105"
        );

        appointmentRepository.save(appt1);
        appointmentRepository.save(appt2);
        appointmentRepository.save(appt3);

        System.out.println("=== Appointment Service Data Initialized ===");
        System.out.println("Patients loaded: " + patientRepository.count());
        System.out.println("Appointments loaded: " + appointmentRepository.count());
    }
}