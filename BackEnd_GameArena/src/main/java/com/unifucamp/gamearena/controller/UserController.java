package com.unifucamp.gamearena.controller;

import com.unifucamp.gamearena.controller.dto.CreateUserDto;
import com.unifucamp.gamearena.controller.dto.LoginUserDto;
import com.unifucamp.gamearena.controller.dto.RecoveryJwtTokenDto;
import com.unifucamp.gamearena.entity.User;
import com.unifucamp.gamearena.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService  userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/login")
    public ResponseEntity<RecoveryJwtTokenDto> authenticateUser(@RequestBody @Valid LoginUserDto loginUserDto) {

        log.info("Nova requisição de login.");
        RecoveryJwtTokenDto token = userService.authenticateUser(loginUserDto);

        log.info("Novo login efetuado com sucesso.");
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<Void> createUser(@RequestBody @Valid CreateUserDto createUserDto) {

        log.info("Nova requisição de registro de usuário.");
        userService.createUser(createUserDto);

        log.info("Novo registro efetuado com sucesso.");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<User>>  getAllUsers() {
        log.info("Nova requisição para listar usuários.");
        return ResponseEntity.ok(userService.listUsers());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        log.info("Nova Requisição para deletar um usuario.");
        userService.deleteUser(id);

        log.info("Usuário deletado com sucesso.");
        return ResponseEntity.ok().build();
    }

}

