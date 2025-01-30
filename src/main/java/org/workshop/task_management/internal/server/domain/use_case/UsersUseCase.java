package org.workshop.task_management.internal.server.domain.use_case;

import org.workshop.task_management.internal.server.domain.entities.users.Login;
import org.workshop.task_management.internal.server.domain.entities.users.Token;

public interface UsersUseCase {
    Token login(Login payload);
}
