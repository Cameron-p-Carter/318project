package csci318.healthcare.financial.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long appointmentId;
    private Long patientId;
    private String patientName;
    private BigDecimal amount;

    private boolean insuranceClaimed;
    private String status; // e.g., "PENDING", "PAID", "CLAIMED"

    // Default constructor required by JPA
    public Invoice() {}

    // Full constructor (for your DataInitializer)
    public Invoice(Long appointmentId, Long patientId, String patientName, BigDecimal amount) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.amount = amount;
        this.insuranceClaimed = false;
        this.status = "PENDING";
    }

    // Getters and setters
    public Long getId() { return id; }

    public Long getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Long appointmentId) { this.appointmentId = appointmentId; }

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public boolean isInsuranceClaimed() { return insuranceClaimed; }
    public void setInsuranceClaimed(boolean insuranceClaimed) { this.insuranceClaimed = insuranceClaimed; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
