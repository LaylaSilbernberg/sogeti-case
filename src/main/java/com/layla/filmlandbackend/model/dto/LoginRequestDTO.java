package com.layla.filmlandbackend.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequestDTO(

        @NotNull(message = "Cannot be empty")
        @NotBlank(message = "Cannot be empty")
        @Email(message = "Must be a valid email address")
        String username,
        @NotNull(message = "Cannot be empty")
        @NotBlank(message = "Cannot be empty")
        String password
) {
}
