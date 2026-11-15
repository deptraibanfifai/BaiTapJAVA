package com.library.management.system.config;

import com.library.management.system.entity.User;
import com.library.management.system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!userRepository.existsByUsername("admin")) {
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .fullName("Quản trị viên")
                    .email("admin@library.com")
                    .enabled(true)
                    .build();
            userRepository.save(admin);
            System.out.println(" Đã tạo tài khoản Admin mặc định: admin / admin123");
        }
    }
}