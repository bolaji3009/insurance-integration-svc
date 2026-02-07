package org.ng.hmo.insurance.web;

import org.ng.hmo.insurance.model.ProvisioningCommand;
import org.ng.hmo.insurance.service.ProvisioningService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/provisioning")
public class ProvisioningController {

    private final ProvisioningService provisioningService;

    public ProvisioningController(ProvisioningService provisioningService) {
        this.provisioningService = provisioningService;
    }

    @PostMapping("/patient")
    public ResponseEntity<Map<String, String>> provisionPatient(@Valid @RequestBody ProvisioningCommand cmd) {
        var patientUuid = provisioningService.provisionPatient(cmd);
        return ResponseEntity.ok(Map.of("patientUuid", patientUuid));
    }
}
