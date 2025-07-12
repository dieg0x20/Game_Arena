package com.unifucamp.gamearena.ultils;

import com.unifucamp.gamearena.entity.Role;
import com.unifucamp.gamearena.entity.User;
import com.unifucamp.gamearena.enums.RoleName;
import com.unifucamp.gamearena.repository.UserRepository;
import com.unifucamp.gamearena.security.SecurityConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AdminUserInitializer implements ApplicationRunner {

    private final UserRepository userRepository;

    private final SecurityConfiguration securityConfiguration;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.senha}")
    private String adminSenha;

    public AdminUserInitializer(UserRepository userRepository, SecurityConfiguration securityConfiguration) {
        this.userRepository = userRepository;
        this.securityConfiguration = securityConfiguration;
    }

    @Override
    public void run(ApplicationArguments args) {
        Optional<User> optionalUser = userRepository.findByEmail(adminEmail);

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            userRepository.save(existingUser);

        } else {
            try {
                if (adminSenha == null || adminEmail == null) {
                    throw new RuntimeException("Credenciais de administrador estão nulas");
                }

                User newUser = User.builder()
                        .email(adminEmail)
                        .password(securityConfiguration.passwordEncoder().encode(adminSenha))
                        .roles(List.of(Role.builder().name(RoleName.ROLE_ADMIN).build()))
                        .build();

                userRepository.save(newUser);
                System.out.println("Usuário administrador criado.");

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }
}

