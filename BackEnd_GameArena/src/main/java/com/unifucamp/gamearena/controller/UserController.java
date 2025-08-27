package com.unifucamp.gamearena.controller;

import com.unifucamp.gamearena.controller.dto.CreateUserDTO;
import com.unifucamp.gamearena.controller.dto.ResponseUserDTO;
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

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Valid CreateUserDTO createUserDto) {

        log.info("Nova requisição de registro de usuário.");
        userService.createUser(createUserDto);

        log.info("Novo registro efetuado com sucesso.");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ResponseUserDTO>> getAllUsers() {
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


