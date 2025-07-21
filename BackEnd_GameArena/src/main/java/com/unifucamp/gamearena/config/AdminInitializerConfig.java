package com.unifucamp.gamearena.config;

import com.unifucamp.gamearena.entity.Role;
import com.unifucamp.gamearena.entity.User;
import com.unifucamp.gamearena.enums.RoleName;
import com.unifucamp.gamearena.repository.UserRepository;
import com.unifucamp.gamearena.security.SecurityConfiguration;
import com.unifucamp.gamearena.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AdminInitializerConfig implements ApplicationRunner {

    private final UserRepository userRepository;
    private final SecurityConfiguration securityConfiguration;
    private final RoleService roleService;
    private static final Logger logger = LoggerFactory.getLogger(AdminInitializerConfig.class);

    @Value("${admin.email}")
    private String adminEmail;
    @Value("${admin.password}")
    private String adminPassword;

    public AdminInitializerConfig(UserRepository userRepository, SecurityConfiguration securityConfiguration, RoleService roleService) {
        this.userRepository = userRepository;
        this.securityConfiguration = securityConfiguration;
        this.roleService = roleService;
    }


    @Override
    public void run(ApplicationArguments args) {

        if (adminPassword == null || adminEmail == null) {
            throw new RuntimeException("Credenciais de administrador estão nulas");
        }

        Optional<User> optionalUser = userRepository.findByEmail(adminEmail);

        if (optionalUser.isEmpty()) {

            String password = securityConfiguration.passwordEncoder().encode(adminPassword);
            List<Role> roles = roleService.buscarRoles(RoleName.ROLE_ADMIN, RoleName.ROLE_USER);

            User user = new User(
                    adminEmail,
                    password,
                    roles
            );

            userRepository.save(user);

            optionalUser = userRepository.findByEmail(adminEmail);

            if (optionalUser.isPresent()) {

                logger.info("Usuario ADMIN adicionado com sucesso");
            }

        }
    }
}