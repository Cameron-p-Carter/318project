package csci318.healthcare.appointment.infrastructure.repository;

import csci318.healthcare.appointment.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByEmail(String email);
    Optional<Patient> findByMedicalRecordNumber(String medicalRecordNumber);
}