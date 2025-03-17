package com.nuevospa.taskmanagement.utils;

import com.nuevospa.taskmanagement.entity.User;
import com.nuevospa.taskmanagement.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class DataLoader {

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);


    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
                User user1 = new User(null, "Juan Perez","juan@test.com", passwordEncoder.encode("password123"), Collections.emptySet());
                User user2 = new User(null, "Pablo Silva","pablo@test.com", passwordEncoder.encode("password123"), Collections.emptySet());

                userRepository.save(user1);
                userRepository.save(user2);

                logger.info("Usuarios precargados con contraseñas encriptadas.");
        };
    }
}
