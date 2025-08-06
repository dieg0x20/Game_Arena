package com.unifucamp.gamearena.service;

import com.unifucamp.gamearena.controller.dto.CreateUserDto;
import com.unifucamp.gamearena.controller.dto.LoginUserDto;
import com.unifucamp.gamearena.controller.dto.RecoveryJwtTokenDto;
import com.unifucamp.gamearena.entity.Role;
import com.unifucamp.gamearena.entity.User;
import com.unifucamp.gamearena.exception.GameArenaException;
import com.unifucamp.gamearena.exception.UserDataAlreadyExistsException;
import com.unifucamp.gamearena.exception.UserNotExistsException;
import com.unifucamp.gamearena.repository.UserRepository;
import com.unifucamp.gamearena.security.JwtTokenService;
import com.unifucamp.gamearena.security.SecurityConfiguration;
import com.unifucamp.gamearena.security.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;
    private final SecurityConfiguration securityConfiguration;
    private final RoleService roleService;
    @Value("${admin.email}")
    private String adminEmail;

    public UserService(UserRepository userRepository, JwtTokenService jwtTokenService, AuthenticationManager authenticationManager, SecurityConfiguration securityConfiguration, RoleService roleService) {
        this.userRepository = userRepository;
        this.jwtTokenService = jwtTokenService;
        this.authenticationManager = authenticationManager;
        this.securityConfiguration = securityConfiguration;
        this.roleService = roleService;
    }

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }

    public void createUser(CreateUserDto createUserDto) {

        log.info("Nova tentativa de criação de usuários. {}", createUserDto.email());

        Optional<User> user = userRepository.findByEmail(createUserDto.email());

        if (user.isPresent()){
            log.warn("Este e-mail já está em uso.");
            throw new UserDataAlreadyExistsException("Este e-mail já está em uso.");
        }

        String password = securityConfiguration.passwordEncoder().encode(createUserDto.password());

        Role role = roleService.buscarRole(createUserDto.role());

        User newUser = new User(
                createUserDto.email(),
                password,
                List.of(role)
        );

        userRepository.save(newUser);

        log.info("Novo usuario {} cadastrado com sucesso", createUserDto.email());
    }

    public List<User> listUsers() {

         return userRepository.findAll();
    }

    public void deleteUser(String id) {

        log.info("Tentativa de eliminar o usuario: {}", id);

        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()){
            log.info("Usuário não encontrado na base de dados.");
            throw new UserNotExistsException("Usuário não encontrado na base de dados.");
        }

        if (user.get().getEmail().equals(adminEmail)){
            log.warn("Tentativa de deletar o administrador padrão.");
            throw new GameArenaException();
        }

        userRepository.deleteById(id);
        log.info("Usuário deletado: {}", id);
    }

}
