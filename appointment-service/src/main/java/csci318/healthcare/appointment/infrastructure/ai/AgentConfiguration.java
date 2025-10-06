package csci318.healthcare.appointment.infrastructure.ai;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AgentConfiguration {

    @Value("${langchain4j.google-ai-gemini.api-key:}")
    private String apiKey;

    @Bean
    public ChatLanguageModel chatLanguageModel() {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new RuntimeException("GEMINI_API_KEY environment variable not set");
        }
        return GoogleAiGeminiChatModel.builder()
            .apiKey(apiKey)
            .modelName("gemini-1.5-flash")
            .temperature(0.3)
            .build();
    }

    @Bean
    public AppointmentAssistant appointmentAssistant(ChatLanguageModel chatModel, AppointmentTools tools) {
        return AiServices.builder(AppointmentAssistant.class)
            .chatLanguageModel(chatModel)
            .chatMemoryProvider(sessionId -> MessageWindowChatMemory.withMaxMessages(10))
            .tools(tools)
            .build();
    }
}