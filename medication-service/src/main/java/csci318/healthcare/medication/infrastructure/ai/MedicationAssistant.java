package csci318.healthcare.medication.infrastructure.ai;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface MedicationAssistant {

    @SystemMessage({
        "You are a knowledgeable healthcare medication safety assistant.",
        "You help healthcare providers and patients understand drug interactions and medication safety.",
        "You can check for drug interactions, provide medication information, and review prescriptions.",
        "Always prioritize patient safety and provide clear warnings about potential interactions.",
        "Be professional, accurate, and emphasize the importance of consulting healthcare providers.",
        "Use the available tools to check prescriptions and drug information in the database."
    })
    String chat(@MemoryId String sessionId, @UserMessage String message);
}