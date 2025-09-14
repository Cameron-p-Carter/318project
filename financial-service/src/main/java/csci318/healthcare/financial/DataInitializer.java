package csci318.healthcare.financial;

import csci318.healthcare.financial.entity.Invoice;
import csci318.healthcare.financial.service.FinancialService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    private final FinancialService service;

    public DataInitializer(FinancialService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) {
        System.out.println("📦 Initializing sample invoices...");

        Invoice inv1 = new Invoice(101L, 1L, "John Doe", BigDecimal.valueOf(150));
        Invoice inv2 = new Invoice(102L, 2L, "Jane Smith", BigDecimal.valueOf(200));
        Invoice inv3 = new Invoice(103L, 1L, "John Doe", BigDecimal.valueOf(100));

        service.createInvoice(inv1);
        service.createInvoice(inv2);
        service.createInvoice(inv3);

        System.out.println("✅ Sample invoices created!");
    }
}
