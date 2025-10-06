package csci318.healthcare.records.service;

import csci318.healthcare.records.domain.MedicalRecord;
import csci318.healthcare.records.infrastructure.repository.MedicalRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MedicalRecordsService {

    private final MedicalRecordRepository recordRepository;

    public MedicalRecordsService(MedicalRecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public MedicalRecord createRecord(Long patientId, String patientName) {
        MedicalRecord record = new MedicalRecord(patientId, patientName);
        return recordRepository.save(record);
    }

    public MedicalRecord addDiagnosis(Long recordId, String diagnosis) {
        MedicalRecord record = recordRepository.findById(recordId)
            .orElseThrow(() -> new RuntimeException("Medical record not found"));

        record.addDiagnosis(diagnosis);
        return recordRepository.save(record);
    }

    public MedicalRecord addTreatment(Long recordId, String treatment) {
        MedicalRecord record = recordRepository.findById(recordId)
            .orElseThrow(() -> new RuntimeException("Medical record not found"));

        record.addTreatment(treatment);
        return recordRepository.save(record);
    }

    public MedicalRecord updateNotes(Long recordId, String notes) {
        MedicalRecord record = recordRepository.findById(recordId)
            .orElseThrow(() -> new RuntimeException("Medical record not found"));

        record.updateNotes(notes);
        return recordRepository.save(record);
    }

    public MedicalRecord getRecordByPatientId(Long patientId) {
        return recordRepository.findByPatientId(patientId)
            .orElseThrow(() -> new RuntimeException("Medical record not found for patient"));
    }

    public List<MedicalRecord> getAllRecords() {
        return recordRepository.findAll();
    }

    public MedicalRecord getRecordById(Long id) {
        return recordRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Medical record not found"));
    }
}