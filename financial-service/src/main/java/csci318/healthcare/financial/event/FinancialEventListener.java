package csci318.healthcare.financial.event;

import csci318.healthcare.financial.entity.Invoice;
import csci318.healthcare.financial.service.FinancialService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.function.Consumer;

@Component
public class FinancialEventListener {

    private final FinancialService service;

    public FinancialEventListener(FinancialService service) {
        this.service = service;
    }

    @Bean
    public Consumer<String> appointmentEvents() {
        return message -> {
            try {
                String[] parts = message.split(",");
                Long appointmentId = Long.parseLong(parts[0]);
                Long patientId = Long.parseLong(parts[1]);
                String patientName = parts[2];

                Invoice invoice = new Invoice();
                invoice.setAppointmentId(appointmentId);
                invoice.setPatientId(patientId);
                invoice.setPatientName(patientName);
                invoice.setAmount(BigDecimal.valueOf(100)); // example
                service.createInvoice(invoice);

                System.out.println("📩 Processed appointment event for patient " + patientName);
            } catch (Exception e) {
                System.err.println("Failed to process appointment event: " + e.getMessage());
            }
        };
    }
}
