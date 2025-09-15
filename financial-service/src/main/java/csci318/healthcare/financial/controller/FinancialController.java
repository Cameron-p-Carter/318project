package csci318.healthcare.financial.controller;

import csci318.healthcare.financial.entity.Invoice;
import csci318.healthcare.financial.service.FinancialService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/financial")
public class FinancialController {

    private final FinancialService service;

    public FinancialController(FinancialService service) {
        this.service = service;
    }

    @GetMapping("/invoices")
    public List<Invoice> getAllInvoices() {
        return service.getAllInvoices();
    }

    @PostMapping("/invoices/{id}/claim")
    public Invoice claimInsurance(@PathVariable Long id) {
        return service.claimInsurance(id);
    }

    @PostMapping("/invoices/{id}/pay")
    public Invoice markPaid(@PathVariable Long id) {
        return service.markPaid(id);
    }
}
