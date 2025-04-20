package com.unifucamp.game_arena.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(
        @NotBlank(message = "Email cannot be empty") @Email(message = "Invalid email format") String email,

        @Size(min = 6, message = "Password must be at least 3 characters long") @NotBlank(message = "Password cannot be empty") String password) {
}
