package csci318.healthcare.appointment.controller;

import csci318.healthcare.appointment.infrastructure.ai.AppointmentAssistant;
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

    private final AppointmentAssistant appointmentAssistant;

    public AIController(AppointmentAssistant appointmentAssistant) {
        this.appointmentAssistant = appointmentAssistant;
    }

    @GetMapping("/appointment-assistant")
    public ResponseEntity<Map<String, String>> chat(
            @RequestParam String sessionId,
            @RequestParam String message) {

        String response = appointmentAssistant.chat(sessionId, message);

        Map<String, String> result = new HashMap<>();
        result.put("sessionId", sessionId);
        result.put("userMessage", message);
        result.put("assistantResponse", response);

        return ResponseEntity.ok(result);
    }
}