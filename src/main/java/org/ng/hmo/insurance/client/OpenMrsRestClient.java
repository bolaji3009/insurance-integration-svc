package org.ng.hmo.insurance.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class OpenMrsRestClient {

    private final RestClient restClient;

    public OpenMrsRestClient(
            @Value("${openmrs.restUrl}") String restUrl,
            @Value("${openmrs.user}") String user,
            @Value("${openmrs.password}") String password) {
        this.restClient = RestClient.builder()
                .baseUrl(restUrl)
                .defaultHeaders(headers -> {
                    headers.setBasicAuth(user, password);
                    headers.set("Content-Type", "application/json");
                    headers.set("Accept", "application/json");
                })
                .build();
    }

    public void postPersonAttribute(String personUuid, String attributeJson) {
        restClient.post()
                .uri("/person/{personUuid}/attribute", personUuid)
                .body(attributeJson)
                .retrieve()
                .toBodilessEntity();
    }
}
