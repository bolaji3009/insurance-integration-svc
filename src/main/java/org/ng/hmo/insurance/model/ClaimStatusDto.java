package org.ng.hmo.insurance.model;

public record ClaimStatusDto(
        String claimId,
        String status,
        String message,
        Double totalAmount,
        Double approvedAmount
) {
}
