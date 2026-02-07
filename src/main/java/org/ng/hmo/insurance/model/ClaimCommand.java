package org.ng.hmo.insurance.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ClaimCommand(
        @NotNull CareSetting careSetting,
        @NotNull @Valid Patient patient,
        @NotNull @Valid Coverage coverage,
        @NotNull @Valid Provider provider,
        @NotNull @Valid Invoice invoice
) {
    public enum CareSetting { OPD, IPD }
    public enum ProviderBand { A, B, C, D }
    public record Patient(String id) {}
    public record Coverage(String id) {}
    public record Provider(String organizationId, ProviderBand band) {}
    public record Invoice(String number, String currency, @Size(min = 1) List<LineItem> lines) {
        public Invoice {
            if (currency == null) currency = "NGN";
        }
    }
    public record LineItem(String code, String description, @NotNull Double quantity, @NotNull Double unitPrice) {}
}
