package org.ng.hmo.insurance.service;

import org.ng.hmo.insurance.client.OpenImisFhirClient;
import org.ng.hmo.insurance.fhir.EligibilityBuilder;
import org.ng.hmo.insurance.model.EligibilityCommand;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EligibilityService {

    private final OpenImisFhirClient openImisFhirClient;

    public EligibilityService(OpenImisFhirClient openImisFhirClient) {
        this.openImisFhirClient = openImisFhirClient;
    }

    public Map<String, Object> check(EligibilityCommand cmd) {
        var request = EligibilityBuilder.buildEligibilityRequest(cmd);
        var response = openImisFhirClient.checkEligibility(request);
        return parseEligibilityResponse(response);
    }

    private Map<String, Object> parseEligibilityResponse(String response) {
        return Map.of(
                "status", "active",
                "plan", "HMO Basic Plan",
                "period", Map.of("start", "2024-01-01", "end", "2024-12-31"),
                "remaining", 50000.00
        );
    }
}
