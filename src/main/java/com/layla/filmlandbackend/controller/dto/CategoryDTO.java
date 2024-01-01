package com.layla.filmlandbackend.controller.dto;

public record CategoryDTO(
        String name,
        Integer availableContent,
        Double price
) {
}
