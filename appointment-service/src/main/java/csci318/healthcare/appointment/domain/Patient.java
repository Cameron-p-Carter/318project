package csci318.healthcare.appointment.domain;

import jakarta.persistence.*;
import org.springframework.data.domain.AbstractAggregateRoot;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patients")
public class Patient extends AbstractAggregateRoot<Patient> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String phone;
    
    private LocalDate dateOfBirth;
    
    @Column(name = "medical_record_number", unique = true)
    private String medicalRecordNumber;
    
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments = new ArrayList<>();
    
    protected Patient() {}
    
    public Patient(String firstName, String lastName, String email, String phone, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.medicalRecordNumber = generateMedicalRecordNumber();
        
        registerEvent(new PatientRegisteredEvent(this.id, this.firstName, this.lastName, this.email));
    }
    
    private String generateMedicalRecordNumber() {
        return "MRN-" + System.currentTimeMillis();
    }
    
    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getMedicalRecordNumber() { return medicalRecordNumber; }
    public List<Appointment> getAppointments() { return appointments; }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
}