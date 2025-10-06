package csci318.healthcare.medication.infrastructure.repository;

import csci318.healthcare.medication.domain.Prescription;
import csci318.healthcare.medication.domain.PrescriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findByPatientId(Long patientId);
    List<Prescription> findByStatus(PrescriptionStatus status);
    List<Prescription> findByAppointmentId(Long appointmentId);
}