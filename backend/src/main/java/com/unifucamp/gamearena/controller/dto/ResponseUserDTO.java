package com.unifucamp.gamearena.controller.dto;

import com.unifucamp.gamearena.entity.Role;

import java.util.List;

public record ResponseUserDTO(
        String id,
        String email
) {
}
