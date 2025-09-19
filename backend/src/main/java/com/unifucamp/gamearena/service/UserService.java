package com.unifucamp.gamearena.service;

import com.unifucamp.gamearena.config.BCryptEncoder;
import com.unifucamp.gamearena.controller.dto.CreateUserDTO;
import com.unifucamp.gamearena.controller.dto.ResponseUserDTO;
import com.unifucamp.gamearena.entity.Role;
import com.unifucamp.gamearena.entity.User;
import com.unifucamp.gamearena.exception.GameArenaException;
import com.unifucamp.gamearena.exception.UserDataAlreadyExistsException;
import com.unifucamp.gamearena.exception.UserNotExistsException;
import com.unifucamp.gamearena.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptEncoder bCryptEncoder;
    @Value("${admin.email}")
    private String adminEmail;

    public UserService(UserRepository userRepository, RoleService roleService, BCryptEncoder bCryptEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.bCryptEncoder = bCryptEncoder;
    }

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public void createUser(CreateUserDTO createUserDto) {

        Optional<User> user = userRepository.findByEmail(createUserDto.email());

        if (user.isPresent()) {
            log.warn("Falha na criação de usuário. Email já em uso");
            throw new UserDataAlreadyExistsException("E-mail já em uso.");
        }

        String password = bCryptEncoder.passwordEncoder().encode(createUserDto.password());
        Role role = roleService.findRole(createUserDto.role());

        User newUser = new User(
                createUserDto.email(),
                password,
                List.of(role)
        );

        log.info("Novo usuário criado.");
        userRepository.save(newUser);
    }

    public List<ResponseUserDTO> listUsers() {

        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> new ResponseUserDTO(
                        user.getId(),
                        user.getEmail()
                ))
                .collect(Collectors.toList());
    }

    public void deleteUser(String id) {

        log.warn("Solicitação de exclusão ID: {}", id);

        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            log.warn("Usuário inválido.");
            throw new UserNotExistsException("Usuário inválido.");
        }

        if (user.get().getEmail().equals(adminEmail)) {
            log.warn("Solicitação de exclusão do administrador negada.");
            throw new GameArenaException();
        }

        log.info("Usuário deletado: {}", id);
        userRepository.deleteById(id);
    }

}

