package org.workshop.task_management.internal.server.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.workshop.task_management.internal.server.domain.use_case.HealthCheckUseCase;

@RestController
@RequestMapping("/api/v1/health-check")
public class HealthCheckHandler {
    private final HealthCheckUseCase healthCheckUseCase;
    
    public HealthCheckHandler(HealthCheckUseCase healthCheckUseCase) {
        this.healthCheckUseCase = healthCheckUseCase;
    }

    @RequestMapping("/live")
    public ResponseEntity<String> liveness() {
        return ResponseEntity.ok("alive");
    }

    @RequestMapping("/ready")
    public ResponseEntity<String> readiness() {
        if (!this.healthCheckUseCase.readiness()) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("not ready");
        }
        return ResponseEntity.ok("ready");
    }
}
