package org.workshop.task_management.internal.server.domain.repositories;

public interface HealthCheckRepository {
    boolean readiness();
}
