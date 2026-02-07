package org.ng.hmo.insurance.web;

import org.ng.hmo.insurance.model.ClaimCommand;
import org.ng.hmo.insurance.model.ClaimStatusDto;
import org.ng.hmo.insurance.service.ClaimsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/claims")
public class ClaimsController {

    private final ClaimsService claimsService;

    public ClaimsController(ClaimsService claimsService) {
        this.claimsService = claimsService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> submit(@Valid @RequestBody ClaimCommand cmd) {
        var claimId = claimsService.submit(cmd);
        return ResponseEntity.accepted().body(Map.of("claimId", claimId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClaimStatusDto> status(@PathVariable String id) {
        return ResponseEntity.ok(claimsService.status(id));
    }
}
