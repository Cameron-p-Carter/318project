package csci318.healthcare.appointment.controller.dto;

import csci318.healthcare.appointment.domain.AppointmentType;
import java.time.LocalDateTime;

public class AppointmentRequest {
    private Long patientId;
    private String doctorName;
    private LocalDateTime appointmentDateTime;
    private AppointmentType type;
    private String location;

    public AppointmentRequest() {}

    public AppointmentRequest(Long patientId, String doctorName, LocalDateTime appointmentDateTime,
                            AppointmentType type, String location) {
        this.patientId = patientId;
        this.doctorName = doctorName;
        this.appointmentDateTime = appointmentDateTime;
        this.type = type;
        this.location = location;
    }

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }

    public LocalDateTime getAppointmentDateTime() { return appointmentDateTime; }
    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }

    public AppointmentType getType() { return type; }
    public void setType(AppointmentType type) { this.type = type; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}