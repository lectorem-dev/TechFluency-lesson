package ru.istok.backend.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.istok.backend.user.entity.User;
import ru.istok.backend.user.entity.UserRole;
import ru.istok.backend.user.entity.UserStatus;
import ru.istok.backend.user.repository.UserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminSeedRunner implements ApplicationRunner {

    private static final String ADMIN_LOGIN = "admin";
    private static final String ADMIN_NAME = "Администратор";
    private static final String ADMIN_PASSWORD = "secret";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(@NonNull ApplicationArguments args) {
        String passwordHash = passwordEncoder.encode(ADMIN_PASSWORD);
        log.info("BCrypt-хеш для встроенного администратора: {}", passwordHash);

        User admin = userRepository.findByLogin(ADMIN_LOGIN)
                .orElseGet(User::new);

        admin.setName(ADMIN_NAME);
        admin.setLogin(ADMIN_LOGIN);
        admin.setPasswordHash(passwordHash);
        admin.setRole(UserRole.ADMIN);
        admin.setStatus(UserStatus.ACTIVE);

        userRepository.save(admin);
        log.info("Встроенный пользователь '{}' сохранен в базе данных", ADMIN_LOGIN);
    }
}
