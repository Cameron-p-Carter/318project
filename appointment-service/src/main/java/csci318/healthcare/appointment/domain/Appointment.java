package csci318.healthcare.appointment.domain;

import jakarta.persistence.*;
import org.springframework.data.domain.AbstractAggregateRoot;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment extends AbstractAggregateRoot<Appointment> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    
    @Column(nullable = false)
    private String doctorName;
    
    @Column(nullable = false)
    private LocalDateTime appointmentDateTime;
    
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;
    
    @Enumerated(EnumType.STRING)
    private AppointmentType type;
    
    private String notes;
    private String location;
    
    protected Appointment() {}
    
    public Appointment(Patient patient, String doctorName, LocalDateTime appointmentDateTime, 
                      AppointmentType type, String location) {
        this.patient = patient;
        this.doctorName = doctorName;
        this.appointmentDateTime = appointmentDateTime;
        this.type = type;
        this.location = location;
        this.status = AppointmentStatus.SCHEDULED;
        
        registerEvent(new AppointmentScheduledEvent(this.id, patient.getId(), 
                     patient.getFullName(), doctorName, appointmentDateTime));
    }
    
    public void reschedule(LocalDateTime newDateTime) {
        LocalDateTime oldDateTime = this.appointmentDateTime;
        this.appointmentDateTime = newDateTime;
        registerEvent(new AppointmentRescheduledEvent(this.id, oldDateTime, newDateTime));
    }
    
    public void cancel() {
        this.status = AppointmentStatus.CANCELLED;
        registerEvent(new AppointmentCancelledEvent(this.id, this.appointmentDateTime));
    }
    
    public void complete() {
        this.status = AppointmentStatus.COMPLETED;
        registerEvent(new AppointmentCompletedEvent(this.id, this.patient.getId()));
    }
    
    public Long getId() { return id; }
    public Patient getPatient() { return patient; }
    public String getDoctorName() { return doctorName; }
    public LocalDateTime getAppointmentDateTime() { return appointmentDateTime; }
    public AppointmentStatus getStatus() { return status; }
    public AppointmentType getType() { return type; }
    public String getNotes() { return notes; }
    public String getLocation() { return location; }
    
    public void setNotes(String notes) { this.notes = notes; }
}