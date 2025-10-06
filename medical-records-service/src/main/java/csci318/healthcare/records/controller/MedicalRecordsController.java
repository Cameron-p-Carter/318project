package csci318.healthcare.records.controller;

import csci318.healthcare.records.domain.MedicalRecord;
import csci318.healthcare.records.service.MedicalRecordsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/medical-records")
public class MedicalRecordsController {

    private final MedicalRecordsService recordsService;

    public MedicalRecordsController(MedicalRecordsService recordsService) {
        this.recordsService = recordsService;
    }

    @PostMapping
    public ResponseEntity<MedicalRecord> createRecord(@RequestBody Map<String, Object> request) {
        Long patientId = Long.valueOf(request.get("patientId").toString());
        String patientName = request.get("patientName").toString();

        MedicalRecord record = recordsService.createRecord(patientId, patientName);
        return ResponseEntity.status(HttpStatus.CREATED).body(record);
    }

    @GetMapping
    public ResponseEntity<List<MedicalRecord>> getAllRecords() {
        return ResponseEntity.ok(recordsService.getAllRecords());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecord> getRecord(@PathVariable Long id) {
        return ResponseEntity.ok(recordsService.getRecordById(id));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<MedicalRecord> getRecordByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(recordsService.getRecordByPatientId(patientId));
    }

    @PutMapping("/{id}/diagnosis")
    public ResponseEntity<MedicalRecord> addDiagnosis(@PathVariable Long id,
                                                     @RequestBody Map<String, String> request) {
        String diagnosis = request.get("diagnosis");
        MedicalRecord record = recordsService.addDiagnosis(id, diagnosis);
        return ResponseEntity.ok(record);
    }

    @PutMapping("/{id}/treatment")
    public ResponseEntity<MedicalRecord> addTreatment(@PathVariable Long id,
                                                     @RequestBody Map<String, String> request) {
        String treatment = request.get("treatment");
        MedicalRecord record = recordsService.addTreatment(id, treatment);
        return ResponseEntity.ok(record);
    }

    @PutMapping("/{id}/notes")
    public ResponseEntity<MedicalRecord> updateNotes(@PathVariable Long id,
                                                    @RequestBody Map<String, String> request) {
        String notes = request.get("notes");
        MedicalRecord record = recordsService.updateNotes(id, notes);
        return ResponseEntity.ok(record);
    }
}