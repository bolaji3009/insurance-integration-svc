package org.ng.hmo.insurance.fhir;

import org.ng.hmo.insurance.model.ClaimCommand;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ClaimBuilder {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

    public static String buildClaim(ClaimCommand cmd) {
        var today = LocalDate.now();
        var lines = buildLineItems(cmd);

        return """
                {
                  "resourceType": "Claim",
                  "status": "active",
                  "type": {
                    "coding": [ { "system": "http://hl7.org/fhir/us/davinci-pdex/CodeSystem/PatientClass", "code": "%s" } ]
                  },
                  "use": "claim",
                  "patient": { "reference": "Patient/%s" },
                  "created": "%s",
                  "insurer": { "display": "openIMIS" },
                  "provider": { "reference": "Organization/%s" },
                  "priority": { "coding": [ { "system": "http://hl7.org/fhir/us/davinci-pdex/CodeSystem/Modifiers", "code": "stat" } ] },
                  "payee": { "type": { "coding": [ { "system": "http://hl7.org/fhir/us/carin-bb/CodeSystem/C4BBPayeeType", "code": "provider" } ] } },
                  "coverage": [ { "reference": "Coverage/%s" } ],
                  "item": %s
                }
                """.formatted(
                cmd.careSetting(),
                cmd.patient().id(),
                DATE_FORMAT.format(today),
                cmd.provider().organizationId(),
                cmd.coverage().id(),
                lines
        );
    }

    private static String buildLineItems(ClaimCommand cmd) {
        var sb = new StringBuilder("[");

        for (int i = 0; i < cmd.invoice().lines().size(); i++) {
            var line = cmd.invoice().lines().get(i);
            sb.append("""
                    {
                      "sequence": %d,
                      "productOrService": {
                        "coding": [ { "system": "http://snomed.info/sct", "code": "%s", "display": "%s" } ]
                      },
                      "quantity": { "value": %f },
                      "unitPrice": { "value": %f, "currency": "%s" }
                    }""".formatted(i + 1, line.code(), line.description(), line.quantity(), line.unitPrice(), cmd.invoice().currency()));

            if (i < cmd.invoice().lines().size() - 1) {
                sb.append(",");
            }
        }

        sb.append("]");
        return sb.toString();
    }
}
