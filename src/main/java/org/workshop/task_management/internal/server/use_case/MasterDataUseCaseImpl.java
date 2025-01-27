package org.workshop.task_management.internal.server.use_case;

import org.springframework.stereotype.Service;
import org.workshop.task_management.internal.server.domain.entities.master_data.PriorityLevels;
import org.workshop.task_management.internal.server.domain.entities.master_data.Role;
import org.workshop.task_management.internal.server.domain.entities.master_data.TaskStatus;
import org.workshop.task_management.internal.server.domain.repositories.MasterDataRepository;
import org.workshop.task_management.internal.server.domain.use_case.MasterDataUseCase;

import java.util.List;

@Service
public class MasterDataUseCaseImpl implements MasterDataUseCase {

    private final MasterDataRepository masterDataRepository;

    public MasterDataUseCaseImpl(MasterDataRepository masterDataRepository) {
        this.masterDataRepository = masterDataRepository;
    }

    @Override
    public List<TaskStatus> listTaskStatuses() {
        return masterDataRepository.listTaskStatuses();
    }

    @Override
    public List<PriorityLevels> listPriorityLevels() {
        return masterDataRepository.listPriorityLevels();
    }

    @Override
    public List<Role> listRoles() {
        return masterDataRepository.listRoles();
    }
}