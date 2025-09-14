package csci318.healthcare.financial.event;

import csci318.healthcare.financial.entity.Invoice;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class FinancialEventPublisher {

    private final StreamBridge streamBridge;

    public FinancialEventPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void publishInvoiceCreated(Invoice invoice) {
        streamBridge.send("financialEvents-out-0",
                "Invoice Created: " + invoice.getId() + " for appointment " + invoice.getAppointmentId());
    }

    public void publishInsuranceClaimed(Invoice invoice) {
        streamBridge.send("financialEvents-out-0",
                "Insurance Claimed: Invoice " + invoice.getId());
    }
}
