package com.unifucamp.gamearena.controller.dto;

import com.unifucamp.gamearena.entity.Role;

import java.util.List;

public record RecoveryUserDto(
        Long id,
        String email,
        List<Role> roles
) {
}
