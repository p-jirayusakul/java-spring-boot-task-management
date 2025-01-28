package org.workshop.task_management.internal.server.use_case;

import org.springframework.stereotype.Service;
import org.workshop.task_management.internal.server.domain.repositories.HealthCheckRepository;
import org.workshop.task_management.internal.server.domain.repositories.MasterDataRepository;
import org.workshop.task_management.internal.server.domain.use_case.HealthCheckUseCase;

@Service
public class HealCheckUseCaseImpl  implements HealthCheckUseCase {
    private final HealthCheckRepository healthCheckRepository;

    public HealCheckUseCaseImpl(HealthCheckRepository healthCheckRepository) {
        this.healthCheckRepository = healthCheckRepository;
    }

    @Override
    public boolean readiness() {
        return this.healthCheckRepository.readiness();
    }
}
