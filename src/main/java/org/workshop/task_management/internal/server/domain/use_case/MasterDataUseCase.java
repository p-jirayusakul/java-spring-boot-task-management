package org.workshop.task_management.internal.server.domain.use_case;

import org.workshop.task_management.internal.server.domain.entities.master_data.PriorityLevels;
import org.workshop.task_management.internal.server.domain.entities.master_data.Role;
import org.workshop.task_management.internal.server.domain.entities.master_data.TaskStatus;

import java.util.List;

public interface MasterDataUseCase {
    List<TaskStatus> listTaskStatuses();
    List<PriorityLevels> listPriorityLevels();
    List<Role> listRoles();
}
