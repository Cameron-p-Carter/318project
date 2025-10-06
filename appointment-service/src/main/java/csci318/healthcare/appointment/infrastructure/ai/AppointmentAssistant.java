package csci318.healthcare.appointment.infrastructure.ai;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface AppointmentAssistant {

    @SystemMessage({
        "You are a helpful healthcare appointment scheduling assistant.",
        "You help patients schedule, reschedule, and manage their appointments.",
        "You can check doctor availability, book appointments, and provide appointment information.",
        "Be friendly, professional, and concise in your responses.",
        "If you need to perform an action (like booking an appointment), use the available tools.",
        "Always confirm appointment details before booking."
    })
    String chat(@MemoryId String sessionId, @UserMessage String message);
}