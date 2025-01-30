package org.workshop.task_management.internal.server.use_case;

import org.springframework.stereotype.Service;
import org.workshop.task_management.internal.server.domain.entities.users.Login;
import org.workshop.task_management.internal.server.domain.entities.users.Token;
import org.workshop.task_management.internal.server.domain.entities.users.Users;
import org.workshop.task_management.internal.server.domain.repositories.UsersRepository;
import org.workshop.task_management.internal.server.domain.use_case.UsersUseCase;
import org.workshop.task_management.pkg.exceptions.UnAuthorization;
import org.workshop.task_management.pkg.middleware.security.JwtUtil;

import static org.workshop.task_management.pkg.utils.Helper.verifyPassword;

@Service
public class UsersUseCaseImpl implements UsersUseCase {

    private final UsersRepository usersRepository;
    private final JwtUtil jwtUtil;

    public UsersUseCaseImpl(UsersRepository usersRepository, JwtUtil jwtUtil) {
        this.usersRepository = usersRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Token login(Login payload) {
        
        Users user = usersRepository.getUser(payload.getUsername());
        boolean verify = verifyPassword(payload.getPassword(), user.getPassword());
        if (!verify) {
            throw new UnAuthorization("invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getId().toString()); // ใช้ user.getId() ในการสร้าง Token
        return new Token(token);
    }
}
