package csci318.healthcare.medication.infrastructure.ai;

import csci318.healthcare.medication.domain.Prescription;
import csci318.healthcare.medication.service.MedicationService;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class MedicationTools {

    private final MedicationService medicationService;

    // Common drug interactions database (simplified for demo)
    private static final Map<String, List<String>> DRUG_INTERACTIONS = new HashMap<>();

    static {
        DRUG_INTERACTIONS.put("WARFARIN", Arrays.asList("Aspirin", "Ibuprofen", "Vitamin K"));
        DRUG_INTERACTIONS.put("METFORMIN", Arrays.asList("Alcohol", "Contrast Dye"));
        DRUG_INTERACTIONS.put("LISINOPRIL", Arrays.asList("Potassium Supplements", "NSAIDs"));
        DRUG_INTERACTIONS.put("AMOXICILLIN", Arrays.asList("Methotrexate", "Warfarin"));
        DRUG_INTERACTIONS.put("IBUPROFEN", Arrays.asList("Warfarin", "Aspirin", "Lisinopril"));
    }

    public MedicationTools(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    @Tool("Check for drug interactions between medications")
    public String checkDrugInteractions(String medication1, String medication2) {
        String med1 = medication1.toUpperCase();
        String med2 = medication2.toUpperCase();

        List<String> interactions1 = DRUG_INTERACTIONS.getOrDefault(med1, new ArrayList<>());
        List<String> interactions2 = DRUG_INTERACTIONS.getOrDefault(med2, new ArrayList<>());

        boolean hasInteraction = interactions1.stream()
            .anyMatch(drug -> drug.equalsIgnoreCase(medication2));

        if (hasInteraction) {
            return "⚠️ WARNING: Potential interaction detected between " + medication1 + " and " + medication2 +
                   ". These medications may interact. Please consult a healthcare provider before combining.";
        } else if (interactions2.stream().anyMatch(drug -> drug.equalsIgnoreCase(medication1))) {
            return "⚠️ WARNING: Potential interaction detected between " + medication1 + " and " + medication2 +
                   ". These medications may interact. Please consult a healthcare provider before combining.";
        } else {
            return "No known major interactions found between " + medication1 + " and " + medication2 +
                   ". However, always consult a healthcare provider for personalized advice.";
        }
    }

    @Tool("Get patient's current prescriptions")
    public String getPatientPrescriptions(long patientId) {
        try {
            List<Prescription> prescriptions = medicationService.getPrescriptionsByPatient(patientId);
            if (prescriptions.isEmpty()) {
                return "No active prescriptions found for patient ID: " + patientId;
            }

            StringBuilder result = new StringBuilder("Active prescriptions for patient " + patientId + ":\n");
            for (Prescription rx : prescriptions) {
                result.append("- ").append(rx.getMedicationName())
                      .append(" ").append(rx.getDosage())
                      .append(", ").append(rx.getFrequency())
                      .append(" (").append(rx.getStatus()).append(")\n");
            }
            return result.toString();
        } catch (Exception e) {
            return "Error retrieving prescriptions: " + e.getMessage();
        }
    }

    @Tool("Check all medications for a patient for potential interactions")
    public String reviewPatientMedications(long patientId) {
        try {
            List<Prescription> prescriptions = medicationService.getPrescriptionsByPatient(patientId);
            if (prescriptions.size() < 2) {
                return "Patient has fewer than 2 medications. No interactions to check.";
            }

            List<String> medications = prescriptions.stream()
                .map(p -> p.getMedicationName())
                .collect(Collectors.toList());

            StringBuilder result = new StringBuilder("Interaction Review for Patient " + patientId + ":\n\n");
            boolean foundInteraction = false;

            for (int i = 0; i < medications.size(); i++) {
                for (int j = i + 1; j < medications.size(); j++) {
                    String interaction = checkDrugInteractions(medications.get(i), medications.get(j));
                    if (interaction.contains("WARNING")) {
                        result.append(interaction).append("\n\n");
                        foundInteraction = true;
                    }
                }
            }

            if (!foundInteraction) {
                result.append("No major drug interactions detected among current medications.");
            } else {
                result.append("⚠️ IMPORTANT: Consult healthcare provider about these interactions.");
            }

            return result.toString();
        } catch (Exception e) {
            return "Error reviewing medications: " + e.getMessage();
        }
    }

    @Tool("Get medication information")
    public String getMedicationInfo(String medicationName) {
        String med = medicationName.toUpperCase();
        List<String> interactions = DRUG_INTERACTIONS.getOrDefault(med, new ArrayList<>());

        if (interactions.isEmpty()) {
            return medicationName + " - No major interactions in database. This is a simplified database; " +
                   "always consult a pharmacist or healthcare provider for complete information.";
        }

        return medicationName + " - Known interactions with: " + String.join(", ", interactions) +
               ". Always inform your healthcare provider of all medications you are taking.";
    }
}