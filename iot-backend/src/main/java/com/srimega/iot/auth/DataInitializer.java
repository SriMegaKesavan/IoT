package com.srimega.iot.auth;

import com.srimega.iot.auth.entity.User;
import com.srimega.iot.auth.model.Role;
import com.srimega.iot.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initUsers(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                userRepository.save(
                        User.builder()
                                .username("admin")
                                .password(passwordEncoder.encode("admin123"))
                                .role(Role.ADMIN)
                                .enabled(true)
                                .build()
                );
            }
        };
    }
}
