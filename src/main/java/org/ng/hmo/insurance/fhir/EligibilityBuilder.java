package org.ng.hmo.insurance.fhir;

import org.ng.hmo.insurance.model.EligibilityCommand;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EligibilityBuilder {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

    public static String buildEligibilityRequest(EligibilityCommand cmd) {
        var today = LocalDate.now();
        var tomorrow = today.plusDays(1);

        return """
                {
                  "resourceType": "CoverageEligibilityRequest",
                  "status": "active",
                  "priority": { "coding": [ { "system": "http://hl7.org/fhir/us/davinci-pdex/CodeSystem/Modifiers", "code": "stat" } ] },
                  "purpose": [ "validation" ],
                  "patient": { "reference": "Patient/%s" },
                  "created": "%s",
                  "insurer": { "display": "openIMIS" },
                  "provider": { "reference": "Organization/%s" },
                  "insurance": [ {
                    "focal": true,
                    "coverage": { "reference": "Coverage/%s" }
                  } ],
                  "item": [ {
                    "category": { "coding": [ { "system": "http://snomed.info/sct", "code": "76881009", "display": "General medicine" } ] }
                  } ]
                }
                """.formatted(
                cmd.patient().id(),
                DATE_FORMAT.format(today),
                cmd.provider().organizationId(),
                cmd.coverage().id()
        );
    }
}
