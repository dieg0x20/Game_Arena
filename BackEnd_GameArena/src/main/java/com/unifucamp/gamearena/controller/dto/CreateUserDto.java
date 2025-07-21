package com.unifucamp.gamearena.controller.dto;

import com.unifucamp.gamearena.enums.RoleName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CreateUserDto(
        @NotBlank
        @Email
        String email,

        @NotBlank
        @Length(min = 6, max = 20)
        String password,

        @NotNull
        RoleName role
) {
}