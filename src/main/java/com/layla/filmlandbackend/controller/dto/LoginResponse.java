package com.layla.filmlandbackend.controller.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginResponse(
        @NotNull
        @NotBlank
        String status,
        @NotNull
        @NotBlank
        String message
) {
}
