package com.unifucamp.gamearena.service;

import com.unifucamp.gamearena.dto.CreateUserDto;
import com.unifucamp.gamearena.dto.LoginUserDto;
import com.unifucamp.gamearena.dto.RecoveryJwtTokenDto;
import com.unifucamp.gamearena.entity.Role;
import com.unifucamp.gamearena.entity.User;
import com.unifucamp.gamearena.repository.UserRepository;
import com.unifucamp.gamearena.security.JwtTokenService;
import com.unifucamp.gamearena.security.SecurityConfiguration;
import com.unifucamp.gamearena.security.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;
    private final SecurityConfiguration securityConfiguration;

    public UserService(UserRepository userRepository, JwtTokenService jwtTokenService, AuthenticationManager authenticationManager, SecurityConfiguration securityConfiguration) {
        this.userRepository = userRepository;
        this.jwtTokenService = jwtTokenService;
        this.authenticationManager = authenticationManager;
        this.securityConfiguration = securityConfiguration;
    }

    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }

    public void createUser(CreateUserDto createUserDto) {

        User newUser = User.builder()
                .email(createUserDto.email())
                .password(securityConfiguration.passwordEncoder().encode(createUserDto.password()))
                .roles(List.of(Role.builder().name(createUserDto.role()).build()))
                .build();

        userRepository.save(newUser);
    }

}
