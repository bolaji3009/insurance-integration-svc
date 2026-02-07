package org.ng.hmo.insurance.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record EligibilityCommand(
        @NotNull @Valid Patient patient,
        @NotNull @Valid Coverage coverage,
        @NotNull @Valid Provider provider
) {
    public record Patient(String id) {}
    public record Coverage(String id) {}
    public record Provider(String organizationId) {}
}
