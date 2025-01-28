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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/master-data")
public class MasterDataHandler {
    private final MasterDataUseCase masterDataUseCase;

    public MasterDataHandler(MasterDataUseCase masterDataUseCase) {
        this.masterDataUseCase = masterDataUseCase;
    }

    private <T> ResponseEntity<CustomResponse> createResponse(List<T> items, String successMessage) {
        if (items == null || items.isEmpty()) {
            return ResponseEntity.ok(CustomResponse.responseSuccess("No data found", new ArrayList<>()));
        }
        return ResponseEntity.ok(CustomResponse.responseSuccess(successMessage, items));
    }

    @GetMapping("/task-status")
    public ResponseEntity<CustomResponse> listTaskStatuses() {
        List<TaskStatus> items = masterDataUseCase.listTaskStatuses();
        return createResponse(items, "get list task status completed");

    }

    @GetMapping("/priority-levels")
    public ResponseEntity<CustomResponse> listPriorityLevels() {
        List<PriorityLevels> items = masterDataUseCase.listPriorityLevels();
        return createResponse(items, "get list priority levels completed");

    }

    @GetMapping("/role")
    public ResponseEntity<CustomResponse> listRoles() {
        List<Role> items = masterDataUseCase.listRoles();
        return createResponse(items, "get list role completed");

    }



}
