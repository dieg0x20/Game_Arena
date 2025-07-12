package com.unifucamp.gamearena.dto;

import com.unifucamp.gamearena.enums.RoleName;

public record CreateUserDto(
        String email,
        String password,
        RoleName role
) {
}