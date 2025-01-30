package org.workshop.task_management.internal.server.handlers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.workshop.task_management.internal.server.domain.entities.task.Task;
import org.workshop.task_management.internal.server.domain.entities.users.Login;
import org.workshop.task_management.internal.server.domain.entities.users.Token;
import org.workshop.task_management.internal.server.domain.use_case.TaskUseCase;
import org.workshop.task_management.internal.server.domain.use_case.UsersUseCase;
import org.workshop.task_management.internal.server.request.LoginRequest;
import org.workshop.task_management.internal.server.request.TaskRequest;
import org.workshop.task_management.pkg.middleware.response.CustomResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UsersHandler {
    private final UsersUseCase usersUseCase;

    public UsersHandler(UsersUseCase usersUseCase) {
        this.usersUseCase = usersUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<CustomResponse> login(@Valid @RequestBody LoginRequest loginRequest) {

        Login payload = new Login();
        payload.setUsername(loginRequest.getUsername());
        payload.setPassword(loginRequest.getPassword());

        Token items = usersUseCase.login(payload);
        return createSuccessResponse("get list task completed", items);
    }

    private ResponseEntity<CustomResponse> createSuccessResponse(String message, Object data) {
        CustomResponse response = CustomResponse.responseSuccess(message, data);
        return ResponseEntity.ok(response);
    }
}
