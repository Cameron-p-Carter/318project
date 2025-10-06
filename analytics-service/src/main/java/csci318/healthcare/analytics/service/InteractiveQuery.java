package csci318.healthcare.analytics.service;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyWindowStore;
import org.apache.kafka.streams.state.WindowStoreIterator;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InteractiveQuery {

    private final StreamsBuilderFactoryBean factoryBean;

    public InteractiveQuery(StreamsBuilderFactoryBean factoryBean) {
        this.factoryBean = factoryBean;
    }

    public List<Map<String, Object>> getAppointmentAnalytics() {
        List<Map<String, Object>> results = new ArrayList<>();
        try {
            KafkaStreams streams = factoryBean.getKafkaStreams();
            if (streams == null) return results;

            ReadOnlyWindowStore<String, Long> store = streams.store(
                StoreQueryParameters.fromNameAndType(
                    "appointment-counts-by-type",
                    QueryableStoreTypes.windowStore()
                )
            );

            Instant now = Instant.now();
            Instant hourAgo = now.minusSeconds(3600);

            // Query for common appointment types
            String[] types = {"CONSULTATION", "FOLLOW_UP", "ROUTINE_CHECKUP", "EMERGENCY"};
            for (String type : types) {
                try (WindowStoreIterator<Long> iterator = store.fetch(type, hourAgo, now)) {
                    while (iterator.hasNext()) {
                        var record = iterator.next();
                        Map<String, Object> entry = new HashMap<>();
                        entry.put("type", type);
                        entry.put("window_start", record.key);
                        entry.put("count", record.value);
                        results.add(entry);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error querying appointment analytics: " + e.getMessage());
        }
        return results;
    }

    public List<Map<String, Object>> getInsuranceClaimsAnalytics() {
        List<Map<String, Object>> results = new ArrayList<>();
        try {
            KafkaStreams streams = factoryBean.getKafkaStreams();
            if (streams == null) return results;

            ReadOnlyWindowStore<String, Long> store = streams.store(
                StoreQueryParameters.fromNameAndType(
                    "insurance-claims-count",
                    QueryableStoreTypes.windowStore()
                )
            );

            Instant now = Instant.now();
            Instant fiveMinutesAgo = now.minusSeconds(300);

            try (WindowStoreIterator<Long> iterator = store.fetch("insurance-claims", fiveMinutesAgo, now)) {
                while (iterator.hasNext()) {
                    var record = iterator.next();
                    Map<String, Object> entry = new HashMap<>();
                    entry.put("window_start", record.key);
                    entry.put("claims_count", record.value);
                    results.add(entry);
                }
            }
        } catch (Exception e) {
            System.err.println("Error querying insurance claims analytics: " + e.getMessage());
        }
        return results;
    }
}