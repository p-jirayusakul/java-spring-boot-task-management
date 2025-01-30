package org.workshop.task_management.internal.server.domain.repositories;

import org.workshop.task_management.internal.server.domain.entities.users.Users;

public interface UsersRepository {
    Users getUser(String username);
}
