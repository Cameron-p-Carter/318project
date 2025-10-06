package csci318.healthcare.appointment.infrastructure.ai;

import csci318.healthcare.appointment.domain.Appointment;
import csci318.healthcare.appointment.domain.AppointmentType;
import csci318.healthcare.appointment.service.AppointmentService;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class AppointmentTools {

    private final AppointmentService appointmentService;

    public AppointmentTools(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Tool("Check available appointment slots for a specific doctor")
    public String checkAvailability(String doctorName, String date) {
        try {
            List<Appointment> appointments = appointmentService.getAllAppointments();
            long count = appointments.stream()
                .filter(a -> a.getDoctorName().equalsIgnoreCase(doctorName))
                .filter(a -> a.getAppointmentDateTime().toString().contains(date))
                .count();

            if (count < 8) {
                return "Dr. " + doctorName + " has availability on " + date + ". " + (8 - count) + " slots available.";
            } else {
                return "Dr. " + doctorName + " is fully booked on " + date + ". Please try another date.";
            }
        } catch (Exception e) {
            return "Error checking availability: " + e.getMessage();
        }
    }

    @Tool("Book an appointment for a patient")
    public String bookAppointment(long patientId, String doctorName, String dateTime, String appointmentType) {
        try {
            LocalDateTime dt = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            AppointmentType type = AppointmentType.valueOf(appointmentType.toUpperCase().replace(" ", "_"));

            Appointment appointment = appointmentService.scheduleAppointment(
                patientId, doctorName, dt, type, "Main Office"
            );

            return "Appointment booked successfully! Appointment ID: " + appointment.getId() +
                   " for " + doctorName + " on " + dt;
        } catch (Exception e) {
            return "Error booking appointment: " + e.getMessage() +
                   ". Please provide: patientId, doctorName, dateTime (YYYY-MM-DDTHH:MM:SS), and appointmentType (CONSULTATION, FOLLOW_UP, etc.)";
        }
    }

    @Tool("Get patient's upcoming appointments")
    public String getPatientAppointments(long patientId) {
        try {
            List<Appointment> appointments = appointmentService.getAppointmentsByPatient(patientId);
            if (appointments.isEmpty()) {
                return "No appointments found for patient ID: " + patientId;
            }

            StringBuilder result = new StringBuilder("Appointments for patient " + patientId + ":\n");
            for (Appointment apt : appointments) {
                result.append("- ID ").append(apt.getId())
                      .append(": ").append(apt.getDoctorName())
                      .append(" on ").append(apt.getAppointmentDateTime())
                      .append(" (").append(apt.getStatus()).append(")\n");
            }
            return result.toString();
        } catch (Exception e) {
            return "Error retrieving appointments: " + e.getMessage();
        }
    }

    @Tool("Reschedule an existing appointment")
    public String rescheduleAppointment(long appointmentId, String newDateTime) {
        try {
            LocalDateTime dt = LocalDateTime.parse(newDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            Appointment appointment = appointmentService.rescheduleAppointment(appointmentId, dt);
            return "Appointment " + appointmentId + " rescheduled to " + dt;
        } catch (Exception e) {
            return "Error rescheduling: " + e.getMessage();
        }
    }
}