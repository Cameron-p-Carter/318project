package csci318.healthcare.analytics.controller;

import csci318.healthcare.analytics.service.InteractiveQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final InteractiveQuery interactiveQuery;

    public AnalyticsController(InteractiveQuery interactiveQuery) {
        this.interactiveQuery = interactiveQuery;
    }

    @GetMapping("/appointments-per-hour")
    public ResponseEntity<List<Map<String, Object>>> getAppointmentsPerHour() {
        return ResponseEntity.ok(interactiveQuery.getAppointmentAnalytics());
    }

    @GetMapping("/insurance-claims")
    public ResponseEntity<List<Map<String, Object>>> getInsuranceClaims() {
        return ResponseEntity.ok(interactiveQuery.getInsuranceClaimsAnalytics());
    }
}