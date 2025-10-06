package csci318.healthcare.financial.event;

import csci318.healthcare.financial.entity.Invoice;
import csci318.healthcare.financial.service.FinancialService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AppointmentEventListener {

    private final FinancialService financialService;

    public AppointmentEventListener(FinancialService financialService) {
        this.financialService = financialService;
    }

    @KafkaListener(topics = "appointment-events", groupId = "financial-service")
    public void handleAppointmentEvent(String message) {
        System.out.println("Financial Service received event: " + message);

        // Parse the event (simple string parsing for demo)
        if (message.contains("\"eventType\":\"AppointmentCompleted\"")) {
            try {
                // Extract appointment and patient IDs
                Long appointmentId = extractLong(message, "appointmentId");
                Long patientId = extractLong(message, "patientId");

                // Create invoice automatically
                Invoice invoice = new Invoice(
                    appointmentId,
                    patientId,
                    "Patient-" + patientId,
                    new BigDecimal("150.00") // Standard consultation fee
                );

                financialService.createInvoice(invoice);
                System.out.println("✓ Auto-created invoice for appointment: " + appointmentId);

            } catch (Exception e) {
                System.err.println("Error creating invoice from event: " + e.getMessage());
            }
        }
    }

    private Long extractLong(String json, String key) {
        String pattern = "\"" + key + "\":";
        int start = json.indexOf(pattern) + pattern.length();
        int end = json.indexOf(",", start);
        if (end == -1) end = json.indexOf("}", start);
        return Long.parseLong(json.substring(start, end).trim());
    }
}