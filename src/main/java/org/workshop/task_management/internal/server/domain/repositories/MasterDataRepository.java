package org.workshop.task_management.internal.server.domain.repositories;

import org.workshop.task_management.internal.server.domain.entities.master_data.PriorityLevels;
import org.workshop.task_management.internal.server.domain.entities.master_data.Role;
import org.workshop.task_management.internal.server.domain.entities.master_data.TaskStatus;

import java.util.List;

public interface MasterDataRepository {
    List<TaskStatus> listTaskStatuses();
    List<PriorityLevels> listPriorityLevels();
    List<Role> listRoles();
}
