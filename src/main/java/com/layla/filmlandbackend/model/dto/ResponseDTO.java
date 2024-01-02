package com.layla.filmlandbackend.model.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ResponseDTO(
        @NotNull(message = "Cannot be empty")
        @NotBlank(message = "Cannot be empty")
        String status,
        @NotNull(message = "Cannot be empty")
        @NotBlank(message = "Cannot be empty")
        String message
) {
}
