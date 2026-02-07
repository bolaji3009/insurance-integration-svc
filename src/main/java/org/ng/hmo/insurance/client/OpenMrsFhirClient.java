package org.ng.hmo.insurance.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class OpenMrsFhirClient {

    private final RestClient restClient;

    public OpenMrsFhirClient(
            @Value("${openmrs.fhirUrl}") String fhirUrl,
            @Value("${openmrs.user}") String user,
            @Value("${openmrs.password}") String password) {
        this.restClient = RestClient.builder()
                .baseUrl(fhirUrl)
                .defaultHeaders(headers -> {
                    headers.setBasicAuth(user, password);
                    headers.set("Content-Type", "application/fhir+json");
                    headers.set("Accept", "application/fhir+json");
                })
                .build();
    }

    public String postPatient(String patientJson) {
        var response = restClient.post()
                .uri("/Patient")
                .body(patientJson)
                .retrieve()
                .toEntity(String.class);
        
        var location = response.getHeaders().getLocation().getPath();
        return location.replace("/ws/fhir2/R4/Patient/", "").split("/")[0];
    }
}
