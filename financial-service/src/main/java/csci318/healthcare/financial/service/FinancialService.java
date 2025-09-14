package csci318.healthcare.financial.service;

import csci318.healthcare.financial.entity.Invoice;
import csci318.healthcare.financial.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinancialService {

    private final InvoiceRepository repository;

    public FinancialService(InvoiceRepository repository) {
        this.repository = repository;
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
        return repository.save(invoice);
    }

    public Invoice markPaid(Long invoiceId) {
        Invoice invoice = repository.findById(invoiceId).orElseThrow();
        invoice.setStatus("PAID");
        return repository.save(invoice);
    }
}
