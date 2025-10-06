package csci318.healthcare.analytics.service;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.Stores;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class StreamProcessor {

    // Use Case 1: Appointment Analytics - Count appointments per hour by type
    @Bean
    public KStream<String, String> appointmentAnalytics(StreamsBuilder builder) {
        KStream<String, String> appointmentStream = builder.stream("appointment-events");

        // Extract appointment type and count in 1-hour tumbling windows
        appointmentStream
            .filter((key, value) -> value.contains("AppointmentScheduled"))
            .map((key, value) -> {
                // Extract appointment type from event
                String type = extractField(value, "appointmentType");
                if (type == null || type.isEmpty()) {
                    type = "UNKNOWN";
                }
                return new KeyValue<>(type, "1");
            })
            .groupByKey(Grouped.with(Serdes.String(), Serdes.String()))
            .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofHours(1)))
            .count(Materialized.as(Stores.persistentWindowStore(
                "appointment-counts-by-type",
                Duration.ofHours(2),
                Duration.ofHours(1),
                false
            )))
            .toStream()
            .map((windowedKey, count) -> {
                String result = String.format(
                    "{\"type\": \"%s\", \"window_start\": \"%s\", \"window_end\": \"%s\", \"count\": %d}",
                    windowedKey.key(),
                    windowedKey.window().startTime(),
                    windowedKey.window().endTime(),
                    count
                );
                return new KeyValue<>(windowedKey.key(), result);
            })
            .to("appointment-analytics-results");

        return appointmentStream;
    }

    // Use Case 2: Financial Analytics - Insurance claims per 30-second window
    @Bean
    public KStream<String, String> financialAnalytics(StreamsBuilder builder) {
        KStream<String, String> financialStream = builder.stream("financial-events");

        // Count insurance claims in 30-second tumbling windows
        financialStream
            .filter((key, value) -> value.contains("InsuranceClaimed") || value.contains("insuranceClaimed"))
            .map((key, value) -> new KeyValue<>("insurance-claims", "1"))
            .groupByKey(Grouped.with(Serdes.String(), Serdes.String()))
            .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofSeconds(30)))
            .count(Materialized.as(Stores.persistentWindowStore(
                "insurance-claims-count",
                Duration.ofMinutes(5),
                Duration.ofSeconds(30),
                false
            )))
            .toStream()
            .map((windowedKey, count) -> {
                String result = String.format(
                    "{\"window_start\": \"%s\", \"window_end\": \"%s\", \"claims_count\": %d}",
                    windowedKey.window().startTime(),
                    windowedKey.window().endTime(),
                    count
                );
                return new KeyValue<>(windowedKey.key(), result);
            })
            .to("financial-analytics-results");

        return financialStream;
    }

    private String extractField(String json, String fieldName) {
        try {
            String pattern = "\"" + fieldName + "\":\"";
            int start = json.indexOf(pattern);
            if (start == -1) return null;
            start += pattern.length();
            int end = json.indexOf("\"", start);
            return json.substring(start, end);
        } catch (Exception e) {
            return null;
        }
    }
}