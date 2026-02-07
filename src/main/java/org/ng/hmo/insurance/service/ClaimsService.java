package org.ng.hmo.insurance.service;

import org.ng.hmo.insurance.client.OpenImisFhirClient;
import org.ng.hmo.insurance.fhir.ClaimBuilder;
import org.ng.hmo.insurance.model.ClaimCommand;
import org.ng.hmo.insurance.model.ClaimStatusDto;
import org.springframework.stereotype.Service;

@Service
public class ClaimsService {

    private final OpenImisFhirClient openImisFhirClient;

    public ClaimsService(OpenImisFhirClient openImisFhirClient) {
        this.openImisFhirClient = openImisFhirClient;
    }

    public String submit(ClaimCommand cmd) {
        var claimJson = ClaimBuilder.buildClaim(cmd);
        return openImisFhirClient.submitClaim(claimJson);
    }

    public ClaimStatusDto status(String id) {
        var claim = openImisFhirClient.getClaimStatus(id);
        return parseClaimStatus(id, claim);
    }

    private ClaimStatusDto parseClaimStatus(String id, String claim) {
        return new ClaimStatusDto(
                id,
                "pending",
                "Claim under review",
                15000.00,
                0.00
        );
    }
}
