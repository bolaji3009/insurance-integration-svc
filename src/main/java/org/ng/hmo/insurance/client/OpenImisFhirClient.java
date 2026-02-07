package org.ng.hmo.insurance.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class OpenImisFhirClient {

    private final RestClient restClient;

    public OpenImisFhirClient(
            @Value("${openimis.fhirUrl}") String fhirUrl,
            @Value("${openimis.user}") String user,
            @Value("${openimis.password}") String password) {
        this.restClient = RestClient.builder()
                .baseUrl(fhirUrl)
                .defaultHeaders(headers -> {
                    headers.setBasicAuth(user, password);
                    headers.set("Content-Type", "application/fhir+json");
                    headers.set("Accept", "application/fhir+json");
                })
                .build();
    }

    public String submitClaim(String claimJson) {
        return restClient.post()
                .uri("/Claim")
                .body(claimJson)
                .retrieve()
                .toEntity(String.class)
                .getHeaders()
                .getLocation()
                .getPath()
                .replace("/Claim/", "");
    }

    public String getClaimStatus(String claimId) {
        return restClient.get()
                .uri("/Claim/{id}", claimId)
                .retrieve()
                .body(String.class);
    }

    public String checkEligibility(String eligibilityRequestJson) {
        return restClient.post()
                .uri("/CoverageEligibilityRequest")
                .body(eligibilityRequestJson)
                .retrieve()
                .body(String.class);
    }
}
