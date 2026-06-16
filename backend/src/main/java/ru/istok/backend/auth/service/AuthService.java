package ru.istok.backend.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.istok.backend.auth.dto.LoginRequest;
import ru.istok.backend.auth.dto.LoginResponse;
import ru.istok.backend.common.exception.ArchivedUserException;
import ru.istok.backend.common.exception.InvalidCredentialsException;
import ru.istok.backend.security.JwtService;
import ru.istok.backend.user.entity.User;
import ru.istok.backend.user.entity.UserStatus;
import ru.istok.backend.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByLogin(request.getLogin())
                .orElseThrow(InvalidCredentialsException::new);

        if (user.getStatus() == UserStatus.ARCHIVED) {
            throw new ArchivedUserException();
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        return new LoginResponse(
                jwtService.generateToken(user),
                user.getId(),
                user.getLogin(),
                user.getName(),
                user.getRole()
        );
    }
}
