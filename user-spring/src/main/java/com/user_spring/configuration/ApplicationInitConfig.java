package com.user_spring.configuration;

import com.user_spring.entity.User;
import com.user_spring.enums.Role;
import com.user_spring.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${user.admin.username}")
    String ADMIN_USERNAME;

    @NonFinal
    @Value("${user.admin.password}")
    String ADMIN_PASSWORD;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername(ADMIN_USERNAME).isEmpty()) {
                var roles = new HashSet<String>();
                roles.add(Role.ADMIN.name());
                User user = User.builder()
                        .username(ADMIN_USERNAME)
                        .roles(roles)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .build();
                userRepository.save(user);
            }
        };
    }
}
