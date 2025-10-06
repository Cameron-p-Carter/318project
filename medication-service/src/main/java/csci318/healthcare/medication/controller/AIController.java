package csci318.healthcare.medication.controller;

import csci318.healthcare.medication.infrastructure.ai.MedicationAssistant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    private final MedicationAssistant medicationAssistant;

    public AIController(MedicationAssistant medicationAssistant) {
        this.medicationAssistant = medicationAssistant;
    }

    @GetMapping("/medication-checker")
    public ResponseEntity<Map<String, String>> chat(
            @RequestParam String sessionId,
            @RequestParam String message) {

        String response = medicationAssistant.chat(sessionId, message);

        Map<String, String> result = new HashMap<>();
        result.put("sessionId", sessionId);
        result.put("userMessage", message);
        result.put("assistantResponse", response);

        return ResponseEntity.ok(result);
    }
}