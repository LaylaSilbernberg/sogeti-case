package com.layla.filmlandbackend.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SubscriptionDTO(
        @NotNull
        @NotBlank
        String name,
        @NotNull
        Integer remainingContent,
        @NotNull
        Double price,
        @NotBlank
        @NotNull
        String startDate
) {
}
