package com.samika.quiz_api;

import com.samika.quiz_api.domain.User;
import com.samika.quiz_api.domain.User.Role;
import com.samika.quiz_api.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        var exists = userRepository.findByUsername("admin").isPresent();
        if (exists) return;

        var admin = new User();
        admin.setFirstName("System");
        admin.setLastName("Admin");
        admin.setEmail("admin@example.com");
        admin.setUsername("admin");

        // ✅ encode the password (NO {noop} here)
        admin.setPasswordHash(passwordEncoder.encode("admin123"));

        admin.setRole(Role.ADMIN);
        admin.setCreatedAt(Instant.now());

        userRepository.save(admin);
        System.out.println("✅ Admin user created: admin / admin123");
    }
}
