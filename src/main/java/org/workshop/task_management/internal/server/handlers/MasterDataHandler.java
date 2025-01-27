package org.workshop.task_management.internal.server.handlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.workshop.task_management.internal.server.domain.entities.master_data.PriorityLevels;
import org.workshop.task_management.internal.server.domain.entities.master_data.Role;
import org.workshop.task_management.internal.server.domain.entities.master_data.TaskStatus;
import org.workshop.task_management.internal.server.domain.use_case.MasterDataUseCase;
import org.workshop.task_management.pkg.middleware.response.CustomResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/master-data")
public class MasterDataHandler {
    private final MasterDataUseCase masterDataUseCase;

    public MasterDataHandler(MasterDataUseCase masterDataUseCase) {
        this.masterDataUseCase = masterDataUseCase;
    }

    @GetMapping("/task-status")
    public ResponseEntity<CustomResponse> listTaskStatuses() {
        List<TaskStatus> items = masterDataUseCase.listTaskStatuses();
        CustomResponse response = CustomResponse.responseSuccess("get list task status completed", items);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/priority-levels")
    public ResponseEntity<CustomResponse> listPriorityLevels() {
        List<PriorityLevels> items = masterDataUseCase.listPriorityLevels();
        CustomResponse response = CustomResponse.responseSuccess("get list priority levels completed", items);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/role")
    public ResponseEntity<CustomResponse> listRoles() {
        List<Role> items = masterDataUseCase.listRoles();
        CustomResponse response = CustomResponse.responseSuccess("get list role completed", items);
        return ResponseEntity.ok(response);
    }

}
