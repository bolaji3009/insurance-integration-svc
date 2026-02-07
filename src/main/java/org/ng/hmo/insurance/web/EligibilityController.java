package org.ng.hmo.insurance.web;

import org.ng.hmo.insurance.model.EligibilityCommand;
import org.ng.hmo.insurance.service.EligibilityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/eligibility")
public class EligibilityController {

    private final EligibilityService eligibilityService;

    public EligibilityController(EligibilityService eligibilityService) {
        this.eligibilityService = eligibilityService;
    }

    @PostMapping("/check")
    public ResponseEntity<Map<String, Object>> check(@Valid @RequestBody EligibilityCommand cmd) {
        var summary = eligibilityService.check(cmd);
        return ResponseEntity.ok(summary);
    }
}
