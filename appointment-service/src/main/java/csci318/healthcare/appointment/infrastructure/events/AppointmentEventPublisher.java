package csci318.healthcare.appointment.infrastructure.events;

import csci318.healthcare.appointment.domain.*;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class AppointmentEventPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String APPOINTMENT_TOPIC = "appointment-events";
    private static final String PATIENT_TOPIC = "patient-events";

    public AppointmentEventPublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @EventListener
    public void handlePatientRegistered(PatientRegisteredEvent event) {
        String message = String.format(
            "{\"eventType\":\"PatientRegistered\",\"patientId\":%d,\"firstName\":\"%s\",\"lastName\":\"%s\",\"email\":\"%s\",\"timestamp\":\"%s\"}",
            event.getPatientId(),
            event.getFirstName(),
            event.getLastName(),
            event.getEmail(),
            event.getOccurredAt()
        );
        kafkaTemplate.send(PATIENT_TOPIC, message);
        System.out.println("Published to Kafka: " + message);
    }

    @EventListener
    public void handleAppointmentScheduled(AppointmentScheduledEvent event) {
        String message = String.format(
            "{\"eventType\":\"AppointmentScheduled\",\"appointmentId\":%d,\"patientId\":%d,\"patientName\":\"%s\",\"doctorName\":\"%s\",\"appointmentDateTime\":\"%s\",\"timestamp\":\"%s\"}",
            event.getAppointmentId(),
            event.getPatientId(),
            event.getPatientName(),
            event.getDoctorName(),
            event.getAppointmentDateTime(),
            event.getOccurredAt()
        );
        kafkaTemplate.send(APPOINTMENT_TOPIC, message);
        System.out.println("Published to Kafka: " + message);
    }

    @EventListener
    public void handleAppointmentCompleted(AppointmentCompletedEvent event) {
        String message = String.format(
            "{\"eventType\":\"AppointmentCompleted\",\"appointmentId\":%d,\"patientId\":%d,\"timestamp\":\"%s\"}",
            event.getAppointmentId(),
            event.getPatientId(),
            event.getOccurredAt()
        );
        kafkaTemplate.send(APPOINTMENT_TOPIC, message);
        System.out.println("Published to Kafka: " + message);
    }

    @EventListener
    public void handleAppointmentCancelled(AppointmentCancelledEvent event) {
        String message = String.format(
            "{\"eventType\":\"AppointmentCancelled\",\"appointmentId\":%d,\"originalDateTime\":\"%s\",\"timestamp\":\"%s\"}",
            event.getAppointmentId(),
            event.getOriginalDateTime(),
            event.getOccurredAt()
        );
        kafkaTemplate.send(APPOINTMENT_TOPIC, message);
        System.out.println("Published to Kafka: " + message);
    }

    @EventListener
    public void handleAppointmentRescheduled(AppointmentRescheduledEvent event) {
        String message = String.format(
            "{\"eventType\":\"AppointmentRescheduled\",\"appointmentId\":%d,\"oldDateTime\":\"%s\",\"newDateTime\":\"%s\",\"timestamp\":\"%s\"}",
            event.getAppointmentId(),
            event.getOldDateTime(),
            event.getNewDateTime(),
            event.getOccurredAt()
        );
        kafkaTemplate.send(APPOINTMENT_TOPIC, message);
        System.out.println("Published to Kafka: " + message);
    }
}