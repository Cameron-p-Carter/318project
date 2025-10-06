package csci318.healthcare.medication.controller;

import csci318.healthcare.medication.domain.Prescription;
import csci318.healthcare.medication.service.MedicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
public class MedicationController {

    private final MedicationService medicationService;

    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    @PostMapping
    public ResponseEntity<Prescription> createPrescription(@RequestBody Prescription prescription) {
        Prescription saved = medicationService.createPrescription(prescription);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<Prescription>> getAllPrescriptions() {
        return ResponseEntity.ok(medicationService.getAllPrescriptions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prescription> getPrescription(@PathVariable Long id) {
        return ResponseEntity.ok(medicationService.getPrescriptionById(id));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Prescription>> getPrescriptionsByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(medicationService.getPrescriptionsByPatient(patientId));
    }

    @PutMapping("/{id}/refill")
    public ResponseEntity<Prescription> refillPrescription(@PathVariable Long id) {
        Prescription prescription = medicationService.refillPrescription(id);
        return ResponseEntity.ok(prescription);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Prescription> cancelPrescription(@PathVariable Long id) {
        Prescription prescription = medicationService.cancelPrescription(id);
        return ResponseEntity.ok(prescription);
    }
}