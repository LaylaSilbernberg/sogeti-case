package com.layla.filmlandbackend.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequestDTO(
        @NotNull
        @NotBlank
        String username,
        @NotNull
        @NotBlank
        String password
) {
}
