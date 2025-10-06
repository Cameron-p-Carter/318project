package csci318.healthcare.appointment.controller;

import csci318.healthcare.appointment.controller.dto.PatientRegistrationRequest;
import csci318.healthcare.appointment.domain.Patient;
import csci318.healthcare.appointment.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final AppointmentService appointmentService;

    public PatientController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<Patient> registerPatient(@RequestBody PatientRegistrationRequest request) {
        Patient patient = new Patient(
            request.getFirstName(),
            request.getLastName(),
            request.getEmail(),
            request.getPhone(),
            request.getDateOfBirth()
        );

        Patient savedPatient = appointmentService.registerPatient(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPatient);
    }

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        return ResponseEntity.ok(appointmentService.getAllPatients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatient(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getPatientById(id));
    }
}