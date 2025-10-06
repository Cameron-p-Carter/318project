package csci318.healthcare.financial.service;

import csci318.healthcare.financial.entity.Invoice;
import csci318.healthcare.financial.repository.InvoiceRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class FinancialService {

    private final InvoiceRepository repository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public FinancialService(InvoiceRepository repository, KafkaTemplate<String, String> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Invoice createInvoice(Invoice invoice) {
        return repository.save(invoice);
    }

    public List<Invoice> getAllInvoices() {
        return repository.findAll();
    }

    public List<Invoice> getInvoicesByPatientId(Long patientId) {
        return repository.findByPatientId(patientId);
    }

    public Invoice claimInsurance(Long invoiceId) {
        Invoice invoice = repository.findById(invoiceId).orElseThrow();
        invoice.setInsuranceClaimed(true);
        Invoice saved = repository.save(invoice);

        // Publish insurance claimed event
        String event = String.format(
            "{\"eventType\":\"InsuranceClaimed\",\"invoiceId\":%d,\"patientId\":%d,\"amount\":\"%s\",\"timestamp\":\"%s\",\"insuranceClaimed\":true}",
            saved.getId(), saved.getPatientId(), saved.getAmount(), Instant.now()
        );
        kafkaTemplate.send("financial-events", event);
        System.out.println("Published to Kafka: " + event);

        return saved;
    }

    public Invoice markPaid(Long invoiceId) {
        Invoice invoice = repository.findById(invoiceId).orElseThrow();
        invoice.setStatus("PAID");
        Invoice saved = repository.save(invoice);

        // Publish invoice paid event
        String event = String.format(
            "{\"eventType\":\"InvoicePaid\",\"invoiceId\":%d,\"patientId\":%d,\"amount\":\"%s\",\"timestamp\":\"%s\"}",
            saved.getId(), saved.getPatientId(), saved.getAmount(), Instant.now()
        );
        kafkaTemplate.send("financial-events", event);
        System.out.println("Published to Kafka: " + event);

        return saved;
    }
}
