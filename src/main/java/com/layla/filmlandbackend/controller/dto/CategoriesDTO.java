package com.layla.filmlandbackend.controller.dto;

import java.util.Set;

public record CategoriesDTO(
        Set<CategoryDTO> availableCategories,
        Set<SubscriptionDTO> subscribedCategories
) {
}
