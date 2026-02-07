package org.ng.hmo.insurance.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProvisioningCommand(
        @NotBlank String policyNumber,
        @NotBlank String givenName,
        @NotBlank String familyName,
        @NotBlank String gender,
        @NotBlank String birthdate,
        @NotBlank String assignedHospitalUuid,
        @NotBlank String assignedFacilityAttributeTypeUuid
) {
}
